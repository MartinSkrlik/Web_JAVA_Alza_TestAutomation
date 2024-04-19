package utility;

import static config.Directories.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.github.javafaker.Faker;
import com.relevantcodes.extentreports.LogStatus;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class FrameworkActions extends Exceptions {

    private final static Faker faker = new Faker();
    
    protected static String getDate(String format, long plusMinus, ChronoUnit... unit) {
        LocalDateTime date = LocalDateTime.now();
        
        if (plusMinus != 0) {
            date = date.plus(plusMinus, unit[0]);
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);       
        return date.format(formatter);
    }

    protected void takeScreenshot(WebDriver driver, String name) {
        try {
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, getScreenshotFile(name));
        } 
        catch (Exception e) {
           processException(e);
        } 
    }

    protected void takeFullscreenScreenshot(WebDriver driver, String name) {
        try {
            Screenshot screenshot = 
                    new AShot().
                    shootingStrategy(ShootingStrategies.viewportPasting(1000)).
                    takeScreenshot(driver);
            
            ImageIO.write(screenshot.getImage(), "PNG", getScreenshotFile(name));
        } 
        catch (Exception e) {
            processException(e);
        } 
    }
    
    protected static String getFileInFolder(String testName, String extension) {
        return createFolder(testName) 
                + "/" + getDate("yyyy-MM-dd_HH-mm-ss", 0)
                +"." + extension;
    }
    
    protected static Path getRelativePath(String screenshotName) {  
        String basePath = System.getProperty(SCR_DIR.get());
        Path pathAbsolute = Paths.get(basePath + "/" + screenshotName);
        Path pathBasic = Paths.get(basePath);

        return pathBasic.relativize(pathAbsolute);
    }
    
    private File getScreenshotFile(String name) {
        return new File(System.getProperty(SCR_DIR.get()) + "/" + name);
    }
    
    private static String createFolder(String tcName) { 
        String folder = FOLDER_DIR.get()
                      + new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(
                        Calendar.getInstance().getTime()).toString()
                      + "_" 
                      + tcName;

        new File(folder).mkdir();
        System.setProperty(SCR_DIR.get(), folder);
        return folder;
    }
      
 /**
 * Retrieves only part of the string specified by REGEX
 * @param regex - regex expression
 * @param fullText - original String to crop
 * 
 */
        
    public static String cropString(String regex, String fullText) {
        Pattern regexPrice = Pattern.compile(regex);
        Matcher matcher = regexPrice.matcher(fullText);
            
        while (matcher.find()) {
            return matcher.group().toString();
        }
        return "";
    }

    public static String getAbsolutePathOfDownloadsFolder() {
        String path = new File("").getAbsolutePath() + DOWNLOADS_DIR.get();
        Log.info("Path of downloads folder: " + path);
        return path;
    }
    
    public static void logMapToReportMonospace(LogStatus logStatus, Map<String, Object> input, String header) {
        ArrayList<String> list        = new ArrayList<String>();
        ArrayList<Integer> lenghtList = new ArrayList<Integer>();
        
        input.entrySet().forEach
        (entry -> {lenghtList.add(entry.getKey().length());});
        int expectedLength = Collections.max(lenghtList);
        Log.info("------------------------------------------------------------------------------------------------------------------------------------------------------");
        Log.info(header);
        input.entrySet().forEach
            (entry -> {list.add(setDesiredLengthOfString(entry.getKey(), expectedLength) + ": " + entry.getValue()); 
                       Log.info(setDesiredLengthOfString(entry.getKey(), expectedLength) + ": " + entry.getValue());});
        Log.info("------------------------------------------------------------------------------------------------------------------------------------------------------");
        Report.addListToCollapsibleLog(header, logStatus, list);
    }
    
    private static String setDesiredLengthOfString(String stringToFormat, Integer expectedLenght) {
    	int length = stringToFormat.length();
    	for(;length < expectedLenght; length++) {
    		stringToFormat = stringToFormat + ".";
    	}
    	return stringToFormat;	
    }
    
    protected static int generateRandomNumberInRange(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
    
    protected static String generateFakeName() {
        return faker.name().fullName();
    }
    
    protected static String generateFakeEmail() {
        return faker.internet().emailAddress();
    }
    
    protected static String generateFakeMobile() {
        return faker.numerify("09########");
    } 
}