package common_Framework;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class FieldValidations{

	RemoteWebDriver driver;
	
	
	public boolean isMandatory(WebElement ElementName) {
		boolean field = false;
		String isMan = ElementName.getText();
		if(isMan.contains("*"))
		{
			field = true;
		}
		return field;
	}
	
	public boolean isEditable(WebElement Element) {
		boolean field = false;
		String fieldTag = Element.getTagName();
		if(fieldTag.contains("input"))
		{
			field = true;
		}
		return field;
	}
	
	public boolean isEmpty(WebElement Element) {
		boolean field = false;
		String fieldFill = Element.getAttribute("class");
		if(!fieldFill.contains("filled") || !fieldFill.contains("valid"))
		{
			field = true;
		}
		return field;
	}
	
	public boolean isClickable(WebElement Element) {
		boolean field = false;
		String fieldTag = Element.getTagName();
		if(fieldTag.contains("a") || fieldTag.contains("base"))
		{
			field = true;
		}
		return field;
	}
	
	public boolean isPresent(WebElement Element) {
		boolean field = false;
		if(Element.isDisplayed())
		{
			field = true;
		}
		return field;
	}
	
	public void validatePanelTitle(WebElement panelTitle, String expectedPanelTitle) throws Exception {

		String actualPanelTitle = panelTitle.getText();
		if(expectedPanelTitle.equals(actualPanelTitle))
		{
			System.out.println("Validation Passed");
		}
		else
		{
			System.out.println("Validation Failed");
		}
		
	}
}
