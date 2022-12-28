package com.ibsplc.pageObjects;

import org.openqa.selenium.WebDriver;

public class LoginPage {

	WebDriver driver;
	private String dataFileName;
	public LoginPage(WebDriver driver, String browserName, String dataFileName) {
		// TODO Auto-generated constructor stub
	}

	

	public HomePage doLogin(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public HomePage doAirlineLogin() {
		// TODO Auto-generated method stub
		return null;
	}



	public HomePage doLogin() {
		// TODO Auto-generated method stub
		//return null;
		return new HomePage(driver,dataFileName);
	}



	public HomePage logoffAndSwitchUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
