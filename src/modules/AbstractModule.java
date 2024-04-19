package modules;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.LogStatus;
import generic.Element;
import utility.Log;
import utility.Report;

public class AbstractModule extends TestStepActions {
    
    protected Map<String, Object> input;
    protected WebDriver driver;
    
    protected String getModuleName() {
        return this.getClass().getSimpleName();
    }

    protected void reportInitialStep(String... testMethodName) {
        String module = getModuleName();
        String testMethod = testMethodName.length > 0 ? testMethodName[0]: "";
        Log.info(String.format(LOG_START, module, testMethod));
        Report.highlightModuleName("INFO", String.format(LOG4J_HEADER, module, testMethod));
    } 
    
    protected void addCurrentUrlToReport(WebDriver driver) {
        String url = driver.getCurrentUrl();
        Report.logger.log(LogStatus.INFO, String.format(LINK, url, url));
    }
    
    protected void logScreenshot(WebDriver driver, String name) {
        Report report = new Report();
        report.new Screenshot(driver).logScreen(name);
    }
    
    protected void logFullScreenshot(WebDriver driver, String name) {
        Report report = new Report();
        report.new Screenshot(driver).logFullScreen(name);
    }
    
    protected void initializeElements(Element<?>...elems) {
        for (Element<?> el: elems) {
            el.setDriver(driver);
        }
    }
    
    private final String LOG_START = "Starting %s %s";
    private final String LOG4J_HEADER = "===== Starting %s %s =====";
    private final String LINK = "URL: <a href='%s'>%s</a>";
}