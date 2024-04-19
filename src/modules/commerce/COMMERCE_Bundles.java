package modules.commerce;

import static pages.COMMERCE_HomePage.Bundle_Page.*;
import static utility.Constant.InputCaseSpecificKeys.*;
import static utility.Constant.InputKeys.*;
import static utility.Constant.TimedKeys.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import generic.Element;
import modules.AbstractModule;
import modules.IWebPageModule;
import objects.Bundle;
import pages.COMMERCE_HomePage;
import performance.Timed;
import utility.Report;
import utility.Validation;
import java.util.Map;

public class COMMERCE_Bundles extends AbstractModule implements IWebPageModule  {  

    private String module;
    private String bindPeriod;
    private Bundle product;
    
    private Element<?> binding;
    private Element<?> home = BUNDLE_PRODUCTS.getElement();
    private Element<?> rfsCheck = ADDRESS_CHECK_SECTION.getElement();
    private Element<?> city = CITY_TEXTBOX.getElement();
    private Element<?> street = STREET_TEXTBOX.getElement();
    private Element<?> houseNr = HOUSE_NUMBER_TEXTBOX.getElement();
    private Element<?> check = CHECK_ADDRESS_BUTTON.getElement();
    private Element<?> rfsOk = SERVICES_AVAILABLE_ON_ADDRESS_MESSAGE.getElement();
    private Element<?> price = PRODUCT_PRICE_TEXT.getElement();
    private Element<?> order = ORDER_BUTTON.getElement();
       
    public COMMERCE_Bundles(Map<String, Object> input) {
        
        this.module = getModuleName();
        this.input = input;
        this.driver = (WebDriver) input.get(DRIVER.get());
        this.product = (Bundle) input.get(PRODUCT.get());
        
        initializeElements(home, rfsCheck, city, street, houseNr, 
                check, rfsOk, price, order);
    }
    
    public void checkAvailabilityOfProductsOnAddress() {
        waitForPageLoad();
        waitForAddressCheckerSection();
        scrollDownToAddressChecker();
        fillInCity();
        fillInStreet();
        fillInHouseNumber();
        logScreenshot(driver, module);
        checkAddress();
        logScreenshot(driver, module);
    }
    
    public void selectBundle() {
        COMMERCE_HomePage page = new COMMERCE_HomePage(product.getInternet(), product.getTv());
        Element<?> bundle = page.getBundleProductLocator();
        price.setParent((By) bundle.getElementLocator());
        order.setParent((By) bundle.getElementLocator());
        initializeElements(bundle);
              
        bindPeriod = product.getBindingPeriod();
        page = new COMMERCE_HomePage(bindPeriod);
        binding = new Element<>(page.getBindingPeriodButtonLocator(), bindPeriod);
        initializeElements(binding);
        
        scrollDownToBundles();
        selectBindingPeriod();
        validatePrice();
        logScreenshot(driver, module);
        orderProduct();
    }
    
    private void selectBindingPeriod() {
        if (bindingPeriodIsPreselected()) {
            Report.logInfoMessage(String.format(PRESELECTED_MSG, bindPeriod));
        }
        else {
            clickBindingPeriodButton();
        }
        logScreenshot(driver, module);
    }
    
    private void validatePrice() {
        String actual = getDisplayedText(price);
        new Validation("Bundle Price", cropString("\\d+", actual), 
                product.getExpectedPrice()).stringEquals();
    }
      
    private boolean bindingPeriodIsPreselected() {
        return getElementTextByAttribute(binding, "class").contains("active");
    }
    
    @Timed(step = Click)
    private void checkAddress() {
        waitForElementDisplayed(check, 1);
        clickElement(check);
        waitForElementDisplayed(rfsOk, 10);
    }
    
    @Timed(step = Click)
    private void clickBindingPeriodButton() {
        clickElement(binding);
        waitForAttributeChange(binding, "class", "active", 5);
    }
    
    @Timed(step = Click)
    private void orderProduct() {
        clickElement(order);
        waitForElementDisappear(order, 10);
    }
    
    @Timed(step = Scroll)
    private void scrollDownToBundles() {
        waitForElementVisible(order, 5);
        scrollElementToMiddleOfScreen(order);
    }
    
    @Timed(step = Scroll)
    private void scrollDownToAddressChecker() {
        scrollElementToMiddleOfScreen(rfsCheck);
    }
    
    @Timed(step = Type)
    private void fillInCity() {
        waitForElementDisplayed(city, 1);
        setElementText(city, (String)input.get(CITY.get()));
    }
    
    @Timed(step = Type)
    private void fillInStreet() {
        waitForElementDisplayed(street, 1);
        setElementText(street, (String)input.get(STREET.get()));
    }
    
    @Timed(step = Type)
    private void fillInHouseNumber() {
        waitForElementDisplayed(houseNr, 1);
        setElementText(houseNr, (String)input.get(HOUSE.get()));
    }
    
    @Timed(step = Wait)
    private void waitForAddressCheckerSection() {
        waitForElementDisplayed(rfsCheck, 5); 
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
    
    private final String PRESELECTED_MSG = "Binding period %s is already preselected.";
}