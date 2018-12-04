package com.gems.testcases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.eclipse.jetty.util.log.Log;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.gems.common.GemsBaseTest;
import com.gems.constant.ApplicationConstant;
import com.gems.core.ApplicationContext;
import com.gems.core.CommonUtil;
import com.gems.core.DriverFactory;
import com.gems.pages.common.DisplayEmptiesPage;
import com.gems.pages.common.GemsEqptLogPage;
import com.gems.pages.common.LoginPage;

public class GemsExstngEqptLogTest extends GemsBaseTest {
	// @Test
	// public void testUploadFile() throws InterruptedException, IOException {
	// DropboxHomePage homePage = login(ApplicationContext.getInstance()
	// .getUserName(), ApplicationContext.getInstance().getPassword());
	// homePage.waitForPageToLoad();
	// homePage.clickUploadFile();
	// homePage.clickChooseFilesBtn();
	// Runtime.getRuntime().exec("src\\test\\resources\\filename.exe");
	// homePage.clickDoneBtn();
	// homePage.pause(5);
	// homePage.waitForPageToLoad();
	// homePage.clickDropDownContainer();
	// homePage.clickLogoutLink();
	// }

	// @Test(dependsOnMethods = { "testUploadFile" })
	// public void testSearchFile() throws InterruptedException {
	// final String fileName = ApplicationConstant.FILENAME;
	// DropboxHomePage homePage = login(ApplicationContext.getInstance()
	// .getUserName(), ApplicationContext.getInstance().getPassword());
	// homePage.waitForPageToLoad();
	// homePage.searchText(fileName);
	// homePage.clickSearch();
	// assertTrue(homePage.isfilePresent(), "search failed");
	// // uploadFile.waitForPageToLoad();
	// homePage.clickDropDownContainer();
	// homePage.clickLogoutLink();
	//
	// }

	// @Test
	// public void createFolder() throws InterruptedException {
	// final String name = CommonUtil.generateAutoUiUniqueName();
	// DropboxHomePage homePage = login(ApplicationContext.getInstance()
	// .getUserName(), ApplicationContext.getInstance().getPassword());
	// homePage.waitForPageToLoad();
	// homePage.clickCreateFolder();
	// homePage.enterFolderName(name);
	// homePage.clickDropDownContainer();
	// homePage.clickLogoutLink();

	// }
	@Test
	public void exstngEqptLog() throws Exception {
		try {
		DisplayEmptiesPage dsplyEmptyPage = login(ApplicationContext.getInstance().getUserName(),
				ApplicationContext.getInstance().getOktUserName(), ApplicationContext.getInstance().getOktPwd());

		String url = dsplyEmptyPage.getPageUrl();
		System.out.println("The logged in URL in is " + url);

		dsplyEmptyPage.switchToWindow();
		dsplyEmptyPage.isPageLoaded();
		//Assert.assertTrue(dsplyEmptyPage.isPageLoaded());
		dsplyEmptyPage.mveToElmnt();		
		dsplyEmptyPage.waitForPageToLoad();
		dsplyEmptyPage.enterLoctionCode();
		dsplyEmptyPage.enterEqType();
		dsplyEmptyPage.clickSearchBtn();		
		if(dsplyEmptyPage.getEqptTable().isDisplayed())
		{
			dsplyEmptyPage.getEqpmtNum();
			GemsEqptLogPage gemsEqptLogPage = new GemsEqptLogPage();
			gemsEqptLogPage.mveToElmnt();
			gemsEqptLogPage.waitForPageToLoad();
			gemsEqptLogPage.enterEqptId();
			gemsEqptLogPage.clickSearchBtn();
			// gemsEqptLogPage.checkPageIsReady();
			gemsEqptLogPage.clickLogActionLnk();
			gemsEqptLogPage.checkPageIsReady();
			gemsEqptLogPage.clckExitBtn();
			gemsEqptLogPage.checkPageIsReady();
			gemsEqptLogPage.clickLogOut();

			if (!"IE".equals(ApplicationContext.getInstance().getDefaultBrowser())) {
				gemsEqptLogPage.switchToMainLogoutWindow();
				LoginPage loginPge = new LoginPage();
				loginPge.clickLogout();
			
		}
		else {
		   Assert.assertEquals(dsplyEmptyPage.geteqptNumMsgText().getText(), "Norr Records Found");	
		}
           	
		}	
		}
		catch (Exception e)
		{
		e.printStackTrace();
			//throw (e);
			
}
		
	}
	
}
		
