package com.test.practice;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WaitFunctionalities {
	
	
	static WebDriver driver;
		
	@SuppressWarnings("deprecation")
	public static void implicitWait()
	{
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.google.com");
		
		driver.manage().timeouts().implicitlyWait( 1000,TimeUnit.SECONDS);
		//This implicit wait will wait for the enitre page the page load is the object of implicit wait
	}
	
	public static void explicitWait()
	{
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
	}

}
