package com.gems.common;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.gems.core.ApplicationContext;
import com.gems.core.ApplicationLog;
import com.gems.core.BasePage;
import com.gems.core.DriverFactory;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult; 

public abstract class BaseTest {
	public static ExtentTest test;
	public static ExtentReports report;
	private static final String EXTEND_TEST_RESULTS = "/ExtendReportResults.html"; 

	
	@AfterMethod
	public void quitDriverAfterMethod(ITestResult result, Method method) throws IOException
	{			
		if(result.getStatus()==ITestResult.FAILURE)
		{	    		
			String failTemp=BasePage.captureFailedScreenshot(DriverFactory.getInstance().getDriver());
			System.out.println("The error screenshot path is " + failTemp);
			test.log(LogStatus.FAIL,test.addScreenCapture(failTemp)+ method.getName() + " ----> Test Method has Failed :(");		
		}
		else
		{
		   String passTemp=BasePage.capturePassedScreenshot(DriverFactory.getInstance().getDriver());
			System.out.println("The passed screenshot path is " + passTemp);
		    test.log(LogStatus.PASS,test.addScreenCapture(passTemp) + method.getName() + " ----> Test Method has Passed :) ");
		}
		ApplicationLog.info("In @quitDriverAfterMethod for thread id:"
				+ Thread.currentThread().getId());
		ApplicationLog.info(" for method: " + method.getName());
		DriverFactory.getInstance().removeDriver();
		
		report.endTest(test);	
	}  

	@BeforeSuite
	public void beforeSuite() {
		ApplicationLog.info("In @BeforeSuite for thread id:"
				+ Thread.currentThread().getId());
		// Load default properties files like OR.properties,
		// applicationConfig.properties
		ApplicationContext.getInstance().loadDefaultProperties();
		report= new ExtentReports(System.getProperty("user.dir")+EXTEND_TEST_RESULTS, true); 
	}

	@BeforeTest
	@Parameters({ "browser" })
	public void beforeTest(@Optional String browser) {
		ApplicationLog.info("In @BeforeTest for thread id:"
				+ Thread.currentThread().getId());
		DriverFactory.getInstance().setBrowser(browser);
	}
	@BeforeMethod
	public void startTest(Method method)
	{	
	test = report.startTest((this.getClass().getSimpleName() + " :: " + method.getName()),method.getName());
	} 

	@AfterClass
	public void quitDriverAfterClass() {
		ApplicationLog.info("In @quitDriverAfterClass for thread id:"
				+ Thread.currentThread().getId());
	}

	@AfterTest
	public void quitDriverAfterTest() {
		ApplicationLog.info("In @quitDriverAfterTest for thread id:"
				+ Thread.currentThread().getId());
	}

	@AfterSuite
	public void afterSuite() {
		ApplicationLog.info("In @afterSuite for thread id:"
				+ Thread.currentThread().getId());
		report.flush();
		report.close(); 
	}
}
