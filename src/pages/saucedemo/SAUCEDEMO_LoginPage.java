package pages.saucedemo;

import org.openqa.selenium.By;
import generic.Element;
import generic.IPageObject;

public class SAUCEDEMO_LoginPage {
    
    public enum Login_Page implements IPageObject {
        
    	USERNAME_FIELD
        (By.xpath("//input[@id='user-name']")),
    	PASSWORD_FIELD 
    	(By.xpath("//input[@id='password']")),
        LOGIN_BUTTON 	
        (By.xpath("//input[@id='login-button']")),
        ERROR_MESSAGE
        (By.xpath("//h3[@data-test = 'error']")),
        PAGE_TITLE 		
        (By.xpath("//span[@class='title']")),
        ;
        
        By findBy;
        
        private Login_Page (By findBy) {
            this.findBy = findBy;
        }
        
        @Override
        public By getLocator() {
            return findBy;
        }
        
        @Override
        public Element<Login_Page> getElement(String... msg) {
            return new Element<Login_Page>(getLocator(), getDescription(msg));
        }
    }
    
    //Sample dynamic elements   
    private String value;
    
    public SAUCEDEMO_LoginPage(String value) {
        this.value = value;
    }
    
    public Element<?> getErrorMessageLocator() {
        By locator = By.xpath("//h3[@data-test='" + value + "']");
        
        return new Element<>(locator, String.format(errorMessageDescription));
    }
        
    private final String errorMessageDescription = "Error Message";
    
    public Element<?> getPageTitleLocator() {
        By locator = By.xpath("//span[@class='" + value + "']");
        
        return new Element<>(locator, String.format(pageTitleDescription));
    }
        
    private final String pageTitleDescription = "Page Title";
}