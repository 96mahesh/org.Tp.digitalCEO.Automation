package common_Framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelManager {

	private FileInputStream fis;
	private File file;
	private FileReader fieldReader;
	public String addParticipantExcelFile = System.getProperty("user.dir")+"\\src\\test\\resources\\testdata\\RTIP_AddParticipant.xlsx";
	public String excelFilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\testdata\\RTIP_AllTestData.xlsx";
	public String propertiesFilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\testdata.properties";
	public String juvenilexcelFile = System.getProperty("user.dir")+"\\src\\test\\resources\\DataFromExcel\\juvenile_TestData.xlsx";
	public String suiteFileName = "";

	public Sheet sh;

	public List getTestDataFromExcel(String FilePath, String SheetName) throws Exception {

		List<String> strings = new ArrayList<String>();
		List<Double> numerics = new ArrayList<Double>();
		file=new File(excelFilePath);
		fis= new FileInputStream(file);
		Workbook wb= WorkbookFactory.create(fis);
		Sheet sh=wb.getSheet(SheetName);
		int usedRows=sh.getPhysicalNumberOfRows();
		int usedColumns= sh.getRow(1).getLastCellNum();

		String stringData;
		//for(int i=1;i<usedRows; i++)
		for(int i=1;i<2; i++)
		{
			for(int j=0;j<usedColumns;j++)
			{
				try
				{
					stringData = sh.getRow(i).getCell(j).getStringCellValue();
					strings.add(stringData);
				}
				catch(Exception e)
				{
					int numericData = (int) sh.getRow(i).getCell(j).getNumericCellValue();
					stringData = numericData+"";
					strings.add(stringData);
				}
			}
		}
		return strings;
	}

	public List getTestDataFromExcel(String FilePath, String SheetName, int recordNumber) throws Exception {

		List<String> strings = new ArrayList<String>();
		List<Double> numerics = new ArrayList<Double>();
		file=new File(excelFilePath);
		fis= new FileInputStream(file);
		Workbook wb= WorkbookFactory.create(fis);
		Sheet sh=wb.getSheet(SheetName);
		int usedRows=sh.getPhysicalNumberOfRows();
		int usedColumns= sh.getRow(1).getLastCellNum();

		String stringData;
		for(int i=1;i<usedRows; i++)
		{
			if(i==recordNumber)
			{
				for(int j=0;j<usedColumns;j++)
				{
					try
					{
						stringData = sh.getRow(i).getCell(j).getStringCellValue();
						strings.add(stringData);
					}
					catch(Exception e)
					{
						int numericData = (int) sh.getRow(i).getCell(j).getNumericCellValue();
						stringData = numericData+"";
						strings.add(stringData);
					}
				}
			}
			else
			{
				System.out.println("Expected record not available");
			}
		}
		return strings;
	}
}
