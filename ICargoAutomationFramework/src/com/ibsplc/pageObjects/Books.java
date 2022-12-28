package com.ibsplc.pageObjects;

import org.openqa.selenium.WebDriver;

import com.ibsplc.common.BasePage;

public class Books extends BasePage{

	private static WebDriver driver;
	private String dataFileName;
	public Books(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public Books(WebDriver driver, String dataFileName)
	{
		super(driver);
		this.driver=driver;
		this.dataFileName=dataFileName;
	}

}
