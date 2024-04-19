package utility;

import static config.Directories.*;
import static utility.Constant.TestStatusKeys.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.zeroturnaround.zip.ZipUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import generic.Element;

public class Report extends FrameworkActions {

    public static ExtentTest logger; 
    public static ExtentReports report;
    private static boolean isWarn = false;
    
 // Report general start/finish methods:
    
    public static void startReport(String testName) {
        report = new ExtentReports(getFileInFolder(testName, "html"), false);
        report.loadConfig(new File(REPORT_CONFIG_DIR.get()));
    }
    
    public static void startTest(String flow, String tcNumber) {
        String date              = getDate("yyyy-MM-dd HH:mm:ss", 0);
        String flowNameFormatted = formatFlowName(flow);
        logger = report.startTest(String.format(START_LOG, 
        		                  tcNumber, flowNameFormatted, date));
    }
    
    private static String formatFlowName(String flow) {
        String splitName = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(flow),' ');
        return StringUtils.capitalize(splitName);
    }
    
    public static void assignCategory(String category) {
        List<String> categories = Arrays.asList(category.split("\\,", -1));
        for (String testCategory : categories) {
            logger.assignCategory(testCategory);
        }
    }
    
    public static void finishReport() {
        report.endTest(logger);
        report.flush();
    }

    public static void passTest() {
        logger = report.startTest("passTest");
        logger.log(LogStatus.PASS, "Pass");
        Assert.assertEquals(logger.getRunStatus(), LogStatus.PASS);
    }
    
    public static String getRunStatus() {
    	String runStatus = Report.logger.getRunStatus().toString();
    	String runStatusCorrected = "";
    	if(runStatus.equalsIgnoreCase(PASS.get()) && !isWarn) {
    		runStatusCorrected = PASSED.get().toUpperCase();
    	}
        if(runStatus.equalsIgnoreCase(FAIL.get())) {
        	runStatusCorrected = FAILED.get().toUpperCase();
    	}
        if(!runStatus.equalsIgnoreCase(FAIL.get()) && isWarn) {
        	runStatusCorrected = WARNING.get().toUpperCase();
        }
        return runStatusCorrected;
    }
     
 // Report html customizations:
    
    public static void logInfoMessage(String msg) {
        Report.logger.log(LogStatus.INFO, addMarkup("INFO") + msg);
    }
    
    public static void logWarningMessage(String msg) {
        Report.logger.log(LogStatus.WARNING, addMarkup("WARN") + msg);
    }
    
    public static void logSuccessMessage(String msg) {
        Report.logger.log(LogStatus.PASS, addMarkup("PASS") + msg);
    }
    
    public static void logFailureMessage(String msg) {
        Report.logger.log(LogStatus.FAIL, addMarkup("FAIL") + msg);
    }
    
    public static void highlightMessage(String status, String logMessage, Colours font, Colours highlight) {
        String log = String.format(HIGHLIGHT, font, highlight, logMessage);
        logger.log(LogStatus.valueOf(status), log);
    }
    
    public static void highlightModuleName(String status, String logMessage) {
        String log = String.format(HIGHLIGHT_MODULENAME, logMessage);
        logger.log(LogStatus.valueOf(status), log);
    }
    
    public static void addListToCollapsibleLog(String header, LogStatus logStatus, ArrayList<?> list) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(COLLAPSIBLE_HEADER, header));
        
        for (Object param : list) {
            stringBuilder.append(String.format(COLLAPSIBLE_MONOSPACE_VALUES, param));
        }
        
        stringBuilder.append(DIV_END);
        Report.logger.log(logStatus, stringBuilder.toString());
    }
    
    public static void addCollapsibleException(String header, LogStatus logStatus, Throwable e) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(COLLAPSIBLE_HEADER, header));
        stringBuilder.append(String.format(COLLAPSIBLE_EXCEPTION, 
                org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e)));
        stringBuilder.append(DIV_END);
        
        if (logger != null) {
            Report.logger.log(logStatus, stringBuilder.toString());
        }
    }
    
    public enum Colours {
      AliceBlue, AntiqueWhite, Aqua, Aquamarine, Azure, Beige, Black, Blue, BlueViolet, Brown, Coral, Crimson, Cyan, DarkBlue, DarkGray, 
      DarkGreen, DarkOrange, DarkRed, ForestGreen, Fuchsia, Gainsboro, Gold, Gray, Green, GreenYellow, LawnGreen, LightBlue, LightGray, 
      LightGreen, LightSteelBlue, Lime, LimeGreen, Magenta, Maroon, MediumBlue, Orange, OrangeRed, Purple, Red, RoyalBlue, Silver, Turquoise, 
      Violet, White, Yellow
    }
    
    /*
     * Original enum wasn't public - to follow up if it causes any error in the future
    enum Colours {
        AliceBlue, AntiqueWhite, Aqua, Aquamarine, Azure, Beige, Black, Blue, BlueViolet, Brown, Coral, Crimson, Cyan, DarkBlue, DarkGray, 
        DarkGreen, DarkOrange, DarkRed, ForestGreen, Fuchsia, Gainsboro, Gold, Gray, Green, GreenYellow, LawnGreen, LightBlue, LightGray, 
        LightGreen, LightSteelBlue, Lime, LimeGreen, Magenta, Maroon, MediumBlue, Orange, OrangeRed, Purple, Red, RoyalBlue, Silver, Turquoise, 
        Violet, White, Yellow
      }
      */
    
    private static final String HIGHLIGHT                    = "<span style='font-weight:bold;color:%s;background-color:%s;'>%s</span>";
    private static final String HIGHLIGHT_MODULENAME         = "<span class = 'label white-text blue'>%s</span>";
    private static final String COLLAPSIBLE_HEADER           = "<div class=\"collapsible_acc\">%s</div><div class=\"content\" style=\"display: none;\">";
    private static final String COLLAPSIBLE_EXCEPTION        = "<pre>%s</pre>";
    private static final String DIV_END                      = "</div>";
    private static final String COLLAPSIBLE_MONOSPACE_VALUES = "<p style=\"font-family: monospace;\">%s</p>";
 
    public static void generateLogAction(Element<?> pageElement, String action) { 	
    	String by = pageElement.getElementPath();
        String description = pageElement.getName();
        LinkedHashMap<String, Object> reportMap = new LinkedHashMap<String, Object>();
        
        String header = action + ": " + description;
        reportMap.put("Action", action);
        reportMap.put("Description", description);
        reportMap.put("Locator", by); 
        FrameworkActions.logMapToReportMonospace(LogStatus.INFO, reportMap, header);
    }
    
    public static void generateSimpleLog(String action, String description) {
        LinkedHashMap<String, Object> reportMap = new LinkedHashMap<String, Object>();

        String header = action + ": " + description;
        reportMap.put("Action", action);
        reportMap.put("Description", description); 
        FrameworkActions.logMapToReportMonospace(LogStatus.INFO, reportMap, header);
    }
    
    public static void generateLogWaitAction(Element<?> pageElement, String action, int timeInMilliseconds) {
    	String timeInMillisecondsString = String.valueOf(timeInMilliseconds) + " milliseconds";
    	String by = pageElement.getElementPath();
        String description = pageElement.getName();
        LinkedHashMap<String, Object> reportMap = new LinkedHashMap<String, Object>();
        
        String header = action + ": " + description;
        reportMap.put("Action", action);
        reportMap.put("Description", description);
        reportMap.put("Locator", by); 
        reportMap.put("Wait time", timeInMillisecondsString);
        FrameworkActions.logMapToReportMonospace(LogStatus.INFO, reportMap, header);
    }
    
    public static void generateSimpleMaxTimeLog(Element<?> pageElement, String action, int maxTimeInSeconds) {
    	String maxTimeInSecondsString = String.valueOf(maxTimeInSeconds) + " seconds";
        String description = pageElement.getName();
        LinkedHashMap<String, Object> reportMap = new LinkedHashMap<String, Object>();
        
        String header = action + ": " + description;
        reportMap.put("Action", action);
        reportMap.put("Description", description); 
        reportMap.put("Maximum Waiting Time", maxTimeInSecondsString);
        FrameworkActions.logMapToReportMonospace(LogStatus.INFO, reportMap, header);
    }
    
    public static void generateLogFillAction(Element<?> pageElement, String action, String value) {
    	String by = pageElement.getElementPath();
        String description = pageElement.getName();
        LinkedHashMap<String, Object> reportMap = new LinkedHashMap<String, Object>();
        
        String header = action + ": " + description;
        reportMap.put("Action", action);
        reportMap.put("Description", description); 
        reportMap.put("Locator", by);
        reportMap.put("Value", value);
        FrameworkActions.logMapToReportMonospace(LogStatus.INFO, reportMap, header);
    }
    
    public static void generateLogFillAndWaitAction(Element<?> pageElement, String action, String value, int timeInMilliseconds) {
    	String timeInMillisecondsString = String.valueOf(timeInMilliseconds) + " milliseconds";
    	String by = pageElement.getElementPath();
        String description = pageElement.getName();
        LinkedHashMap<String, Object> reportMap = new LinkedHashMap<String, Object>();
        
        String header = action + ": " + description;
        reportMap.put("Action", action);
        reportMap.put("Description", description); 
        reportMap.put("Locator", by);
        reportMap.put("Value", value);
        reportMap.put("Wait time", timeInMillisecondsString);
        FrameworkActions.logMapToReportMonospace(LogStatus.INFO, reportMap, header);
    }
    
    public static void generateLogMaxTimeAction(Element<?> pageElement, String action, int maxTimeInSeconds) {
    	String maxTimeInSecondsString = String.valueOf(maxTimeInSeconds) + " seconds";
    	String by = pageElement.getElementPath();
        String description = pageElement.getName();
        LinkedHashMap<String, Object> reportMap = new LinkedHashMap<String, Object>();
        
        String header = action + ": " + description;
        reportMap.put("Action", action);
        reportMap.put("Description", description); 
        reportMap.put("Locator", by);
        reportMap.put("Maximum Waiting Time", maxTimeInSecondsString);
        FrameworkActions.logMapToReportMonospace(LogStatus.INFO, reportMap, header);
    }
      
    public static void generateSimpleMaxTimeLog(String action, String description, int maxTimeInSeconds) {
    	String maxTimeInSecondsString = String.valueOf(maxTimeInSeconds) + " seconds";
        LinkedHashMap<String, Object> reportMap = new LinkedHashMap<String, Object>();
        
        String header = action + ": " + description;
        reportMap.put("Action", action);
        reportMap.put("Description", description);
        reportMap.put("Maximum Waiting Time", maxTimeInSecondsString);
        FrameworkActions.logMapToReportMonospace(LogStatus.INFO, reportMap, header);
    }
    
