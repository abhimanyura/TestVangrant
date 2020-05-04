package utils;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author abhimanyu
 */
public class SeleniumHelper {




	/**
	 * Prints the custom message in the Extend reports output - Pass
	 *
	 * @param logger - ExtendTest Logger
	 * @param msg    - Message
	 */
	public void logPass(ExtentTest logger, String msg) {
		logger.log(LogStatus.PASS, msg);
	}

	/**
	 * Prints the custom message in the Extend reports output with screenshot - Pass
	 *
	 * @param logger- Extend logger
	 * @param msg     - Message
	 * @param driver  - WebDriver
	 */
	public void logPass(ExtentTest logger, String msg, WebDriver driver) {
		String screenshotPath = captureScreenshot(driver);
		String image = logger.addScreenCapture(screenshotPath);
		logPass(logger, msg);
		logger.log(LogStatus.PASS, msg, image);
	}

	/**
	 * Prints the custom message in the Extend reports output - Info
	 *
	 * @param logger - ExtendTest Logger
	 * @param msg    - Message
	 */
	public void logInfo(ExtentTest logger, String msg) {
		logger.log(LogStatus.INFO, msg);

	}

	/**
	 * Prints the custom message in the Extend reports output with screenshot - Info
	 *
	 * @param logger- ExtentTest logger
	 * @param msg     - Message
	 * @param driver  - WebDriver
	 */
	public void logInfo(ExtentTest logger, String msg, WebDriver driver) {
		String screenshotPath = captureScreenshot(driver);
		String image = logger.addScreenCapture(screenshotPath);
		logInfo(logger, msg);
		logger.log(LogStatus.INFO, msg, image);
	}

	/**
	 * This method is used to log a step as fail
	 *
	 * @param logger-ExtentTest logger
	 * @param msg-Message       which need to be logged
	 */
	public void logFail(ExtentTest logger, String msg) {
		logger.log(LogStatus.FAIL, msg);
	}

	/**
	 * Prints the custom message in the Extend reports output with screenshot - Fail
	 *
	 * @param logger- Extend logger
	 * @param msg     - Message
	 */
	public void logFail(ExtentTest logger, String msg, WebDriver driver) {
		String screenshotPath = captureScreenshot(driver);
		String image = logger.addScreenCapture(screenshotPath);
		logFail(logger, msg);
		logger.log(LogStatus.FAIL, msg, image);
	}

