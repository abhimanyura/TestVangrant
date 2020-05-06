package testcase;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import pageobjects.Login;
import utils.ExcelFileUtils;
import utils.LoginHelper;
import utils.PropertyFileUtils;
import utils.SeleniumHelper;

public class OneTimeSetup {
	public static ExtentReports extentReports;
	private static ExtentTest logger;
	public WebDriver driver;

	SeleniumHelper seleniumHelper=new SeleniumHelper();
	LoginHelper loginhelper=new LoginHelper();
	PropertyFileUtils propertyFileUtils=new PropertyFileUtils();
	Login loginPage=new Login();
	public ExcelFileUtils excelFileUtils = new ExcelFileUtils();
	@BeforeSuite(alwaysRun = true, description = "One time setup")
	public void oneTimeSetUp() 
	{
		extentReports = new ExtentReports(seleniumHelper.extentReportLocation("Automation suite Report"));
		
	}

	@BeforeTest(description = "Setting up the browser and logging in")
	public void browserSetup()
	{
		ExtentTest logger = extentReports.startTest("Setting up browser and login ");
		driver = loginhelper.openBrowser(propertyFileUtils.getPropertyValue("Configuration", "browsername")); 
		driver.get(propertyFileUtils.getPropertyValue("Configuration", "URI"));
		seleniumHelper.logInfo(logger, "Browser is launched", driver);
		driver.manage().window().maximize();
		loginPage.login(driver, logger, propertyFileUtils.getPropertyValue("Configuration", "password"));
		extentReports.endTest(logger);
	}


	@AfterTest(alwaysRun = true, description = "TearUp")
	public void tearUp() {
		
		driver.quit();

	}
	@AfterSuite(alwaysRun = true)
	public void flushReport()
	{
		extentReports.flush();
	}
}