// Report Screenshots:
    
    public class Screenshot {
        
        private WebDriver driver;
        
        public Screenshot(WebDriver driver) {
            this.driver = driver;
        }
         
        public void logScreen(String name) {
            if (driver != null) {
                String screenshotName = getScreenshotName(name);
                takeScreenshot(driver, screenshotName);
                addScreenshotToReport(screenshotName);
            }
        }
        
        public void logScreenAsBase64() {
            try {
                base64conversion();
            } 
            catch (Exception e) {
                Log.info(e.getMessage());
            }
        }
        
        public void logFullScreen(String name) {
            String screenshotName = getScreenshotName(name);
            takeFullscreenScreenshot(driver, screenshotName);
            addScreenshotToReport(screenshotName);
        }
        
        private String getScreenshotName(String name) {
            return  name + "_" + FrameworkActions.getDate("yyyy-MM-dd_HH-mm-ss", 0) + ".png";
        }
         
        private void addScreenshotToReport(String screenshotName) {
            String img = logger.addScreenCapture
                         ((FrameworkActions.getRelativePath(screenshotName)).toString());
            
            Report.logger.log(LogStatus.INFO, "Image", "Screenshot: " + img);
        }
        
        private void base64conversion() throws Exception {
            TakesScreenshot newScreen = (TakesScreenshot) driver;
            String screenshot = newScreen.getScreenshotAs(OutputType.BASE64);
            Report.logger.log(LogStatus.INFO, logger.addBase64ScreenShot(BASE64 + screenshot));
        }
    }
    
    public String zipReport() {
        String pathToOriginalFolder = System.getProperty("scr.folder");
        String pathToZippedFolder = pathToOriginalFolder + ".zip";
        
        ZipUtil.pack(new File(pathToOriginalFolder), new File(pathToZippedFolder));
        return pathToZippedFolder;
    }
    
    public static String addMarkup(String value) {
        Labels enumValue = Labels.valueOf(value);
        if(value.equals("WARN")) {
        	isWarn = true;
        }
        return String.format(MARKUP, enumValue.textColour, enumValue.colour,  enumValue.msg);
    }
    
    public static boolean getWarnResult() {
    	return isWarn;
    }
    
    public static void cleanGetWarnResult() {
    	isWarn = false;
    }
    
    enum Labels {
        PASS ("green", "white-text", "SUCCESS:"),
        WARN("orange", "white-text", "WARNING:"),
        FAIL("red", "white-text", "FAILURE:"),
        INFO("blue", "white-text", "INFO:");
        
        private String colour;
        private String textColour;
        private String msg;
        
        Labels(String colour, String textColour, String msg) {
            this.colour = colour;
            this.textColour = textColour;
            this.msg = msg;
        }
    }

    private static final String START_LOG = "%s. %s<br>%s";
    private static final String MARKUP = "<span class='label %s %s'>%s</span>";
    private static final String BASE64 = "data:image/jpeg;base64,";
    
    //Moved from FrameworkActions
    //Logger-reporting methods, kept here just in case
    protected void logAction(Element<?> pageElement, String action, String...value) {
        
        String by = pageElement.getElementPath();
        String description = pageElement.getName();
        
        Log.info(String.format(ACTION, action));
        Log.info(String.format(DESCRIPTION, description));
        Log.info(String.format(LOCATOR, by)); 
        
        if (value.length > 0) {
            Log.info(String.format(VALUE, value[0])); 
        }
        
        Log.info(LINE_BREAK);

        String header = action + ": " + description;
        String locator = String.format(COLLAPSIBLE_LOCATOR, by);
        String text = value.length > 0 ? String.format(COLLAPSIBLE_VALUE, value[0]): "";
        String msg = String.format(COLLAPSIBLE_BODY, header, action, description, locator, text);
        Report.logger.log(LogStatus.INFO, msg);
    }

    protected void simpleLog(String action, String description) {   
        
        Log.info(String.format(ACTION, action));
        Log.info(String.format(DESCRIPTION, description));
        Log.info(LINE_BREAK);
        
        String header = action + ": " + description;
        String msg = String.format(COLLAPSIBLE_BODY, header, action, description,"","");
        Report.logger.log(LogStatus.INFO, msg);
    }
    
    private final String ACTION = "Action........: %s";
    private final String DESCRIPTION = "Description...: %s";
    private final String LOCATOR = "Locator.......: %s";
    private final String VALUE = "Value.........: %s";
    private final String LINE_BREAK = "-------------------------------------------------------------------------------------";
    
    private final String COLLAPSIBLE_LOCATOR = "<p>Locator :   %s</p>";   
    private final String COLLAPSIBLE_VALUE = "<p>Value :   %s</p>";
    private final String COLLAPSIBLE_BODY = "<div class=\"collapsible_acc\">%s</div><div class=\"content\" style=\"display: none;\">"
                                          + "<p>Action :   %s</p><p>Description :   %s</p>%s%s</div>"; 
}