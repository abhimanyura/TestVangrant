package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import utils.SeleniumHelper;

public class Login {
	private By enterUsingPasswordButton=By.cssSelector(".password-login>a");
	private By passwordTextBox=By.id("Password");
	private By enterButton=By.xpath("//button[@type='submit'][contains(text(),'Enter')]");
	private By catalogMenuButton=By.xpath("//a[@class='site-nav__link site-nav__link--main']/span[text()='Catalog']");
	
	SeleniumHelper seleniumHelper=new SeleniumHelper();
	
	
	public boolean login(WebDriver driver,ExtentTest logger,String password)
	{
		boolean loggedIn=false;
		seleniumHelper.waitForElementExistence(driver, enterUsingPasswordButton, "Enter Using passsword Button", logger);
		seleniumHelper.clickElement(driver, logger, enterUsingPasswordButton);
		seleniumHelper.waitForElementExistence(driver, passwordTextBox, "password Text Box", logger);
		seleniumHelper.enterText(driver, logger, passwordTextBox, password, "password Text box");
		seleniumHelper.clickElement(driver, logger, enterButton);
		if(seleniumHelper.waitForElementExistence(driver, catalogMenuButton, "Catalog menu", logger))
		{
			loggedIn=true;
			seleniumHelper.logPass(logger, "The user is logged in successfully",driver);
			
		}
		else
		{
			seleniumHelper.logFail(logger, "The user is not able to log in",driver);
			
		}
		return loggedIn;
	}
}
