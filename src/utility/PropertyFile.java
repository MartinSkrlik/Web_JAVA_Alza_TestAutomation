package utility;

import static utility.Constant.Encodings.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertyFile {
    
    public static String getValue(String key, String file) {
        
        Properties properties = new Properties();
        InputStream input = 
            PropertyFile.
                         class.
                         getClassLoader().
                         getResourceAsStream(file);
        try {
            InputStreamReader reader = new InputStreamReader(input, UTF_8.get());
            properties.load(reader);
        }
        catch (Exception e) {
            Log.error(String.format(ERROR_MSG, e.getMessage()));
        }
        return properties.getProperty(key);
    }
    
    private final static String ERROR_MSG = "Unable to read the property file! Exception: %s";
}