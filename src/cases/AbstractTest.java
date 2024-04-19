package cases;

import static utility.Constant.InputKeys.*;
import static utility.Constant.TestStatusKeys.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.LogStatus;
import config.Config;
import config.Config.Database;
import performance.PerformanceLogger;
import performance.RoboSteps;
import utility.Browser;
import utility.DatabaseUtils;
import utility.Exceptions;
import utility.FrameworkActions;
import utility.Log;
import utility.Report;
import utility.ResultManager;
import utility.TestResult;
import utility.Queries.Test_result;

abstract class AbstractTest extends Exceptions implements TestCases {
    
    protected WebDriver driver;
    protected Map<String, Object> input;
    
    String getTestName() {
        return getClass().getSimpleName();
    }
    
    protected void storeTestRunStatus() {
        String key = (String) input.get(TEST_CASE_NR.get());
        String portal = (String) input.get(PORTAL.get());
        String status = Report.logger.getRunStatus().toString();
        if(Report.getWarnResult() && !status.equals(FAIL.get())) {
        	status = WARN.get();
        }
        
        TestResult result = new TestResult();
        result.setPortal(portal);
        result.setStatus(status);
        
        ResultManager.results.put(key, result);
    }
    
    protected void clearCookies() {
        WebDriver driver = (WebDriver) input.get(DRIVER.get());
        String browser = (String) input.get(BROWSER.get());
        
        if (!browserIsInternetExplorer(browser) && driver != null) {
            driver.manage().deleteAllCookies();
        }
    }
    
    private boolean browserIsInternetExplorer(String browser) {
        return browser.equals("IE");
    }
    
    protected void closeBrowser() {
        this.driver = (WebDriver) input.get(DRIVER.get());
        
        try {
            if (driver != null) {
                driver.close();
                Thread.sleep(2000); // FF webdriver bug workaround
                driver.quit();
            }
        }
        catch (Exception e) {}
    }
    
    protected void killAllChromedriverProcesses() { 
        Runtime rt = Runtime.getRuntime();
        
        try {
            rt.exec("taskkill /im chromedriver.exe /f /t");
            rt.exec("taskkill /im chrome.exe /f /t");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    protected void getUrlFromConfigFile() {
        String portal = (String) input.get(PORTAL.get());
        String env = (String) input.get(ENVIRONMENT.get());
        String link = Config.Links.valueOf(portal.toUpperCase() + "_" + env.toUpperCase()).get();

        input.put(LINK.get(), link);
    }
    
    protected void navigateToUrl() {
        String browser       = input.get(BROWSER.get()).toString().toUpperCase().replaceAll("-", "_");
        String driverMode    = input.get(DRIVER_MODE.get())    != null ? input.get(DRIVER_MODE.get()).toString().toUpperCase()    : "-";
        String driverVersion = input.get(DRIVER_VERSION.get()) != null ? input.get(DRIVER_VERSION.get()).toString().toUpperCase() : "-";
        
        try {
            driver = Browser.valueOf(browser).open(driverMode, driverVersion);
        }
        catch (Exception e) {
            processException(e, driver);
        }
  
        input.put(DRIVER.get(), driver);
        driver.get((String)input.get(LINK.get()));
        //driver.manage().window().maximize();
    }
    
    protected void logTestCaseStart(String testName, String testcaseID) {
        Log.info("Running " + testName + " test case.");
        Log.info("Testcase ID: " + testcaseID);
        
        Report.logger.log(LogStatus.INFO, "<div class=\"collapsible_acc\">"
                + "Running " + testName + " test case.</div>"
                + "<div class=\"content\" style=\"display: none;\">"
                + "<p>Testcase ID: " + testcaseID 
                + "</p><p>Roborun ID: " + System.getProperty("roborun") 
                + "</p></div>");
    }
    
    protected void printAllInputParametersFromMapToLogs(String line) {
    	FrameworkActions.logMapToReportMonospace(LogStatus.INFO, input, "Input from .csv file: " + line);
    }
    
    protected void printObjectValuesToLogs(String header, String value) {
        ArrayList<String> list = new ArrayList<String>();
        list.add(FrameworkActions.cropString("(?<=\\[).+?(?=\\])", value));
        Report.addListToCollapsibleLog(header, LogStatus.INFO, list);
    }
    
    protected void insertNewTestRecordToDatabase() {       
        if (isSendingDataToDatabase()) {
        	String portal = (String) input.get(PORTAL.get());
    		String flow   = (String) input.get(FLOW.get());
            DatabaseUtils db = new DatabaseUtils(Database.ROBO); 
            String id = (db.selectValue(Test_result.GET_GLOBAL_RESULT_ID.get(), "NEXTVAL")).toString();
            input.put(GLOBAL_RESULT_ID.get(), id);
            System.setProperty(GLOBAL_RESULT_ID.get(), id);
            
            String query = String.format(Test_result.INSERT_NEW_TC.get(),
                       System.getProperty(ROBORUN.get()), 
                       id, 
                       System.getProperty(TESTSET.get()), 
                       input.get(TEST_CASE_NR.get()),
                       portal + " " + flow,
                       System.getProperty("user.name"));            
            db.insertQuery(query);
        }
    }

    protected void updateTestRecordStatusAfterTest(String status) {        
        if (isSendingDataToDatabase()) {
            DatabaseUtils db = new DatabaseUtils(Database.ROBO);           
            String query = 
                String.format(Test_result.UPDATE_TC_STATUS.get(),
                        status,
                        System.getProperty(ROBORUN.get()),
                        input.get(TEST_CASE_NR.get()));           
            db.insertQuery(query);
        }
    }
    
    protected void loadStepsToDatabase() {        
        if (isSendingDataToDatabase() && isSendingStepsToDatabase()) {
        	input.put(ALL_STEPS.get(), PerformanceLogger.getStepMap());
            new RoboSteps(input).execute();
        }
    }
    
    private boolean isSendingDataToDatabase() {
        return System.getProperty(RESULT_SWITCH.get()).equals("ON");
    }
    
    private boolean isSendingStepsToDatabase() {
        return System.getProperty(STEP_SWITCH.get()).equals("ON");
    }
    
    abstract void addParametersToMap();
}