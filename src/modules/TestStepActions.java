package modules;

import static org.testng.Assert.assertNotNull;
import static utility.Constant.InputKeys.ONE;
import static utility.Constant.TimedKeys.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.relevantcodes.extentreports.LogStatus;
import generic.Element;
import io.github.sukgu.Shadow;
import performance.Timed;
import utility.FrameworkActions;
import utility.Log;
import utility.PropertyFile;
import utility.Report;

/**
 * Class {@code TestStepActions} contains most common selenesse actions
 * If you create new method it must contain at least one argument.
 * 
 */

public class TestStepActions extends FrameworkActions {	
	
	String waitMultiplier = PropertyFile.getValue("Wait_Multiplier", "config.properties") !=null ? PropertyFile.getValue("Wait_Multiplier", "config.properties") : ONE.get();
	
	private boolean isShadowElement(String description) {
	    return description.toLowerCase().contains("shadowelement");
	}
	
	/**
	 * Returns specific element on the page
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @return locator - [By] - element
	 * It is able to access element in shadow-root, but in open only
	 * Shadow element can be used with methods which use element but not locator only
	 * Locator type of element must be xpath
	 * In order to operate with element in shadow root name of element must contain "SHADOWELEMENT", e.g. LOGIN_BUTTON_SHADOWELEMENT
	 */
    protected WebElement getElement(Element<?> pageElement) {
    	WebElement element = null;    	
    	String description = pageElement.getDescription();
        By locator = (By) pageElement.getElementLocator();
        WebDriver driver = pageElement.getDriver();
        if(isShadowElement(description)) {
            Shadow shadow = new Shadow(driver);
            element = shadow.findElementByXPath(locator.toString().replaceAll("By.xpath: ", ""));
        }
        else {
            element = driver.findElement(locator);            
        }
        return element;
    }
    
	/**
	 * Returns specific element on the page after it`s parent is stale.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @return locator - [By] - parent-element
	 */
    protected WebElement getNestedElement(Element<?> pageElement) {
        By locator = (By) pageElement.getElementLocator();
        By parentLocator = pageElement.getParent();
        WebDriver driver = pageElement.getDriver();
        WebElement parent = driver.findElement(parentLocator);
        checkNestedElementStale(pageElement);
        return parent.findElement(locator);
    }
     
	/**
	 * Returns 0 - n elements, based on the element locator.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @return locator - [By] - list of elements
	 *  
	 */ 
    protected List<WebElement> getElements(Element<?> pageElement) {
        By locator = (By) pageElement.getElementLocator();
        WebDriver driver = pageElement.getDriver();
        return driver.findElements(locator);
    }
    
    /**
     * 
     * @param element - [Element<?>] - page element wrapper
     * @return size - [int] - size of elements
     *  
     */      
    protected int getNumberOfElements(Element<?> pageElement) {
        return getElements(pageElement).size();
    }
    
	/**
	 * Waits until a page is fully loaded.
	 * Note: does not work with Salesforce pages.
     * @param driver - [WebDriver] - current WebDriver
     * @param maxTimeInSeconds - [int] - max. waiting time in seconds  (if constant_speed_index is entered this time will be multiplied by that number)
	 * @return result - [boolean] - result if page was loaded
	 *  
	 */ 
    @Timed(step = Wait)
    protected void waitForFullPageLoad(WebDriver driver, int maxTimeInSeconds) {
    	maxTimeInSeconds = flowSpeed(maxTimeInSeconds);
        WebDriverWait wait = new WebDriverWait(driver, maxTimeInSeconds);
        String logValue = String.valueOf(maxTimeInSeconds); 
        wait.until((ExpectedCondition<Boolean>) driver1 -> {
            boolean result = ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                if (!result) {
                	Log.info("Wait for Webpage to Load... Maximum Waiting Time: " + String.valueOf(logValue) +  " seconds.");
                }
            return result;
        });
    }

    @Timed(step = Wait)
    protected void waitForPageFullLoad(WebDriver driver, int maxTimeInSeconds) {
        maxTimeInSeconds = flowSpeed(maxTimeInSeconds);
        WebDriverWait wait = new WebDriverWait(driver, maxTimeInSeconds);
        String logValue = String.valueOf(maxTimeInSeconds);
        wait.until((ExpectedCondition<Boolean>) driver1 -> {
            boolean result = ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            if (!result) {
                Log.info("Wait for Webpage to Load... Maximum Waiting Time: " + String.valueOf(logValue) +  " seconds.");
            }
            return result;
        });
    }


