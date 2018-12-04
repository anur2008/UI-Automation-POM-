package com.gems.common;


import com.gems.pages.common.DisplayEmptiesPage;
import com.gems.pages.common.GemsEqptLogPage;
import com.gems.pages.common.LoginPage;



public class GemsBaseTest extends BaseTest{


	public DisplayEmptiesPage login(final String usrName, final String oktUsrName, final String oktPwd)throws InterruptedException{
		LoginPage loginPage = new LoginPage();
		loginPage.launch();
		DisplayEmptiesPage dsplyEmptyPage=loginPage.login(usrName, oktUsrName, oktPwd);
		return dsplyEmptyPage;		
	}
	
	
	
}
	
	
