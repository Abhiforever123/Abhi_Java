package com.ibsplc.pageObjects;

import com.ibsplc.common.BasePage;
import com.ibsplc.common.PropertyHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Login extends BasePage {

	public static String filepath = PropertyHandler.getPropValue("resources\\EnvSetup.properties","Login_data");
	public static String objectFileName = PropertyHandler.getPropValue("resources\\EnvSetup.properties","Login_data");
	String username = PropertyHandler.getPropValue(filepath, "username");
	String password = PropertyHandler.getPropValue(filepath, "password");
	String Companycode = PropertyHandler.getPropValue(filepath, "Companycode");
	
	
	private WebDriver driver;
	
	
	public Login(WebDriver driver){
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}


}
