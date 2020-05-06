package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverInfo;

public class LoginHelper {

	static String projectDir = System.getProperty("user.dir");

	/**
	 * This method is used to open the broswer
	 *
	 * @param browserName
	 * @return
	 */
	public WebDriver openBrowser(String browserName) {
		WebDriver driver = null;
		if (browserName.equalsIgnoreCase("Chrome"))
		{

			System.setProperty("webdriver.chrome.driver", projectDir + "/Resources/chromeDriver.exe");            
			driver = new ChromeDriver();
		} 
		else if (browserName.equalsIgnoreCase("FireFox")) 
		{
			System.setProperty("webdriver.chrome.driver", projectDir + "/Resources/geckodriver.exe");
			driver = new FirefoxDriver();
		}


		return driver;
	}
}
