package cases;


import modules.alza.ALZA3_EXERCISE;
import org.openqa.selenium.WebDriver;
import utility.Log;
import utility.Report;
import java.util.LinkedHashMap;
import static utility.Constant.InputKeys.*;
import static utility.Constant.Input_ALZA3_TC.*;
public class ALZA3_Exercise1_TC extends AbstractTest {

    private String test;
    private String line;

    public ALZA3_Exercise1_TC(String line) {
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
                new ALZA3_EXERCISE(input).AcceptPopUPAndSearchForItem();
                new ALZA3_EXERCISE(input).SelectTopRated();
                new ALZA3_EXERCISE(input).ChooseFirstProductAndMemoryDetails();
                new ALZA3_EXERCISE(input).PickProductToFavourteList();
                new ALZA3_EXERCISE(input).OpenFavouriteList();
                new ALZA3_EXERCISE(input).CheckCountOfProduct();
                new ALZA3_EXERCISE(input).CompareDetails();


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
        input.put(PRODUCT_NAME.get(),   testcaseInput[7]);
        input.put(PRODUCT_TYPE.get(),   testcaseInput[8]);

        Log.info("Starting test case: " + test);
        printAllInputParametersFromMapToLogs(line);
    }

    @Override
    public void printLogMessage(String caseId) {
        logTestCaseStart("Alza3 Exercise1 - scenario", caseId);
    }
}