    /**
     * 
     * @param driver - [WebDriver] - current WebDriver
     *  
     */   
    @Timed(step = Click)
    protected void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
    }
 

    /**
     * Scrolls specific element to view (top of screen)
     * @param pageElement - [Element<?>] - page element wrapper
     * 
     */
    @Timed(step = Scroll)
    protected void scrollElementIntoView(Element<?> pageElement) {
    	Log.info("Scroll Element into View");
        WebDriver driver = pageElement.getDriver();
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);    
        scrollElementIntoView(driver, element);
    }
    
    /**
     * Scrolls specific element to view (top of screen)
     * @param driver - [WebDriver] - current WebDriver
     * @param pageElement - [WebElement] - page element wrapper
     * 
     */
    protected void scrollElementIntoView(WebDriver driver, WebElement pageElement) {
        JavascriptExecutor javascript = (JavascriptExecutor)driver;
        javascript.executeScript("arguments[0].scrollIntoView(true)", pageElement);
    }
    
    /**
     * Scrolls specific element to middle of the screen
     * @param pageElement - [Element<?>] - page element wrapper
     * 
     */    
    @Timed(step = Scroll)
    protected void scrollElementToMiddleOfScreen(Element<?> pageElement) {
       WebDriver driver = pageElement.getDriver();
       WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);      
       String scroll = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                     + "var elementTop = arguments[0].getBoundingClientRect().top;"
                     + "window.scrollBy(0, elementTop-(viewPortHeight/2));";
       ((JavascriptExecutor) driver).executeScript(scroll, element);
   }

	/**
	 * Scrolls to bottom of page
	 * @param driver - [WebDriver] - current WebDriver
	 * 
	 */   
    @Timed(step = Scroll)
    protected void scrollPageIntoBottom(WebDriver driver) {
    	Log.info("Scroll Page into Bottom");
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
    }
    
	/**
	 * Moves focus to a specific element. If html/body is the element xpath, the whole page will be focused instead.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * 
	 */
    protected void setFocusToElement(Element<?> pageElement) {
    	Log.info("Set Focus to Element");
        WebDriver driver = pageElement.getDriver();
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);
        
        JavascriptExecutor javascript = (JavascriptExecutor)driver;
        javascript.executeScript("arguments[0].focus();", element);
    }
      
	/**
	 * Loses focus/blurs active element.
	 * @param driver - [WebDriver] - current WebDriver
	 * 
	 */                           
    protected void blurFocusOfActiveElement(WebDriver driver) {
    	Log.info("Blur Focus of Active Element");
        ((JavascriptExecutor)driver).executeScript("!!document.activeElement ? document.activeElement.blur() : 0");
    }
    
	/**
	 * Clicks on element, creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * 
	 */
    @Timed(step = Click)
    protected void clickElement(Element<?> pageElement) {
		Report.generateLogAction(pageElement, "Click Element");
        checkElementStale(pageElement);
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);        
        assertNotNull(element);
        element.click(); 
    }
    
    /**
 	* DoubleClicks on element.
 	* Creates a record in logger and report.
 	* @param pageElement - [Element<?>] - page element wrapper
 	* 
 	*/
    @Timed(step = Click)
    public void doubleClickElement(Element<?> pageElement) {
    	Report.generateLogAction(pageElement, "doubleClickElement");
    	WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);   
    	WebDriver driver = pageElement.getDriver();
        assertNotNull(element);
        Actions action = new Actions(driver);
        action.doubleClick(element).perform();      
    }
    
	/**
	 * Clicks on element using javascript, creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * 
	 */
    @Timed(step = Click)
    protected void clickElementUsingJavascript(Element<?> pageElement) {
    	Report.generateLogAction(pageElement, "Click Element Using JavaScript");
        WebDriver driver = pageElement.getDriver();
        checkElementStale(pageElement);
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);
        assertNotNull(element);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
    }
    
    /**
	 * 
	 * @param pageElement - [Element<?>] - page element wrapper
	 * 
	 */
    @Timed(step = Click)
    protected void clickElementUsingEnterButton(Element<?> pageElement) {
        Report.generateLogAction(pageElement, "Click Element Using Enter");        
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);
        assertNotNull(element);
        element.sendKeys(Keys.ENTER);
    }

	/**
	 * Clicks on element and waits, creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param timeInMilliseconds - [int] - wait time in milliseconds. (if constant_speed_index is entered this time will be multiplied by that number)
	 * 
	 */
    @Timed(step = Click)
    protected void clickElementAndWait(Element<?> pageElement, int timeInMilliseconds) {
    	timeInMilliseconds = flowSpeed(timeInMilliseconds);
        Report.generateLogWaitAction(pageElement, "Click Element and Wait", timeInMilliseconds);
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);
        assertNotNull(element);
        element.click(); 
        sleep(timeInMilliseconds);
    }
    
	/**
	 * Clicks on element using javascript and waits, creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param timeInMilliseconds - [int] - wait time in milliseconds. (if constant_speed_index is entered this time will be multiplied by that number)
	 * 
	 */
    @Timed(step = Click)
    protected void clickElementUsingJavascriptAndWait(Element<?> pageElement, int timeInMilliseconds) {
    	timeInMilliseconds = flowSpeed(timeInMilliseconds);
        Report.generateLogWaitAction(pageElement, "Click Element Using JavaScript and Wait", timeInMilliseconds);
        WebDriver driver = pageElement.getDriver();
        checkElementStale(pageElement);
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);
        assertNotNull(element);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        sleep(timeInMilliseconds);
    }
    
    /**
	 * 
	 * @param pageElement - [Element<?>] - page element wrapper
	 * 
	 */
    @Timed(step = Click)
    protected void scrollAndClickElement(Element<?> pageElement) {
        WebDriver driver = pageElement.getDriver();
        Report.generateLogAction(pageElement, "Scroll and Click Element");       
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);
        assertNotNull(element);
        scrollElementIntoView(driver, element);
        element.click(); 
    }
    
    /**
 	* Wait for element to be clickable
 	* Creates a record in logger and report.
 	* @param pageElement - [Element<?>] - page element wrapper
 	* @param maxTimeInSeconds - [int] - max. waiting time in seconds. (if constant_speed_index is entered this time will be multiplied by that number)
 	* 
 	*/
    @Timed(step = Wait)
    public void waitForElementClickable(Element<?> pageElement, int maxTimeInSeconds) {
    	maxTimeInSeconds = flowSpeed(maxTimeInSeconds);
    	WebDriver driver = pageElement.getDriver();
    	By locator = (By) pageElement.getElementLocator();
    	Report.generateSimpleMaxTimeLog(pageElement, "Wait for Web Element to be Clickable.", maxTimeInSeconds);
    	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
        		                  .withTimeout(Duration.ofSeconds(maxTimeInSeconds))
        		                  .pollingEvery(Duration.ofMillis(500))
    						      .ignoring(NoSuchElementException.class)
    						      .ignoring(StaleElementReferenceException.class)
    						      .ignoring(ElementNotInteractableException.class);
        wait.until((ExpectedCondition<Boolean>) driver1 -> {
        	driver.findElement(locator);
            return true;
        });
    }
    
	/**
	 * Blurs element, useful while working with sensitive data
	 * @param pageElement - [Element<?>] - page element wrapper
	 * 
	 */
    public void blurElement(Element<?> pageElement) {
        WebDriver driver = pageElement.getDriver();
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);
        JavascriptExecutor javascript = (JavascriptExecutor)driver;
        javascript.executeScript("arguments[0].style='filter: blur(3px);'", element);
    }
    
	/**
	 * Puts element into red rectangle.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * 
	 */ 
    protected void highlightElement(Element<?> pageElement) {
        WebDriver driver = pageElement.getDriver();
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);
        JavascriptExecutor javascript = (JavascriptExecutor)driver;
        javascript.executeScript("arguments[0].style.border='3px solid red'", element);
    }

	/**
	 * Removes the highlight from element highlighted by highlightElement method.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * 
	 */
    protected void undoHighlightElement(Element<?> pageElement) {
        WebDriver driver = pageElement.getDriver();
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);
        
        JavascriptExecutor javascript = (JavascriptExecutor)driver;
        javascript.executeScript("arguments[0].style.border=''", element);
    }
    
