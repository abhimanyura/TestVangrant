package pageobjects;

import javax.security.auth.kerberos.KerberosKey;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;

import utils.SeleniumHelper;

public class ProductHomePage {
	
private By homePageButton=By.xpath("//a[@class='site-nav__link site-nav__link--main site-nav__link--active']/span[text()='Home']");
private By catalogMenuButton=By.xpath("//a[@class='site-nav__link site-nav__link--main']/span[text()='Catalog']");
private By searchIcon=By.cssSelector("div.site-header__icons-wrapper>button");
private By searchTextBox=By.cssSelector(".search-form__input-wrapper>input[type='text']");
private By searchResultList=By.cssSelector("#predictive-search-results>li");
private By searchResultNameList=By.cssSelector("#predictive-search-results>li>a>div>span>span");
private By getProductFromSearchResult(String productName)
{
	return By.xpath("//*[@id='predictive-search-results']//span[text()='"+productName+"']");
}
private By addToCartButton=By.xpath("//button[contains(@class,'product-form__cart-submit')]");
private By sizeDropdown=By.xpath("(//*[starts-with(@id,'SingleOptionSelector')])[last()]");
private By colorDropdown=By.xpath("//label[contains(text(),'Color')]//ancestor::div/select");
private By viewCartButton=By.xpath("//a[contains(text(),'View cart')]");
private By singleItemPrice=By.xpath("//td/div[@data-cart-item-price]//div[@data-cart-item-regular-price-group]//dd");
//By.xpath("//td[@class='cart__price text-right']//dd");
private By totalPrice=By.xpath("//td[starts-with(@class,'cart__final-price text-right')]/div/span");
private By quantityInutBox=By.cssSelector("input.cart__qty-input[data-quantity-input-desktop]");
private By getfeaturedCollectionProduct(String productName)
{
	return By.xpath("//span[text()='"+productName+"']/..");
}
private By productNameoncart(String productName)
{
	return By.xpath("//a[@class='cart__product-title' and contains(text(),'"+productName+"')]");
}
private By getProductRowOnViewCartPage(String productName )
{
	return By.xpath("//a[@class='cart__product-title' and contains(text(),'"+productName+"')]//ancestor::tr");
}
SeleniumHelper seleniumHelper=new SeleniumHelper();

/**
 * This method will search the product from search bar and add a product 
 * @param driver
 * @param logger
 * @param productName
 * @return
 */
public boolean searchAndAddAProduct(WebDriver driver,ExtentTest logger,String productName)
{
	boolean added=false;
	seleniumHelper.clickElement(driver, logger, searchIcon);
	seleniumHelper.waitForElementExistence(driver, searchTextBox, "Search textBox", logger);
	seleniumHelper.enterText(driver, logger, searchTextBox, productName, "Search text Box");
	seleniumHelper.clickElement(driver, logger, getProductFromSearchResult(productName));
	seleniumHelper.clickElement(driver, logger, addToCartButton);
	seleniumHelper.clickElement(driver, logger, viewCartButton);
	if(seleniumHelper.waitForElementExistence(driver, productNameoncart(productName), "Product name", logger))
	{
		added=true;
		seleniumHelper.logPass(logger, "The searched product is added to cart",driver);
	}
	else
	{
		seleniumHelper.logFail(logger, "The searched product is not added to cart",driver);
	}
	return added;
}
/**
 * This method will search the product from search bar and add a product of all the sizes provided
 * @param driver
 * @param logger
 * @param productName
 * @param size
 * @return
 */

public boolean searchAndAddAProduct(WebDriver driver,ExtentTest logger,String productName,String size)
{
	boolean added=false;
	seleniumHelper.clickElement(driver, logger, searchIcon);
	seleniumHelper.waitForElementExistence(driver, searchTextBox, "Search textBox", logger);
	seleniumHelper.enterText(driver, logger, searchTextBox, productName, "Search text Box");
	seleniumHelper.clickElement(driver, logger, getProductFromSearchResult(productName));
	seleniumHelper.selectValueFromDropdown(driver, logger, sizeDropdown, size);
	seleniumHelper.clickElement(driver, logger, addToCartButton);
	seleniumHelper.clickElement(driver, logger, viewCartButton);
	if(seleniumHelper.waitForElementExistence(driver, productNameoncart(productName), "Product name", logger))
	{
		added=true;
		seleniumHelper.logPass(logger, "The searched product is added to cart",driver);
	}
	else
	{
		seleniumHelper.logFail(logger, "The searched product is not added to cart",driver);
	}
	return added;
}
/**
 * This method will increase the product quantity and validate the price changes according to passed quantity
 * @param driver
 * @param logger
 * @param productName
 * @param quantity
 */
public void increaseTheProductQuantityAndValidateTheprice(WebDriver driver,ExtentTest logger,String productName,String quantity)
{
	WebElement productRow=driver.findElement(getProductRowOnViewCartPage(productName));
	String pricePerItem=productRow.findElement(singleItemPrice).getText().trim();
	productRow.findElement(quantityInutBox).clear();
	productRow.findElement(quantityInutBox).sendKeys(quantity);
	productRow.findElement(quantityInutBox).sendKeys(Keys.TAB);
	pricePerItem=pricePerItem.replace("Rs.","").replace(" ", "").replace(",", "");
	float expectedPrice=Float.parseFloat(pricePerItem)*Float.parseFloat(quantity);
	seleniumHelper.waitForMilliSec(10000);	
	productRow=driver.findElement(getProductRowOnViewCartPage(productName));
	String actualFinalPrice=productRow.findElement(totalPrice).getText().trim().replace("Rs.","").replace(" ", "").replace(",", "");
	if(expectedPrice==Float.parseFloat(actualFinalPrice))
	{
		seleniumHelper.logPass(logger, "Total price is updated when user increase the quantity",driver);
	}
	else
	{
		seleniumHelper.logFail(logger, "Total price is not updated when user increase the quantity",driver);
	}
}
/**
 * This method will take user to home page,add the given product from featured collection
 * @param driver
 * @param logger
 * @param featuredProductName
 * @return
 */
public boolean addProductFromFeaturedCollection(WebDriver driver,ExtentTest logger,String featuredProductName)
{
	boolean added=false;
	seleniumHelper.clickElement(driver, logger, homePageButton);
	seleniumHelper.scrollVertically(driver);
	if(seleniumHelper.waitForElementExistence(driver, getfeaturedCollectionProduct(featuredProductName), "Featured Product", logger))
	{
		seleniumHelper.clickElement(driver, logger, getfeaturedCollectionProduct(featuredProductName));
		seleniumHelper.clickElement(driver, logger, addToCartButton);
		seleniumHelper.clickElement(driver, logger, viewCartButton);
		if(seleniumHelper.waitForElementExistence(driver,getProductRowOnViewCartPage(featuredProductName),"Product row",logger))
		{
			added=true;
			seleniumHelper.logPass(logger, "featured product is added to cart",driver);
		}
		else
		{
			seleniumHelper.logFail(logger, "featured product is not added to cart",driver);
		}
	
	}
	return added;
}

}
