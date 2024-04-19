package utility;

import static config.Directories.*;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import com.microsoft.edge.seleniumtools.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public enum Browser {
    FIREFOX {
        @Override
        public WebDriver open(String driverMode, String driverVersion) {
            setFirefoxDriver(driverMode, driverVersion);
                    
            FirefoxOptions ffOptions = new FirefoxOptions();
            ffOptions.setAcceptInsecureCerts(true);
            ffOptions.addArguments("--window-size=1920,1080");
                    
            WebDriver driver =  new FirefoxDriver(ffOptions);
            maximizeWindow(driver);
            return driver;
        }
    },

    FIREFOX_HEADLESS {
        @Override
        public WebDriver open(String driverMode, String driverVersion) {
            setFirefoxDriver(driverMode, driverVersion);
                    
            FirefoxOptions ffHeadlessOptions = new FirefoxOptions();
            ffHeadlessOptions.setAcceptInsecureCerts(true);
            ffHeadlessOptions.setHeadless(true);
            ffHeadlessOptions.addArguments("--window-size=1920,1080");
            
            WebDriver driver =  new FirefoxDriver(ffHeadlessOptions);
            return driver;
        }
    },

    IE {
        @Override
        public WebDriver open(String driverMode, String driverVersion) {
            setIeDriver(driverMode, driverVersion);
          
            InternetExplorerOptions ieOptions = new InternetExplorerOptions();
            ieOptions.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "about:blank");
            ieOptions.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
            ieOptions.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
            ieOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
          
            WebDriver driver = new InternetExplorerDriver(ieOptions);
            maximizeWindow(driver);
            return driver;
        }
    },

    EDGE {
        @Override
        public WebDriver open(String driverMode, String driverVersion) {
            setEdgeDriver(driverMode, driverVersion);
            
        	EdgeOptions edgeOptions = new EdgeOptions();
        	edgeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        	edgeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
           
            @SuppressWarnings("deprecation")
			WebDriver driver = new EdgeDriver(edgeOptions);
            maximizeWindow(driver);
            return driver;
        }
    },
    
    EDGE_HEADLESS {
        @Override
        public WebDriver open(String driverMode, String driverVersion) {
            setEdgeDriver(driverMode, driverVersion);
           
        	EdgeOptions edgeOptions = new EdgeOptions();
        	edgeOptions.addArguments("headless");
            edgeOptions.addArguments("disable-gpu");
        	edgeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        	edgeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        	     	         
			@SuppressWarnings("deprecation")
			WebDriver driver = new EdgeDriver(edgeOptions);
            maximizeWindow(driver);
            return driver;
        }
    },

    CHROME {
        @Override
        public WebDriver open(String driverMode, String driverVersion) {
        	setChromeDriver(driverMode, driverVersion);
            
            String downloadFilepath = FrameworkActions.getAbsolutePathOfDownloadsFolder();
            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
            
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", downloadFilepath);
            
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setExperimentalOption("prefs", chromePrefs);
            chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            chromeOptions.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            chromeOptions.addArguments("disable-extensions");
            chromeOptions.addArguments("--disable-features=VizDisplayCompositor");
            chromeOptions.addArguments("--disable-notifications");
            chromeOptions.addArguments("window-size=1920x1080");
            
            WebDriver driver = new ChromeDriver(chromeOptions);
            maximizeWindow(driver);
            
            return driver;
        }
    },
    
    CHROME_HEADLESS {
        @Override
        public WebDriver open(String driverMode, String driverVersion) {
        	setChromeDriver(driverMode, driverVersion);
            
            ChromeOptions headlessChromeOptions = new ChromeOptions();
            headlessChromeOptions.addArguments("headless");
            headlessChromeOptions.addArguments("window-size=1920x1080");
            headlessChromeOptions.addArguments("--disable-features=VizDisplayCompositor");
            
            return new ChromeDriver(headlessChromeOptions);
        }
    };
    
    protected static String driverPath = DRIVER_DIR.get();

    public abstract WebDriver open(String driverMode, String driverVersion);
       
    private static void setFirefoxDriver(String driverMode, String driverVersion) {
        System.setProperty(
                Driver.FIREFOX.getProp(), 
                FF_EXE_DIR.get());
        
    	if(driverIsOnlineNoVersionSpecified(driverMode, driverVersion)) {
    		WebDriverManager.firefoxdriver().setup();
    	}
    	if(driverIsOnlineVersionSpecified(driverMode, driverVersion)) {
    		WebDriverManager.firefoxdriver().driverVersion(driverVersion).setup();
    	}
    	if(driverIsOffline(driverMode)) {        
            System.setProperty(
                    Driver.GECKO.getProp(), 
                    driverPath + 
                    Driver.GECKO.getExe());
        }
    }
     
    private static void setChromeDriver(String driverMode, String driverVersion) {
    	if(driverIsOnlineNoVersionSpecified(driverMode, driverVersion)) {
    		WebDriverManager.chromedriver().setup();
    	}
    	if(driverIsOnlineVersionSpecified(driverMode, driverVersion)) {
    		WebDriverManager.chromedriver().driverVersion(driverVersion).setup();
    	}
    	if(driverIsOffline(driverMode)) {
            System.setProperty(
                    Driver.CHROME.getProp(), 
                    driverPath + 
                    Driver.CHROME.getExe());
    	}
    }
   
    private static void setIeDriver(String driverMode, String driverVersion) {
    	if(driverIsOnlineNoVersionSpecified(driverMode, driverVersion)) {
    		WebDriverManager.iedriver().setup();
    	}
    	if(driverIsOnlineVersionSpecified(driverMode, driverVersion)) {
    		WebDriverManager.iedriver().driverVersion(driverVersion).setup();
    	}
    	if(driverIsOffline(driverMode)) {
            System.setProperty(
                    Driver.IE.getProp(), 
                    driverPath + 
                    Driver.IE.getExe());
    	}
    }
    
    private static void setEdgeDriver(String driverMode, String driverVersion) {
    	if(driverIsOnlineNoVersionSpecified(driverMode, driverVersion)) {
    		WebDriverManager.edgedriver().setup();
    	}
    	if(driverIsOnlineVersionSpecified(driverMode, driverVersion)) {
    		WebDriverManager.edgedriver().driverVersion(driverVersion).setup();
    	}
    	if(driverIsOffline(driverMode)) {
            System.setProperty(
                    Driver.EDGE.getProp(), 
                    driverPath + 
                    Driver.EDGE.getExe());
    	}
    }
    
    private static void maximizeWindow(WebDriver driver) {
        driver.manage().window().maximize();
    }
       
    enum Driver {
        CHROME  ("webdriver.chrome.driver", "chromedriver.exe"), 
        IE      ("webdriver.ie.driver", "IEDriverServer.exe"), 
        EDGE    ("webdriver.edge.driver", "msedgedriver.exe"), 
        FIREFOX ("webdriver.firefox.bin", null),
        GECKO   ("webdriver.gecko.driver", "geckodriver.exe"),
        ;
        
        private String property;
        private String exe;
        
        private Driver(String property, String exe) {
            this.property = property;
            this.exe = exe;
        }
        
        public String getProp() {
            return property;
        }
        
        public String getExe() {
            return exe;
        }
    }  
    
    private static boolean driverIsOnlineNoVersionSpecified(String driverMode, String driverVersion) {
    	return (driverMode.equalsIgnoreCase("-") || driverMode.equalsIgnoreCase("ONLINE")) && driverVersion.equalsIgnoreCase("-");
    }
    
    private static boolean driverIsOnlineVersionSpecified(String driverMode, String driverVersion) {
    	return !driverMode.equalsIgnoreCase("-") && driverMode.equalsIgnoreCase("ONLINE") && !driverVersion.equalsIgnoreCase("-");
    }
    
    private static boolean driverIsOffline(String driverMode) {
    	return !driverMode.equalsIgnoreCase("-") && driverMode.equalsIgnoreCase("OFFLINE");
    }
}