// Text manipulating element actions:
	/**
	 * Retrieves Attribute text of the element 
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param attribute - [String] - the attribute we want to get text from
	 * 
	 */
    protected String getElementTextByAttribute(Element<?> pageElement, String attribute) {
    	Report.generateLogFillAction(pageElement, "Get Element Text", attribute);
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);
        assertNotNull(element);
        return element.getAttribute(attribute);
    }
    
	/**
	 * Retrieves innerHtml text of the element 
	 * @param pageElement - [Element<?>] - page element wrapper
	 * 
	 */
    protected String getElementText(Element<?> pageElement) { 
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement): getElement(pageElement);
        assertNotNull(element);
        String value =  element.getAttribute("innerHTML");
        Report.generateLogFillAction(pageElement, "Get Element Text", value);
        return value;
    }
 
	/**
	 * Retrieves text visible on the webpage
	 * Creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * 
	 */
    protected String getDisplayedText(Element<?> pageElement) { 
        WebDriver driver = pageElement.getDriver();
        new WebDriverWait(driver, 10)
                .ignoring(StaleElementReferenceException.class)
                .until((WebDriver d) -> {
                    WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);
                    element.getText();
                    return true;
        });
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);
        String elementText = element.getText();
        Report.generateLogFillAction(pageElement, "Get Displayed Text", elementText);
        return elementText;
    }
    
	/**
	 * Retrieves text not visible on the webpage (the text is present in DOM)
	 * Creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * 
	 */
    protected String getHiddenTextUsingJavascript(Element<?> pageElement) {
        By locator = (By) pageElement.getElementLocator();
        WebDriver driver = pageElement.getDriver();
        WebElement element = driver.findElement(locator);
        assertNotNull(element);
        String value = (String) ((JavascriptExecutor)driver).executeScript("return arguments[0].innerText", element);
        Report.generateLogFillAction(pageElement, "Get Not Visisble Text Using Javascript", value);
        return value;
    }
    
	/**
	 * Clicks in an input field and deletes text
	 * Creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * 
	 */
    @Timed(step = Select)
    protected void clickAndClearInputField(Element<?> pageElement) {  
        Report.generateLogAction(pageElement, "Clear Element Text");    
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);       
        assertNotNull(element);
        element.click();
        sleep(500);
        element.clear();
    }

	/**
	 * Fills in an input field
	 * Creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param value - [String] - text of the element that is going to be filled
	 * 
	 */
    @Timed(step = Type)
    protected void setElementText(Element<?> pageElement, String value) {  
        Report.generateLogFillAction(pageElement, "Set Element Text", value);    
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);       
        assertNotNull(element);
        element.clear();
        element.sendKeys(value);
    }
    
	/**
	 * Fills in an input field
	 * Creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param value - [String] - text of the element that is going to be filled
	 * @param timeInMilliseconds - [int] - wait time in milliseconds. (if constant_speed_index is entered this time will be multiplied by that number)
	 * 
	 */
    @Timed(step = Type)
    protected void setElementTextAndWait(Element<?> pageElement, String value, int timeInMilliseconds) {  
    	timeInMilliseconds = flowSpeed(timeInMilliseconds);
        Report.generateLogFillAndWaitAction(pageElement, "Set Element Text", value, timeInMilliseconds);    
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);       
        assertNotNull(element);
        element.clear();
        element.sendKeys(value);
        sleep(timeInMilliseconds);
    }
    
	/**
	 * Fills in an input field
	 * Creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param value - [String] - text of the element that is going to be filled
	 * 
	 */
    @Timed(step = Type)
    protected void setElementTextNoClear(Element<?> pageElement, String value) {  
    	Report.generateLogFillAction(pageElement, "Set Element Text", value);   
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);      
        assertNotNull(element);
        element.sendKeys(value);
    }

	/**
	 * Fills in an input field
	 * Creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param value - [String] - text of the element that is going to be filled
	 * 
	 */
    @Timed(step = Type)
    protected void setElementTextAndConfirm(Element<?> pageElement, String value) {   
    	Report.generateLogFillAction(pageElement, "Set Element Text and Confirm", value); 
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);     
        assertNotNull(element);
        element.clear(); 
        element.sendKeys(value);
        element.sendKeys(Keys.RETURN);
    }
    
	/**
	 * Fills in an input field, but most characters are masked with "*" in logs/report. Used mainly for usernames.
	 * Creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param value - [String] -text of the element that is going to be filled
	 * 
	 */
    @Timed(step = Type)
    protected void setElementUnrecognizableText(Element<?> pageElement, String value) { 
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);
        String secureText = value.replaceAll("\\B[a-zA-Z0-9]", "*");
        Report.generateLogFillAction(pageElement, "Set Element Unrecognizable Text", secureText);
        assertNotNull(element);
        element.clear(); 
        element.sendKeys(value);
    }
    
	/**
	 * Fills in an input field, but masks it with "*" in logs/report. Used mainly for passwords.
	 * Creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param value - [String] -text of the element that is going to be filled
	 * 
	 */
    @Timed(step = Type)
    protected void setElementSecureText(Element<?> pageElement, String value) { 
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);
        String secureText = value.replaceAll("(?s).", "*"); 
        Report.generateLogFillAction(pageElement, "Set Element Secure Text", secureText);
        assertNotNull(element);
        element.clear(); 
        element.sendKeys(value);
    }
    
	/**
	 * Selects a value from a specific dropdown.
	 * Creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param value - [String] - text of the option that is going to be selected
	 * 
	 */
    @Timed(step = Select)
    protected void selectElementFromListByVisibleText(Element<?> pageElement, String value) {
    	Report.generateLogFillAction(pageElement, "Select Element from List by Visible Text", value);  
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);       
        assertNotNull(element);
        Select dropDown = new Select(element);
        dropDown.selectByVisibleText(value);
    }
    
	/**
	 * Selects a value from a specific dropdown.
	 * Creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param value - [String] - text of the option that is going to be selected
	 * 
	 */
    @Timed(step = Select)
    protected void selectElementFromListByValue(Element<?> pageElement, String value) {
    	Report.generateLogFillAction(pageElement, "Select Element from List by Value", value); 
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);       
        assertNotNull(element);
        Select dropDown = new Select(element);
        dropDown.selectByValue(value);
    }

	/**
	 * Selects a value from dropdown, that is actually values + links.
	 * Creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param value - [String] - text of the option that is going to be selected
	 * 
	 */
    @Timed(step = Select)
    protected void selectElementFromButtonList(Element<?> pageElement, String value) {
        WebDriver driver = pageElement.getDriver();
        Report.generateLogFillAction(pageElement, "Select Element from Button List", value);
        WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);
        assertNotNull(element);
        WebElement dropdown = driver.findElement(By.linkText(value));
        dropdown.click();
    }

