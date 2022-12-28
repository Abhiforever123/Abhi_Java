package com.ibsplc.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;


public class  EventHandler implements WebDriverEventListener{
	private final static Logger logger = Logger.getLogger(EventHandler.class);
	private final List<String> discardedTags = Arrays.asList("textarea");

	@Override
	public void afterAlertAccept(WebDriver arg0) {
		logger.info("Alert has been accepted");
		
	}

	@Override
	public void afterAlertDismiss(WebDriver arg0) {
		logger.info("Alert has been dismissed");
		
	}

	@Override
	public void afterChangeValueOf(WebElement arg0, WebDriver arg1,
			CharSequence[] arg2) {
		String value = null;
		if (arg2 != null){
			value="";
			for (CharSequence ch:arg2)
				value = value + String.valueOf(ch);
		}
		if (value != null)
			logger.info("Entered the text ["+value+"] to the element ["+getElementDetails(arg0, arg1)+"]");
		else
			logger.info("Cleared text on the element ["+getElementDetails(arg0, arg1)+"]");
		
	}

	@Override
	public void afterClickOn(WebElement arg0, WebDriver arg1) {
		logger.info("Clicked on element ["+getElementDetails(arg0, arg1)+"]");
		
	}

	@Override
	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		WebElement element = null;
		try{
			element = arg2.findElement(arg0);
		}catch(Exception e){}
		
		if (element != null){
			logger.info("Found element ["+getElementDetails(element, arg2)+"]");
			highlightElement(element, arg2);
		}
		else
			logger.warn("Could not find element ["+arg0+"]");
	}

	@Override
	public void afterNavigateBack(WebDriver arg0) {
		logger.info("Navigated back to "+arg0.getCurrentUrl());
		
	}

	@Override
	public void afterNavigateForward(WebDriver arg0) {
		logger.info("Navigated forward to "+arg0.getCurrentUrl());
		
	}

	@Override
	public void afterNavigateRefresh(WebDriver arg0) {
		logger.info("The page "+arg0.getCurrentUrl()+" has been refreshed");
		
	}

	@Override
	public void afterNavigateTo(String arg0, WebDriver arg1) {
		logger.info("Navigated to ["+arg0+"]");
		
	}

	@Override
	public void afterScript(String arg0, WebDriver arg1) {
		logger.info("Executed the script ["+arg0+"]");
		
	}

	@Override
	public void beforeAlertAccept(WebDriver arg0) {
		logger.info("Accepting alert with text ["+arg0.switchTo().alert().getText()+"]");
		
	}

	@Override
	public void beforeAlertDismiss(WebDriver arg0) {
		logger.info("Dismissing alert with text ["+arg0.switchTo().alert().getText()+"]");
		
	}

	@Override
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1,
			CharSequence[] arg2) {
		
		String value = null;
		if (arg2 != null){
			value="";
			for (CharSequence ch:arg2)
				value = value + String.valueOf(ch);
		}
		if (value != null)
			logger.info("Entering the text ["+value+"] to the element ["+getElementDetails(arg0, arg1)+"]");
		else
			logger.info("Clearing text on the element ["+getElementDetails(arg0, arg1)+"]");
	}

	@Override
	public void beforeClickOn(WebElement arg0, WebDriver arg1) {
		logger.info("Clicking on element ["+getElementDetails(arg0, arg1)+"]");
		
	}

	@Override
	public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		logger.info("Searching for element ["+arg0+"]");		
	}

	@Override
	public void beforeNavigateBack(WebDriver arg0) {
		logger.info("Navigating back from "+arg0.getCurrentUrl());
		
	}

	@Override
	public void beforeNavigateForward(WebDriver arg0) {
		logger.info("Navigating forward from "+arg0.getCurrentUrl());
		
	}

	@Override
	public void beforeNavigateRefresh(WebDriver arg0) {
		logger.info("Refreshing the  page "+arg0.getCurrentUrl());
		
	}

	@Override
	public void beforeNavigateTo(String arg0, WebDriver arg1) {
		logger.info("Navigating to ["+arg0+"]");
		
	}

	@Override
	public void beforeScript(String arg0, WebDriver arg1) {
		logger.info("Executing script ["+arg0+"]");
		
	}

	@Override
	public void onException(Throwable arg0, WebDriver arg1) {
		logger.error("Exception occured ["+arg0.getClass().getName()+"]");
		
	}
	
	/**
	 * Created by A-7605 on 6-3-18
	 * This method returns all the attributes and its value in a dom element
	 * @param element
	 * @param driver
	 * @return
	 */
	private String getElementDetails(WebElement element, WebDriver driver){
		if( element == null || driver == null)
			return null;
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		Object elementDetails=executor.executeScript("var items = {};for (index = 0; index < arguments[0].attributes.length; ++index) {if(arguments[0].attributes[index].value){items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value}};return items;", element);
		String elementDetail = elementDetails.toString();
		elementDetail = "<"+element.getTagName()+" "+elementDetail.substring(1,elementDetail.length()-1)+">";
		return elementDetail;
	}
	
	/**
	 * Created by A-7605 on 6-3-18
	 * This method highlight an element by applying custom border colour
	 * @param element
	 * @param driver
	 */
	private void highlightElement(WebElement element, WebDriver driver) {
		
        try{
        	if(discardedTags.contains(element.getTagName()))
    			return;
        	JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: green; border: 2px solid red;");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
        }catch(Exception e){
        	
        }
        return;
    }

	@Override
	public <X> void afterGetScreenshotAs(OutputType<X> arg0, X arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterGetText(WebElement arg0, WebDriver arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterSwitchToWindow(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <X> void beforeGetScreenshotAs(OutputType<X> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeGetText(WebElement arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeSwitchToWindow(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

}
