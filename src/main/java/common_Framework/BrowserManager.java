package common_Framework;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserManager 
{
	RemoteWebDriver driver;
	
	public RemoteWebDriver openBrowser()
	{
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		System.out.println("browser opened");
		return driver;
	}
	

	public RemoteWebDriver openBrowsers(String browsername)
	{
		if(browsername.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");//Assign property to browser driver
			driver = new ChromeDriver();//object created and browser will open
			driver.manage().window().maximize();
		}
		else if(browsername.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "geckodriver.exe");//Assign property to browser driver
			FirefoxOptions options = new FirefoxOptions();
			options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
			driver = new FirefoxDriver(options);//object created and browser will open
			driver.manage().window().maximize();	
		}
		else if(browsername.equalsIgnoreCase("opera"))
		{
			//79.0.4143.22
			System.setProperty("webdriver.opera.driver", "operadriver.exe");//Assign property to browser driver
			//OperaOptions options = new OperaOptions();
			//options.setBinary("C:\\Users\\srachavarapu\\AppData\\Local\\Programs\\Opera\\launcher.exe");
			driver = new OperaDriver();//object created and browser will open
			driver.manage().window().maximize();	
		}
		else if(browsername.equalsIgnoreCase("edge"))
		{
			//93.0.961.52
			System.setProperty("webdriver.edge.driver", "msedgedriver.exe");//Assign property to browser driver
			driver = new EdgeDriver();//object created and browser will open
			driver.manage().window().maximize();
		}
		else if(browsername.equalsIgnoreCase("ie"))
		{

		}
		System.out.println("multi browsers opened for Cross Browser Testing");
		return driver;
	}

	public void closeSite()
	{
		System.out.println("close the application site");
		driver.quit();
	}
}