// Wait actions:
	/**
	 * Thread sleep action. Use only when no other wait is effective.
	 * @param timeInMilis - [int] - wait time in miliseconds
	 * 
	 */ 
    protected void sleep(int timeInMilis) {   
        try {
            Thread.sleep(timeInMilis);
        }
        catch (Exception e) {
            Log.error("Method failed.");
            Log.error("Exception: " + org.apache.commons.lang3.exception
                      .ExceptionUtils.getStackTrace(e));
        }
    }
    
	/**
	 * Waits until Element is stale.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * 
	 */
    protected void checkElementStale(Element<?> pageElement) {
        By locator = (By) pageElement.getElementLocator();
        WebDriver driver = pageElement.getDriver();
            
        new WebDriverWait(driver, 100)
           .ignoring(StaleElementReferenceException.class)
           .until((WebDriver d) -> {
               d.findElement(locator).isEnabled();
               return true;
               });
    }
        
	/**
	 * Waits until Element is stale.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * 
	 */
    protected void checkNestedElementStale(Element<?> pageElement) {
        By locator = (By) pageElement.getElementLocator();
        WebDriver driver = pageElement.getDriver();
        WebElement parent = driver.findElement(pageElement.getParent());
            
        new WebDriverWait(driver, 100)
           .ignoring(StaleElementReferenceException.class)
           .until((WebDriver d) -> {
               parent.findElement(locator).isEnabled();
               return true;
               });
    }

	/**
	 * Waits until an element appears on page.
	 * Creates a record in logger.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param maxTimeInSeconds - [int] - max. waiting time in seconds. (if constant_speed_index is entered this time will be multiplied by that number)
	 * 
	 */
    @Timed(step = Wait)
    protected void waitForElementVisible(Element<?> pageElement, int maxTimeInSeconds) {
    	maxTimeInSeconds = flowSpeed(maxTimeInSeconds);
        WebDriver driver = pageElement.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, maxTimeInSeconds, 500);
        Report.generateLogMaxTimeAction(pageElement, "Waiting for Element Visible", maxTimeInSeconds);
        try {
            wait.until((ExpectedCondition<Boolean>) driver1 -> 
                waitConditionDom(pageElement));
        }
        catch (Exception e) {
            processException(e, driver);
        }
    }
    
	/**
	 * Waits until an element appears on page.
	 * Creates a record in logger.
	 * @param pageElement - [Element<?>] - page element wrapper 
	 * @param maxTimeInSeconds - [int] - max. waiting time in seconds. (if constant_speed_index is entered this time will be multiplied by that number)
	 * 
	 */
    @Timed(step = Wait)
    protected void waitForElementDisplayed(Element<?> pageElement, int maxTimeInSeconds) {
    	maxTimeInSeconds = flowSpeed(maxTimeInSeconds);
        WebDriver driver   = pageElement.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, maxTimeInSeconds);
        String description = pageElement.getDescription();
        
        Report.logger.log(LogStatus.INFO, "Waiting for web element to appear on webpage for max. " + maxTimeInSeconds + " seconds: " + description);
        Log.info("Waiting for element: " + pageElement.getElementPath());
        try {
            wait.until((ExpectedCondition<Boolean>) driver1 -> 
                waitConditionDisplay(pageElement));
        }
        catch (Exception e) {
            processException(e, driver);
        }
    }
    
	/**
	 * Waits until an element appears on page.
	 * Creates a record in logger.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param maxTimeInSeconds - [int] - max. waiting time in seconds. (if constant_speed_index is entered this time will be multiplied by that number)
	 * 
	 */
    @Timed(step = Wait)
	public boolean waitIfElementAppears(Element<?> pageElement, int maxTimeInSeconds ) {
    	maxTimeInSeconds = flowSpeed(maxTimeInSeconds);
		WebDriver driver   = pageElement.getDriver();
		String description = pageElement.getDescription();
		By locator         = (By)pageElement.getElementLocator();
		try {
			Log.info("Element: " + description);
			Report.generateSimpleMaxTimeLog(pageElement, "Waiting for web element to appear on webpage", maxTimeInSeconds);
	        
	        WebDriverWait wait = new WebDriverWait(driver, maxTimeInSeconds);
	        String logValue = String.valueOf(maxTimeInSeconds); 
	        wait.until((ExpectedCondition<Boolean>) driver1 -> {
	            boolean result = !driver.findElements(locator).isEmpty();
	                if (!result) {
	                    Log.info("Waiting for web element to appear on webpage... Maximum waiting time: " + String.valueOf(logValue) + " seconds.");
	                }
	            return result;
	        });
            return true;
		} 
		catch (TimeoutException ex) {
			Report.generateSimpleMaxTimeLog(pageElement, "Element didn't appear on webpage after desired time", maxTimeInSeconds);
			return false;
		}		
	}
    
	/**
	 * 
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param attribute - [String] - element attribute
	 * @param exp - [String] - changed attribute to
	 * @param maxTimeInSeconds - [int] - max. waiting time in seconds. (if constant_speed_index is entered this time will be multiplied by that number)
	 * 
	 */
    @Timed(step = Wait)
    protected void waitForAttributeChange(Element<?> pageElement, String attribute, String exp, int maxTimeInSeconds) {
    	maxTimeInSeconds = flowSpeed(maxTimeInSeconds);
        WebDriver driver = pageElement.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, maxTimeInSeconds);
        String description = pageElement.getDescription();
        
        Report.logger.log(LogStatus.INFO, "Waiting for web element`s attribute change for max. " + maxTimeInSeconds + " seconds: " + description);
        Log.info("Waiting for elements " + pageElement.getElementPath() + " attribute " + attribute + " change to: " + exp);
        
        try {
            wait.until((ExpectedCondition<Boolean>) driver1 -> 
                waitConditionAttributeChange(pageElement, attribute, exp));
        }
        catch (Exception e) {
            processException(e, driver);
        }
    }
    
    /**
	 * 
	 * @param pageElement - [Element<?>] - page element wrapper
	 * 
	 */
    private boolean waitConditionDom(Element<?> pageElement) {
        By locator = (By) pageElement.getElementLocator();
        WebDriver driver = pageElement.getDriver();
        
        
        boolean result = driver.findElements(locator).size() > 0;
        if (!result) {
            Log.info("Waiting for web element to appear on webpage. Element: " + pageElement.getDescription());
        }
        return result;
    }
    
    /**
	 * 
	 * @param pageElement - [Element<?>] - page element wrapper
	 * 
	 */
    private boolean waitConditionDisplay(Element<?> pageElement) {
        By locator = (By) pageElement.getElementLocator();
        WebDriver driver = pageElement.getDriver();
        
        boolean result = false;
        List<WebElement> elementsInDom = driver.findElements(locator);
        boolean visibleInDom = elementsInDom.size() > 0;
        
        if (!visibleInDom) {
            Log.info("Waiting for web element to appear on webpage. Element: " + pageElement.getDescription());
        }
        else {
            for (WebElement element: elementsInDom) {
                if (element.isDisplayed()) {
                    result = true;
                }
            }
        }
        return result;
    }
    
	/**
	 * 
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param attribute - [String] - element attribute
	 * @param exp - [String] - changed attribute to
	 * 
	 */
    @Timed(step = Wait)
    private boolean waitConditionAttributeChange(Element<?> pageElement, String attribute, String exp) {
        boolean result = getElementTextByAttribute(pageElement, attribute).contains(exp);
        if (!result) {
            Log.info("Waiting for web element to appear on webpage. Element: " + pageElement.getDescription());
        }
        return result;
    }
    
	/**
	 * Waits until an element disappears from page.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param maxTimeInSeconds - [int] - max. waiting time in seconds. (if constant_speed_index is entered this time will be multiplied by that number)
	 * 
	 */
    @Timed(step = Wait)
    protected void waitForElementDisappear(Element<?> pageElement, int maxTimeInSeconds) {
    	maxTimeInSeconds = flowSpeed(maxTimeInSeconds);
        By locator = (By) pageElement.getElementLocator();
        WebDriver driver = pageElement.getDriver();
        Report.generateLogMaxTimeAction(pageElement, "Waiting for web element to disappear from webpage", maxTimeInSeconds);
        WebDriverWait wait = new WebDriverWait(driver, maxTimeInSeconds);
        wait.until((ExpectedCondition<Boolean>) driver1 -> {
        boolean result = driver.findElements(locator).isEmpty();
            if (!result) {
                Log.info("Waiting for web element to disappear from webpage...");
            }
            return result;
        });
    }
     
