package modules.saucedemo;

import static pages.saucedemo.SAUCEDEMO_LoginPage.Login_Page.*;
import static utility.Constant.InputKeys.*;
import static utility.Constant.Input_SAUCEDEMO_LoginTC.*;
import org.openqa.selenium.WebDriver;
import generic.Element;
import modules.AbstractModule;
import modules.IWebPageModule;
import pages.saucedemo.SAUCEDEMO_LoginPage;
import utility.Report;
import utility.Validation;
import java.util.Map;

public class SAUCEDEMO_VerifyPageTitle extends AbstractModule implements IWebPageModule  {  

    private String module;
    private String title;
   
    private Element<?> usernameField = USERNAME_FIELD.getElement();
    private Element<?> errorMessage  = ERROR_MESSAGE.getElement();
    
    public SAUCEDEMO_VerifyPageTitle(Map<String, Object> input) {
        
        this.module = getModuleName();
        this.input  = input;
        this.driver = (WebDriver)input.get(DRIVER.get());
        this.title  = (String)input.get(TITLE.get()) != null ? (String)input.get(TITLE.get()) : "-";
        
        initializeElements(usernameField, errorMessage);
    }
    
    public void verifyPage() {
    	waitForPageLoad();
        verifyPageTitle();
    }
    
    private void verifyPageTitle() {
    	SAUCEDEMO_LoginPage page = new SAUCEDEMO_LoginPage("title");   	
    	Element<?> pageTitle = page.getPageTitleLocator();
    	initializeElements(pageTitle);
    	waitForElementVisible(pageTitle, 60);
    	new Validation("PAGE TITLE", getElementText(pageTitle), title).stringEquals();
        logScreenshot(driver, module);
    }
       
    private void waitForUsernameFieldDisappear() {
    	try {
            waitForElementDisappear(usernameField, 30);
    	}
    	catch(Exception e) {
            checkIfErrorAppers(e);
    	}
    }
    
    private void checkIfErrorAppers(Throwable e) {
    	if(waitIfElementAppears(errorMessage, 5)) {
    		Report.logFailureMessage("Error message appeared while trying to login! <br> Error message: " + getElementText(errorMessage));
    		processException(e, (WebDriver) input.getOrDefault(DRIVER.get(), null));
    	}
    }

	@Override
	public void waitForPageLoad() {
        waitForUsernameFieldDisappear();
        addCurrentUrlToReport(driver);
        logScreenshot(driver, module);
	}
}