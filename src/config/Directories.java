package config;

import utility.PropertyFile;

public enum Directories {    
    USER_DIR ("user.dir"),
    SCR_DIR("scr.folder"),
    DATA_DIR (PropertyFile.getValue("Path_TestData", "config.properties")),
    REPORT_FOLDER_DIR (PropertyFile.getValue("Path_Report", "config.properties")),
    REPORT_CONFIG_DIR (PropertyFile.getValue("ReportConfig", "config.properties")),
    FOLDER_DIR (PropertyFile.getValue("Path_Folder", "config.properties")),
    DRIVER_DIR (PropertyFile.getValue("Path_Driver", "config.properties")),
    FF_EXE_DIR (PropertyFile.getValue("Path_Firefox", "config.properties")),
    EDGE_EXE_DIR (PropertyFile.getValue("Path_Edge", "config.properties")),
    DOWNLOADS_DIR (PropertyFile.getValue("Path_Download", "config.properties")),
    ;

    private String value;

    Directories (String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }
} 