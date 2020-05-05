package testcase;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentTest;

import pageobjects.ProductHomePage;

public class ProductTestCases extends OneTimeSetup {
	ProductHomePage productHomePage= new ProductHomePage();
	SoftAssert softassert=new SoftAssert();
	@Test(priority=1,dataProvider = "readExcelData",description = "Search a prouct and add it to cart")
	public void searchandAddAProduct(String productName,String quantity,String size)
	{
		ExtentTest logger=extentReports.startTest("Search and add A product");
		softassert.assertEquals(productHomePage.searchAndAddAProduct(driver, logger, productName),true);
		extentReports.endTest(logger);
		softassert.assertAll();
	}
	@Test(priority = 2,dataProvider = "readExcelData",description = "Increase the quantity of product and verify the total price")
	public void increaseProductQuantityAndCheckPrice(String productname,String quantity,String size)
	{
		ExtentTest logger=extentReports.startTest("increase Product Quantity And Check Price");
		productHomePage.increaseTheProductQuantityAndValidateTheprice(driver, logger, productname, quantity);
		extentReports.endTest(logger);
	}
	@Test(priority=3,dataProvider = "readExcelData",description = "Search a prouct and add different size of the product to cart")
	public void searchandAddAProductOfdifferentSize(String productName,String quantity,String size)
	{
		ExtentTest logger=extentReports.startTest("Search and add A product of different size");
		for(int i=0;i<size.split(",").length;i++)
		{
			softassert.assertEquals(productHomePage.searchAndAddAProduct(driver, logger, productName,size.split(",")[i]),true);
		}
		extentReports.endTest(logger);
		softassert.assertAll();
	}	
	@DataProvider(name = "readExcelData")
	public Object[][] readExcelData() {

		return excelFileUtils.getExcelData(excelFileUtils.getExcelSheetPath(), "productpage");
	}	



}
