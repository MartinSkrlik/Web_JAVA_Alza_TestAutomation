package utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class {@code Log} is a log4j2 logging class
 * Logs are saved to file: src/logfile.log
 * Log setup file: src/log4j2.xml
 * 
 */

public class Log {

    private static Logger Log = LogManager.getLogger(Log.class.getName());
      
    public static void startTestCase(String tcName) {
        Log.info(ASTERISK_LINE);
        Log.info(ASTERISK_LINE);
        Log.info(String.format(TESTCASE_NAME_START_LINE, tcName));
        Log.info(ASTERISK_LINE);
        Log.info(ASTERISK_LINE);
    }
     
    public static void endTestCase(String tcName) {
        Log.info(END_LINE);
        Log.info(String.format(TESTCASE_NAME_END_LINE, tcName));
        Log.info(X_LINE);
        Log.info(X_LINE);
        Log.info(X_LINE);
        Log.info(X_LINE);
    }
     
    public static void info(String message) {
        Log.info(message);
    }
     
    public static void warn(String message) {
        Log.warn(message);
    }
     
    public static void error(String message) {
        Log.error(message);
    }
     
    public static void fatal(String message) {
        Log.fatal(message);
    }
     
    public static void debug(String message) {
        Log.debug(message);
    }
     
    private static final String ASTERISK_LINE = "*************************************************************************************";
    private static final String TESTCASE_NAME_START_LINE = "====================             %s             ============================";
    private static final String TESTCASE_NAME_END_LINE = "XXXXXXXXXXXXXXXXXXXX             %s             XXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    private static final String END_LINE = "XXXXXXXXXXXXXXXXXXXX             -E---N---D-             XXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    private static final String X_LINE = "X";  
}