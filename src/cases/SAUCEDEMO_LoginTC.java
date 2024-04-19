package cases;

import static utility.Constant.InputKeys.*;
import static utility.Constant.Input_SAUCEDEMO_LoginTC.*;
import java.util.LinkedHashMap;
import org.openqa.selenium.WebDriver;
import modules.saucedemo.SAUCEDEMO_Login;
import modules.saucedemo.SAUCEDEMO_VerifyPageTitle;
import org.testng.annotations.Test;
import utility.Log;
import utility.Report;

public class SAUCEDEMO_LoginTC extends AbstractTest {
    
    private String test;
    private String line;
    
    public SAUCEDEMO_LoginTC (String line) {
       this.input = new LinkedHashMap<String, Object>();
       this.line = line;
       this.test = getTestName();
    }

    public void runTest() {
        
        try {
            addParametersToMap();
            insertNewTestRecordToDatabase();
            getUrlFromConfigFile();
            navigateToUrl();
            
            new SAUCEDEMO_Login				(input).login();
            new SAUCEDEMO_VerifyPageTitle	(input).verifyPage();
        }
        catch (Exception e) {
            processException(e, (WebDriver) input.getOrDefault(DRIVER.get(), null));
        }
        finally {
            storeTestRunStatus();
            updateTestRecordStatusAfterTest(Report.getRunStatus());
            loadStepsToDatabase();
            clearCookies();
            closeBrowser();
        }
    }
    
    @Override
    void addParametersToMap() {
        
        String[] testcaseInput = line.split("\\,", -1);
        
        input.put(PORTAL.get(),         testcaseInput[0]);
        input.put(ENVIRONMENT.get(),    testcaseInput[1]);
        input.put(FLOW.get(),           testcaseInput[2]);
        input.put(TEST_CASE_NR.get(),   testcaseInput[3]);
        input.put(BROWSER.get(),        testcaseInput[4]);
        input.put(DRIVER_MODE.get(),    testcaseInput[5]);
        input.put(DRIVER_VERSION.get(), testcaseInput[6]);
        input.put(USERNAME.get(),       testcaseInput[7]);
        input.put(PASSWORD.get(),       testcaseInput[8]);
        input.put(TITLE.get(),          testcaseInput[9]);
        
        Log.info("Starting test case: " + test);
        printAllInputParametersFromMapToLogs(line);
    }
    
    @Override
    public void printLogMessage(String caseId) {
        logTestCaseStart("Login - positive scenario", caseId);
    }
}