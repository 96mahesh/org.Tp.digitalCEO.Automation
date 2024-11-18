package common_Framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.SecureRandom;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base
{
	

	public BrowserManager browser = new BrowserManager(); 
	public DropdownManager dropDown = new DropdownManager();
	public NonHTMLManager nonHTML = new NonHTMLManager();
	public Date_Management date = new Date_Management();
	public ExcelManager excel = new ExcelManager();
	public Logger logger = new Logger();
	public FieldValidations fieldValidate = new FieldValidations();
	
	
	//***************************************Original Base*********************************************************************************
	
	/* Declare object of RemoteWebDriver for the Page Class 
	 * and use related AccessModifier to it */
	public RemoteWebDriver driver;
	public Properties propertiesFile;
	 

	/* Create Constructor Method to this Page Class
	 * and add Parameter/Argument as driver object */

	public RemoteWebDriver openBrowser()
	{

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		//driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
		return driver;
	}

	public void launchSite(String URL) throws Exception
	{
		waitTill(5);
		driver.get(URL);
		waitTill(7);
	}
	
	public void loadPropertiesFile(String FullPathof_PropertiesFile) throws Exception
	{
		/* Create object to Properties class 
		 * load properties file
		 * get Run Data from the properties file */
		
		propertiesFile = new Properties();
		//String directory = System.getProperty("user.dir");
		FileInputStream fip = new FileInputStream(FullPathof_PropertiesFile);
		propertiesFile.load(fip);
	}
	
	public String getPropertiesFromPropertiesFile(String propertyName)
	{
		String property = propertiesFile.getProperty(propertyName);
		return property;
	}
	
	public void waitTill(String timeInSeconds) throws Exception
	{
		String initialTime = timeInSeconds;
		int convertedTime = Integer.parseInt(initialTime);
		Thread.sleep(convertedTime);
	}
	
	public void waitTill(int timeInSeconds) throws Exception
	{
		int millisTime = timeInSeconds*1000;
		Thread.sleep(millisTime);
	}
	
	public long calculateRunTime(long startTime, long endTime)
	{
		long totalTime = endTime-startTime;
		totalTime = totalTime/1000;
		return totalTime;
	}
	
	public void closeBrowser()
	{
	//close
	driver.quit();
	}
	
	//************************************End Original Base********************************************************************************
	
	
	
	public void launch(RemoteWebDriver driver, String url) throws Exception {
		driver.get(url);
		sleep(5);
	}

	//Gives us the currently working page URL address
	public String getPageURL(RemoteWebDriver driver) {
		String currURL = driver.getCurrentUrl();
		return currURL;
	}

	//Gives the currently working page Title
	public String getCurrentPageTitle(RemoteWebDriver driver) {
		String currTitle = driver.getTitle();
		return currTitle;
	}

	//Gives the single window-handle of newly opened tab
	public String getSingleWindowHandle(RemoteWebDriver driver) {
		String win = driver.getWindowHandle();
		return win;
	}

	//Gives all window-handles of opened tabs
	public List<String> getAllWindowHandles(RemoteWebDriver driver) {
		Set<String> wins = driver.getWindowHandles();
		List<String> allWins = new ArrayList<String>(wins);
		return allWins;
	}

	//Close the currently working tab
	public void closeCurrent(RemoteWebDriver driver) {
		driver.close();
	}

	//Close all tabs in current browser
	public void closeAll(RemoteWebDriver driver) {
		driver.quit();
	}

	public void sleep(int inSeconds) throws Exception
	{
	     Thread.sleep(1000*inSeconds);
	}
	
	public void navigate(String PageToNavigate) throws Exception {
		driver.navigate().to(PageToNavigate);
		sleep(3);
	}
	
	public void highlightElement(WebElement ElementToHighlight) throws Exception {
		driver.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 4px solid red;');", ElementToHighlight);
		sleep(3);
	}
	
	public String getMessageFromAlert(RemoteWebDriver driver) {
		String alertMessage = driver.switchTo().alert().getText();
		return alertMessage;
	}
	
	//To highlight an element 
	public void highlightElement(RemoteWebDriver driver, WebElement ElementToHighlight) throws Exception {
	//	driver.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 4px solid red;');", ElementToHighlight);
		driver.executeScript("arguments[0].setAttribute('style', 'background: #A3EC4E; border: 4px solid red;');", ElementToHighlight);
	//	jsExec.executeScript("arguments[0].setAttribute('style', 'background: #A3EC4E; border: 2px #F9A120');", driver.findElement(locator));

		sleep(3);
	}
	
	public void navigate(RemoteWebDriver driver, String PageToNavigate) throws Exception {
		driver.navigate().to(PageToNavigate);
		//sleep(3);
	}
	
	public void refreshPage(RemoteWebDriver driver) throws Exception {
		driver.navigate().refresh();
		sleep(3);
	}
	
	public String getPageId(RemoteWebDriver driver) {
		String pageURL = getPageURL(driver);
		String[] url = pageURL.split("=");
		return url[1];
	}
	public void copyTextToClipboard(String text) {
		StringSelection selection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);
		logger.log("Text " + text + " is copied to Clipborad");
	}
	
	/*public void getRegEx(Strinv)
	{
		Pattern.compile(regex)
	}*/

}

