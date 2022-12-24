package com.test.practice;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrokenLinks {
	
	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://amazon.com");
		
		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println("Number of links are"+ links.size());
		
		List<String> urlList= new ArrayList<String>();
		
		
		for(WebElement e : links)
		{
			String url = e.getAttribute("href");
			urlList.add(url);
			//checkBrokenLink(url); //one way of doing it 
		}
		
		//using java streams
		urlList.parallelStream().forEach(e->checkBrokenLink(e));
		long endTime= System.currentTimeMillis();
		System.out.println("Time taken totally: "+endTime);
		driver.quit();
	}

	
	public static void checkBrokenLink(String linkUrl)
	{
		try {
			
			URL url = new URL(linkUrl);
			HttpURLConnection httpUrlConn= (HttpURLConnection) url.openConnection();
			httpUrlConn.setConnectTimeout(5000);
			httpUrlConn.connect();
			
			
			if(httpUrlConn.getResponseCode()>=400)
			{
				System.out.println(linkUrl+"--->"+httpUrlConn.getResponseMessage()+" is a broken link");
			}
			
			
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
}
