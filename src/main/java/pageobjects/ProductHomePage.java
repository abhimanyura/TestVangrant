package pageobjects;

import org.openqa.selenium.By;

public class ProductHomePage {
	
private By enterUsingPasswordButton=By.cssSelector(".password-login>a");
private By passwordTextBox=By.id("Password");
private By enterButton=By.xpath("//button[@type='submit'][contains(text(),'Enter')]");
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
private By singleItemPrice=By.xpath("//td[@class='cart__price text-right']//s");
private By totalPrice=By.xpath("//td[starts-with(@class,'cart__final-price text-right')]/div/span");
private By quantityInutBox=By.xpath("input.cart__qty-input[data-quantity-input-desktop]");
private By getfeaturedCollectionProduct(String productName)
{
	return By.xpath("//span[text()='"+productName+"']/..");
}

	

}