// Alert pop-up window actions:
	/**
	 * Waits until an alert pop-up appears on page.
	 * Creates a record in logger and report. 
	 * @param driver - [WebDriver] - current WebDriver
	 * @param maxTimeInSeconds - [int] - max. waiting time in seconds. (if constant_speed_index is entered this time will be multiplied by that number)
	 * 
	 */
    @Timed(step = Wait)
    protected void waitForAlert(WebDriver driver, int maxTimeInSeconds) {
    	maxTimeInSeconds = flowSpeed(maxTimeInSeconds);
    	Report.generateSimpleMaxTimeLog("Wait For Alert", "Alert pop-up window", maxTimeInSeconds); 
        WebDriverWait wait = new WebDriverWait(driver, maxTimeInSeconds);
        wait.until(ExpectedConditions.alertIsPresent());
    }

	/**
	* Accepts alert pop-up (clicks YES/OK/Accept/...).
	* Creates a record in logger and report. 
	* @param driver - [WebDriver] - current WebDriver
	* 
	*/
    @Timed(step = Click)
    protected void acceptAlert(WebDriver driver) {
    	Report.generateSimpleLog("Accept Alert", "Accept Alert");   
        String parentWindowHandler = driver.getWindowHandle();
        driver.switchTo().alert().accept();
        driver.switchTo().window(parentWindowHandler);
    }

	/**
	 * Declines alert pop-up (clicks NO/Decline/Dismiss...).
	 * Creates a record in logger and report. 
	 * @param driver - [WebDriver] - current WebDriver
	 * 
	 */
    @Timed(step = Click)
    protected void dismissAlert(WebDriver driver) {
        Report.generateSimpleLog("Dismiss Alert", "Dismiss Alert");
        String parentWindowHandler = driver.getWindowHandle();
        driver.switchTo().alert().dismiss();
        driver.switchTo().window(parentWindowHandler);
    }

	/**
	 * Checks if a specific element is present on the page
	 * Creates a record in logger. 
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @return true - if element is present on the page / false - if element is not present on the page
	 * 
	 */
    protected boolean verifyElementIsPresent(Element<?> pageElement) { 
        List<WebElement> pageElements = getElements(pageElement);
        
        if (pageElements.size() > 0) {
            Log.info("Element is present on the webpage.");Report.logSuccessMessage("Element is present on the webpage.");
            return true; 
        } 
        else {
            Log.info("Element is not present on the webpage.");Report.logWarningMessage("Element is not present on the webpage.");
            return false;
        }
    }

	/**
	 * Checks if a specific element is selected on the page
	 * Creates a record in logger. 
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @return true - if element is selected on the page / false - if element is not selected on the page
	 * 
	 */
    protected boolean verifyIsSelected(Element<?> pageElement) { 
    	By locator = (By) pageElement.getElementLocator();
        WebDriver driver = pageElement.getDriver();
        if(driver.findElement(locator).isSelected()) {
        	Log.info("Element is Selected");Report.logSuccessMessage("Element is Selected");
        	return true;
        }
        else {
        	Log.info("Element is not Selected");Report.logFailureMessage("Element is not Selected");
        	return false;
        }
    }
    
	/**
	 * 
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param value - [String] - value of verify text
	 * @return true - if element is selected on the page / false - if element is not selected on the page
	 * 
	 */
    public boolean verifyText(Element<?> pageElement, String value) {
    	Report.generateLogFillAction(pageElement, "Verify Text", value);
    	WebElement element = pageElement.getParent() != null ? getNestedElement(pageElement) : getElement(pageElement);       
        assertNotNull(element);
        String prefilledText = element.getAttribute("value");
        
        if (prefilledText.equals(value)) {  
            Log.info("Element text matches expected value.");
            Report.logInfoMessage("Result........: Element text matches expected value.");
            return true; 
        } 
        else {
            Log.info("Element text does not match expected value.");
            Report.logInfoMessage("Result........: Element text does not match expected value.");
            return false;
        } 
    }

	/**
	 * Check if element has a specific css class
	 * Creates a record in logger and report. 
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param elementClass - [String] -class we are checking 
	 * @return true - if the value was found / false - if the value was not found
	 * 
	 */
    protected boolean elementHasClass(Element<?> element, String elementClass) {
        Report.generateSimpleLog("Element Has Class", elementClass);
        return getElement(element).getAttribute("class").contains(elementClass);
    }

