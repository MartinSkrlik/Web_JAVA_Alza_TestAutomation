package utility;

import com.relevantcodes.extentreports.LogStatus;

public class Validation {

    private boolean condition;
    private String msg;
    private String check;
    private Object actual;
    private Object expected;
    
    public Validation(String check, Boolean condition) {
        this.check = check;
        this.condition = condition;
    }
    
    public Validation(String check, Object actual, Object expected) {
        this.check = check;
        this.actual = actual;
        this.expected = expected;
    }
    
    public void stringEquals() {
        String actualValue = (String) actual;
        String expectedValue = (String) expected;
        
        if (actualValue.equals(expectedValue)) {
             msg = String.format(MSG_EQUAL_PASS, actualValue, expectedValue);
            logPassMessage();
        }
        else {
            msg = String.format(MSG_EQUAL_FAIL, actualValue, expectedValue);
            logWarningMessage();
        }
    }
    
    public void stringNotEquals() {
        String value1 = (String) actual;
        String value2 = (String) expected;
        
        if (!value1.equals(value2)) {
            msg = String.format(MSG_NOTEQUAL_PASS, value1, value2);
            logPassMessage();
        }
        else {
            msg = String.format(MSG_NOTEQUAL_FAIL, value1, value2);
            logWarningMessage();
        }
    }
    
    public void doubleEquals() {
        Double actualValue = (Double) actual;
        Double expectedValue = (Double) expected;
        
        if (actualValue.equals(expectedValue)) {
             msg = String.format(MSG_EQUAL_PASS, String.valueOf(actualValue), String.valueOf(expectedValue));
            logPassMessage();
        }
        else {
            msg = String.format(MSG_EQUAL_FAIL, String.valueOf(actualValue), String.valueOf(expectedValue));
            logWarningMessage();
        }
    }
    
    public void integerEquals() {
        Integer actualValue   = (Integer) actual;
        Integer expectedValue = (Integer) expected;
        
        if (actualValue.equals(expectedValue)) {
             msg = String.format(MSG_EQUAL_PASS, String.valueOf(actualValue), String.valueOf(expectedValue));
            logPassMessage();
        }
        else {
            msg = String.format(MSG_EQUAL_FAIL, String.valueOf(actualValue), String.valueOf(expectedValue));
            logWarningMessage();
        }
    }
    
    public void contains() {
        String actualValue = (String) actual;
        String expectedValue = (String) expected;
        
        if (actualValue.contains(expectedValue)) {
            msg = String.format(MSG_CONTAIN_PASS, actualValue, expectedValue);
            logPassMessage();
        }
        else {
             msg = String.format(MSG_CONTAIN_FAIL, actualValue, expectedValue);
             logWarningMessage();
        }
    }
       
    public void isTrue() {
        if (!condition) {
            msg = String.format(MSG_EQUAL_FAIL, "true", String.valueOf(condition));
            logFailMessage();
        }else{
            msg = String.format(MSG_EQUAL_PASS, "true", String.valueOf(condition));
            logPassMessage();
        }
    }
    
    public void isFalse() {
        if (condition) {
            msg = String.format(MSG_EQUAL_FAIL, "false", String.valueOf(condition));
            logFailMessage();
        }else {
            msg = String.format(MSG_EQUAL_PASS, "false", String.valueOf(condition));
            logPassMessage();
        }
    }
    
    private void logPassMessage() {
        Log.info(String.format(LOG_MSG_HEADER, check, msg));
        Report.logger.log(LogStatus.PASS, String.format(REPORT_MSG_HEADER, check, Report.addMarkup("PASS"), msg));
    }
    
    private void logWarningMessage() {
        Log.info(String.format(LOG_MSG_HEADER, check, msg));
        Report.logger.log(LogStatus.WARNING, String.format(REPORT_MSG_HEADER, check, Report.addMarkup("WARN"), msg));
    }
    
    private void logFailMessage() {
        Log.info(String.format(LOG_MSG_HEADER, check, msg));
        Report.logger.log(LogStatus.FAIL, String.format(REPORT_MSG_HEADER, check, Report.addMarkup("FAIL"), msg));
    }
    
    private final String MSG_EQUAL_PASS = "Actual value '%s' equals to expected value '%s'.";
    private final String MSG_EQUAL_FAIL = "Actual value '%s' does not equal to expected value '%s'.";
    
    private final String MSG_NOTEQUAL_PASS = "Values '%s' and '%s' are not equal as expected.";
    private final String MSG_NOTEQUAL_FAIL = "Values '%s' and '%s' should not be equal.";
    
    private final String MSG_CONTAIN_PASS = "Actual value '%s' contains expected value '%s'.";
    private final String MSG_CONTAIN_FAIL = "Actual value '%s' does not contain expected value '%s'.";
    
    private final String REPORT_MSG_HEADER = "Validating %s: </br> %s %s";
    private final String LOG_MSG_HEADER = "%s : %s";
}