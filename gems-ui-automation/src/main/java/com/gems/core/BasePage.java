package com.gems.core;


import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.gems.constant.ApplicationConstant;
import com.gems.enums.SleepConstant;

public abstract class BasePage {
	private static final int DEFAULT_WAIT_TIME = 15;

	public abstract boolean isPageLoaded();

	public abstract void waitForPageToLoad();

	public WebDriver getDriver() {
		return DriverFactory.getInstance().getDriver();
	}

	public void refreshPage() {
		getDriver().navigate().refresh();
	}

	public String getLocatorValue(final String locator) {
		return ApplicationContext.getInstance().getLocatorProps()
				.getProperty(locator, "");
	}

	public void go(String applicationURL) {
		try {
			RemoteWebDriver remoteDriver = ((RemoteWebDriver) DriverFactory
					.getInstance().getDriver());
			String browserName = remoteDriver.getCapabilities()
					.getBrowserName();
			System.out.println("*** Application launch Start for browser: "
					+ browserName + " at :" + new Date());
			getDriver().get(applicationURL);
			System.out.println("*** Application launch End for browser: "
					+ browserName + " at :" + new Date());
			getDriver().manage().window().maximize();
		} catch (WebDriverException exp) {
			DriverFactory.getInstance().removeDriver();
			ApplicationLog
					.info("Got exception while launching application for test: "
							+ Reporter.getCurrentTestResult().getName());
			throw exp;
		}
	}

	public String getPageTitle() {
		return getDriver().getTitle();
	}

	public String getPageUrl() {
		return getDriver().getCurrentUrl();
	}

	protected WebElement findElement(String descLoc) {
		waitForElementVisible(descLoc);
		//return getDriver().findElement(getBy(descLoc));
		return highLightElement(getDriver().findElement(getBy(descLoc))); 
	}

	protected List<WebElement> findElements(String desciptiveLoc) {
		waitForElementVisible(desciptiveLoc);
		return getDriver().findElements(getBy(desciptiveLoc));
	}

	protected boolean isElementDisplayed(String desciptiveLoc) {
		boolean isElementDisplayed = false;
		List<WebElement> elements = getDriver().findElements(
				getBy(desciptiveLoc));
		if (elements.size() > 0) {
			isElementDisplayed = elements.get(0).isDisplayed();
		}
		return isElementDisplayed;
	}

	protected void waitForElementVisible(final String descriptiveLocator,
			final int... timeoutSeconds) {
		try {
			getWait(timeoutSeconds)
					.until(ExpectedConditions
							.visibilityOfElementLocated(getBy(descriptiveLocator)));
		} catch (TimeoutException ignore) {
			// Ignore timeout exception
		}
	}

	/**
	 * This method is used to wait for elements to become visible.
	 * 
	 * @param descriptiveLocator
	 *            Descriptive locators to locate elements.
	 * @param timeoutSeconds
	 *            Max time to wait for elements to become visible.
	 */
	protected void waitForElementsVisible(final String descriptiveLocator,
			final int... timeoutSeconds) {
		try {
			getWait(timeoutSeconds)
					.until(ExpectedConditions
							.visibilityOfAllElementsLocatedBy(getBy(descriptiveLocator)));
		} catch (TimeoutException ignore) {
			ApplicationLog
					.warn("Elements located by selector are not visible :"
							+ getBy(descriptiveLocator).toString());
		}
	}

	protected void waitForElementInVisible(final String descriptiveLocator,
			final int... timeoutSeconds) {
		try {
			getWait(timeoutSeconds)
					.until(ExpectedConditions
							.invisibilityOfElementLocated(getBy(descriptiveLocator)));
		} catch (TimeoutException ignore) {
			ApplicationLog.warn("Element located by selector is not visible :"
					+ getBy(descriptiveLocator).toString());
		}
	}

