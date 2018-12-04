package com.gems.pages.common;

import org.openqa.selenium.WebElement;

import com.gems.constant.ApplicationConstant;
import com.gems.core.BasePage;

public class GemsEqptLogPage extends BasePage {
	
	public WebElement getEventNav() {
		return findElement(getLocatorValue("logEqpt.events.nav"));
	}
	public WebElement getEqmptLog() {
		return findElement(getLocatorValue("logEqpt.eqptLog.link"));
	}
	
	
	public WebElement getEqptIdTxtBox() {
		return findElement(getLocatorValue("logEqpt.eqptId.textbox"));
	}
	public WebElement getSearchButton() {
		return findElement(getLocatorValue("logEqpt.search.Btn"));
	}
	public WebElement getLogActionLnk() {
		return findElement(getLocatorValue("logEqpt.action.lnk"));
	} 
	public WebElement getLogActionExitBtn( ) {
		return findElement(getLocatorValue("logEqpt.action.exitBtn"));
	}
		public WebElement getLogOutLnk() {
			return findElement(getLocatorValue("logEqpt.logout.link"));
		} 
	
	public void mveToElmnt() {
		moveToElmnt(getEventNav(),getEqmptLog());
	}
	public void enterEqptId()
			throws InterruptedException {
		DisplayEmptiesPage displayEmptyPage=new DisplayEmptiesPage();
		//String eqpId=displayEmptyPage.getEqmtNumber();
		//String eqId=displayEmptyPage.selEqptId().getText();
		String eqptNum=displayEmptyPage.getEquipmentNumber();
		getEqptIdTxtBox().sendKeys(eqptNum);
		System.out.println("equiptId="+eqptNum);
	}

	public void clickSearchBtn() {
		getSearchButton().click();
	}
	public void clickLogActionLnk() {
		getLogActionLnk().click();
	} 
	public void clckExitBtn() {
		getLogActionExitBtn().click();
	} 
	public void clickLogOut() {
		getLogOutLnk().click();
		System.out.println("The window is clicked on logout for the first time");
	} 
//	private WebElement getUploadBtn() {
//		return findElement(getLocatorValue("home.upload.file.button"));
//	}
//
//	public void clickUploadFile() {
//		// System.out.println(getUploadBtn().getSize());
//		getUploadBtn().click();
//	}
//
//	private WebElement getdropDownContainer() {
//		return findElement(getLocatorValue("home.bubble.dropdown.container"));
//	}
//
//	public void clickDropDownContainer() {
//		getdropDownContainer().click();
//	}
//
//	private WebElement getChooseFilesBtn() {
//
//		return findElement(getLocatorValue("home.choose.button"));
//
//	}
//
//	public void clickChooseFilesBtn() {
//		getChooseFilesBtn().click();
//
//	}
//
//	private WebElement getDoneBtn() {
//		return findElement(getLocatorValue("home.upload.done.btn"));
//
//	}
//
//	public void clickDoneBtn() {
//		getDoneBtn().click();
//	}
//
//	private WebElement getLogoutBtn() {
//		return findElement(getLocatorValue("home.logout.link"));
//	}
//
//	private WebElement getSearchTextbox() {
//		return findElement(getLocatorValue("home.search.textbox"));
//
//	}
//
//	public void searchText(String fileName) {
//		getSearchTextbox().sendKeys(fileName);
//	}
//
//	private WebElement getSearchBtn() {
//		return findElement(getLocatorValue("home.search.btn"));
//	}
//
//	public void clickSearch() {
//		getSearchBtn().click();
//	}
//
//	private WebElement getSearchResults() {
//		return findElement(getLocatorValue("home.search.results"));
//	}
//
//	public boolean isfilePresent() {
//		final String result = ApplicationConstant.RESULT;
//		if (getSearchResults().getText().contains(result)) {
//
//			return true;
//		} else
//			return false;
//	}
//
//	private WebElement createfolderBtn() {
//		return findElement(getLocatorValue("home.folder.create.btn"));
//	}
//
//	public void clickCreateFolder() {
//		createfolderBtn().click();
//	}
//
//	private WebElement getFolderName() {
//		return findElement(getLocatorValue("home.folder.name.txtBox"));
//
//	}
//
//	public void enterFolderName(String name) {
//		getFolderName().sendKeys(name);
//	}
//
//	public void clickLogoutLink() {
//		getLogoutBtn().click();
//	}


	@Override
	public boolean isPageLoaded() {	
		return isElementDisplayed(getLocatorValue("logEqpt.action.hazardBtn"));
	}
	@Override
	public void waitForPageToLoad() {
		waitForElementVisible(getLocatorValue("logEqpt.eqptId.textbox"));
		
	}

}
