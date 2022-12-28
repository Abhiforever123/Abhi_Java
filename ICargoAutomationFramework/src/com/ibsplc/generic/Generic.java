package com.ibsplc.generic;

import com.ibsplc.common.BaseSetup;
import com.ibsplc.common.PropertyHandler;
import com.ibsplc.pageObjects.HomePage;
import com.ibsplc.pageObjects.LoginPage;
//import com.ibsplc.pageObjects.PortalHomePage;

import org.openqa.selenium.WebDriver;

public class Generic{

//	public static String dataFilePath = PropertyHandler.getPropValue("resources\\EnvSetup.properties","Login_data.properties");
//	String username = PropertyHandler.getPropValue(dataFilePath, "username");
//	String password = PropertyHandler.getPropValue(dataFilePath, "password");
//	String Companycode = PropertyHandler.getPropValue(dataFilePath, "Companycode");
	
	String dataFileName, browserName;
	WebDriver driver;
	
	public Generic(WebDriver driver, String browserName, String dataFileName) {
 
		this.driver = driver;
		this.browserName = browserName;
		this.dataFileName = dataFileName;
	}
	public HomePage login() 
	{
		LoginPage lp = new LoginPage(driver, browserName, dataFileName);
        return lp.doLogin();
	}
	public HomePage login(String username) {
		LoginPage lp = new LoginPage(driver, browserName, dataFileName);
        return lp.doLogin(username);
	}
	public HomePage Airlinlogin() {
		LoginPage lp = new LoginPage(driver, browserName, dataFileName);
        return lp.doAirlineLogin();
	}
	
	//*********Changes: A-8260 Logoff and Switch User
	public HomePage logoffAndSwitchUser(String username)
	{
		LoginPage lp = new LoginPage(driver, browserName, dataFileName);
        return lp.logoffAndSwitchUser( username);
	}
	
//	public PortalHomePage portalLogin(String portalURL){
//		
//		driver.get(portalURL);
//		PortalHomePage php = new PortalHomePage(driver, browserName, dataFileName);
//		return php.login();
//	}
}
