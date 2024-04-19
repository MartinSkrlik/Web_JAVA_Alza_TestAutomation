package utility;

import static utility.Exceptions.CustomErrorMesssages.*;
import java.sql.SQLException;
import java.util.ArrayList;
import org.openqa.selenium.Alert;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.Assert;
import com.relevantcodes.extentreports.LogStatus;

public class Exceptions {
    
    private Throwable e;
    private WebDriver driverParam;
    private String trace;
    private String message;
    
    // In case there is no access to driver in case of certain exception, it is set driverParam = null; 
    
    protected void processException(Throwable e, WebDriver... driver) {
        this.e = e;
        driverParam = driver.length > 0 ? driver[0] : null;
        
        if (e instanceof NoSuchElementException) {
            this.message = NO_ELEMENT.get();
        }
        else if (e instanceof TimeoutException) {
            this.message = TIMEOUT.get();
        }
        else if (e instanceof ElementNotVisibleException) {
            this.message = NOT_VISIBLE.get();
        }
        else if (e instanceof ElementClickInterceptedException) {
            this.message = INTERCEPTED.get();
        }
        else if (e instanceof ElementNotSelectableException) {
            this.message = NOT_SELECTABLE.get();
        }
        else if (e instanceof ElementNotInteractableException) {
            this.message = NOT_INTERACTIBLE.get();
        }
        else if (e instanceof StaleElementReferenceException) {
            this.message = NOT_STALE.get();
        }
        else if (e instanceof UnreachableBrowserException) {
            this.message = UNREACHABLE_BROWSER.get();
            driverParam = null;
        }
        else if (e instanceof NoSuchFrameException) {
            this.message = NO_FRAME.get();
        }
        else if (e instanceof UnhandledAlertException) {
            Alert alert = driverParam.switchTo().alert();
            this.message = String.format(UNEXPECTED_ALERT.get(), alert.getText());
            driverParam = null;
        }
        else if (e instanceof SQLException) {
            this.message = SQL_FAILED.get();
            driverParam = null;
        }
        else if (e instanceof java.lang.IllegalArgumentException) {
            this.message = String.format(UNSUPPORTED.get(), e.getMessage());
        }
        else {
            this.message = OTHER.get();
        }
        
        logException();
        
//        if (driverIsAvailable()) {
//            Report report = new Report();
//            Report.logInfoMessage("Screenshot of current state");
//            report.new Screenshot(driverParam).logScreen(FAILURE_SCREENSHOT_NAME);
//            Report.logInfoMessage("Full page screenshot of current state");
//            report.new Screenshot(driverParam).logFullScreen(FAILURE_FULLSCREENSHOT_NAME);
//            Report.logger.log(LogStatus.INFO, String.format(URL_LOG, driver[0].getCurrentUrl()));
//        }
        Assert.fail(trace);
    }
    
    enum CustomErrorMesssages {
        NO_ELEMENT ("WEB ELEMENT NOT DISPLAYED ON WEB PAGE."),
        TIMEOUT ("WEB ELEMENT NOT DISPLAYED ON WEB PAGE AFTER n MINUTES."),
        NOT_VISIBLE ("WEB ELEMENT IS NOT VISIBLE ON WEB PAGE."),
        INTERCEPTED ("CLICKING ON WEB ELEMENT WAS INTERCEPTED."),
        NOT_SELECTABLE ("UNABLE TO SELECT OPTION."),
        NOT_INTERACTIBLE ("UNABLE TO PERFORM ACTION ON THE WEB ELEMENT"),
        NOT_STALE ("WEB ELEMENT WAS NOT LOADED PROPERLY."),
        UNREACHABLE_BROWSER ("BROWSER IS NOT REACHABLE."),
        NO_FRAME ("IFRAME NOT FOUND ON WEB PAGE."),
        UNEXPECTED_ALERT ("UNEXPECTED ALERT POP-UP APPEARED ON WEB PAGE. ALERT MESSAGE: %s"),
        SQL_FAILED ("EXECUTING SQL QUERY FAILED."),
        UNSUPPORTED ("UNSUPPORTED PARAMETER: %s"),
        OTHER (""),
        ;
        
        private String msg;
        
        CustomErrorMesssages(String msg) {
            this.msg = msg;
        }
        
        public String get() {
            return msg;
        }
    }
    
    private boolean driverIsAvailable() {
        return driverParam != null;
    }
    
    public void processException(SQLException e) {
        this.e = e;  
        Report.addCollapsibleException(
                Report.addMarkup("FAIL") + String.format(SQL_MSG, message), LogStatus.FAIL, e);
        logException();
    }
    
    // used in methods where report is not yet available/not available anymore
    public void processExceptionWithoutLogToReport(Exception e) {
        this.e = e;  
        System.out.println(e);
    }
    
    private void logException() {
        trace = org.apache.commons.lang3.exception
                .ExceptionUtils.getStackTrace(e);
        ArrayList<Object> trace = new ArrayList<Object>();
        trace.add(e);
   
        Log.error(String.format(TC_MSG, message));
        Log.error(String.format(EXCEPTION_MSG, trace));
        Report.addCollapsibleException(
                Report.addMarkup("FAIL") + String.format(TC_MSG, message), LogStatus.FAIL, e);
    }
    
    private final String FAILURE_SCREENSHOT_NAME     = "FAILURE SCREENSHOT";
    private final String FAILURE_FULLSCREENSHOT_NAME = "FAILURE FULL SCREENSHOT";
    private final String URL_LOG                     = "URL: %s";
    private final String SQL_MSG                     = "Processing SQL Query failed. %s";
    private final String TC_MSG                      = "Test case failed. %s";
    private final String EXCEPTION_MSG               = "Exception: %s";
}