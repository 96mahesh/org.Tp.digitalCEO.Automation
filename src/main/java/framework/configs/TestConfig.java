 package framework.configs;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.log4testng.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestConfig {
	static Logger LOGGER = Logger.getLogger(TestConfig.class);
	
	private static TestConfig testConfig;
	private static Config config;
	private String testModulesPath;
	private String browser;
	private String appURL;
	private String appUserId;
	private String appPWD;
	private String reportPath;
	private static String ENVName;
	private int FailureRetryCount;
	private String recordingFlag;
	private String MINWAITTIME;
	private String AVGWAITTIME;
	private String MAXWAITTIME;
	private String OBJWAITTIME;
	private String scriptTimeoutSecounds;
	private static String moduleName;
	private static String projDir;
	private static String testSuiteName;
	
	private TestConfig() {		
	}
	
	static {
		try {
			if(System.getProperty("ENVName") == null) {
				ENVName = "TEST";
			}else {
				ENVName = System.getProperty("ENVName");
			}
			projDir = System.getProperty("user.dir");
			config = new Config(projDir + "/src/main/resources/Test_Config/"+ ENVName +"_config.properties");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		PropertyConfigurator.configure("log4j.properties");
		if(System.getProperty("moduleName")==null) {
			moduleName = config.getPropertyValue("moduleName");
		}else {
			moduleName = config.getPropertyValue("moduleName");
		}
	}
	
	public static TestConfig getInstance() {
		if(testConfig == null) {
			testConfig = new TestConfig();
		}
		return testConfig;
	}
	
	public static Config getConfig() {
		return config;
	}
	
	public String getTestModulesPath() {
		return testModulesPath;
	}
	
	public String getBrowser() {
		return browser;
	}
	
	public String getAppURL() {
		return appURL;
	}
	
	public String getAppUserId() {
		return appUserId;
	}
	
	public String getAppPWD() {
		return appPWD;
	}
	
	public String getReportPath() {
		return reportPath;
	}
	
	public String getExecEnvironment() {
		return ENVName;
	}
	
	public int getFailureRetryCount() {
		return FailureRetryCount;
	}
	
	public String getRecordingFlag() {
		return recordingFlag;
	}
	
	public String getMINWAITTIME() {
		return MINWAITTIME;
	}
	
	public String getAVGWAITTIME() {
		return AVGWAITTIME;
	}
	
	public String getMAXWAITTIME() {
		return MAXWAITTIME;
	}
	
	public String getOBJWAITTIME() {
		return OBJWAITTIME;
	}
	
	public String getScriptTimeoutSecounds() {
		return scriptTimeoutSecounds;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public String getProjDir() {
		return projDir;
	}
	
	public String getTestSuiteName() {
		return testSuiteName;
	}
	
	//Framework Initialization
	public void suiteSetup() {
		try {
			frameworkSetup();			
		}catch(Exception e) {
			LOGGER.error("Exception " + e.getClass().getName() + " caught from suitesetup menthod");
		}
	}
	
	public WebDriver createDriverInstance(String browserType) {
		WebDriver driver = null;
		browserType =browserType.toUpperCase();
		ChromeOptions options = null;
		switch(browserType) {
			case "CHROME":
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				break;
			case "CHROMEHEADLESS":
				WebDriverManager.chromedriver().setup();
				options = new ChromeOptions();
				options.addArguments("headless");
				options.addArguments("--no-sandbox");
				options.addArguments("window-size=1920,1080");
				options.addArguments("--start-fullscreen");
				driver = new ChromeDriver(options);
				break;		
		}
		driver.manage().timeouts().setScriptTimeout(Long.parseLong(TestConfig.getInstance().getScriptTimeoutSecounds()), TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(Long.parseLong(TestConfig.getInstance().getAVGWAITTIME()), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Long.parseLong(TestConfig.getInstance().getScriptTimeoutSecounds()), TimeUnit.SECONDS);
		
		return driver;
	}

	private void frameworkSetup() throws IOException  {
		browser = config.getPropertyValue("browser");
		appURL = config.getPropertyValue("appURL");
		appUserId = config.getPropertyValue("appUserId");
		appPWD = config.getPropertyValue("appPWD");
		reportPath = config.getPropertyValue("reportPath");
		ENVName = config.getPropertyValue("ENVName");
		testSuiteName = config.getPropertyValue("testSuiteName");
		FailureRetryCount = Integer.parseInt(config.getPropertyValue("FailureRetryCount"));
		recordingFlag = config.getPropertyValue("recordingFlag");
		MINWAITTIME = config.getPropertyValue("MINWAITTIME");
		AVGWAITTIME = config.getPropertyValue("AVGWAITTIME");
		MAXWAITTIME = config.getPropertyValue("MAXWAITTIME");
		OBJWAITTIME = config.getPropertyValue("OBJWAITTIME");
		scriptTimeoutSecounds = config.getPropertyValue("scriptTimeoutSecounds");		
	}
	
}
