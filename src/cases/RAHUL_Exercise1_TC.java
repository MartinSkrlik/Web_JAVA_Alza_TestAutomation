package cases;

import modules.rahul.RAHUL_EXERCISE;
import org.openqa.selenium.WebDriver;
import utility.Log;
import utility.Report;

import java.util.LinkedHashMap;

import static utility.Constant.InputKeys.*;
import static utility.Constant.Input_RAHUL_TC.*;

public class RAHUL_Exercise1_TC extends AbstractTest {

    private String test;
    private String line;

    public RAHUL_Exercise1_TC(String line) {
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
//                new RAHUL_EXERCISE(input).verifyPage(); // 3. verify title is on page, 4. verify title text,
//                new RAHUL_EXERCISE(input).clickAndVerifyRadioButton(); // 5. click radio btn, 6. verify radio btn selected
//                new RAHUL_EXERCISE(input).setTextAndVerify(); // 7. set text, 8. verify text is selected
//                new RAHUL_EXERCISE(input).selectDropDownAndVerify(); // 9. select from dropdown option, 10. verify option is selected
//                new RAHUL_EXERCISE(input).selectCheckBoxAndVerify(); // 11. select from checkbox, 12. verify checkbox is selected
//                new RAHUL_EXERCISE(input).openAndVerifyWindowExample(); // 13. open new window, 14. switch to window, 15. do screenshot 16. verify title, 17. close window
//                new RAHUL_EXERCISE(input).openAndVerifyTabExample(); // 18. open new tab, 19. switch to tab, 20. verify tab title, 21. do screenshot, 22. close tab
//                new RAHUL_EXERCISE(input).enterAlertNameAndVerify(); // 23. enter your name, 24. click alert, 25. verify alert, 26. do screenshot, 27. accept alert
//                new RAHUL_EXERCISE(input).enterConfirmNameAndVerify(); // 28. enter again your name, 29. click confirm, 30. verify alert, 31 do screenshot, 32 cancel alert
//                new RAHUL_EXERCISE(input).verifyInputAndClickHide(); // 33. verify input is displayed 34. hide and verify again
                new RAHUL_EXERCISE(input).countAllrowsInTable(); //35. covunt all rows in example table
                new RAHUL_EXERCISE(input).verifyEmptyRowInTable(); //36. verify in fixed table is no empty row
                new RAHUL_EXERCISE(input).printSpecificRow(); //37. print specific row
//                new RAHUL_EXERCISE(input).hoverMouseOverHoverButton(); //38. hover mouse over button  39. do screenshot 40. click TOP 41. do screenshot
//                new RAHUL_EXERCISE(input).switchToFrameAndVerify(); // 42. switch to frame 43. verify frame title 44. close driver

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
        input.put(TITLE.get(),          testcaseInput[7]);
        input.put(RADIO.get(),          testcaseInput[8]);
        input.put(COUNTRY.get(),        testcaseInput[9]);
        input.put(OPTION.get(),         testcaseInput[10]);
        input.put(CHECKBOX.get(),       testcaseInput[11]);
        input.put(WINDOW_TITLE.get(),   testcaseInput[12]);
        input.put(TAB_TITLE.get(),      testcaseInput[13]);
        input.put(NAME.get(),           testcaseInput[14]);
        input.put(ALERT.get(),          testcaseInput[15]);
        input.put(CONFIRM_ALERT.get(),  testcaseInput[16]);
        input.put(NUMBER_OF_ROWS.get(), testcaseInput[17]);

        Log.info("Starting test case: " + test);
        printAllInputParametersFromMapToLogs(line);
    }
    
    @Override
    public void printLogMessage(String caseId) {
        logTestCaseStart("Rahul Exercise1 - scenario", caseId);
    }
}