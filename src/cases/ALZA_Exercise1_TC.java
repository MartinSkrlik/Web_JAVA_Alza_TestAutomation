package cases;

import modules.alza.ALZA_EXERCISE;
import org.openqa.selenium.WebDriver;
import utility.Log;
import utility.Report;
import java.util.LinkedHashMap;
import static utility.Constant.InputKeys.*;
import static utility.Constant.Input_ALZA_TC.*;
public class ALZA_Exercise1_TC extends AbstractTest {

    private String test;
    private String line;

    public ALZA_Exercise1_TC(String line) {
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
                new ALZA_EXERCISE(input).SearchForPhoneAndSelectTopRated();
                new ALZA_EXERCISE(input).ChooseProductAndRememberNameAndPrice();
                new ALZA_EXERCISE(input).ClickBuy();
                new ALZA_EXERCISE(input).VerifyProductNameAndProceedButton();
                new ALZA_EXERCISE(input).ClickProceedToCheckout();
                new ALZA_EXERCISE(input).VerifyTabModelNameAndPriceToPay();
                new ALZA_EXERCISE(input).ClickContinue();
                new ALZA_EXERCISE(input).VerifyTabAndModelName();
                new ALZA_EXERCISE(input).SelectBratislavaMainShop();
                new ALZA_EXERCISE(input).VerifyTitleCheckboxAndPrice();
                new ALZA_EXERCISE(input).SelectConfirm();
                new ALZA_EXERCISE(input).VerifyTabAndModelName();
                new ALZA_EXERCISE(input).VerifyMainShopIsSelected();
                new ALZA_EXERCISE(input).VerifyModelNameIsVisible();
                new ALZA_EXERCISE(input).VerifyDispatchPriceIsVisible();
                new ALZA_EXERCISE(input).SelectPaymentMethod();
                new ALZA_EXERCISE(input).VerifyCreditCardPaymentIsForFree();
                new ALZA_EXERCISE(input).SelectContinue();
                new ALZA_EXERCISE(input).VerifyTabPaymentIsPresent();
                new ALZA_EXERCISE(input).VerifyModelNameIsVisible();
                new ALZA_EXERCISE(input).VerifyProductPrice();
                new ALZA_EXERCISE(input).VerifyDeliveryMethod();
                new ALZA_EXERCISE(input).VerifyDispatchPriceIsVisible();
                new ALZA_EXERCISE(input).VerifyPaymentMethod();
                new ALZA_EXERCISE(input).VerifyCreditCardPaymentIsForFree();
                new ALZA_EXERCISE(input).VerifyTotalPrice();

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
        input.put(TITLE.get(),          testcaseInput[8]);
        input.put(PHONE_NAME.get(),     testcaseInput[9]);
        input.put(TEXT.get(),           testcaseInput[10]);
        input.put(FULL_NAME.get(),      testcaseInput[11]);
        input.put(POP_UP_TITLE.get(),   testcaseInput[12]);
        input.put(CHECKBOX_TITLE.get(), testcaseInput[13]);
        input.put(DISPATCH_PRICE.get(), testcaseInput[14]);
        input.put(DELIVRY_METHOD.get(), testcaseInput[15]);
        input.put(PAYMENT_METHOD.get(), testcaseInput[16]);
        input.put(MODEL_PRICE.get(),    testcaseInput[17]);
        input.put(TOTAL_PRICE.get(),    testcaseInput[18]);

        Log.info("Starting test case: " + test);
        printAllInputParametersFromMapToLogs(line);
    }

    @Override
    public void printLogMessage(String caseId) {
        logTestCaseStart("Rahul Exercise1 - scenario", caseId);
    }
}