package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.relevantcodes.extentreports.LogStatus;
import com.github.underscore.lodash.U;

/*
 * In order to call readValue(String yamlValues) use format from following example: 
 * YamlFileReader.readValue("yamldataTemplate:dev.test.data"); - this reads specified value from YAML file
 * In order to call jsonToMap(String yamlValues, HashMap<String, Object> map3) use format from following example: 
 * YamlFileReader.jsonToMap("yamlDataTemplate:sit", globalParametersMap); - this reads value block from YAML file and adds them to the specified map and creates a log
 */

public class YamlFileReader {
	
	public static String readValue(String yamlValues) {
        String value = "";  
		try {
     	    String splitBy          = "\\:";
            List<String> list       = Arrays.asList(yamlValues.split(splitBy));
            String fileName         = list.get(0);
            String parameterPath    = list.get(1);
	        String yamlToJsonFormat = yamlToJson(fileName);
	        value                   = readValueFromJson(yamlToJsonFormat, parameterPath);
		}
        catch (Exception e) {
        	System.out.println("YAML file can not be read");
            e.printStackTrace();
        }
		return value;
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void valuesToMap(String yamlValues, HashMap<String, Object> map) {
 	    String splitBy          = "\\:";
        List<String> list       = Arrays.asList(yamlValues.split(splitBy));
        String fileName         = list.get(0);
        String parameterPath    = list.get(1);
        String yamlToJsonFormat = yamlToJson(fileName);
        Map<String, Object> jsonInput  =new HashMap<String, Object>(); 
        jsonInput.putAll((Map)U.get((Map<String, Object>)U.fromJson(yamlToJsonFormat), parameterPath));
        map.putAll(jsonInput);
        FrameworkActions.logMapToReportMonospace(LogStatus.INFO, jsonInput, "Input from YAML file");
    }
    
	private static String yamlToJson(String fileName) {
		String json             = "";
		InputStream inputStream = getInputStream(fileName);
        final Yaml yaml         = new Yaml();
        final Object loadedYaml = yaml.load(inputStream);
		Gson gson               = new GsonBuilder().setPrettyPrinting().create();
		json                    = gson.toJson(loadedYaml, LinkedHashMap.class);
        return json;
    }
	
	private static String readValueFromJson(String yamltoJsonFormat, String parameterPath) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(yamltoJsonFormat));
		StringBuilder sb  = new StringBuilder();
		String line       = br.readLine();
		while (line != null) {
		    sb.append(line);
		    sb.append(System.lineSeparator());
		    line = br.readLine();
		}
		br.close();
		String jsonInput  = sb.toString();		
		Object document   = Configuration.defaultConfiguration().jsonProvider().parse(jsonInput);		
		String value      = JsonPath.read(document, parameterPath);
		return value;
	}
	
	private static InputStream getInputStream(String fileName) {
    	InputStream inputStream     = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            InputStream inputStreamYaml = classLoader.getResource(fileName + ".yaml") != null ? new FileInputStream(new File(classLoader.getResource(fileName + ".yaml").getFile())) : null;
            InputStream inputStreamYml  = classLoader.getResource(fileName + ".yml")  != null ? new FileInputStream(new File(classLoader.getResource(fileName + ".yml").getFile()))  : null;
            InputStream inputStreamJson = classLoader.getResource(fileName + ".json") != null ? new FileInputStream(new File(classLoader.getResource(fileName + ".json").getFile())) : null;
            
            if(inputStreamYaml != null) {
            	inputStream = inputStreamYaml;
            }
            if(inputStreamYml != null) {
            	inputStream = inputStreamYml;
            }
            if(inputStreamJson != null) {
            	inputStream = inputStreamJson;
            }
        } 
        catch (Exception e) {
        	System.out.println("YAML file can not be loaded");
            e.printStackTrace();
        }
        return inputStream;
    }   
}