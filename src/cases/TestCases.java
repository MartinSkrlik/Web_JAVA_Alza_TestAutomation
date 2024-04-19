package cases;

import com.relevantcodes.extentreports.LogStatus;
import utility.Log;
import utility.Report;

public interface TestCases {
    void runTest();
    void printLogMessage(String testcaseID);
}

class UnknownTest implements TestCases {

    @Override
    public void runTest() {
        String logMessage = UNRECOGNIZED_MSG;
        Report.logger.log(LogStatus.INFO, logMessage);
        Log.info(logMessage);
    }

    @Override
    public void printLogMessage(String testcaseID) {    
    }
    
    private final String UNRECOGNIZED_MSG = "TEST CASE NOT RECOGNIZED!";
}