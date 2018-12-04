package com.gems.pages.common;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.gems.constant.ApplicationConstant;
import com.gems.core.BasePage;
import com.gems.core.DriverFactory;






public class DisplayEmptiesPage extends BasePage {
	private static String eqId=null;
	private static  String eqpId=null;		
	public WebElement getInventoryNav() {
		return findElement(getLocatorValue("dsplyEmpties.inventory.nav"));		
		   }  
	
	public WebElement getDisplyEmpties()
	{
	return findElement(getLocatorValue("dsplyEmpties.select.item"));
	}
	public WebElement getLocationCode() {
		return findElement(getLocatorValue("dsplyEmpties.locationCode.text"));
				
	}
	public WebElement geteqptType() {
		return findElement(getLocatorValue("dsplyEmpties.eqmptType.text"));
				
	}
	public WebElement getSearchBtn() {
		return findElement(getLocatorValue("dsplyEmpties.search.Btn"));
		
				
	}
	public WebElement geteqptNumMsgText() {
		return findElement(getLocatorValue("dsplyEmpties.msg.text"));
				
	}
		
	public WebElement selEqptId() {
		return findElement(getLocatorValue("dsplyEmpties.select.eqptId"));
		
	}
	public WebElement getEqptTable() {
		return findElement(getLocatorValue("dsplyEmpties.table.id"));
	}

	public void mveToElmnt() {
		moveToElmnt(getInventoryNav(),getDisplyEmpties());
	}
	
	public void clickDisplayEmpty() {
		getDisplyEmpties().click();
		//System.out.println(S);
	}
		public void enterLoctionCode()
				throws InterruptedException {
			getLocationCode().sendKeys(ApplicationConstant.locCode);
		}
		public void enterEqType()
				throws InterruptedException {
			geteqptType().sendKeys(ApplicationConstant.eqType);
		}
		

		public void clickSearchBtn() {
			getSearchBtn().click();
		}
		
		
		public String getEqpmtNum() {
		 eqId=selEqptId().getText();
			System.out.println("EquipmentId:"+eqId);
			return eqId;
		}
		
		public String getEquipmentNumber() {
			this.eqpId=eqId;
			System.out.println("EquipmentId1:"+eqId);
			return eqpId;			
			
		}
			
	
	

	@Override
	public boolean isPageLoaded() {
		return isElementDisplayed(getLocatorValue("dsplyEmpties.inventory.nav"));
		
	}

	@Override
	public void waitForPageToLoad() {
		waitForElementsVisible(getLocatorValue("dsplyEmpties.eqmptType.text"));
		
	}
	

}