	/**
	 * This method is used to capture screenshot
	 *
	 * @param driver-WebDriver instance
	 * @return- Location of the screenshot
	 */
	public String captureScreenshot(WebDriver driver) {
		String s1 = null;
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			String failureImageFileName = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss").format(new GregorianCalendar().getTime()) + ".png";
			String failureScreenshotDir = new SimpleDateFormat("MMM-dd").format(new GregorianCalendar().getTime());
			String extentReportSubDir = new SimpleDateFormat("MMM-dd").format(new GregorianCalendar().getTime());
			String extentReportDir = "Reports/extentReport" + extentReportSubDir + "/";
			String dir = extentReportDir + "Screenshot" + failureScreenshotDir + "/";
			File srcDir = new File(dir);
			if (!srcDir.exists()) {
				srcDir.mkdir();
			}
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(dir + failureImageFileName));
			s1 = "Screenshot" + failureScreenshotDir + "/" + failureImageFileName;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return s1;
	}

	/**
	 * This method is used to click Element
	 *
	 * @param driver-WebDriver  instance
	 * @param logger-ExtentTest logger
	 * @param locator-locator   of an element
	 */
	public void clickElement(WebDriver driver, ExtentTest logger, By locator) {
		if(waitForElementExistence(driver,locator,"",logger)) {
			WebElement ele = driver.findElement(locator);
			if (ele != null) {
				try
				{
					ele.click();
				}
				catch (ElementNotVisibleException e)
				{
					logFail(logger, "ElementNotVisibleException");
				}
				catch (StaleElementReferenceException e) {
					logFail(logger, "StaleElementReferenceException");
				} catch (InvalidElementStateException e) {
					clickElementUsingJavaScript(driver, locator);
				} catch (Exception e) {
					try {
						JavascriptExecutor jse = (JavascriptExecutor) driver;
						jse.executeScript("scroll(0, 400);");
						ele.click();
						logInfo(logger, "Scrolled 400");
					} catch (Exception e2) {
						try {
							JavascriptExecutor jse = (JavascriptExecutor) driver;
							jse.executeScript("scroll(0, 500);");
							ele.click();
							logInfo(logger, "Scrolled 500");
						} catch (Exception e3) {
							try {
								JavascriptExecutor jse = (JavascriptExecutor) driver;
								jse.executeScript("scroll(0, 600);");
								ele.click();
								logInfo(logger, "Scrolled 600");
							} catch (Exception e4) {
								try {
									JavascriptExecutor jse = (JavascriptExecutor) driver;
									jse.executeScript("scroll(0, 700);");
									ele.click();
									logInfo(logger, "Scrolled 700");

								} catch (Exception e5) {
									try {
										JavascriptExecutor jse = (JavascriptExecutor) driver;
										jse.executeScript("scroll(0, 800);");
										ele.click();
										logInfo(logger, "Scrolled 800");
									} catch (Exception ex) {
										logInfo(logger, ex.toString() + "error occured");
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * This method waits until element becomes visible
	 *
	 * @param driver- WebDriver  instance
	 * @param locator-          element locator
	 * @param elementName-Name  of the WebElement
	 * @param logger-ExtentTest logger
	 * @return
	 */
	public boolean waitForElementExistence(WebDriver driver, By locator, String elementName, ExtentTest logger) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 120);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (TimeoutException e) {
			logFail(logger, "Element is not present "+elementName+" Timeout Exception  occurred : "+ e.getMessage(),driver);
			return false;
		} catch (ElementNotVisibleException e) {
			logFail(logger, "Element is not visible "+elementName+ " Exception is "+ e.getMessage(),driver);
			return false;
		} catch (ElementNotFoundException e) {
			logFail(logger, "Element is not found "+elementName+ " Exception is "+ e.getMessage(),driver);
			return false;
		} catch (Exception e) {
			logFail(logger, "Exception is :" + e.getMessage(), driver);
			return false;
		}
	}

	/**
	 * This method waits until element becomes visible
	 *
	 * @param driver-WebDriver  instance
	 * @param locator-          element locator
	 * @param elementName-Name  of the WebElement
	 * @param logger-ExtentTest logger
	 * @return
	 */
	public boolean waitForElementToBeClickable(WebDriver driver, By locator, String elementName, ExtentTest logger) {
		if(waitForElementExistence(driver, locator, elementName, logger)) {
			try {
				WebDriverWait wait = new WebDriverWait(driver, 120);
				wait.until(ExpectedConditions.elementToBeClickable(locator));
				return true;
			} catch (Exception e) {
				logFail(logger, "Exception is :" + e.getMessage(), driver);
				return false;
			}
		}
		else
		{
			logFail(logger, "Element is not clickable as element is not visible" , driver);
			return false;
		}
	}

	/**
	 * This method is waits until certain element disappears
	 *
	 * @param driver-WebDriver  driver
	 * @param locator-Element   locator
	 * @param elementName-Name  of the element
	 * @param logger-ExtentTest logger
	 * @return true if element disappears in given time or viceversa
	 */
	public boolean waitForElementDisappear(WebDriver driver, By locator, String elementName, ExtentTest logger)
	{
		try {
			WebDriverWait wait = new WebDriverWait(driver, 90);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			return true;
		} catch (TimeoutException e) {
			logInfo(logger, "Element is not present " + elementName + " Timeout Exception  occured : " + e.getMessage(), driver);
			return false;
		} catch (ElementNotVisibleException e) {
			logInfo(logger, "Element is not visible " + elementName + " Exception is " + e.getMessage(), driver);
			return false;
		} catch (ElementNotFoundException e) {
			logInfo(logger, "Element is found " + elementName + " Exception is " + e.getMessage(), driver);
			return false;
		} catch (Exception e) {
			logInfo(logger, "Exception is :" + e.getMessage(), driver);
			return false;
		}

	}

	/**
	 * This method is used to select the value from dropdown,It will highlight the dropdown as well
	 *
	 * @param driver-WebDriver          instance
	 * @param logger-ExtentTest         logger
	 * @param elementLocator-webElement locator
	 * @param value-The text value which need to be selected
	 */
	public void selectValueFromDropdown(WebDriver driver, ExtentTest logger, By elementLocator, String value)
	{
		if(waitForElementExistence(driver,elementLocator,"",logger))
		{
			WebElement ele = driver.findElement(elementLocator);
			Select sel = new Select(ele);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);
			try {
				sel.selectByVisibleText(value);
				logInfo(logger, "The given text " + value + "selected From dropdown");
			} catch (Exception e) {
				logFail(logger, "The given text is not present under dropdown");
			}
		}
		else
		{
			logInfo(logger, "The dropdown is not visible ",driver);
		}
	}

	/**
	 * This method is used to click element using javaScript
	 *
	 * @param driver-WebDriver       instance
	 * @param elementLocator-Locator of an element
	 */
	public void clickElementUsingJavaScript(WebDriver driver, By elementLocator)
	{
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(elementLocator));
		}
		catch (Exception e)
		{
		}
	}

	/**
	 * This method is used to click element using javaScript
	 *
	 * @param driver-WebDriver   instance
	 * @param element-WebElement
	 */
	public void clickElementUsingJavaScript(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);

	}


	/**
	 *
	 * @param driver
	 * @param logger
	 * @param elementLocator
	 * @param value
	 * @param objName
	 */
	public void editTextOfaTextBox(WebDriver driver, ExtentTest logger, By elementLocator, String value, String objName) {
		try {
			WebElement elm = driver.findElement(elementLocator);
			if ((elm != null)) {
				if (!value.isEmpty()) {
					elm.clear();
					elm.sendKeys(value);
					logInfo(logger, "Successfully entered the text " + value + " in the field :" + objName);
				}
			} else
				logFail(logger, "Element not found", driver);
		} catch (ElementNotFoundException e) {
			logFail(logger, "Element not found", driver);
		} catch (InvalidElementStateException e) {
			logFail(logger, "The webElement is non editable", driver);
		} catch (Exception e) {
			logFail(logger, "Other Exceptions : " + e.getMessage(), driver);
		}
	}

	/**
	 *
	 * @param driver
	 * @param logger
	 * @param elementLocator
	 * @param value
	 * @param objName
	 */
	public void enterText(WebDriver driver, ExtentTest logger, By elementLocator, String value, String objName) {
		try {
			WebElement elm = driver.findElement(elementLocator);
			if ((elm != null)) {

				elm.clear();
				elm.sendKeys(value);
				logInfo(logger, "Successfully entered the text " + value + " in the field :" + objName);
			} else
				logFail(logger, "Element not found", driver);
		} catch (ElementNotFoundException e) {
			logFail(logger, "Element not found", driver);
		} catch (InvalidElementStateException e) {
			logFail(logger, "The webElement is non editable", driver);
		} catch (Exception e) {
			logFail(logger, "Other Exceptions : " + e.getMessage(), driver);
		}
	}

	public void enterValueinTextFieldUsingJavaScriptExecutor(ExtentTest logger, WebDriver driver, By elementLocator, String value) 
	{
		try {
			WebElement ele = driver.findElement(elementLocator);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].value='" + value + "';", ele);
		} catch (Exception e) {
			logFail(logger, "Element is not visible");
		}
	}


	/**
	 * This method is used to find the Column number from a table
	 *
	 * @param driver
	 * @param tableLocator
	 * @param columnName
	 * @return
	 */
	public int findColumnNumberInTable(WebDriver driver, By tableLocator, String columnName) {
		WebElement table = driver.findElement(tableLocator);
		int columnNumber = 0;
		if (table != null) {

			List<WebElement> tableRows = table.findElements(By.tagName("tr"));
			List<WebElement> header = tableRows.get(0).findElements(By.tagName("th"));
			for (int i = 0; i < header.size(); i++) {
				if (header.get(i).getText().contentEquals(columnName)) {
					columnNumber = i;
					break;
				}
			}
			return columnNumber;
		} else {

			return -1;
		}
	}

	/***
	 *
	 * @param logger
	 * @param driver
	 * @param tableLocator
	 * @param cellData
	 * @param columnNumber
	 * @return
	 */
	public int findRowNumberForACellValueInTable(ExtentTest logger, WebDriver driver, By tableLocator, String cellData, int columnNumber) {
		WebElement table = driver.findElement(tableLocator);
		int rowNumber = -1;
		if (table != null) {
			List<WebElement> tableRows = table.findElements(By.tagName("tr"));
			//System.out.println("Number of rows is"+tableRows.size());
			for (int i = 1; i < tableRows.size(); i++) {
				List<WebElement> columns = tableRows.get(i).findElements(By.tagName("td"));
				//System.out.println("Number of rows is"+columns.size());
				//System.out.println("span text is"+columns.get(columnNumber).findElement(By.tagName("span")).getText());
				if (columns.get(columnNumber).findElement(By.tagName("span")).getText().equalsIgnoreCase(cellData)) {
					rowNumber = i;
					break;
				}

			}
			return rowNumber;
		} else {
			logFail(logger, "The searched data  " + cellData + "  is not present in table");
			return -1;
		}
	}

	/**
	 * This method is used to generate the ExtendReport Path
	 */

	public String extentReportLocation(String className) {

		String timeStamp = new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime());
		String extentReportSubDir = new SimpleDateFormat("MMM-dd").format(new GregorianCalendar().getTime());
		String extentReportDir = "Reports/extentReport" + extentReportSubDir + "/";
		File srcDir = new File(extentReportDir);
		if (!srcDir.exists()) {
			srcDir.mkdir();
		}

		String extendReportsPath = extentReportDir + className + timeStamp + ".html";
		return extendReportsPath;
	}

	/**
	 * This method is used to mouse over on an element
	 *
	 * @param logger
	 * @param driver
	 * @param elementLocator
	 */
	public void mouseHover(ExtentTest logger, WebDriver driver, By elementLocator) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(elementLocator)).build().perform();
		} catch (Exception e) {
			// TODO: handle exception
			logFail(logger, "The element is not present");
		}
	}


	/**
	 * This method is used to mouse over on an element
	 *
	 * @param logger
	 * @param driver
	 * @param element-WebElement
	 */
	public void mouseHover(ExtentTest logger, WebDriver driver, WebElement element) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
		} catch (ElementNotFoundException e) {
			// TODO: handle exception
			logFail(logger, "The element is not present");
		}
	}

	/**
	 * @param logger
	 * @param driver
	 * @param locator
	 * @return
	 */
	public String getText(ExtentTest logger, WebDriver driver, By locator) {
		if (waitForElementExistence(driver, locator, "elementname", logger)) {
			return driver.findElement(locator).getText();
		} else {
			return "";
		}

	}

	public String getAttributeValue(WebDriver driver, ExtentTest logger, By locator, String attributeName) {
		if (waitForElementExistence(driver, locator, "elementname", logger)) {
			return driver.findElement(locator).getAttribute(attributeName);
		} else {
			return "";
		}
	}

	public void compareTextOfWebElement(WebDriver driver, ExtentTest logger, By elementLocator, String expectedText, String elementName) {
		WebElement element = driver.findElement(elementLocator);
		if (element != null) {
			if (element.getText().contentEquals(expectedText)) {
				logPass(logger, "Correct text is getting displayed inside " + elementName);
			} else {
				logFail(logger, "Correct text is not getting displayed inside " + elementName);
			}
		}
	}

	public void compareAttributeValueOfWebElement(WebDriver driver, ExtentTest logger, By elementLocator, String attributeName, String expectedText, String elementName) {
		waitForElementExistence(driver, elementLocator, elementName, logger);

		try {
			WebElement element = driver.findElement(elementLocator);

			if (element != null) {
				if (element.getAttribute(attributeName).contentEquals(expectedText)) {
					logPass(logger, "Expected text is getting displayed inside " + elementName + "field");
				} else {
					logFail(logger, "Expected text is not getting displayed inside " + elementName + " Field, instead of " + expectedText + "," + element.getAttribute(attributeName) + " this is displayed");
				}
			}
		}
		catch (Exception e)
		{
			logFail(logger, e.getMessage()+" Error occured while getting the text attribute" + elementName + "field");
		}
	}

	public String getFileExtension(File file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf('.');
		if (lastIndexOf == -1) {
			return ""; // empty extension
		}
		return name.substring(lastIndexOf);
	}

	public void deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		}
	}

	/**
	 * This method is used to scroll to a particular element.
	 */
	public void scrollToElement(WebDriver driver, By locator) {
		WebElement element = driver.findElement(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

	}
	/**
	 * This method is used to Refresh the page.
	 */
	public void pageRefresh(WebDriver driver) {
		driver.navigate().refresh();
	}


	public static String getCurrentTimeStamp()
	{
		Date date=new Date();
		DateFormat dateFormat=new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
		return dateFormat.format(date);
	}

	public String getFirstFileNameFromLocation(ExtentTest logger,String locationPath)
	{
		String reqFileName="";
		try {
			File file = new File(locationPath);
			if (file.isDirectory()) {
				String fileName[] = file.list();
				reqFileName=fileName[0];
			}
		}
		catch( ArrayIndexOutOfBoundsException e)
		{
			logFail(logger,"There is no file presnt at the location");

		}
		return reqFileName;
	}




	/**
	 * This method is used to wait until the jquery is loaded
	 */
	public  void waitForJqueryTobeLoaded(WebDriver driver)
	{
		until(driver, (d) ->
		{
			Boolean isJqueryCallDone = (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active==0");
			if (!isJqueryCallDone) System.out.println("JQuery call is in Progress");
			return isJqueryCallDone;
		}, Long.parseLong("120"));
	}
	private static void until(WebDriver driver, Function<WebDriver, Boolean> waitCondition, Long timeoutInSeconds){
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutInSeconds);
		webDriverWait.withTimeout(timeoutInSeconds, TimeUnit.SECONDS);
		try{
			webDriverWait.until(waitCondition);
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {

	}


}



