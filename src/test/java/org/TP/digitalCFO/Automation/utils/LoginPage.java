package org.TP.digitalCFO.Automation.utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import common_Framework.Base;
import common_Framework.BrowserManager;
import common_Framework.FieldValidations;

public class LoginPage extends Base {
	BrowserManager browserDriver = new BrowserManager();
	FieldValidations fields = new FieldValidations();

	// PageFactory Elements
	@FindBy(how = How.XPATH, using = "//div/form/div/a//img[@classname='logo']")
	private WebElement logo;
	
	@FindBy(how=How.XPATH, using="//div[@class='login-form']/descendant::h4[text()='Login to your Account']")
	private WebElement loginPageTitle;
	
	@FindBy(how = How.XPATH, using = "//input[@name='loginuser']")
	private WebElement userName;
	
	@FindBy(how = How.XPATH, using = "//input[@name='password']")
	private WebElement password;
	
	@FindBy(how = How.XPATH, using = "//div[@class='recaptcha-checkbox-border']")
	private WebElement reCaptcha_CheckBox;
	
	@FindBy(how = How.XPATH, using = "//div[@id='rc-anchor-container']/descendant::label[contains(text(), 'not a robot')]")
	private WebElement reCaptchaText;

	@FindBy(how = How.ID, using = "loginButton")
	private WebElement login_Button;

	@FindBy(how = How.LINK_TEXT, using = "Forgot password")
	private WebElement forgot_Password_Page_Link;

	@FindBy(how = How.XPATH, using = "//a[normalize-space()='External User Login']")
	private WebElement external_UserLogin_Page_Link;

	public LoginPage verifyAndEnterEmailAdress(String emailAddress) {
		fields.isPresent(userName);
		fields.isEditable(userName);
		userName.sendKeys(emailAddress);
		return this;
	}
	
	public LoginPage verifyAndEnterPassword(String pwd)
	{
		fields.isPresent(password);
		fields.isEditable(password);
		password.sendKeys(pwd);
		return this;
	}
	
	public LoginPage verifyAndClickreCaptcha()
	{
		fields.isPresent(reCaptcha_CheckBox);
		fields.isClickable(reCaptcha_CheckBox);
		reCaptcha_CheckBox.click();
		return this;
	}
	
	public LoginPage verifyreCaptchaText(String expectedText)
	{
		fields.isPresent(reCaptchaText);
		String actualText = reCaptchaText.getText();
		if(actualText.equals(expectedText))
		{
			System.out.println("Both are Matched");
		}
		else
		{
			System.out.println("Failed verifcation" + verifyreCaptchaText(expectedText));
		}
		return this;
	}
}
