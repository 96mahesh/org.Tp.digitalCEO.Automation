package common_Framework;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import common.utilsAll.CommonUtils;
import common.utilsAll.DataLoaders;
import framework.configs.InitUtilObjects;
import framework.configs.TestConfig;
import junit.framework.Assert;

public class BaseTest {
	public static LogMe LOGGERSUITE;
	public static ThreadLocal<LogMe> LOGGER = new ThreadLocal<LogMe>();
	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	public static ThreadLocal<framework.configs.InitUtilObjects> utilObj = new ThreadLocal<framework.configs.InitUtilObjects>();
	public static ThreadLocal<SoftAssert> sAssert = new ThreadLocal<SoftAssert>();
	public static ThreadLocal<LinkedHashMap<String, LinkedHashMap<String, String>>> allTestData = ThreadLocal
			.withInitial(() -> {
				return common.utilsAll.DataLoaders.getAllTestData(
						TestConfig.getInstance().getProjDir() + "/src/main/resources/Test_Data/"
								+ TestConfig.getInstance().getModuleName() + "/TestData.xlsx",
						TestConfig.getInstance().getTestSuiteName());
			});
	public static String PARAENT_REPORT_FOLDER_PATH;
	public static String REPORT_FOLDER_PATH;
	public static String SCREENSHOT_FOLDER_PATH;
	public static String VIDEO_FOLDER_PATH;
	public static String recoringFlag = "";

	@BeforeSuite
	public void suiteSetup() {
		try {
			TestConfig.getInstance().suiteSetup();
			String currDate = new SimpleDateFormat("yyyy-MMM-dd").format(new Date());
			PARAENT_REPORT_FOLDER_PATH = "src/main/resources/Test_Reports/" + TestConfig.getInstance().getModuleName()
					+ "_" + currDate;
			File parentDirectory = new File(PARAENT_REPORT_FOLDER_PATH);
			String currDateTimestamp = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss").format(new Date()).replace(" ", "-")
					.replaceAll(":", "-");
			REPORT_FOLDER_PATH = PARAENT_REPORT_FOLDER_PATH + "/" + currDateTimestamp;
			File reportDirectory = new File(REPORT_FOLDER_PATH);
			SCREENSHOT_FOLDER_PATH = REPORT_FOLDER_PATH + "/Screenshots";
			File screenshotDirectory = new File(SCREENSHOT_FOLDER_PATH);
			VIDEO_FOLDER_PATH = REPORT_FOLDER_PATH + "/Videos";
			File videoDirectory = new File(VIDEO_FOLDER_PATH);

			if (CommonUtils.isWindows()) {
				if (!parentDirectory.exists()) {
					parentDirectory.mkdirs();
				}

				if (!reportDirectory.exists()) {
					reportDirectory.mkdirs();
				}

				if (!screenshotDirectory.exists()) {
					screenshotDirectory.mkdirs();
				}

				if (!videoDirectory.exists()) {
					videoDirectory.mkdirs();
				}
			} else if (CommonUtils.isLinux()) {
				CommonUtils.runCommand("bash", "-c", "mkdir", "-p", SCREENSHOT_FOLDER_PATH);
				CommonUtils.runCommand("bash", "-c", "mkdir", "-p", VIDEO_FOLDER_PATH);
			} else {
				// please implement missing OS
			}

			ExtentManager.createInstance(
					REPORT_FOLDER_PATH + "/AUTOMATION_Test-Reports" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date()).replace(" ", "-").replaceAll(":", "-") + ".html");
			LOGGERSUITE = new LogMe(BaseTest.class);
			LOGGERSUITE.logInfo("*********EXECUTION STARTED**********\n\n");
			// BaseTest.allTestData.set(utilObj.get().getDataLoaders().getAllTestData(TestConfig.getInstance().getProjDir()+"/src/main/resources/Test_Data/"+TestConfig.getInstance().getModuleName()+"/TestData.xlsx",
			// TestConfig.getInstance().getTestSuiteName()));
		} catch (Exception e) {
			LOGGERSUITE.logError("Exception " + e.getClass().getName() + " caught from suite setup method");
		}
	}

	@BeforeMethod
	public void startReporting(Method method) throws Exception {
		// reloadUpdatedExcel();
		BaseTest.driver.set(TestConfig.getInstance().createDriverInstance(TestConfig.getInstance().getBrowser()));
		utilObj.set(new InitUtilObjects());
		BaseTest.LOGGER.set(new LogMe(BaseTest.class));
		BaseTest.LOGGER.get().test_name = method.getName();
		if (TestConfig.getInstance().getRecordingFlag().equals("yes")) {
			try {
				MyScreenRecorder.startRecording(method.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sAssert.set(new SoftAssert());
		BaseTest.extentTest.set(BaseTest.LOGGER.get().logBeginTestCase(BaseTest.LOGGER.get().test_name));
		BaseTest.LOGGER.get().setupTestObj(BaseTest.LOGGER.get().test_name);
		BaseTest.utilObj.get().setupTestObj();
		BaseTest.LOGGER.get().logInfo("Application URL is " + TestConfig.getInstance().getAppURL());
		BaseTest.driver.get().get(TestConfig.getInstance().getAppURL());
		BaseTest.driver.get().manage().window().maximize();
	}

	@AfterMethod
	public void testResult(Method method, ITestResult result) throws IOException {
		BaseTest.sAssert.get().assertAll();

		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			BaseTest.LOGGER.get().logTestStep(extentTest.get(), "PASS",
					"<b><------ Test case execution completed and successful for test:"
							+ BaseTest.LOGGER.get().test_name + " -----><b>",
					false);
			break;
		case ITestResult.FAILURE:
			BaseTest.LOGGER.get().logTestStep(extentTest.get(), "FAIL",
					"<b><------ Test case failed for test:" + BaseTest.LOGGER.get().test_name + " -----><b>", true);
			break;
		case ITestResult.SKIP:
			BaseTest.LOGGER.get().logWithScreenshot("skip", "Test Case " + method.getName() + "  skiped",
					BaseTest.driver.get());
			break;
		default:
			break;
		}
		LOGGER.get().logEndTestCase(method.getName(), extentTest.get());

		try {
			ExtentManager.getInstance().flush();
		} finally {
			ExtentManager.getInstance().flush();
		}

		if (TestConfig.getInstance().getRecordingFlag().equals("yes")) {
			try {
				MyScreenRecorder.stopRecording();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		BaseTest.driver.get().quit();
	}

	@AfterSuite
	public void generateResult() {
		try {
			ExtentManager.getInstance().flush();
		} finally {
			ExtentManager.getInstance().flush();
		}
	}

	public void reloadUpdatedExcel() {
		BaseTest.allTestData.set(DataLoaders.getAllTestData(
				TestConfig.getInstance().getProjDir() + "/src/main/resources/Test_Data/"
						+ TestConfig.getInstance().getModuleName() + "/TestData.xlsx",
				TestConfig.getInstance().getTestSuiteName()));
	}
}