	public void waitForElementToBeClickable(final String descriptiveLocator,
			final int... timeoutSeconds) {
		try {
			getWait(timeoutSeconds).until(
					ExpectedConditions
							.elementToBeClickable(getBy(descriptiveLocator)));
		} catch (TimeoutException ignore) {
			ApplicationLog
					.warn("Element located by selector is not clickable :"
							+ getBy(descriptiveLocator).toString());
		}
	}

	public void waitForElementToBeDisplayed(final String descriptiveLocator,
			final int... timeoutSeconds) {
		try {
			getWait(timeoutSeconds).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(final WebDriver driver) {
					final List<WebElement> elements = driver
							.findElements(getBy(descriptiveLocator));
					return elements != null && !elements.isEmpty()
							&& elements.get(0).isDisplayed();
				}
			});
		} catch (TimeoutException ignore) {
			ApplicationLog
					.warn("Element located by selector is not displayed :"
							+ getBy(descriptiveLocator).toString());
		}
	}

	public void waitForAttribute(final String descriptiveLocator,
			final String attribute, final String value,
			final int... timeoutSeconds) {
		try {
			getWait(timeoutSeconds).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(final WebDriver driver) {
					final WebElement element = driver
							.findElement(getBy(descriptiveLocator));
					return element.getAttribute(attribute) != null
							&& element.getAttribute(attribute).contains(value);
				}
			});
		} catch (TimeoutException ignore) {
			ApplicationLog
					.warn("Element located by selector is does not have required attribute with value:"
							+ getBy(descriptiveLocator).toString());
			ApplicationLog.warn("Expected attribute: " + attribute
					+ " with value: " + value);
		}
	}

	protected void waitForElementText(final WebElement element,
			final String text, final int... timeoutSeconds) {
		try {
			getWait(timeoutSeconds).until(
					ExpectedConditions.textToBePresentInElement(element, text));
		} catch (TimeoutException ignore) {
			ApplicationLog
					.warn("Element located by selector is does not have required text:"
							+ text);
		}
	}

	/**
	 * This method is used to wait for text to be present in element value
	 * attribute.
	 * 
	 * @param element
	 *            Element for which to wait
	 * @param text
	 *            Text for which to wait
	 * @param timeoutSeconds
	 *            Max seconds to wait for value
	 */
	protected void waitForTextToBePresentInElementValue(
			final WebElement element, final String text,
			final int... timeoutSeconds) {
		try {
			getWait(timeoutSeconds).until(
					ExpectedConditions.textToBePresentInElementValue(element,
							text));
		} catch (TimeoutException ignore) {
			ApplicationLog.warn("Element does not have expected value in it:"
					+ text);
		}
	}

	public void waitForElementToEnable(final String descriptiveLocator,
			final int... timeoutSeconds) {
		try {
			getWait(timeoutSeconds).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(final WebDriver driver) {
					final WebElement element = driver
							.findElement(getBy(descriptiveLocator));
					return element.isEnabled()
							&& element.getAttribute("disabled") == null;
				}
			});
		} catch (TimeoutException ignore) {
			ApplicationLog.warn("Element located by selector is not enable :"
					+ getBy(descriptiveLocator).toString());
		}
	}

	protected void mouseOver(final WebElement element) {
		Actions action = new Actions(getDriver());
		action.moveToElement(element).build().perform();
		sleep(SleepConstant.THREE_HUNDRED_MILLISECONDS);
	}
	protected void moveToElmnt(final WebElement element, final WebElement element2)
	{
		Actions act = new Actions(getDriver());
		new WebDriverWait(getDriver(), 60).until(ExpectedConditions.visibilityOf(element));
		act.moveToElement(element).moveToElement(element2).click().build().perform();
	}

	public void clickUsingJavaScript(final WebElement element) {
		((JavascriptExecutor) getDriver()).executeScript(
				"arguments[0].click();", element);
	}

	/**
	 * This method is used to verify is alert box displayed or not.
	 * 
	 * @return True if alert displayed else false
	 */
	public boolean isAlertPresent() {
		try {
			getDriver().switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}

	public void acceptAlert() {
		Alert alert = getDriver().switchTo().alert();
		alert.accept();
	}

	public void dismissAlert() {
		Alert alert = getDriver().switchTo().alert();
		alert.dismiss();
	}

	public void pause(int... seconds) {
		int waitTime = 5000;
		if (seconds.length > 0) {
			waitTime = seconds[0] * 1000;
		}
		sleep(waitTime);
	}

	/**
	 * This method is used to sleep for one milliseconds by default.
	 */
	public void sleep() {
		sleep(SleepConstant.ONE_SECOND.getMilliSeconds());
	}

	/**
	 * This method is used to sleep for given milliseconds.
	 * 
	 * @param milliSeconds
	 *            milliseconds for sleep
	 */
	public void sleep(final long milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to sleep for given milliseconds.
	 * 
	 * @param SleepConstant
	 *            SleepConstant value for sleep/pause
	 */
	public void sleep(final SleepConstant sleepConstant) {
		sleep(sleepConstant.getMilliSeconds());
	}

	private WebDriverWait getWait(final int... time) {
		// Verify is explicit wait time passed
		int waitTime = DEFAULT_WAIT_TIME;
		if (time.length > 0) {
			waitTime = time[0];
		}
		getDriver().manage().timeouts()
				.implicitlyWait(0, TimeUnit.MILLISECONDS);
		final WebDriverWait wait = new WebDriverWait(getDriver(), waitTime, 2);
		wait.ignoring(WebDriverException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(NoSuchWindowException.class);
		return wait;
	}

	public By getBy(final String desciptiveLoc) {
		String[] locValue;
		System.out.println("descriptiveLoc is " + desciptiveLoc);
		try {
			locValue = desciptiveLoc.split("=", 2);
			System.out.println("locValue[0] is " + locValue[0]);
		} catch (Exception exe) {
			System.out.println("Invalid locator:=> " + desciptiveLoc);
			throw exe;
		}
		switch (locValue[0].toUpperCase()) {
		case "ID":
			return By.id(locValue[1]);
		case "NAME":
			return By.name(locValue[1]);
		case "CSS":
			return By.cssSelector(locValue[1]);
		case "CLASSNAME":
			return By.cssSelector(locValue[1]);
		case "LINKTEXT":
			return By.linkText(locValue[1]);
		case "PARTIALLINKTEXT":
			return By.partialLinkText(locValue[1]);
		case "TAGNAME":
			return By.tagName(locValue[1]);
		default:
			return By.xpath(locValue[1]);
		}
	}
	

	public static String captureFailedScreenshot(WebDriver driver)
	{
		String path = null;
		Date now = new Date();
		String pattern = "EEEEE dd MMMMM yyyy HH:mm:ss.SSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date =simpleDateFormat.format(now);
		date = date.replace(' ', '_');
		date = date.replace(':', '_');
		TakesScreenshot ts=(TakesScreenshot)driver;	 	      
		File source=ts.getScreenshotAs(OutputType.FILE);		 
		String dirPath=System.getProperty("user.dir")+"\\ErrorScreenshots\\";  
if(removeDir(dirPath)== true) {
			path = System.getProperty("user.dir")+"\\ErrorScreenshots\\"+ ApplicationContext.getInstance().getDefaultBrowser()+ "_" + date  +".png";
			File destination=new File(path);
			try 
			{
				FileUtils.copyFile(source, destination);
			} catch (IOException e) 
			{
				System.out.println("Failed Screenshot Capture Failed "+e.getMessage());
			}

		}				

		return path;
	}  
	public static String capturePassedScreenshot(WebDriver driver)
	{
		String path = null;
		Date now = new Date();
		String pattern = "EEEEE dd MMMMM yyyy HH:mm:ss.SSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date =simpleDateFormat.format(now);
		date = date.replace(' ', '_');
		date = date.replace(':', '_');
		TakesScreenshot ts=(TakesScreenshot)driver;	 	      
		File source=ts.getScreenshotAs(OutputType.FILE);		 
		String dirPath=System.getProperty("user.dir")+"\\PassedScreenshots\\"; 
		if(removeDir(dirPath)== true) {
			path = System.getProperty("user.dir")+"\\PassedScreenshots\\"+ ApplicationContext.getInstance().getDefaultBrowser()+ "_" + date  +".png";
			File destination=new File(path);
			try 
			{ 
			FileUtils.copyFile(source, destination);
			} catch (IOException e) 
			{
				System.out.println("Passed Screenshot Capture Failed "+e.getMessage());
			}

		}				

		return path;
	}  

	public void switchToWindow() {		
		try {
			// firstWinHandle = The  window handle of the primary parent window
			String firstWinHandle = DriverFactory.getInstance().getDriver().getWindowHandle(); 
			waitForNumberofWindowsToEqual(2);//this method is for waiting till 2 windows come up 		 
			Set handles = DriverFactory.getInstance().getDriver().getWindowHandles();		 
			handles.remove(firstWinHandle);
			for (String handle : DriverFactory.getInstance().getDriver().getWindowHandles()) {
				if (handle!=firstWinHandle) {	 
					DriverFactory.getInstance().getDriver().switchTo().window(handle).manage().window().maximize();	
					System.out.println("The focus is now shifted to the new window successfully !!");
				}		
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}	

	}
	
		public void switchToMainLogoutWindow() {
				try {			
					for (String handle : DriverFactory.getInstance().getDriver().getWindowHandles()) {				 
						DriverFactory.getInstance().getDriver().switchTo().window(handle);}
					System.out.println("Reached underlying Main window");
				}
				catch (Exception e) {
					e.printStackTrace();
				}	
			} 

	public void waitForNumberofWindowsToEqual(final int numberOfWindows) {              
		new WebDriverWait(DriverFactory.getInstance().getDriver(),30){}.until(new ExpectedCondition<Boolean>(){   
			@Override public Boolean apply(WebDriver driver) {
				return (driver.getWindowHandles().size() == numberOfWindows);}
		});

	}


	public static boolean removeDir(String dirPath)
	{
		try
		{
			File destFile = new File(dirPath);
			// checks if the directory has any file, lists the files in the directory and deletes it one after the other
			if (destFile.isDirectory())
			{
				File[] files = destFile.listFiles();
				if (files != null && files.length > 0)
				{
					for (File aFile : files) 
					{
						System.gc();
						Thread.sleep(2000);
						FileDeleteStrategy.FORCE.delete(aFile);
						System.out.println("The deleted file is: " +aFile);
					};
				}
				destFile.delete();
				System.out.println("The deleted Dirctory is: " +destFile);
			} 
			else 
			{
				// if directory has no files, delete the directory as it is 
				destFile.delete();
			}
		}
		catch(Exception e)
		{
			ApplicationLog.warn("Exception Occured while Deleting Folder :" +e);
		}
		return true;
	} 
	public static WebElement highLightElement( WebElement element)
	{
		JavascriptExecutor js=(JavascriptExecutor)DriverFactory.getInstance().getDriver(); 
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red; border-style: dashed;');", element);
		try 
		{
			Thread.sleep(1000);
		} 
		catch (InterruptedException e) {
			System.out.println(e.getMessage());
		} 
		js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);
		return element; 

	} 
	
		public void checkPageIsReady() {

			  JavascriptExecutor js = (JavascriptExecutor)DriverFactory.getInstance().getDriver();
			  //Initially the below condition will check the ready state of page.
			  if (js.executeScript("return document.readyState").toString().equals("complete")){ 
			   System.out.println("The Page is already loaded!!");
			   return; 
			  } 

			  //This loop will rotate for 25 times to check if page is ready after every 1 second.
			  for (int i=0; i<25; i++){ 
			   try { 
		    Thread.sleep(1000);
			    }catch (InterruptedException e) {} 
			   //To check page ready state.
			   if (js.executeScript("return document.readyState").toString().equals("complete")){ 
				   System.out.println("The Page is loaded after " + i  + " seconds");
			    break; 
			   }   
			  }
			 }
		} 



