package common_Framework;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Wrapper {

	RemoteWebDriver driver;
	String BaseURL = "http://bergenrtipa.eastus.cloudapp.azure.com:18080/tetrusespn/#"; //Replace the String value with required URL
	private String item;
	private String expected;
	private File f;
	private FileReader fr;
	private Properties p;
	private FileInputStream fis;
	public Sheet sh;
	String expectedFieldName;
	int expectedFieldValueNumeric;
	String expectedFieldValue;

	public String addParticipantExcelFile = System.getProperty("user.dir")+"\\src\\test\\resources\\testdata\\RTIP_AddParticipant.xlsx";
	public String excelFilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\testdata\\RTIP_TestDataFinal.xlsx";
	public String propertiesFilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\testdata.properties";
	public String juvenilexcelFile = System.getProperty("user.dir")+"\\src\\test\\resources\\DataFromExcel\\juvenile_TestData.xlsx";

	public FluentWait fWait;
	public WebDriverWait w;

	public Wrapper(RemoteWebDriver driver, long maxTimeInSeconds) {
		w = new WebDriverWait(driver, maxTimeInSeconds);
		fWait = new FluentWait(maxTimeInSeconds);
	}




	//Gives us the currently working page URL address	-1
	public String getPageURL() {
		String currURL = driver.getCurrentUrl();
		return currURL;
	}

	//Gives the currently working page Title			-2
	public String getCurrentPageTitle() {
		String currTitle = driver.getTitle();
		return currTitle;
	}

	//Gives the single window-handle of newly opened tab	-3
	public String getSingleWindowHandle() {
		String win = driver.getWindowHandle();
		return win;
	}

	//Gives all window-handles of opened tabs				-4
	public List<String> getAllWindowHandles() {
		Set<String> wins = driver.getWindowHandles();
		List<String> allWins = new ArrayList<String>(wins);
		return allWins;
	}

	//Holds execution for given time						-5
	public void sleep(int inSeconds) throws Exception
	{
		Thread.sleep(1000*inSeconds);
	}

	//Navigates to expected page							-6
	public void navigate(String PageToNavigate) throws Exception {
		driver.navigate().to(BaseURL+PageToNavigate);
		sleep(3);
	}

	//To Highlight specific Element of the page				-7
	public void highlightElement(WebElement ElementToHighlight) throws Exception {
		driver.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 4px solid red;');", ElementToHighlight);
		sleep(3);
	}

	//To highlight an element 								-8
	public void highlightElement(RemoteWebDriver driver, WebElement ElementToHighlight) throws Exception {
		driver.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 4px solid red;');", ElementToHighlight);
		sleep(3);
	}

	//To select Year from the Calendar - this is useful when we want to select year from drop-down					-9
	public void yearPicker(String expectedYear, WebElement yearDropdown, List<WebElement> years) throws Exception
	{
		yearDropdown.click();
		Thread.sleep(5000);
		Select s = new Select(yearDropdown);

		years = s.getOptions();
		for(int i=0;i<years.size();i++)
		{
			String actualYear = years.get(i).getText();
			if(expectedYear.equalsIgnoreCase(actualYear))
			{
				years.get(i).click();
				Thread.sleep(5000);
				break;
			}
		}
	}

	//To select Month from the Calendar						-10
	public void monthPicker(String expectedmonth, WebElement monthDropdown, List<WebElement> months ) throws Exception
	{
		monthDropdown.click();
		Thread.sleep(5000);
		Select s= new Select(monthDropdown);

		months	=s.getOptions();
		for(int i=0;i<months.size();i++)
		{
			String actualMonth = months.get(i).getText();
			System.out.println(actualMonth);
			if(expectedmonth.equalsIgnoreCase(actualMonth))
			{
				months.get(i).click();
				Thread.sleep(5000);
				break;
			}
		}
	}

	//To select Date from the Calendar						-11
	public void datePicker(String expectedDate, List<WebElement> rowsofMonth, List<WebElement> coloumnofWeek ) throws Exception
	{
		for(int i=0;i<rowsofMonth.size();i++)
		{
			for(int j=0;j<coloumnofWeek.size();j++)
			{
				String actualDate = coloumnofWeek.get(j).getText();
				System.out.println(actualDate);
				if(expectedDate.equalsIgnoreCase(actualDate))
				{
					coloumnofWeek.get(j).click();
					Thread.sleep(3000);
				}
			}
		}
	}

	//To select list-item from the drop-down				-12
	public void selectItem(List<WebElement> dropdownlistElement, String expecteditem) throws Exception
	{
		//System.out.println(dropdownlistElement.size());
		for(int i=0; i<dropdownlistElement.size(); i++)
		{
			item = dropdownlistElement.get(i).getText();
			if(item.equalsIgnoreCase(expecteditem))
			{
				dropdownlistElement.get(i).click();
				Thread.sleep(2000);
				//System.out.println("dropdown item selected");
			}
		}
	}

	//To click the check-box from the Drop-down items		-13
	public void selectItem(List<WebElement> dropdownListElement, List<WebElement> dropdownTextElement, String expecteditem) throws Exception
	{
		Thread.sleep(3000);
		System.out.println(dropdownListElement.size());
		System.out.println(dropdownTextElement.size());
		for(int i=0; i<dropdownListElement.size(); i++)
		{
			item = dropdownTextElement.get(i).getText();
			if(item.trim().equalsIgnoreCase(expecteditem))
			{
				dropdownListElement.get(i).click();
				Thread.sleep(2000);
				//System.out.println("dropdown item selected");
			}
		}
	}

	//To select the item from the Drop-down items			-14
	public void selectItem(WebElement dropdown, List<WebElement> dropdownListElement, List<WebElement> dropdownTextElement,String expectedTitle, String expecteditem) throws Exception
	{
		for(int i=0; i<dropdownTextElement.size(); i++)
		{
			String title = dropdownTextElement.get(i).getText();
			if(title.trim().equalsIgnoreCase(expectedTitle))
			{
				dropdown.click();
				for(int j=0;j<dropdownListElement.size();j++) 
				{
					String item = dropdownListElement.get(j).getAttribute("aria-label");
					if(item.trim().equalsIgnoreCase(expecteditem))
					{
						dropdownListElement.get(j).click();
						Thread.sleep(2000);
						break;
					}
				}
			}
		}
	}

	//To select required radio-button						-15
	public void selectRadioButton(List<WebElement> radioButton, String expectedRadioButtonLabel) {
		for(int i=0;i<radioButton.size();i++)
		{
			String actualRadioButtonLabel = radioButton.get(i).getText();
			if(expectedRadioButtonLabel.equalsIgnoreCase(actualRadioButtonLabel))
			{
				radioButton.get(i).click();
			}
		}
	}

	//Srinivasa Rao Rachavarapu - 24-Oct-2022
	//To select required radio-button						-15
	public void selectRadioButton(List<WebElement> radioButtonLabel, List<WebElement> radioButton, String expectedRadioButtonLabel) {
		for(int i=0;i<radioButton.size();i++)
		{
			String actualRadioButtonLabel = radioButtonLabel.get(i).getText();
			if(expectedRadioButtonLabel.equalsIgnoreCase(actualRadioButtonLabel))
			{
				radioButton.get(i).click();
			}
		}
	}

	//To check whether the field is having "*"(Asterisk)	-16
	public boolean isMandatory(WebElement Element) {
		boolean field = false;
		String isMan = Element.getText();
		if(isMan.contains("*"))
		{
			field = true;
		}
		return field;
	}

	//To check whether the field is editable or not			-17
	public boolean isEditable(WebElement Element) {
		boolean field = false;
		String fieldTag = Element.getTagName();
		if(fieldTag.contains("input"))
		{
			field = true;
		}
		return field;
	}

	//To check whether the field is empty to get filled		-18
	public boolean isEmpty(WebElement Element) {
		boolean field = false;
		String fieldFill = Element.getAttribute("class");
		if(!fieldFill.contains("filled") || !fieldFill.contains("valid"))
		{
			field = true;
		}
		return field;
	}

	//To check whether the Element is clickable or not		-19
	public boolean isClickable(WebElement Element) {
		boolean field = false;
		String fieldTag = Element.getTagName();
		if(fieldTag.contains("a") || fieldTag.contains("base"))
		{
			field = true;
		}
		return field;
	}

	//To check whether the required element is present or not	-20
	public boolean isPresent(WebElement Element) {
		boolean field = false;
		if(Element.isDisplayed())
		{
			field = true;
		}
		return field;
	}

	//To upload file from local machine(Windows OS only)		-21
	public void uploadSystemFile(String systemfile) throws Exception
	{
		StringSelection ss = new StringSelection(systemfile);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.delay(3000);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
	}

	//To scroll page, to the bottom 							-22
	public void pageScrollDown(RemoteWebDriver driver)
	{
		driver.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	//To scroll down to specific height to the bottom in page 	-23
	public void pageScrollDown(RemoteWebDriver driver, int height)
	{
		driver.executeScript("window.scrollTo(0, height)" );
	}

	//To scroll down with in the page to a specific scroll-window-24
	public void elementScrollDown(RemoteWebDriver driver, WebElement element)
	{
		String x = driver.executeScript("arguments[0].scrollHeight;", element).toString();
		driver.executeScript("arguments[0].scrollTop="+x+";", element);
	}

	//To scroll up the page to top								-25
	public void pageScrollUp(RemoteWebDriver driver)
	{
		driver.executeScript("window.scrollTo(document.body.scrollHeight, 0)" );
	}

	//To scroll up to specific height to the top in page		-26
	public void pageScrollUp(RemoteWebDriver driver, int height)
	{
		driver.executeScript("window.scrollTo(height, 0)" );
	}

	//To scroll up with in the page to a specific scroll-window -27
	public void elementScrollUp(RemoteWebDriver driver, WebElement element)
	{
		driver.executeScript("arguments[0].scrollTop=0;", element);
	}

	//To scroll left in page									-28
	public void pageScrollLeft(RemoteWebDriver driver)
	{
		driver.executeScript("window.scrollBy(0, document.body.scrollWidth)" );
	}

	//To scroll left to specific width in page					-29
	public void pageScrollLeft(RemoteWebDriver driver, int width)
	{
		driver.executeScript("window.scrollTo(0, width)" );
	}

	//To scroll left with in the page to a specific scroll-window-30
	public void elementScrollLeft(RemoteWebDriver driver, WebElement element)
	{
		driver.executeScript("arguments[0].scrollLeft=0;", element);
	}
	//To scroll right in page									-31
	public void pageScrollRight(RemoteWebDriver driver)
	{
		driver.executeScript("window.scrollBy(document.body.scrollWidth, 0)" );
	}

	//To scroll right to specific width in page					-32
	public void pageScrollRight(RemoteWebDriver driver, int width)
	{
		driver.executeScript("window.scrollTo(width, 0)" );
	}

	//To scroll right with in the page to a specific scroll-window	-33
	public void elementScrollRight(RemoteWebDriver driver, WebElement element)
	{
		String x = driver.executeScript("arguments[0].scrollWidth;", element).toString();
		driver.executeScript("arguments[0].scrollLeft="+x+";", element);
	}

	//To scroll to a specific element in page						-34
	public void scrollToElement(RemoteWebDriver driver, WebElement element)
	{
		driver.executeScript("arguments[0].scrollIntoView()", element );
	}

	//To generate string randomly for input							-35
	public String randomStringGenerator(int requiredLength, String rootString) 
	{
		return new SecureRandom().ints(requiredLength, 0, rootString.length()).mapToObj(rootString::charAt).map(Object::toString)
				.collect(Collectors.joining());
	}

	//To get test data from properties file using HashMap, add required property and property-value in properties file	-36
	public HashMap getTestDataFromPropertiesFile() throws Exception
	{
		HashMap<String, String> hm= new HashMap<String, String>();
		f=new File(propertiesFilePath);
		fr=new FileReader(f);
		p=new Properties();
		p.load(fr);
		System.out.println(p.getProperty("URL")+"from prop file");
		String url = p.getProperty("url");
		System.out.println(url+"String Storage");
		String unp = p.getProperty("username");
		String pwdp = p.getProperty("password");
		String bname = p.getProperty("browser");
		String userurl = p.getProperty("UserURL");
		String agencyurl = p.getProperty("AgencyURL");
		String auditurl = p.getProperty("AuditLogsURL");
		String formurl = p.getProperty("FormURL");
		String rtaurl = p.getProperty("RTAURL");
		String concentfile = p.getProperty("concentFilePath");
		hm.put("p_url", url);
		hm.put("p_username", unp);
		hm.put("p_password", pwdp);
		hm.put("p_browser", bname);
		hm.put("p_userurl", userurl);
		hm.put("p_agency", agencyurl);
		hm.put("p_auditurl", auditurl);
		hm.put("p_formurl", formurl);
		hm.put("p_rtaurl", rtaurl);
		hm.put("p_file", concentfile);
		return hm;
	}

	//To get test-data from excel file of any given sheet					-37
	public List getTestDataFromExcelToList(String sheetName) throws Exception
	{
		List systems = new ArrayList();
		f=new File(excelFilePath);
		fis= new FileInputStream(f);
		Workbook wb= WorkbookFactory.create(fis);
		Sheet sh=wb.getSheet(sheetName);
		int usedRows=sh.getPhysicalNumberOfRows();
		int usedColumns= sh.getRow(1).getLastCellNum();

		int j = 1;
		for(int i=1;i<usedRows; i++)
		{
			try
			{
				String system = sh.getRow(i).getCell(0).getStringCellValue();
				systems.add(system);
			}
			catch(Exception e)
			{
				System.out.println("Data not found");
			}
		}
		return systems;
	}

	//To fill test-data into expected field									-38
	public String setFieldValue(String expectedData, String expectedSheet, String expectedFile) throws Exception {

		FileInputStream fi = new FileInputStream(expectedFile); //HSSF.xls & XSSF.xlsx 	HTML Spread Sheet File - HSSF	XML Spread Sheet File - XSSF
		Workbook wb = WorkbookFactory.create(fi);
		sh = wb.getSheet(expectedSheet);
		int usedRows = sh.getPhysicalNumberOfRows();
		int usedColumns = sh.getRow(0).getLastCellNum();

		for(int i=1; i<usedRows; i++) //i=1 
		{
			try {
				expectedFieldName = sh.getRow(i).getCell(0).getStringCellValue();
				expectedFieldValue = sh.getRow(i).getCell(1).getStringCellValue();
			}catch(Exception e) {
				expectedFieldValueNumeric = (int) sh.getRow(i).getCell(1).getNumericCellValue();
				expectedFieldValue = Integer.toString(expectedFieldValueNumeric);
			}
			if(expectedFieldName.equalsIgnoreCase(expectedData))
			{
				break;
			}
		}
		return expectedFieldValue;
	}

	//To set current Date and Time											-39
	public String dateAndTime()
	{
		Format formatter = new SimpleDateFormat("YYYYMMdd_HHmmssSSS");
		Date date= new Date(System.currentTimeMillis());
		return formatter.format(date);
	}

	//To get current date & Time (suitable for special_characters like ':')	-40
	public String getCurrDateAndTimeWithTimeSeperation(String timeStampFormat) {
		SimpleDateFormat s = new SimpleDateFormat(timeStampFormat);
		Date d = new Date();
		String currentDateAndTime = s.format(d);
		return currentDateAndTime;
	}

	//To get current date & Time(suitable for creation of alpha-numeric test-data)	-41
	public String getCurrDateAndTime() {
		SimpleDateFormat s = new SimpleDateFormat("dd-MM-yy_HH-mm-ss");
		Date d = new Date();
		String currentDateAndTime = s.format(d);
		return currentDateAndTime;
	}

	//To get page screenshot														-42
	public  String getScreenshot(RemoteWebDriver driver, String ScreenshotName) throws IOException
	{
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		File source = driver.getScreenshotAs(OutputType.FILE);
		File destination = new File(System.getProperty("user.dir")+"\\test-output\\Screenshots\\"+ScreenshotName+dateName+".png");
		FileHandler.copy(source,  destination); 
		return destination.getAbsolutePath();
	}

	//To get entire test-data from excel into HashMap								-43
	public HashMap getTestDataFromExcelToHashMap(String sheetName) throws Exception
	{
		HashMap<String, String> hm=new HashMap<String, String>();
		f=new File(excelFilePath);
		fis= new FileInputStream(f);
		Workbook wb= WorkbookFactory.create(fis);
		Sheet sh=wb.getSheet(sheetName);
		int usedRows=sh.getPhysicalNumberOfRows();
		int usedColumns= sh.getRow(1).getLastCellNum();

		int j = 1;
		for(int i=1;i<usedRows; i++)
		{
			try
			{
				String uname = sh.getRow(i).getCell(0).getStringCellValue();
				String pwd = sh.getRow(i).getCell(1).getStringCellValue();
				String bname = sh.getRow(i).getCell(3).getStringCellValue();
				String url = sh.getRow(i).getCell(4).getStringCellValue();

				if(i==j)
				{
					hm.put("user"+j, uname);
					hm.put("pswd"+j, pwd);
					hm.put("url"+j, url);
				}
				j++;
			}
			catch(Exception e)
			{
				System.out.println("Data not found");
			}
		}
		return hm;
	}

	//To get user-name from excel sheet												-44
	public String getUsernameFromExcel() throws Exception
	{
		String un = this.getTestDataFromExcelToHashMap("Sheet1").get("user1").toString();
		return un;
	}

	//WaitManager class methods

	//To stop the loading of the page to avoid unnecessary waiting in page loading.	-45
	public Timeouts stopLoadingPage(long timeToStopLoad)
	{
		Timeouts t = driver.manage().timeouts().pageLoadTimeout(timeToStopLoad, TimeUnit.SECONDS);
		return t;
	}

	//To stop the execution of java script											-46
	public Timeouts stopJSEScript(long timeToStopScript)
	{
		Timeouts t = driver.manage().timeouts().setScriptTimeout(timeToStopScript, TimeUnit.SECONDS);
		return t;
	}

	//To wait until element finds but will check for every given polling time		-47
	public FluentWait waitForEveryPolling(Duration pollingTime)
	{
		fWait = new FluentWait(driver);
		fWait.pollingEvery(pollingTime);
		return fWait;
	}

	//To wait until element is ready to click										-48
	public WebDriverWait waitTillClick(WebElement element)
	{
		w.until(ExpectedConditions.elementToBeClickable(element));
		return w;
	}

	//To wait until element visible in the page-source								-49		
	public WebDriverWait waitTillElementAppear(WebElement element)
	{
		w.until(ExpectedConditions.visibilityOf(element));
		return w;
	}

	//To wait until element invisible in the page-source							-50
	public WebDriverWait waitTillElementDisappear(WebElement element)
	{
		w.until(ExpectedConditions.invisibilityOf(element));
		return w;
	}

	//To wait until text in the element's field is invisible in the page			-51
	public WebDriverWait waitTillTextDisappear(WebElement element, String text)
	{
		w.until(ExpectedConditions.invisibilityOfElementWithText((By) element, text));
		return w;
	}

	//To wait until the title of the page matches with given title					-52
	public WebDriverWait waitTillTitleMatch(String title)
	{
		w.until(ExpectedConditions.titleContains(title));
		return w;
	}

	//To wait until the URL of the page matches with given URL						-53
	public WebDriverWait waitTillUrlMatch(String url)
	{
		w.until(ExpectedConditions.urlToBe(url));
		return w;
	}

	//To wait until the alert to present on the page								-54
	public WebDriverWait waitTillAlertPopup()
	{
		w.until(ExpectedConditions.alertIsPresent());
		return w;
	}

	//To wait until the availability of the frame which we want to switch into it using locator.	-55
	public WebDriverWait waitTillSwitchToFrame(By locator)
	{
		w.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
		return w;
	}

	//To wait until the availability of the frame which we want to switch into it using frame index.	-56
	public WebDriverWait waitTillSwitchToFrame(int frameIndex)
	{
		w.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
		return w;
	}

	//To wait until the availability of the frame which we want to switch into it using xpath		-57
	public WebDriverWait waitTillSwitchToFrame(String xpath)
	{
		w.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(xpath));
		return w;
	}

	//To wait until the availability of the number of windows to be present							-58
	public WebDriverWait waitTillSwitchToWindow(int numberOfWindows)
	{
		w.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
		return w;
	}

	//To wait until the text of the page-element matches with given text							-59
	public WebDriverWait waitTillTextMatches(WebElement element, String text)
	{
		w.until(ExpectedConditions.textToBePresentInElement(element, text));
		return w;
	}

	//***********************************************************************************************************************	12-Dec-2022-Srini

	//To launch site																				-60
	public void launch(RemoteWebDriver driver, String url) throws Exception {
		driver.get(url);
		sleep(60);
	}

	//Gives us the currently working page URL address												-61
	public String getPageURL(RemoteWebDriver driver) {
		String currURL = driver.getCurrentUrl();
		return currURL;
	}

	//Gives the currently working page Title														-62
	public String getCurrentPageTitle(RemoteWebDriver driver) {
		String currTitle = driver.getTitle();
		return currTitle;
	}

	//Gives the single window-handle of newly opened tab											-63
	public String getSingleWindowHandle(RemoteWebDriver driver) {
		String win = driver.getWindowHandle();
		return win;
	}

	//Gives all window-handles of opened tabs														-64
	public List<String> getAllWindowHandles(RemoteWebDriver driver) {
		Set<String> wins = driver.getWindowHandles();
		List<String> allWins = new ArrayList<String>(wins);
		return allWins;
	}

	//Close the currently working tab																-65
	public void closeCurrent(RemoteWebDriver driver) {
		driver.close();
	}

	//Close all tabs in current browser																-66
	public void closeAll(RemoteWebDriver driver) {
		driver.quit();
	}

	//																								-67
	public String getMessageFromAlert(RemoteWebDriver driver) {
		String alertMessage = driver.switchTo().alert().getText();
		return alertMessage;
	}

	//																								-68
	public void navigate(RemoteWebDriver driver, String PageToNavigate) throws Exception {
		driver.navigate().to(BaseURL+PageToNavigate);
		//sleep(3);
	}

	//																								-69
	public String getPageId(RemoteWebDriver driver) {
		String pageURL = getPageURL(driver);
		String[] url = pageURL.split("=");
		return url[1];
	}

	//***********************************************************************************************************************
	// To select an item from Auto-complete listbox when item name is in text						-70				18-Jan-2023---@Srini
	public void autoCompleteUsingGetText(List<WebElement> autocompleteListItems, String ExpectedListItem) throws Exception {
		String ActualListItem;
		for(int i=0;i<autocompleteListItems.size();i++)
		{
			ActualListItem = autocompleteListItems.get(i).getText();
			if(ActualListItem.toLowerCase().contains(ExpectedListItem.toLowerCase()))
			{
				autocompleteListItems.get(i).click();
				sleep(2);
				break;
			}
		}
	}
	
	//To choose the checkbox
	public void selectCheckbox(List<WebElement> checkBoxLabel, List<WebElement> checkBoxInput, String expectedCheckBox) throws Exception
	{
		String actualCheckboxLabel;
		for(int i=0;i<checkBoxLabel.size();i++)
		{
			actualCheckboxLabel = checkBoxLabel.get(i).getText();
			if(actualCheckboxLabel.trim().toLowerCase().contains(expectedCheckBox.toLowerCase()))
			{
				checkBoxInput.get(i).click();
				sleep(2);
				break;
			}
		}
	}
	
}
