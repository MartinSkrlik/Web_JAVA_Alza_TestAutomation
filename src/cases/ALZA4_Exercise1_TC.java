package cases;


import modules.alza.ALZA4_EXERCISE;
import org.openqa.selenium.WebDriver;
import utility.Log;
import utility.Report;

import java.util.LinkedHashMap;

import static utility.Constant.InputKeys.*;
import static utility.Constant.Input_ALZA4_TC.*;

public class ALZA4_Exercise1_TC extends AbstractTest {

    private String test;
    private String line;

    public ALZA4_Exercise1_TC(String line) {
       this.input = new LinkedHashMap<>();
       this.line = line;
       this.test = getTestName();
    }

    public void runTest() {

        try {
            addParametersToMap();
            insertNewTestRecordToDatabase();
            getUrlFromConfigFile();

            navigateToUrl(); // 1. navigate to page, 2., maximize
                new ALZA4_EXERCISE(input).AcceptPopUp();
                new ALZA4_EXERCISE(input).OpenCart();
                new ALZA4_EXERCISE(input).AddFirstItem();
                new ALZA4_EXERCISE(input).AddSecondItem();
                new ALZA4_EXERCISE(input).AddThirdItem();
                new ALZA4_EXERCISE(input).GetPricesOfProduct();
                new ALZA4_EXERCISE(input).RemoveOneProduct();
                new ALZA4_EXERCISE(input).ComparePriceofProduct();
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
        for (int i = 0; i < testcaseInput.length; i++){
            testcaseInput[i] = testcaseInput[i].replaceAll(";;",",");
        }

        input.put(PORTAL.get(),         testcaseInput[0]);
        input.put(ENVIRONMENT.get(),    testcaseInput[1]);
        input.put(FLOW.get(),           testcaseInput[2]);
        input.put(TEST_CASE_NR.get(),   testcaseInput[3]);
        input.put(BROWSER.get(),        testcaseInput[4]);
        input.put(DRIVER_MODE.get(),    testcaseInput[5]);
        input.put(DRIVER_VERSION.get(), testcaseInput[6]);
        input.put(PRODUCT_TYPE1.get(),  testcaseInput[7]);
        input.put(PRODUCT_TYPE2.get(),  testcaseInput[8]);
        input.put(PRODUCT_TYPE3.get(),  testcaseInput[9]);
        input.put(PRODUCT_REMOVED.get(),testcaseInput[10]);

        Log.info("Starting test case: " + test);
        printAllInputParametersFromMapToLogs(line);
    }

    @Override
    public void printLogMessage(String caseId) {
        logTestCaseStart("Alza4 Exercise1 - scenario", caseId);
    }
}