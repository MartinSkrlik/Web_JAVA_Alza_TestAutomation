package config;

import utility.PropertyFile;

public class Config {

    //to be able call decryption method anywhere in the project 
    public static String getDecryptedValue(String key) {
        return PropertyFile.getValue(key, "local.properties");
    }

    //name of link must to be upper case
    public enum Links {
        UPC_PROD {
            @Override
            public String get() {
                return PropertyFile.getValue("UPC_PROD", "links.properties");
            }
        },

        SAUCEDEMO_PROD {
            @Override
            public String get() {
                return PropertyFile.getValue("Saucedemo_PROD", "links.properties");
            }
        },

        RAHUL_PROD {
            @Override
            public String get() {
                return PropertyFile.getValue("RAHUL_PROD", "links.properties");
            }
        },
        ALZA_PROD {
            @Override
            public String get() {
                return PropertyFile.getValue("ALZA_PROD", "links.properties");
            }
        },
        ALZA2_PROD {
            @Override
            public String get() {
                return PropertyFile.getValue("ALZA2_PROD", "links.properties");
            }
        },
        ALZA3_PROD {
            @Override
            public String get() {
                return PropertyFile.getValue("ALZA3_PROD", "links.properties");
            }
        },
        ALZA4_PROD {
            @Override
            public String get() {
                return PropertyFile.getValue("ALZA4_PROD", "links.properties");
            }
        },
        ;
        public abstract String get();
    }

    public enum Database {

        ROBO(
                PropertyFile.getValue("ROBODB_Link", "local.properties"),
                PropertyFile.getValue("ROBODB_Username", "local.properties"),
                PropertyFile.getValue("ROBODB_Pwd", "local.properties"),
                PropertyFile.getValue("ROBODB_Driver", "config.properties")
        ),
        ;

        private String url;
        private String user;
        private String pwd;
        private String driver;

        private Database(String url, String user, String pwd, String driver) {
            this.url = url;
            this.user = user;
            this.pwd = pwd;
            this.driver = driver;
        }

        public String getConnectionString() {
            return url;
        }

        public String getUser() {
            return user;
        }

        public String getPWD() {
            return pwd;
        }

        public String getDriver() {
            return driver;
        }
    }
}