// iFrame navigating actions:
	/**
	 * Switches to child iFrame
	 * Creates a record in logger and report. 
	 * @param pageElement - [Element<?>] - page element wrapper
	 * 
	 */    
    protected void switchToFrameByLocator(Element<?> pageElement) {
        By locator = (By) pageElement.getElementLocator();
        WebDriver driver = pageElement.getDriver();
        
        try {
        	Log.info("Switch to frame: " + locator);
            driver.switchTo().frame(driver.findElement(locator));
            printCurrentFrameName(driver);
        } 
        catch (NoSuchElementException exception) {
            throw exception;
        }
    }
    
	/**
	 * 
	 * Creates a record in logger and report. 
	 * @param driver - [WebDriver] - current WebDriver.
	 * 
	 */
    private void printCurrentFrameName(WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
        String currentFrame = (String) jsExecutor.executeScript("return self.name");
        Log.info("Switched to Frame: " + currentFrame);
    }

	/**
	 * Switches to parent iFrame. Use multiple times in case of nested iFrames
	 * Creates a record in logger and report. 
	 * @param driver - [WebDriver] - current WebDriver.
	 * 
	 */
    protected void switchToParentFrame(WebDriver driver) {
    	Log.info("Switch to Parent frame");
        driver.switchTo().parentFrame();
    }
    
	/**
	 * Switches out of the iFrames.
	 * Creates a record in logger and report. 
	 * @param driver - [WebDriver] - current WebDriver.
	 * 
	 */
    protected void stepOutOfAllFrames(WebDriver driver) {
    	Log.info("Step out of all Frames");
        driver.switchTo().defaultContent();
    }
    
	/**
	 * Switches to another browser tab.
	 * Creates a record in logger and report. 
	 * @param driver - [WebDriver] - current WebDriver.
	 * @param windowIndex - [int] - browser tab index counted from the left, starting with 1
	 * 
	 */ 
    @Timed(step = Click)
    protected void switchToBrowserTab(WebDriver driver, int windowIndex) {
    	Report.generateSimpleLog("Switch to Browser Tab", "Window index = " + windowIndex);
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(windowIndex - 1));
    }

	/**
	 * 
	 * @param driver - [WebDriver] - current WebDriver.
	 * 
	 */
    @Timed(step = Click)
    protected void closeCurrentTab(WebDriver driver) {
        driver.close();
    }
    
