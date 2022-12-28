package com.ibsplc.pageObjects;

import org.openqa.selenium.WebDriver;

public class HomePage {
	
	private WebDriver driver;
	private String dataFileName;
	public HomePage(WebDriver driver, String dataFileName)
	{
		this.driver=driver;
		this.dataFileName=dataFileName;
		
	}
	
	
	public HomePage gotoBooks() 
	{
		
		return this;
	}

}
