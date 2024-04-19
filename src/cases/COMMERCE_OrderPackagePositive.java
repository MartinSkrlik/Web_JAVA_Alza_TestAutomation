package cases;

import static utility.Constant.InputKeys.*;
import static utility.Constant.InputCaseSpecificKeys.*;
import java.util.LinkedHashMap;
import org.openqa.selenium.WebDriver;
import modules.commerce.COMMERCE_Bundles;
import modules.commerce.COMMERCE_Contact;
import modules.commerce.COMMERCE_Home;
import objects.Bundle;
import utility.Log;
import utility.Report;


public class COMMERCE_OrderPackagePositive extends AbstractTest {
    
    private String test;
    private String line;
    
    public COMMERCE_OrderPackagePositive (String line) {
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
            
            COMMERCE_Home homePage = new COMMERCE_Home(input);
            homePage.navigateToBundles();
            
            COMMERCE_Bundles bundlePage = new COMMERCE_Bundles(input);
            bundlePage.checkAvailabilityOfProductsOnAddress();
            bundlePage.selectBundle();
            
            COMMERCE_Contact contactPage = new COMMERCE_Contact(input);
            contactPage.fillInContactDetails();
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
        
        input.put(PORTAL.get(),       testcaseInput[0]);
        input.put(ENVIRONMENT.get(),  testcaseInput[1]);
        input.put(BROWSER.get(),      testcaseInput[2]);
        input.put(FLOW.get(),         testcaseInput[3]);
        input.put(TEST_CASE_NR.get(), testcaseInput[4]);      
        input.put(CITY.get(),         testcaseInput[9]);
        input.put(STREET.get(),       testcaseInput[10]);
        input.put(HOUSE.get(),        testcaseInput[11]);


        Bundle product = new Bundle();
        product.setInternet(testcaseInput[5]);
        product.setTv(testcaseInput[6]);
        product.setBindingPeriod(testcaseInput[7]);
        product.setExpectedPrice(testcaseInput[8]);
        input.put(PRODUCT.get(), product);
        
        Log.info("Starting test case: " + test);
        printAllInputParametersFromMapToLogs(line);
    }
    

    @Override
    public void printLogMessage(String caseId) {
        logTestCaseStart("Order Product - positive scenario", caseId);
    }
}