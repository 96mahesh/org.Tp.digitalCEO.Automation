package common_Framework;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Reporter extends Base{

	RemoteWebDriver driver;
	public static String PROJECTNAME;

	private ExtentSparkReporter htmlReporter;
	private ExtentReports testPerformReport;
	private ExtentTest test;
	static Map<Integer, ExtentTest> extentTestMap;

	Base base;

	// Constructor to setup reports configuration like Title, Theme, CSS...etc.,
	public Reporter(RemoteWebDriver driver, String projectTitle) throws Exception {
		this.driver = driver;
		String username = System.getProperty("user.name");
		PROJECTNAME = System.getProperty("user.dir");

		extentTestMap = new HashMap<Integer, ExtentTest>();
		base = new Base();

		htmlReporter = new ExtentSparkReporter(PROJECTNAME + "\\ReportsAndLogs\\Automation-TestPerform-Reports\\"
				+ username + dateTimeGenerator() + "Report.html");
		htmlReporter.config().setDocumentTitle("TestPerform - Customized Automation Report");
		htmlReporter.config().setReportName(projectTitle + " Test Report");
		htmlReporter.config().setTheme(Theme.DARK);
		String css = ".r-img {width: 30%;}";
		htmlReporter.config().setCss(css);

		testPerformReport = new ExtentReports();
		testPerformReport.attachReporter(htmlReporter);
		testPerformReport.setSystemInfo("Resource", System.getProperty("user.name"));
		testPerformReport.setSystemInfo("Browser", System.getProperty("Browser"));

		base.loadPropertiesFile(PROJECTNAME + "\\src\\test\\resources\\Properties\\RunData.properties");
		try {
			testPerformReport.setSystemInfo("Environment", base.getPropertiesFromPropertiesFile("URL"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			testPerformReport.setSystemInfo("Environment", base.getPropertiesFromPropertiesFile("URL"));
			e.printStackTrace();
		}
		testPerformReport.setSystemInfo("System", System.getProperty("os.name"));
	}

	// To start the Report
	public ExtentTest startTestReport(String testName, String desc) {
		test = testPerformReport.createTest(testName, desc);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
		return test;
	}

	// To log the Test result status in reports
	public void afterMethod(ITestResult results) {
		String testName = results.getMethod().getMethodName();
		if (results.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, testName + "  : test case is failed ");
			test.log(Status.FAIL, "Test case failed is " + results.getThrowable());
			try {
				String screenshotPath = getScreenshot(driver, results.getName());
				test.addScreenCaptureFromPath(screenshotPath);
			} catch (Exception e) {
				test.log(Status.WARNING, "Can't caputure the screenshot");
			}
		} else if (results.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, testName + "  : test case is skipped ");
		} else if (results.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, testName + "  : test case is passed ");
		}
	}

	// ADD THE SCREENSHOT METHOD
	public static String getScreenshot(RemoteWebDriver driver, String ScreenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots" under src
		// folder
		String destination = PROJECTNAME + "/test-output/Screenshots/" + ScreenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	public void PASS(String message) {
		test.log(Status.PASS, message);
	}

	public void FAIL(String message) {
		String screenshotPath;
		try {
			screenshotPath = getScreenshot(driver, "step");
			test.addScreenCaptureFromPath(screenshotPath);
		} catch (IOException e) {
			test.log(Status.WARNING, "Couldn't capture the Screenshot on test step");
		} catch (Exception ex) {
			test.log(Status.FAIL, "<span class='label failure'>Fail</span> -  " + message);
		}

		/*
		 * test.log(Status.FAIL,"<span class='label failure'>Fail</span> -  " +
		 * message); if (DataManager.getRunData().getScreenShotFailureOnStep()) { String
		 * screenshotPath; try { screenshotPath = getScreenshot(driver, "step");
		 * test.addScreenCaptureFromPath(screenshotPath); }catch (IOException e) {
		 * test.log(Status.WARNING,"Couldn't capture the Screenshot on test step"); } }
		 * else { test.log(Status.FAIL,"<span class='label failure'>Fail</span> -  " +
		 * message); } Assert.fail();
		 */
	}

	public void SKIP(String message) {
		test.log(Status.SKIP, "<span class='label success'>Pass</span> -  " + message);
	}

	public void WARN(String message) {
		test.log(Status.WARNING, "<span class='label success'>Pass</span> -  " + message);
	}

	public void FATAL(String message) {
		test.log(Status.FAIL, "<span class='label fatal'>Fatal</span> -  " + message);
		Assert.fail();
	}

	public void INFO(String message) {
		test.log(Status.INFO, "<span class='label info'>Info</span> -  " + message);
	}

	// To end the Report
	public void endTestReport() {
		testPerformReport.flush();
	}

	public String dateTimeGenerator() {
		Format formatter = new SimpleDateFormat("YYYYMMdd_HHmmssSSS");
		Date date = new Date(System.currentTimeMillis());
		return formatter.format(date);
	}

}
