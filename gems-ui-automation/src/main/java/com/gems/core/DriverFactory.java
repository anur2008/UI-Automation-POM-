package com.gems.core;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverFactory {
	// DriverFactory class instance
	private static DriverFactory instance = new DriverFactory();
	// Browser thread local object
	private static ThreadLocal<String> browserThreadLocal = new ThreadLocal<String>();
	
	// Map to store driver object created for all threads
	private static ConcurrentHashMap<Long, WebDriver> driverMap = new ConcurrentHashMap<Long, WebDriver>();

	/**
	 * Private constructor to block DriverFactory object creation.
	 */
	private DriverFactory() {
		// Do-nothing..Do not allow to initialize this class from outside
	}

	/**
	 * This method is used to get DriverFactory class instance.
	 * 
	 * @return Driver Factory Object
	 */
	public static DriverFactory getInstance() {
		return instance;
	}

	/**
	 * This method is used to store browser name for current thread.
	 * 
	 * @param browserName
	 *            Browser name
	 */
	public void setBrowser(String browserName) {
		ApplicationLog.info("Setting Thread Local browser name as: "
				+ browserName + " for thread id:"
				+ Thread.currentThread().getId());
		browserThreadLocal.set(browserName);
	}

	/**
	 * This method is used to get browser name for current thread. If name is
	 * set then it will return null.
	 * 
	 * @return Browser name or null
	 */
	public static String getBrowser() {
		String browser = browserThreadLocal.get();
		System.out.println("browser="+browser);
		ApplicationLog.info("Getting Thread Local browser name: " + browser
				+ " for thread id:" + Thread.currentThread().getId());
		return browser;
	}

	/**
	 * This method is used to get drivers map.
	 * 
	 * @return Driver map object
	 */
	public ConcurrentHashMap<Long, WebDriver> getDrives() {
		return driverMap;
	}

	/**
	 * This method is used to set driver object for current thread. This method
	 * is called when we try to get driver from driverThreadLocal object using
	 * method getDriver() and driver is not set for current thread.
	 */
	ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<WebDriver>() {
		@Override
		protected WebDriver initialValue() {
			ApplicationLog.info("Driver is not set for Thread Local id:"
					+ Thread.currentThread().getId());
			WebDriver driver = null;
			try {
				driver = createDriver();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				ApplicationLog
						.info("Got exception while creating driver object: "
								+ e);
			}
			return driver;
		}
	};

	/**
	 * This method is used to get driver object for current thread.
	 * 
	 * @return Web Driver object
	 */
	public WebDriver getDriver() {
		return driverThreadLocal.get();
	}

	/**
	 * Quit the driver and remove it from local thread.
	 */
	synchronized public void removeDriver() {
		ApplicationLog.info("Removing driver for Thread Local id:"
				+ Thread.currentThread().getId());
		if (driverMap.containsKey(Thread.currentThread().getId())) {
			try {
				driverThreadLocal.get().quit();
				driverThreadLocal.remove();
				driverMap.remove(Thread.currentThread().getId());
				ApplicationLog.info("Removed driver for Thread Local id:"
						+ Thread.currentThread().getId());
			} catch (Exception exc) {
				// just ignore exception is any
				ApplicationLog.logException(
						"Caught exception while quit driver:", exc);
			}
		}
	}

	/**
	 * This method is used to create driver object and store driver object in
	 * driverMap for future reference.
	 * 
	 * @return Web Driver Object
	 * @throws Exception
	 */ 
	synchronized private WebDriver createDriver() throws Exception {
		WebDriver driver = null;
		String browser = getBrowser();
		if (browser == null) {
			browser = ApplicationContext.getInstance().getDefaultBrowser();
		}

		ApplicationLog.info("Creating driver for: " + browser
				+ " for thread id:" + Thread.currentThread().getId());
		if (ApplicationContext.getInstance().isRemoteExecution()) {
			DesiredCapabilities caps = null;
			if (browser.equalsIgnoreCase("Firefox")) {
				LoggingPreferences logPrefs = new LoggingPreferences();
				logPrefs.enable(LogType.DRIVER, Level.OFF);
				caps = DesiredCapabilities.firefox();
				caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			} else if (browser.equalsIgnoreCase("Chrome")) {
				File file = new File(System.getProperty("user.dir")
						+ "/drivers/chromedriver.exe");
				System.setProperty("webdriver.chrome.driver",
						file.getAbsolutePath());
				caps = DesiredCapabilities.chrome();
			} 
			else if (browser.equalsIgnoreCase("IE")) {
				File file = new File(System.getProperty("user.dir")
						+ "/drivers/IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver",
						file.getAbsolutePath());
				caps = DesiredCapabilities.internetExplorer();
				caps.setCapability(
						InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				caps.setCapability(
						CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,
						true);
				caps.setCapability("ignoreProtectedModeSettings", true);
				caps.setCapability(
						InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,
						false);
				caps.setCapability(
						InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				caps.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL,
						"about:blank");
			}

		} else if (browser.equalsIgnoreCase("Firefox")) {
			LoggingPreferences logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.DRIVER, Level.OFF);
			DesiredCapabilities caps = DesiredCapabilities.firefox();
			caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			driver = new FirefoxDriver(caps);
		} else if (browser.equalsIgnoreCase("Chrome")) {
			File file = new File(System.getProperty("user.dir")
					+ "/drivers/chromedriver.exe");
			System.setProperty("webdriver.chrome.driver",
					file.getAbsolutePath());
			DesiredCapabilities caps = DesiredCapabilities.chrome();
			// Disable extensions
			ChromeOptions options = new ChromeOptions();
			options.addArguments("chrome.switches", "--disable-extensions");
			caps.setCapability(ChromeOptions.CAPABILITY, options);

			driver = new ChromeDriver(caps);
		} else if (browser.equalsIgnoreCase("IE")) {
			File file = new File(System.getProperty("user.dir")
					+ "/drivers/IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
			caps.setCapability(
					InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
					true);
			caps.setCapability(
					CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,
					true);
			caps.setCapability("ignoreProtectedModeSettings", true);
			caps.setCapability(
					InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
			caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,
					true);
			caps.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL,
					"about:blank");
			caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS,true);
			caps.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, true);
			driver = new InternetExplorerDriver(caps);

		} else {
			throw new Exception("Invalid browser name:" + browser);
		}
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		// Add driver object in driverMap
		driverMap.put(Thread.currentThread().getId(), driver);
		ApplicationLog.info("Created driver for: " + browser
				+ " for thread id:" + Thread.currentThread().getId());
		return driver;
	}
}


			
			
				