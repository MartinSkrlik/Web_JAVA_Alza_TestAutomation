package modules.commerce;

import static pages.COMMERCE_OrderPage.Contact_Page.*;
import static utility.Constant.InputCaseSpecificKeys.*;
import static utility.Constant.InputKeys.*;
import static utility.Constant.TimedKeys.*;
import org.openqa.selenium.WebDriver;
import generic.Element;
import modules.AbstractModule;
import modules.IWebPageModule;
import performance.Timed;
import utility.FrameworkActions;
import utility.Validation;
import java.util.Map;

public class COMMERCE_Contact extends AbstractModule implements IWebPageModule  {  

    private String module;

    private Element<?> home = CONTACT_INFORMATION_SECTION.getElement();
    private Element<?> name = NAME_AND_SURNAME_TEXTBOX.getElement();
    private Element<?> email = EMAIL_TEXTBOX.getElement();
    private Element<?> phone = PHONE_TEXTBOX.getElement();
    private Element<?> city = CITY_TEXTBOX.getElement();
    private Element<?> street = STREET_TEXTBOX.getElement();
    private Element<?> house = HOUSE_NUMBER_TEXTBOX.getElement();
    private Element<?> toOrder = CONTINUE_TO_ORDER_BUTTON.getElement();
    
    public COMMERCE_Contact(Map<String, Object> input) {
        
        this.module = getModuleName();
        this.input = input;
        this.driver = (WebDriver) input.get(DRIVER.get());
        
        initializeElements(home, name, email, phone, city, street, house, toOrder);
    }
    
    public void fillInContactDetails() {
        waitForPageLoad();
        fillInName();
        fillInEmail();
        sleep(500);
        fillInPhoneNumber();
        sleep(500);
        validatePrefilledAddress();
        logScreenshot(driver, module);
        continueToOrder();
        logScreenshot(driver, module);
    }
    
    private void validatePrefilledAddress() {
        new Validation("City", getElementTextByAttribute(city, "value"), input.get(CITY.get())).stringEquals();
        new Validation("Street", getElementTextByAttribute(street, "value"), input.get(STREET.get())).stringEquals();
        new Validation("House number", getElementTextByAttribute(house, "value"), input.get(HOUSE.get())).stringEquals();
    }
    
    @Timed(step = Click)
    private void continueToOrder() {
        getElement(toOrder);
        clickElementUsingJavascript(toOrder);
    }
    
    @Timed(step = Scroll)
    private void scrollToContinueToOrderButton() {
        waitForElementDisplayed(toOrder, 5);
        scrollElementToMiddleOfScreen(toOrder);
    }
    
    @Timed(step = Type)
    private void fillInName() {
        waitForElementDisplayed(name, 2);
        setElementText(name, FrameworkActions.generateFakeName());
    }
    
    @Timed(step = Type)
    private void fillInEmail() {
        waitForElementDisplayed(email, 1);
        setElementText(email, FrameworkActions.generateFakeEmail());
    }
    
    @Timed(step = Type)
    private void fillInPhoneNumber() {
        waitForElementDisplayed(phone, 1);
        setElementText(phone, FrameworkActions.generateFakeMobile());
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