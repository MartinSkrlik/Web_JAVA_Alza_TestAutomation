package utility;

import static utility.Constant.InputKeys.*;
import static utility.Constant.TestStatusKeys.*;
import java.util.HashMap;
import java.util.Map;
import com.relevantcodes.extentreports.LogStatus;

public class ResultManager {
    public static Map<String, TestResult> results;
    private static int i;
     
    public ResultManager() {
        results = new HashMap<>();
    }
    
    public static int getNumberOfAllTestCasesInStatus(String status) {
        i = 0;
        results.entrySet().
                stream().
                forEach(e -> countStatus(e.getValue(), status));
        
        return i;
    }
    
    public static int getNumberOfPortalSpecificTestCasesInStatus(String portal, String status) {
        i = 0;
        results.entrySet().
                stream().
                forEach(e -> countStatusPerPortal(e.getValue(), portal, status));
        
        return i;
    }
    
    private static void countStatus(TestResult te, String status) {
        if (te.getStatus().equalsIgnoreCase(status)) {
            i ++;
        }
    }
    
    private static void countStatusPerPortal(TestResult te, String portal, String status) {
        if (te.getStatus().equalsIgnoreCase(status) && te.getPortal().equalsIgnoreCase(portal)) {
            i ++;
        }
    }
    
    public static void logGlobalRunIdAndResultIdWithStatus(String testCaseNumber, Boolean isSendingDataToDatabase) {
        String status = results.get(testCaseNumber).getStatus();
        LogStatus logStatus = LogStatus.INFO;
        if(status.equals(WARN.get())) {
            logStatus = LogStatus.WARNING;
        }
        if(status.equals(PASS.get())) {
            logStatus = LogStatus.PASS;
        }
        if(status.equals(FAIL.get())) {
            logStatus = LogStatus.FAIL;
        }
        createLog(logStatus, isSendingDataToDatabase);  
    }
    
    private static void createLog(LogStatus logStatus, Boolean isSendingDataToDatabase) {
    	String runIdToLog    = "GLOBAL RUN ID.....: " + System.getProperty(ROBORUN.get());
        if(isSendingDataToDatabase) {
            String resultIdToLog = "GLOBAL RESULT ID: "   + System.getProperty(GLOBAL_RESULT_ID.get());
            Report.logger.log(logStatus, Report.addMarkup("INFO") + runIdToLog + " <br> " + Report.addMarkup("INFO") + resultIdToLog);
        }
        else {
            Report.logger.log(logStatus, Report.addMarkup("INFO") + runIdToLog);
        }
    }
}