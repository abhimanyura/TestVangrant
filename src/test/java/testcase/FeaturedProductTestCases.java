package testcase;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentTest;

import pageobjects.ProductHomePage;

public class FeaturedProductTestCases extends OneTimeSetup {
	ProductHomePage productHomePage= new ProductHomePage();
	SoftAssert softassert=new SoftAssert();
	@Test(priority=1,dataProvider = "readExcelData",description = "Select a product from Featured collection")
	public void addAnItemFromFeaturedCollection(String featuredProductname)
	{
		ExtentTest logger=extentReports.startTest("Select a product from Featured collection");
		softassert.assertEquals(productHomePage.addProductFromFeaturedCollection(driver, logger, featuredProductname),true);
		extentReports.endTest(logger);
		softassert.assertAll();
	}
	
	@DataProvider(name = "readExcelData")
	public Object[][] readExcelData() {
	   
	    return excelFileUtils.getExcelData(excelFileUtils.getExcelSheetPath(), "featuredProductPage");


	}	
		
}
