package cases;

import static utility.Queries.Test_run.*;
import static utility.Constant.InputKeys.*;
import static utility.Constant.Numbering.*;
import static utility.Constant.TestStatusKeys.*;
import java.time.Instant;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import config.Config.Database;
import dataProvider.FileDataProvider;
import performance.PerformanceLogger;
import utility.DatabaseUtils;
import utility.Exceptions;
import utility.ResultManager;
import utility.Log;
import utility.Report;

public class TestHandler extends Exceptions {
	
    private boolean recordCreated = false;

    @BeforeSuite
    @Parameters({ "TCName" })
    public void setupReport(String TCName) {
        try {
            Report.startReport(TCName);
        } 
        catch (Exception e) {
            processExceptionWithoutLogToReport(e);
        }
    }

    @BeforeSuite
    public void callBaseClass() {
        new ResultManager();
    }

    @BeforeSuite
    @Parameters({ "ResultSwitch" , "StepSwitch", "TCName" })
    public void setUpRoboresultSwitch(@Optional("OFF") String resultSwitch, 
    		@Optional("OFF") String stepSwitch, String tcName) {
        
        System.setProperty(TESTSET.get(), tcName);
        System.setProperty(ROBORUN.get(), "-");
        System.setProperty(RESULT_SWITCH.get(), resultSwitch);
        System.setProperty(STEP_SWITCH.get(), stepSwitch);
        
        addRoborunIdToSuite();
        if (isSendingDataToDatabase()) {
            addDataToRoborunTable();
        }
    }

    @BeforeMethod
    @Parameters({ "TCName" })
    public void startLogger(String TCName) {
        Log.startTestCase(TCName);
    }
    
    @BeforeMethod
    public void cleanupPrevoiusTestRun() {
        Log.info("Cleaning up previous testdata...");
        PerformanceLogger.clearSteps();
        Report.cleanGetWarnResult();
    }
    
    @Test(dataProvider = "File", dataProviderClass = FileDataProvider.class)
    public void handleTC(String line) {
        try {
            String[] array    = line.split("\\,", -1);
            String flow       = array[2];
            String testcaseID = array[TEST_CASE_ID_INPUT_INDEX.get()];
            System.setProperty(TEST_CASE_NR.get(), testcaseID);
            
            addTotalRoborunTable();
            
            Report.startTest(flow, testcaseID);

            TestCases tests = new TestCaseFactory().getTestCase(line);
            tests.printLogMessage(testcaseID);
            tests.runTest();
        }
        catch (Exception e) {
            processException(e);
        }
    }

    private void addRoborunIdToSuite() {
        generateRoborunID();
    }
    
    private void addTotalRoborunTable() {
        if (!recordCreated && isSendingDataToDatabase()) {
            int total = new FileDataProvider().getNumberOfProcessedLines();
            
            DatabaseUtils db = new DatabaseUtils(Database.ROBO);
            db.insertQuery(String.format(UPDATE_TOTAL.get(), 
                    total, 
                    getRoborunId())); 
            
            recordCreated = true;
        }
    }
    
    private boolean isSendingDataToDatabase() {
        return System.getProperty(RESULT_SWITCH.get()).equals("ON");
    }

    private void addDataToRoborunTable() {
        int total = new FileDataProvider().getNumberOfProcessedLines();
            
        DatabaseUtils db = new DatabaseUtils(Database.ROBO);
        db.insertQuery(String.format(INSERT_NEW_TESTRUN.get(), 
                getRoborunId(), 
                getTestset(), 
                System.getProperty("user.name"),
                total));     
    }
    
    private String generateRoborunID() {
        long unixTimestamp = Instant.now().toEpochMilli();
        String roborunID = Long.toString(unixTimestamp).substring(2);

        System.setProperty(ROBORUN.get(), roborunID);
        Log.info("ROBORUN ID: " + roborunID);
        return roborunID;
    }

    @AfterMethod
    @Parameters({ "TCName" })
    public void closeReporting(String TCName) {
    	String testcaseID = System.getProperty(TEST_CASE_NR.get()) != null ? System.getProperty(TEST_CASE_NR.get()) : "-";
    	
        Log.info("Running after method...");
        if(!testcaseID.equals("-")) {
            ResultManager.logGlobalRunIdAndResultIdWithStatus(System.getProperty(TEST_CASE_NR.get()), isSendingDataToDatabase());
        }
        Log.endTestCase(TCName);
        Report.finishReport();
    }

    @AfterSuite
    public void sendToRoborun() {
        Log.info("Running after suite...");

        if (isSendingDataToDatabase()) {
            int passed  = ResultManager.getNumberOfAllTestCasesInStatus(PASS.get());
            int failed  = ResultManager.getNumberOfAllTestCasesInStatus(FAIL.get());
            int warning = ResultManager.getNumberOfAllTestCasesInStatus(WARN.get());
            
            DatabaseUtils db = new DatabaseUtils(Database.ROBO);
            db.insertQuery(String.format(UPDATE_RUN_STATUS.get(), 
                    passed, 
                    failed, 
                    warning,
                    getRoborunId()));
            
            db.insertHtmlBlob(ADD_HTML_REPORT.get(), 
                    getRoborunId(), 
                    new Report().zipReport());
        }      
        System.setProperty(ROBORUN.get(), "-");
    }
    
    private String getRoborunId() {
        return System.getProperty(ROBORUN.get());
    }
    
    private String getTestset() {
        return System.getProperty(TESTSET.get());
    }
}