// send keys operations
    /**
 	* Manually selects all, copies it and paste (CTRL + A, CTRL + C and CTRL + V).
 	* Creates a record in logger.
 	* @param driver - [WebDriver] - current WebDriver.
 	* 
 	*/
    @Timed(step = Click)
    public void copyAndPasteManually(WebDriver driver) {
    	Log.info("Copy all and paste with keyboard");
        new Actions(driver).sendKeys(Keys.chord(Keys.CONTROL, "a")).perform();
        sleep(1000);
        new Actions(driver).sendKeys(Keys.chord(Keys.CONTROL, "c")).perform();
        sleep(1000);
        new Actions(driver).sendKeys(Keys.chord(Keys.CONTROL, "v")).perform();
        sleep(1000);
    }
    
    /**
 	* Press key action.
 	* Creates a record in logger.
 	* @param pageElement - [Element<?>] - page element wrapper
 	* @param key - [String] - if it is a standard key of alphabet/numeric, just send the key in param in quotation marks (e.g. key="t").
 	* Press SHIFT/TAB/ALT/CTRL/F1-12/ENTER keys
 	* @param key - [String] - start with Keys. + name of the key, no quotation marks (e.g. key=Keys.CONTROL).
 	*      
 	*/
    @Timed(step = Click)
    public void pressKey(Element<?> pageElement, String key) {
    	Log.info("Press on keyboard: " + key);
    	WebElement element = getElement(pageElement);
        element.sendKeys(key);
    }

    /**
 	* Press key action. 
 	* Creates a record in logger.
 	* @param pageElement - [Element<?>] - page element wrapper
 	* @param key1 and key2 - [String] - if it is a standard key of alphabet/numeric, just send the key in param in quotation marks (e.g. key="t").
 	* Press SHIFT/TAB/ALT/CTRL/F1-12/ENTER keys
 	* @param key1 and key2 - [String] - start with Keys. + name of the key, no quotation marks (e.g. key=Keys.CONTROL).
 	*      
 	*/   
    @Timed(step = Click)
    protected void pressTwoKeysAtOnce(Element<?> pageElement, String key1, String key2) {
    	Log.info("Press on keyboard: " + key1 + ", " + key2);
        WebElement element = getElement(pageElement);
        element.sendKeys(Keys.chord(key1, key2));
    }

