package modules.commerce;

import static pages.COMMERCE_HomePage.Home_Page.*;
import static utility.Constant.TimedKeys.*;
import org.openqa.selenium.WebDriver;
import generic.Element;
import modules.AbstractModule;
import modules.IWebPageModule;
import performance.Timed;
import java.util.Map;

public class COMMERCE_Home extends AbstractModule implements IWebPageModule  {  

    private String module;

    private Element<?> home = HOME_PAGE_HEADER.getElement();
    private Element<?> bundle = BUNDLE_TAB.getElement();
    
    public COMMERCE_Home(Map<String, Object> input) {
        
        this.module = getModuleName();
        this.input = input;
        this.driver = (WebDriver) input.get("driver");
        
        initializeElements(home, bundle);
    }
    
    public void navigateToBundles() {
        waitForPageLoad();
        clickOnBundleTab();
        waitForBundleTabSelected();
        logScreenshot(driver, module);
    }
    
    @Timed(step = Click)
    private void clickOnBundleTab() {
        waitForElementDisplayed(bundle, 10);
        clickElement(bundle);
    }
    
    @Timed(step = Wait)
    private void waitForBundleTabSelected() {
        waitForAttributeChange(bundle, "class", "active", 5);
    }

    @Timed(step = Wait)
    private void waitForHomePage() {
        waitForElementVisible(home, 20); 
    }

    @Override
    public void waitForPageLoad() {
        waitForHomePage();
        addCurrentUrlToReport(driver);
        logScreenshot(driver, module);
    }
}