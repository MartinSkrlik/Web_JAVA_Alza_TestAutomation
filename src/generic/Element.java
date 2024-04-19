package generic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Page Element wrapper class:

public class Element <E extends Enum<E> & IPageObject> {
        
    private Object locator;
    private String description;
    private String key;
    private WebDriver driver;
    private By parent;
    private Class<E> enumClass;
    
    public Element(By locator, String...description) {
        this.locator = locator;
        this.description = getDescription(description);
    }
    
    public Element(WebDriver driver, By locator, String...description) {
        this.driver = driver;
        this.locator = locator;
        this.description =  getDescription(description);
        this.setDriver(driver);
    }

    public WebDriver getDriver() {
        return driver;
    }
    
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
        
    public Object getElementLocator() {
        return locator;
    }
        
    public String getName() {
        return description;
    }
        
    public void setParent(By parent) {
        this.parent = parent;
    }
        
    public By getParent() {
        return parent;
    }
     
    private String getDescriptionBody() {
        return removeUnderscores(enumClass.getSimpleName()) + " -> "
                + removeUnderscores(key.toString().toLowerCase());
    }
    
    public void setDescription(String... message) {
        String messageText = message.length > 0 ? message[0]: "";
        this.description = getDescriptionBody() + " " + messageText;
     }
    
    public String getDescription() {
        return description;
     }
    
    public String getElementPath() {
        String parentLocator = parent != null ? parent.toString(): "";
        return parentLocator + locator.toString().replaceAll("By.xpath: .", "");
    }
    
    private String getDescription(String...description) {
        return description.length > 0 ? description[0]: "";
    }
    
    private String removeUnderscores(String text) {
        return text.replace("_", " ");
    }
}