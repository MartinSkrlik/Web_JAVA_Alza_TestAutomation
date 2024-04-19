package modules.saucedemo;

import static pages.saucedemo.SAUCEDEMO_LoginPage.Login_Page.*;
import static utility.Constant.InputKeys.*;
import static utility.Constant.Input_SAUCEDEMO_LoginTC.*;
import org.openqa.selenium.WebDriver;
import generic.Element;
import modules.AbstractModule;
import modules.IWebPageModule;
import java.util.Map;

public class SAUCEDEMO_Login extends AbstractModule implements IWebPageModule  {  

    private String module;
    private String username;
    private String password;

    private Element<?> usernameField = USERNAME_FIELD.getElement();
    private Element<?> passwordField = PASSWORD_FIELD.getElement();
    private Element<?> loginButton   = LOGIN_BUTTON.getElement();
    
    public SAUCEDEMO_Login(Map<String, Object> input) {
        
        this.module   = getModuleName();
        this.input    = input;
        this.driver   = (WebDriver)input.get(DRIVER.get());
        this.username = (String)input.get(USERNAME.get()) != null ? (String)input.get(USERNAME.get()) : "-";     
        this.password = (String)input.get(PASSWORD.get()) != null ? (String)input.get(PASSWORD.get()) : "-";
        
        initializeElements(usernameField, passwordField, loginButton);
    }
    
    public void login() {
        waitForPageLoad();
        loginWithUsernameandPassword();
    }
    
    private void loginWithUsernameandPassword() {
    	setElementText(usernameField, username);
        setElementText(passwordField, password);
        logScreenshot(driver, module);
    	clickElement(loginButton);
    }
    
    private void waitForLoginPage() {
        waitForElementVisible(usernameField, 30); 
    }

    @Override
    public void waitForPageLoad() {
        waitForLoginPage();
        addCurrentUrlToReport(driver);
        logScreenshot(driver, module);
    }
}