// multiple windows navigation
    
    /**
 	*  In case of multiple tabs/windows being open, this method moves focus to the last open window/tab.
 	*  Creates a record in logger.
 	*  @param driver - [WebDriver] - current WebDriver.
 	* 
 	*/
    public void getLatestWindowFocused(WebDriver driver) {
        String mostRecentWindowHandle = "";
        Log.info("Focus on Latest Opened Window/Tab");
            for (String winHandle:driver.getWindowHandles()) {
                mostRecentWindowHandle = winHandle;        
            }
        driver.switchTo().window(mostRecentWindowHandle);
        JavascriptExecutor javascript = (JavascriptExecutor)driver;
        javascript.executeScript("window.focus();");
    }
    
    /**
 	*  Hover Mouse Over Element.
 	*  Creates a record in logger and report. 
 	*  @param pageElement - [Element<?>] - page element wrapper
 	* 
 	*/
    @Timed(step = Select)
    public void mouseOverElement(Element<?> pageElement) {
    	WebElement element = getElement(pageElement);
        WebDriver driver = pageElement.getDriver();
    	Report.generateLogAction(pageElement, "Hover Mouse Over Element");
    	assertNotNull(element);
    	Actions builder = new Actions(driver);
    	builder.moveToElement(element).perform();
    }
    
    /**
 	*  Hover Mouse Over Element and Click.
 	*  Creates a record in logger and report. 
 	*  @param pageElement - [Element<?>] - page element wrapper
 	* 
 	*/
    @Timed(step = Click)
    public void mouseOverElementAndClick(Element<?> pageElement) {
    	WebElement element = getElement(pageElement);
        WebDriver driver = pageElement.getDriver();
    	Report.generateLogAction(pageElement, "Hover Mouse Over Element and Click");
    	assertNotNull(element);
    	Actions builder = new Actions(driver);
    	builder.moveToElement(element).click(element);
    	builder.perform();
    }
        
	/**
	 * Internet explorer switch between windows action. 
	 * Start with navigateToNewWindowInternetExplorerPart_1, then do the action that will create/open new window.
	 * Continue with navigateToNewWindowInternetExplorerPart_2
	 * @param driver - [WebDriver] - current WebDriver.
	 *
	 *      
	 */
    @Timed(step = Click)
    protected Set<String> navigateToNewWindowInternetExplorerPart_1(WebDriver driver) {
        Set<String> handlesBeforeTheClick = driver.getWindowHandles();
        Log.info("Previously open windows: " + handlesBeforeTheClick.size());
        return handlesBeforeTheClick;
    }
    
	/**
	 * Internet explorer switch between windows action. 
	 * Start with navigateToNewWindowInternetExplorerPart_1, then do the action that will create/open new window.
	 * Continue with navigateToNewWindowInternetExplorerPart_2
	 * @param driver - [WebDriver] - current WebDriver.
	 * @param handlesBeforeTheClick - [Set<String>] - set of open window handles, retrieved by navigateToNewWindowInternetExplorerPart_1 method
	 *
	 *      
	 */
    @Timed(step = Click)
    protected void navigateToNewWindowInternetExplorerPart_2(WebDriver driver, Set<String> handlesBeforeTheClick) {
        while (handlesBeforeTheClick.size() >= driver.getWindowHandles().size()) {
            sleep(1000);
            Log.info("Number of previously open windows: " + handlesBeforeTheClick.size());
            Log.info("Number of currently open windows: " + driver.getWindowHandles().size());
        }
        
        Log.info("Currently open windows: " + driver.getWindowHandles().size());
       
        Set<String> handlesAfterClick = driver.getWindowHandles();
        handlesAfterClick.removeAll(handlesBeforeTheClick);
        Log.info("Old Focus is on page: " + driver.getCurrentUrl());
        
        String handleToNewWindow = handlesAfterClick.iterator().next();
        driver.switchTo().window(handleToNewWindow);
        Log.info("New Focus is on page: " + driver.getCurrentUrl());
        
        ((JavascriptExecutor) driver).executeScript("window.focus();");
        sleep(1000);
    }
    
	/**
	 * Retrieves value stored in session.
	 * @param driver - [WebDriver] - current WebDriver.
	 * @param key - [String] - session key, where the value is stored
	 * 
	 */
    protected String getSessionValue(WebDriver driver, String key) {
        JavascriptExecutor javascript = (JavascriptExecutor)driver;
        return (String) javascript.executeScript(String.format(
                    "return window.sessionStorage.getItem('%s');", key));
    }
    
	/**
	 * Selects interactable element
	 * Useful in case if the same element is stored more times in the source code and only one is active 
	 * 
	 */
    protected WebElement getInteractableElement(Element<?> pageElement) {
    	By locator = (By) pageElement.getElementLocator();
    	WebDriver driver = pageElement.getDriver();
    	int numOfElements = getNumberOfElements(pageElement);
    	List<WebElement> elementsList = driver.findElements(locator);
    	int i = 0;
    	WebElement element = null;
    	while(i < numOfElements) {
    		if(elementIsInteractable(elementsList.get(i))) {
    			element = elementsList.get(i);
    			break;
    		}
    		i++;
    	}
		return element;
    }
    
    private boolean elementIsInteractable(WebElement element) {
    	return element.isDisplayed();
    }
    
	/**
	 * Clicks on element and waits, creates a record in logger and report.
	 * Creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param timeInMilliseconds - [int] - wait time in milliseconds. (if constant_speed_index is entered this time will be multiplied by that number)
	 * 
	 */
    @Timed(step = Click)
    public void clickInteractableElementAndWait(Element<?> pageElement, int timeInMilliseconds) {
    	timeInMilliseconds = flowSpeed(timeInMilliseconds);
    	Report.generateLogWaitAction(pageElement, "Select Interactable Element than Click and Wait", timeInMilliseconds);
    	By locator = (By) pageElement.getElementLocator();
    	WebDriver driver = pageElement.getDriver();
    	int numOfElements = getNumberOfElements(pageElement);
    	List<WebElement> elementsList = driver.findElements(locator);
    	int i = 0;
    	while(i < numOfElements) {
    		if(elementIsInteractable(elementsList.get(i))) {
    			elementsList.get(i).click();
    			sleep(timeInMilliseconds);
    			break;
    		}
    		i++;
    	}
    }
    
 // Text manipulating element actions:
	/**
	 * Retrieves Attribute text of the element 
	 * Creates a record in logger and report.
	 * @param pageElement - [Element<?>] - page element wrapper
	 * @param attribute - [String] - the attribute we want to get text from
	 * 
	 */
     protected String getInteractableElementTextByAttribute(Element<?> pageElement, String attribute) {
     	 Report.generateLogFillAction(pageElement, "Select Interactable Element and get Text", attribute);
         WebElement element = getInteractableElement(pageElement);
         assertNotNull(element);
         return element.getAttribute(attribute);
     }
     
 	/**
 	 * Retrieves innerHtml text of the element 
 	 * Creates a record in logger and report.
 	 * @param pageElement - [Element<?>] - page element wrapper
 	 * 
 	 */
     protected String getInteractableElementText(Element<?> pageElement) { 
         WebElement element = getInteractableElement(pageElement);
         assertNotNull(element);
         String value =  element.getAttribute("innerHTML");
         Report.generateLogFillAction(pageElement, "Select Interactable Element and get Text", value);
         return value;
     }
     
 	/**
 	 * Fills in an input field
 	 * Creates a record in logger and report.
 	 * @param pageElement - [Element<?>] - page element wrapper
 	 * @param value - [String] - text of the element that is going to be filled
 	 * @param timeInMilliseconds - [int] - wait time in milliseconds. (if constant_speed_index is entered this time will be multiplied by that number)
 	 * 
 	 */
     @Timed(step = Type)
     protected void setInteractableElementTextAndWait(Element<?> pageElement, String value, int timeInMilliseconds) {  
    	 timeInMilliseconds = flowSpeed(timeInMilliseconds);
         Report.generateLogFillAndWaitAction(pageElement, "Select Interactable Element and set Text", value, timeInMilliseconds);    
         WebElement element = getInteractableElement(pageElement);
         assertNotNull(element);
         element.clear();
         element.sendKeys(value);
         sleep(timeInMilliseconds);
     }
     
   	/**
   	 * Method to compute waiting time if waitMultiplier != 1.
   	 * @param time - [Integer] - wait time
   	 * 
   	 */
      private int flowSpeed(int time) {
          if (!(waitMultiplier.equals(ONE.get()))) {
     	     Float computation = time * Float.valueOf(waitMultiplier);
     	     time = computation.intValue();
     	 }
          return time;
      }
}