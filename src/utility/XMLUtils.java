package utility;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.io.FilenameUtils;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XMLUtils {
    
    public String getXmlAttribute(String File, String Tagname) throws Exception {

        String value = "";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(new File(File));
        NodeList nodeList = document.getElementsByTagName(Tagname);
        
       for (int x = 0, size = nodeList.getLength();
                x < size; x++) {
            
            value = nodeList.item(x).getAttributes()
                                    .getNamedItem("value")
                                    .getNodeValue().toString();
            Log.info("XML Attribute value: " + value); 
        }
        return value;
    }
    
    public String getNameOfCurrentXml (ITestContext context) {
        XmlSuite suiteName = context.getCurrentXmlTest().getSuite(); 
        String testngfilepath = suiteName.getFileName().toString();
        String testngfile = FilenameUtils.getName(testngfilepath);
    
        Log.info("Current running TestNG file: " + testngfile);
        return testngfile;
    }
}