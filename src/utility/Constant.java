package utility;

/**
 * Class {@code Constant} contains all global constants and default values
 */

public class Constant {

    public enum InputKeys {
        PORTAL("portal"), ENVIRONMENT("environment"), BROWSER("browser"), DRIVER_MODE("driverMode"), DRIVER_VERSION("driverVersion"),
        FLOW("flow"), TEST_CASE_NR("testCaseNumber"), DRIVER("driver"), LINK("link"), RESULT_SWITCH("resultSwitch"),
        STEP_SWITCH("stepSwitch"), TESTSET("testsetID"), ROBORUN("roborun"), GLOBAL_RESULT_ID("global_result_id"),
        ALL_STEPS("allSteps"), ONE("1"),
        ;

        private String value;

        private InputKeys(String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }
    }

    public enum Numbering {
        FLOW_PARAMETER_INPUT_INDEX(2),
        TEST_CASE_ID_INPUT_INDEX(3),
        ;

        private int value;

        private Numbering(int value) {
            this.value = value;
        }

        public int get() {
            return value;
        }
    }

    public enum InputCaseSpecificKeys {
        CITY("city"),
        STREET("street"),
        HOUSE("houseNr"),
        PRODUCT("product"),
        ;

        private String value;

        private InputCaseSpecificKeys(String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }
    }

    public enum Input_SAUCEDEMO_LoginTC {
        USERNAME("username"),
        PASSWORD("password"),
        TITLE("title"),
        ;

        private String value;

        private Input_SAUCEDEMO_LoginTC(String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }
    }

    public enum Input_RAHUL_TC {
        TITLE("title"),
        RADIO("radio"),
        COUNTRY("country"),
        OPTION("option"),
        CHECKBOX("checkbox"),
        WINDOW_TITLE("window_title"),
        TAB_TITLE("tab_title"),
        NAME("name"),
        ALERT("alert"),
        CONFIRM_ALERT("confirm_alert"),
        NUMBER_OF_ROWS("number_of_rows"),
        ;

        private String value;

        private Input_RAHUL_TC(String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }
    }

    public enum Input_ALZA_TC {
        PRODUCT_NAME("product_name"),
        TITLE("title"),
        PHONE_NAME("phone_name"),
        TEXT("text"),
        FULL_NAME("full_name"),
        POP_UP_TITLE("pop_up_title"),
        CHECKBOX_TITLE("checkbox_title"),
        DISPATCH_PRICE("dispatch_price"),
        DELIVRY_METHOD("delivery_method"),
        PAYMENT_METHOD("payment_method"),
        MODEL_PRICE("model_price"),
        TOTAL_PRICE("total_price"),
        ;

        private String value;

        private Input_ALZA_TC(String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }
    }

    public enum Input_ALZA2_TC {
        PRODUCT_NAME("product_name"),
        TITLE("title"),

        ;

        private String value;

        private Input_ALZA2_TC(String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }
    }

    public enum Input_ALZA3_TC {
        PRODUCT_NAME("product_name"),
        TITLE("title"),
        PRODUCT_TYPE("product_type"),
        ;

        private String value;

        private Input_ALZA3_TC(String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }
    }
    public enum Input_ALZA4_TC {
        TITLE("title"),
        PRODUCT_TYPE1("product_type1"),
        PRODUCT_TYPE2("product_type2"),
        PRODUCT_TYPE3("product_type3"),
        PRODUCT_REMOVED("product_removed")
        ;

        private String value;

        private Input_ALZA4_TC(String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }
    }
    public enum PerformanceKeys {
        DESCRIPTION("description"),
        TYPE("type"),
        START("start"),
        END("end"),
        DURATION("duration"),
        STEP_ID("step id"),
        ELEMENT_NULL("Step without Element"),
        ;

        private String value;

        private PerformanceKeys(String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }
    }

    public enum TimedKeys {
        Wait, Click, Type, Scroll, Select;
    }

    public enum TestStatusKeys {
        PASS("pass"), FAIL("fail"), WARN("warn"),
        PASSED("passed"), FAILED("failed"), WARNING("warning");

        private String value;

        private TestStatusKeys(String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }
    }

    public enum Encodings {
        UTF_8("UTF-8"), UTF_16("UTF-16"), ISO_8859_1("ISO-8859-1");

        private String value;

        private Encodings(String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }
    }
}