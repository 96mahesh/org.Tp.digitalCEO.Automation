package common_Framework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	private BufferedWriter buffWriter = null;
	private FileWriter fileWriter = null;
	private File logfile = null;

	//To generate and get ready with a new log
	public void startLogger() throws Exception
	{
		String username= System.getProperty("user.name");

		String fileName= System.getProperty("user.dir")+"\\ReportsAndLogs\\Logs\\"+username+dateTimeGenerator()+".txt";
		logfile = new File(fileName);
		logfile.createNewFile();
		if (fileWriter==null) 
		{
			fileWriter = new FileWriter(logfile);
		} 
		if (buffWriter==null)
		{
			buffWriter = new BufferedWriter(fileWriter);
		}
	}
	
	//To inform which test case going to Start(Test-case Title)
	public void StartTest (String string)
	{
		try {
			//buffWriter.write("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + "\n");
			buffWriter.write("++++++++++++++++++   Starting Test: "  +string+"   ++++++++++++++++++++++++++++++" + "\n");
			//buffWriter.write("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + "\n");
		}
		catch (IOException e) {
			System.out.println("#################################### START TEST LOG IS NOT GENERATED ############################");
		}
	}
	
	//To inform the stopping of Test-case
	public void STOPTest (String testCaseName)
	{
		try {
			//buffWriter.write("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + "\n");
			buffWriter.write("++++++++++++++++++   Stopping Test: "  +testCaseName+"   ++++++++++++++++++++++++++++++" + "\n");
			//buffWriter.write("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + "\n");
		}
		catch (IOException e) {
			System.out.println("#################################### STOP TEST LOG IS NOT GENERATED ############################");
		}
	}
	
	//To inform which Test-suite going to start(Test-suite Title)
	public void StartSuite (String testCaseName)
	{
		try {
			//buffWriter.write("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + "\n");
			buffWriter.write("++++++++++++++++++   Starting Suite: "  +testCaseName+"   ++++++++++++++++++++++++++++++" + "\n");
			//buffWriter.write("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + "\n");
		}
		catch (IOException e) {
			System.out.println("#################################### START SUITE LOG IS NOT GENERATED ############################");
		}
	}
	
	//To inform the stopping of Test-suite
	public void StopSuite (String testCaseName)
	{
		try {
			//buffWriter.write("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + "\n");
			buffWriter.write("++++++++++++++++++   Stopping Suite: "  +testCaseName+"   ++++++++++++++++++++++++++++++" + "\n");
			//buffWriter.write("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + "\n");
		}
		catch (IOException e) {
			System.out.println("#################################### STOP SUITE LOG IS NOT GENERATED ############################");
		}
	}
	
	//To end the log 
	public void StopLogging() 
	{
		try {
			buffWriter.flush();
			fileWriter.flush();
			buffWriter.close();
			fileWriter.close();
			
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
	}
	
	//To inform the Log-Description
	public void log(String logDescription)
	{
		try {
			buffWriter.write(logDescription + "\n");
			} catch (IOException e) {
			System.out.println("#################################### LOG FILE IS NOT GENERATED ############################");
		}
	}
	
	//To inform the Pass-Description
	public void pass(String PassDescription)
	{
		try {
			String pass = " \""+PassDescription+"\" is   ####PASSED####  ";
			buffWriter.write(pass + "\n");
			} catch (IOException e) {
			System.out.println("#################################### LOG FILE IS NOT GENERATED ############################");
		}
	}
	
	//To inform the Fail-Description
	public void fail (String FailDescription)
	{
		try {
			String fail = " \""+FailDescription+"\" is   ###?FAILED?###  ";
			buffWriter.write(fail + "\n");
			} catch (IOException e) {
			System.out.println("#################################### LOG FILE IS NOT GENERATED ############################");
		}
	}
	
	//To inform the Warn-Description
	public void warn (String WarnDescription)
	{
		try {
			String warn = " \""+WarnDescription+"\" is   ###!ERROR!###  ";
			buffWriter.write(warn + "\n");
			} catch (IOException e) {
			System.out.println("#################################### LOG FILE IS NOT GENERATED ############################");
		}
	}
	
	//To inform the Debug-Description
	public void Debug (String debugInfo)
	{
		try {
			buffWriter.write(debugInfo + "\n");
		} catch (IOException e) {
			System.out.println("#################################### DEBUG LOG IS NOT GENERATED ############################");
		}
	}
	
	public String dateTimeGenerator()
	{
		Format formatter = new SimpleDateFormat("YYYYMMdd_HHmmssSSS");
		Date date= new Date(System.currentTimeMillis());
		return formatter.format(date);
	}
}
