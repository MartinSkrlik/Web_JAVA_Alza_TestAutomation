package utility;

public class Queries {
    
    public enum Test_result {
        INSERT_NEW_TC 
        ("INSERT INTO TEST_RESULT (GLOBAL_RUN_ID, GLOBAL_RESULT_ID, TEST_SET_DESCRIPTION, TEST_RESULT_ID, TEST_CASE_DESCRIPTION, "
                + "EXECUTED_BY, INSERT_DATE, EXECUTION_START, STATUS, STEPS_LOADED) VALUES (%s , %s, '%s', %s, '%s','%s', SYSDATE, SYSDATE, 'STARTED', 0)"),
        
        UPDATE_TC_STATUS
        ("UPDATE TEST_RESULT SET EXECUTION_END = SYSDATE, STATUS = '%s' "
                + "WHERE GLOBAL_RUN_ID = %s AND TEST_RESULT_ID = %s"),
        
        UPDATE_STEPS_LOADED
        ("UPDATE TEST_RESULT SET STEPS_LOADED = 1 "
                + "WHERE GLOBAL_RUN_ID = %s AND GLOBAL_RESULT_ID = %s"),
        
        GET_GLOBAL_RESULT_ID
        ("SELECT GLOBAL_RESULT_ID_SEQ.NEXTVAL AS NEXTVAL FROM DUAL"),
        ;
        
        private final String value;

        private Test_result(String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }
    }
    
    public enum Test_run {
        INSERT_NEW_TESTRUN 
        ("INSERT INTO TEST_RUN (GLOBAL_RUN_ID, TEST_SET_DESCRIPTION, EXECUTED_BY, EXECUTION_START, STATUS, TESTS_TOTAL) "
                + "VALUES (%s, '%s', '%s', SYSDATE, 'STARTED', %s)"),
        
        UPDATE_RUN_STATUS
        ("UPDATE TEST_RUN SET EXECUTION_END = SYSDATE, STATUS = 'DONE', TESTS_PASSED = %s, TESTS_FAILED = %s,  TESTS_WARNING = %s "
                + "WHERE GLOBAL_RUN_ID = %s"),
        
        ADD_HTML_REPORT
        ("UPDATE TEST_RUN SET HTML_REPORT = ? WHERE GLOBAL_RUN_ID = ?"),
        
        UPDATE_TOTAL
        ("UPDATE TEST_RUN SET TESTS_TOTAL = %s "
                + "WHERE GLOBAL_RUN_ID = %s"),
        ;
        
        private final String value;

        private Test_run(String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }
    }
    
    public enum Test_steps {
        INSERT_STEPS 
        ("INSERT INTO TEST_STEP (GLOBAL_RUN_ID, GLOBAL_RESULT_ID, TEST_SET_DESCRIPTION, "
        		+ "TEST_RESULT_ID, TEST_CASE_DESCRIPTION, TEST_STEP_ID, TEST_STEP_DESCRIPTION, "
        		+ "EXECUTION_START,"
        		+ "EXECUTION_END, "
        		+ "DURATION, ACTION_TYPE, STATUS) "
                + "VALUES (%s, %s, '%s', "
                + "%s, '%s', %s, '%s', "
                + "TO_TIMESTAMP('%s','YYYY-MM-DD HH24:MI:SS.FF3'), "
                + "TO_TIMESTAMP('%s','YYYY-MM-DD HH24:MI:SS.FF3'), "
                + "%s, '%s', '%s')")  
        ;

        private final String value;

        private Test_steps(String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }
    }
}