package common_Framework;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

public class Date_Management
{
	public RemoteWebDriver driver;
	private StopWatch watch = new StopWatch();

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
					int date = Integer.parseInt(actualDate);
					coloumnofWeek.get(j).click();
					Thread.sleep(3000);
				}
			}
		}
	}

	//To get current date & Time (suitable for special_characters like ':')
	public String getCurrDateAndTimeWithTimeSeperation() {
		SimpleDateFormat s = new SimpleDateFormat("dd-MM-yy_HH:mm:ss");
		Date d = new Date();
		String currentDateAndTime = s.format(d);
		return currentDateAndTime;
	}
	
	//To get current date & Time (suitable for non-special_characters)
	public String getCurrDateAndTime() {
		SimpleDateFormat s = new SimpleDateFormat("dd-MM-yy_HH-mm-ss");
		Date d = new Date();
		String currentDateAndTime = s.format(d);
		return currentDateAndTime;
	}
	
	public String dateTimeGenerator()
	{
		Format formatter = new SimpleDateFormat("YYYYMMdd_HHmmssSSS");
		Date date= new Date(System.currentTimeMillis());
		return formatter.format(date);
	}
	
	//Stopwatch
	
	public String calculateRunTime(long startTime, long endTime) {
		long runTimeMilliSec = (endTime-startTime);
		float runTimingSec = (runTimeMilliSec/1000);
		float runTimingMin = runTimingSec/60;	//9.45
		String time = runTimingMin+"";
		//System.out.println(time);
		String min = time.substring(0, 1);
		String sec = time.substring(2, 3);
		String runTime = min+" minutes "+sec+" seconds";
		return runTime;
	}
	public String calculateRunTimeGeneric(long startTime, long endTime) {
		long runTimeMilliSec = (endTime-startTime);
		float runTimingSec = runTimeMilliSec/1000;
		float runTiming = runTimingSec/60;	//9.45
		String time = runTiming+"";
		System.out.println(time);
		char[] timeArray = time.toCharArray();
		String min=null;
		String sec=null;
		int j=0;
		for(int i=1;i<timeArray.length;i++)
		{
			if(timeArray[i]=='.')
			{
				j=i+1;
				sec = time.substring(j);
			}
			else
			{
				min = time.substring(0, i);
			}
		}
		min = min.substring(0, min.length()-1);
		String runTime = min+" minutes "+sec+" seconds";
		return runTime;
	}

}
