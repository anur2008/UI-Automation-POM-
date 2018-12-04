package com.gems.pages.common;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.gems.core.ApplicationContext;
import com.gems.core.BasePage;
import com.gems.core.DriverFactory;
import com.gems.pages.common.GemsEqptLogPage;
import org.openqa.selenium.Keys;

public class LoginPage extends BasePage {

	public WebElement getSigninLink() {
		return findElement(getLocatorValue("login.oktSignin.Btn"));
	}

	public WebElement getUserName() throws InterruptedException {
		return findElement(getLocatorValue("login.usrName.textbox"));
	}
	public WebElement getOktUserName() throws InterruptedException {
		return findElement(getLocatorValue("login.oktUsrName.textbox"));
	}
	public WebElement getOktPwd() throws InterruptedException {
		return findElement(getLocatorValue("login.oktPwd.textbox"));
	}

	public WebElement getPassword() {
		return findElement(getLocatorValue("login.password.textbox"));
	}

	public WebElement getOktaSignInBtn() {
		return findElement(getLocatorValue("login.oktSignin.Btn"));
	}
	public WebElement getLogoutlnk() {
		return findElement(getLocatorValue("logEqpt.logout.link"));
	}
	public WebElement getSearchButton() {
		return findElement(getLocatorValue("logEqpt.search.Btn"));
	}

	public void launch() {
		final String applicationURL;
		applicationURL = ApplicationContext.getInstance().getApplicationUrl();
		go(applicationURL);
	}

	public void enterUserName(final String userName)
			throws InterruptedException {
		getUserName().sendKeys(userName);
	}
	public void enterOktUserName(final String oktuserName)
			throws InterruptedException {
		getUserName().sendKeys(oktuserName);
	}
	public void clickLogout()
			throws InterruptedException {
		getLogoutlnk().click();
		System.out.println("The main windows logout is now clicked");
	}	

	public void enterPassword(final String password) {

		getPassword().sendKeys(password);
	}
	public void clickTab(final String usrName)throws InterruptedException {

		getUserName().sendKeys(Keys.TAB);
	}

	public void clickOktaSignin() {
		getOktaSignInBtn().click();
	}
	public void clickSearchBtn() {
		getSearchButton().click();
	}
	

	

	@Override
	public boolean isPageLoaded() {
		return isElementDisplayed(getLocatorValue("login.btn"));
	}
	@Override
	public void waitForPageToLoad() {
		waitForElementVisible(getLocatorValue("logEqpt.eqptId.textbox"));
	}

//	@Override
//	public void waitForPageToLoad() {
//		waitForElementVisible(getLocatorValue("sign-in.link"));
//	}

//	public DropboxHomePage login(final String userName, final String password)
//			throws InterruptedException {
//		DropboxHomePage homePage = new DropboxHomePage();
//		if (isPageLoaded()) {
//			DriverFactory.getInstance().getDriver().manage().deleteAllCookies();
//			getSigninLink().click();
//			getUserName().isDisplayed();
//			getUserName().sendKeys(userName);
//			getPassword().sendKeys(password);
//			getLoginButton().click();
//
//		}
//
//		return homePage;
//	}
	
	public DisplayEmptiesPage login(final String usrName, final String oktUsrName, final String oktPwd)
			throws InterruptedException {
		DisplayEmptiesPage dsplyEmptyPage = new DisplayEmptiesPage();
		if (isPageLoaded()) {
			DriverFactory.getInstance().getDriver().manage().deleteAllCookies();
			//getSigninLink().click();
			getUserName().isDisplayed();
			getUserName().sendKeys(usrName);
			Actions action = new Actions(DriverFactory.getInstance().getDriver()); 
			action.sendKeys(getUserName(),Keys.TAB).build().perform();
			//clickTab(usrName);
			//System.out.println(oktPwd);
			getOktUserName().sendKeys(oktUsrName);
			getOktPwd().sendKeys(oktPwd);
			clickOktaSignin();
			//DriverFactory.getInstance().getDriver().switchTo().window();
			
			//getEqptId().sendKeys(eqptId);
			
			//System.out.println(oktPwd);
		
			//getLoginButton().click();

		}

		return dsplyEmptyPage;
	}
}
