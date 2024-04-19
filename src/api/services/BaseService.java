package api.services;

import static io.restassured.RestAssured.urlEncodingEnabled;
import java.io.PrintStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.output.WriterOutputStream;
import com.cedarsoftware.util.io.JsonWriter;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utility.Report;

public class BaseService {
    
    protected static StringWriter writer;
    protected static PrintStream captor;   
    protected String serviceName;
    protected Response response;
    protected Object responseBody;
    protected List<?> responselist;   

    protected RequestSpecification getJsonRequestSpec() {
        writer = new StringWriter();
        captor = new PrintStream(new WriterOutputStream(writer, Charset.defaultCharset()), true);
        
        return  RestAssured.
                            given().
                            config(RestAssured.config().logConfig(new LogConfig(captor, true))).
                            urlEncodingEnabled(urlEncodingEnabled).
                            log().body().
                            contentType(ContentType.JSON).
                            accept(ContentType.JSON);
    }
    
    protected RequestSpecification getXmlFormUrlRequestSpecification() {
        writer = new StringWriter();
        captor = new PrintStream(new WriterOutputStream(writer, Charset.defaultCharset()), true);
        
        return RestAssured.
                           given().
                           header("Content-Type", "application/x-www-form-urlencoded").
                           config(RestAssured.config().logConfig(new LogConfig(captor, true))).
                           urlEncodingEnabled(urlEncodingEnabled).
                           log().body();
    }
    
    protected void logRequestToReport() {
        String msg = writer.toString();
        String req = String.format(REQUEST_RESPONSE_TEMPLATE, msg);
        Report.addListToCollapsibleLog(REQUEST_HEADER, LogStatus.INFO, 
                new ArrayList<> (Arrays.asList(req)));
    }
    
    protected void logResponseToReport() {
        String msg = "";
        
        try {
            msg = JsonWriter.
                             formatJson(response.asString()).
                             replace("\t", "&nbsp;").
                             replace("    ", "&nbsp;");
        }
        catch (com.cedarsoftware.util.io.JsonIoException e) {
            msg = response.asString();
        }
        String resp = String.format(REQUEST_RESPONSE_TEMPLATE, msg);
        Report.addListToCollapsibleLog(RESPONSE_HEADER, LogStatus.INFO, 
                new ArrayList<> (Arrays.asList(resp)));
    }
    
    protected void logResponseTimeToReport() {
        long time = response.getTimeIn(TimeUnit.MILLISECONDS);
        Report.logInfoMessage(String.format(RESPONSE_TIME_MSG, time));
    }
        
    protected void validateResponseCode(Class<?> respObject, int expected) throws Exception {
        int actualStatus = response.getStatusCode();
        
        if (actualStatus != expected) {
            throw new Exception(String.format(ERROR_MSG, serviceName, actualStatus));
        }
        else { 
            responseBody = response.as(respObject);       
        }
    }
    
    protected void validateArrayResponseCode(Class<?> respObject, int expected) throws Exception {
        int actualStatus = response.getStatusCode();
        
        if (actualStatus != expected) {
            throw new Exception(String.format(ERROR_MSG, serviceName, actualStatus));
        }
        else { 
            responselist = Arrays.asList(response.getBody().as(respObject));        
        }
    }
    
    private final String REQUEST_HEADER = "Request";
    private final String RESPONSE_HEADER = "Response";
    private final String REQUEST_RESPONSE_TEMPLATE = "<pre>%s</pre>";
    private final String RESPONSE_TIME_MSG = "Response time: %s";
    private final String ERROR_MSG = "API call failed. Service: %s. Response code: %s";
}