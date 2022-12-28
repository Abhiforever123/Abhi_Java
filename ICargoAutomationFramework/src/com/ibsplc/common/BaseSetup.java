package com.ibsplc.common;

import com.ibsplc.common.enums.Browser;
import com.ibsplc.generic.Assertion;
import com.ibsplc.generic.AssertionList;
import com.ibsplc.generic.EventHandler;
import com.itextpdf.text.Annotation;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.commons.exec.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
//import org.openqa.selenium.Rectangle;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by a-7681 on 12/11/2017.
 */
public class BaseSetup implements ISuiteListener, ITestListener, IInvokedMethodListener {

	protected final static Logger logger = Logger.getLogger(BaseSetup.class); 	
    protected static String browserName;       	
    private static String ScreenShotPath = "reports\\ScreenShots\\";
	private static String TOLRUNREQ = null;
	private static long startMillis;
	private DesiredCapabilities capabilities;
	protected  WebDriver driver;
	protected String portalURL = "";
	private int sales_pass, sales_fail, sales_block = 0;
	private int ops_pass, ops_fail, ops_block = 0;
	private int cra_pass, cra_fail, cra_block = 0;
	private int mra_pass, mra_fail, mra_block = 0;
	
	@BeforeClass(alwaysRun=true)
	@Parameters({"browser", "URL"})
	public void beforeClass(String browserName, String url, ITestContext ctx){
		
        ReportValues.setBrowserName(browserName); //krishna        
        Method[] methods = getClass().getDeclaredMethods();
        String[] methodNames = new String[methods.length];
        String[] tcMap = new String[methods.length];
        for (int count = 0; count < methods.length; count++) {
            methodNames[count] = methods[count].getName();
            try{
            	if(methods[count].getAnnotation(Test.class).enabled())
            		logger.info("Method [" + methodNames[count] + "] will be executed");
            }catch(Exception e){
            	 logger.info("Exception in Before class");
            }
        }
        ReportValues.setTotalCount(getClass().getSimpleName(), methodNames); //krishna    
//        String str = Arrays.toString(ctx.getIncludedGroups());        
//        ReportValues.setImplementation(str);
	}
    
	@Override
	public void onStart(ISuite arg0) {
		
		String userDir = System.getProperty("user.dir");
        String reportPath = userDir + "\\Reports\\report.html";
        startMillis = System.currentTimeMillis();
        
        this.portalURL = arg0.getParameter("portalURL");        
//        ReportValues.setAppVersion(arg0.getParameter("Version"));
        ReportValues.setAppURL(arg0.getParameter("URL"));
        ReportValues.setUserName(arg0.getParameter("UserName"));
        ReportValues.setSuiteName(arg0.getName());
        ReportValues.setImplementation(arg0.getParameter("Implementation"));
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat dateNtime = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date date = new Date();
        ReportValues.setExecDate(formatter.format(date));
        ReportValues.setTestStart(dateNtime.format(date));
        //For DB entry
        if (arg0.getParameter("DBreportReq").equalsIgnoreCase("true")) {

            String insert, delete;
            TOLRUNREQ = "ARUN1234";
            DBUtility.connectToDB();
            delete = "DELETE FROM TOLRUNINFO WHERE TOLRUNREQID = 'ARUN1234'";
            DBUtility.executeUpdateStmnt(delete);
            delete = "DELETE FROM TOLRUNSTA WHERE TOLRUNREQID = 'ARUN1234'";
            DBUtility.executeUpdateStmnt(delete);
            insert = "INSERT INTO TOLRUNINFO(\"CMPCOD\",\"TOLRUNREQID\",\"ICOVER\",\"SEQNUM\",\"EMPID\",\"STRTIM\",\"REMARK\",\"SERVURL\",\"KEY\") VALUES ('AV','" + TOLRUNREQ.toUpperCase() + "','" + arg0.getParameter("Version") + "','1','" + arg0.getParameter("UserName") + "',TO_DATE('" + dateNtime.format(date) + "','DD/MM/YYYY HH24:MI:SS'),'Remarks come here','" + arg0.getParameter("URL") + "','" + TOLRUNREQ.substring(4) + "')";
            DBUtility.executeUpdateStmnt(insert);
            DBUtility.closeDB();

        }

        try {
            FileUtils.cleanDirectory(new File(new File(ScreenShotPath).getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        } 
        intializeLogger();
        
	}
	
	@Override
	public void onFinish(ISuite arg0) {
		
		SimpleDateFormat dateNtime = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date date = new Date();
        ReportValues.setTestEnd(dateNtime.format(date));
        ReportValues.setExecTime(System.currentTimeMillis() - startMillis);
        logger.info("Execution time: "+ (System.currentTimeMillis() - startMillis));
        ReportValues.generateReport();
        //ReportValues.triggerReportMail();
        //For DB Entry
        if (arg0.getParameter("DBreportReq").equalsIgnoreCase("true")) {

            String update = "UPDATE TOLRUNINFO SET ENDTIME = TO_DATE('" + dateNtime.format(date) + "','DD/MM/YYYY HH24:MI:SS') WHERE CMPCOD='AV' AND TOLRUNREQID='" + TOLRUNREQ + "' AND ICOVER='" + arg0.getParameter("Version") + "' AND SEQNUM='1'";
            DBUtility.connectToDB();
            DBUtility.executeUpdateStmnt(update);
            DBUtility.closeDB();

        }
        killBrowserDriver();
	}
	
	@Override
	public void onTestStart(ITestResult arg0) {
			
	}
	
	@BeforeMethod(alwaysRun=true)
	@Parameters({"browser", "headless", "URL","EventLogReq"})
	public void initialSetupMethod(Method method, String browserName, boolean isHeadless, String url, boolean eventLogReq) {
	        logger.info("+++++++++++++++++++++++++++++++++++++++++++++++");
			logger.info("Executing the method ["+ method.getName()+"]");
	        //childTest = reports.startTest(method.getName());
	        this.browserName = browserName;
	        Browser browser = Browser.valueOf(browserName.toUpperCase());
	        switch (browser) {
	            case FIREFOX:
	                String sFF = System.getProperty("user.dir");
	                String pathFF = sFF + "\\lib\\geckodriver.exe";
	                System.setProperty("webdriver.gecko.driver", pathFF);
	                capabilities = DesiredCapabilities.firefox();
	                capabilities.setBrowserName("firefox");
	                capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
	                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	                capabilities.setCapability("marionette", true);
	                capabilities.setCapability("unexpectedAlertBehaviour", "ignore");
	                // Below line can be added to avoid full page load.
	                // profile.setPreference("webdriver.load.strategy", "unstable");
	                driver = new FirefoxDriver(capabilities);
	                break;
	            case IE:
	                capabilities = DesiredCapabilities.internetExplorer();

	                String s2 = System.getProperty("user.dir");
	                String path = s2 + "\\lib\\IEDriverServer.exe";

	                System.setProperty("webdriver.ie.driver", path);
	                capabilities.setBrowserName("internet explorer");

	                capabilities.setPlatform(org.openqa.selenium.Platform.WINDOWS);
	                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	                capabilities.setCapability("unexpectedAlertBehaviour", "ignore");
//	                capabilities.setCapability("ignoreProtectedModeSettings", true);

	                driver = new InternetExplorerDriver(capabilities);
	                break;
	            case CHROME:
	                capabilities = DesiredCapabilities.chrome();
	                String sc2 = System.getProperty("user.dir");
	                String pathc = sc2 + "\\lib\\chromedriver.exe";

	                System.setProperty("webdriver.chrome.driver", pathc);
	                ChromeOptions options = new ChromeOptions();// Added for checking
	                if(isHeadless){
	                	options.addArguments("headless");	//headless
		                options.addArguments("window-size=1200x600");//headless
	                }
	                Proxy proxy = new Proxy();// Added for checking for proxy settings
	                proxy.setProxyType(Proxy.ProxyType.SYSTEM);// Added for checking for
	                // proxy settings
	                capabilities.setBrowserName("chrome");
	                capabilities.setPlatform(org.openqa.selenium.Platform.WINDOWS);
	                capabilities.setCapability("proxy", proxy);// Added for checking for proxy settings
	                capabilities.setCapability(ChromeOptions.CAPABILITY, options);// Added
	                // for
	                // checking
	                capabilities.setCapability("chrome.switches",
	                        Arrays.asList("--start-maximized"));
	                capabilities.setCapability("unexpectedAlertBehaviour", "ignore");
//	                capabilities.setCapability("pageLoadStrategy", "eager");
	                driver = new ChromeDriver(capabilities);
	                break;

	        }
	        if(eventLogReq){
	        	driver = registerEvent(driver);        	
	        }else{
	        	logger.warn("WebDriver event logging is disabled");
	        }
	        driver.manage().window().maximize();
	        driver.get(url);
	        /*Create and initialize SoftAssert object*/
	        Assertion.setSoftAssert(Assertion.createSoftAssert());
	    }

	@Override
	public void onTestFailure(ITestResult arg0) {
		logger.info("#####The method ["+arg0.getMethod().getMethodName()+"] [FAILED]#####\n" + arg0.getThrowable().getMessage());	
		
		if(arg0.getMethod().getMethodName().contains("SAL_"))
			sales_fail++;
		else if(arg0.getMethod().getMethodName().contains("OPS_"))
			ops_fail++;
		else if(arg0.getMethod().getMethodName().contains("CRA_"))
			cra_fail++;
		else if(arg0.getMethod().getMethodName().contains("MRA_"))
			mra_fail++;
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		
		logger.info("#####The method ["+arg0.getMethod().getMethodName()+"] [SKIPPED]#####");
        
        //For DB entry
        if(arg0.getTestContext().getSuite().getParameter("DBreportReq").equalsIgnoreCase("true")) {

            String insert = "INSERT INTO TOLRUNSTA(\"CMPCOD\",\"TOLRUNREQID\",\"ICOVER\",\"SEQNUM\",\"TSTCASID\",\"STATUS\",\"TSTSCNID\",\"REDCASID\",\"REDSCNID\") VALUES ('AV','" + TOLRUNREQ + "','" + arg0.getTestContext().getSuite().getParameter("Version") + "','1','" + arg0.getMethod().getMethodName() + "','Skipped','" + arg0.getTestContext().getClass().getSimpleName() + "','ID','SCNID')";
            DBUtility.connectToDB();
            DBUtility.executeUpdateStmnt(insert);
            DBUtility.closeDB();
        }
        ReportValues.setSkippedCount(arg0.getMethod().getRealClass().getSimpleName(), arg0.getMethod().getMethodName(), ""+arg0.getMethod().getDescription(), arg0.getMethod().getMethodsDependedUpon(), arg0.getMethod().getEnabled()); //krishna
        
        if(arg0.getMethod().getMethodName().contains("SAL_"))
			sales_block++;
		else if(arg0.getMethod().getMethodName().contains("OPS_"))
			ops_block++;
		else if(arg0.getMethod().getMethodName().contains("CRA_"))
			cra_block++;
		else if(arg0.getMethod().getMethodName().contains("MRA_"))
			mra_block++;
        
        Assertion.setSoftAssert(null);
        ReportValues.generateIntermediateReport();
        //driver.quit();
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
			logger.info("#####The method ["+arg0.getMethod().getMethodName()+"] [PASSED]#####");
			
			if(arg0.getMethod().getMethodName().contains("SAL_"))
				sales_pass++;
			else if(arg0.getMethod().getMethodName().contains("OPS_"))
				ops_pass++;
			else if(arg0.getMethod().getMethodName().contains("CRA_"))
				cra_pass++;
			else if(arg0.getMethod().getMethodName().contains("MRA_"))
				mra_pass++;
			
	}
	
	@AfterMethod(alwaysRun=true)
    @Parameters({"Version", "DBreportReq"})
    public void finalSetupMethod(String version, boolean dbReport, Method method, ITestResult results, ITestContext ctx) {
        if (results.getStatus() == ITestResult.FAILURE) {
        	logger.info("Excuted the method ["+method.getName()+"] with status [FAILED]");

            //For DB Entry
            if (dbReport) {

                String insert = "INSERT INTO TOLRUNSTA(\"CMPCOD\",\"TOLRUNREQID\",\"ICOVER\",\"SEQNUM\",\"TSTCASID\",\"STATUS\",\"TSTSCNID\",\"REDCASID\",\"REDSCNID\") VALUES ('AV','" + TOLRUNREQ + "','" + version + "','1','" + method.getName() + "','Fail','" + getClass().getSimpleName() + "','ID','SCNID')";
                DBUtility.connectToDB();
                DBUtility.executeUpdateStmnt(insert);
                DBUtility.closeDB();
            }

            File screenshotFile = null;
            File destination = new File(ScreenShotPath + ctx.getCurrentXmlTest().getName()+"_"+method.getName() + ".PNG");

            try {
                screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshotFile, destination);
            } catch (NoSuchWindowException e) {
                try {
                    String[] windowHandles = driver.getWindowHandles().toArray(
                            new String[0]);
                    driver.switchTo().window(windowHandles[windowHandles.length - 1]);
                    screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                } catch (WebDriverException e1) {
                    logger.info("WebDriverException Occured");
                }
                try {
                    FileUtils.copyFile(screenshotFile, destination);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (UnhandledAlertException e) {
                try {
                    BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                    ImageIO.write(image, "png", destination);
                    try {
                        driver.switchTo().alert().accept();
                    } catch (NoAlertPresentException e1) {
                        e1.printStackTrace();
                    }
                } catch (AWTException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch(TimeoutException te) {
            	logger.info("Timeout exception while taking screenshot.");
            	te.printStackTrace();
            }

            ReportValues.addFailCount(getClass().getSimpleName(), method.getName(), AssertionList.getAssertionList().toString(), results.getEndMillis() - results.getStartMillis(),
                    ExceptionUtils.getStackTrace(results.getThrowable()),"../Screenshots/"+destination.getName()); //krishna

           
        }
        
        
        if (results.getStatus() == ITestResult.SUCCESS) {
        	logger.info("Excuted the method ["+method.getName()+"] with status [PASSED]");
            //childTest.log(LogStatus.PASS, "The Test Case is Passed..!!");
            ReportValues.addPassCount(getClass().getSimpleName(), method.getName(), AssertionList.getAssertionList().toString(), results.getEndMillis() - results.getStartMillis()); //krishna

            //For DB entry
            if (dbReport) {

                String insert = "INSERT INTO TOLRUNSTA(\"CMPCOD\",\"TOLRUNREQID\",\"ICOVER\",\"SEQNUM\",\"TSTCASID\",\"STATUS\",\"TSTSCNID\",\"REDCASID\",\"REDSCNID\") VALUES ('AV','" + TOLRUNREQ + "','" + version + "','1','" + method.getName() + "','Pass','" + getClass().getSimpleName() + "','ID','SCNID')";
                DBUtility.connectToDB();
                DBUtility.executeUpdateStmnt(insert);
                DBUtility.closeDB();
            }
        }

        AssertionList.clearList();
        Assertion.setSoftAssert(null);
        ReportValues.generateIntermediateReport();
        driver.quit();
    }
	
	@Override
	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onStart(ITestContext arg0) {
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}
	

	/******************************/
	
	private String checkForError() {
		
	    driver.switchTo().defaultContent();
	    String xpath = "//*[@id='errorDisplayDiv']/table/tbody/tr/td[2]";
	    String text = null;
	    try {
	        text = driver.findElement(By.xpath(xpath)).getText();
	    } catch (NoSuchElementException e) {
	        List<WebElement> frameList = driver.findElements(By.xpath("//iframe[contains(@name,'iCargoContentFrame')]"));
	        driver.switchTo().frame(frameList.get(1));
	        try {
	            text = driver.findElement(By.xpath(xpath)).getText();
	        } catch (NoSuchElementException e1) {
	        }
	    }
	
	    return text;
	}
	
	/**
	 * Created by A-7605
	 */
	protected void intializeLogger() {
	    Properties properties = new Properties();
	
	    try {
	        properties.load(new FileInputStream("log4j.properties"));
	        PropertyConfigurator.configure(properties);
	        logger.info("Logger configured");
	    } catch (IOException e) {
	        e.printStackTrace();
	
	    }
	}
	
	public enum Days {
	    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
	}
	    
	/**
	 * Created by A-7605 on 6-3-18
	 * This method is used to associate event handler with the driver
	 * @param driver
	 * @return
	 */
	protected WebDriver registerEvent(WebDriver driver){
		EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
		EventHandler eventListener = new EventHandler();
		eventDriver.register(eventListener);
		driver = eventDriver;
		logger.info("Event handler registered with driver "+driver);
		return driver;    	
	}
	    
	/**
	 * Created by A-7605 on 32-5-18
	 * This method kills already running driver files
	 */
	private void killBrowserDriver(){
		logger.info("Killing driver files");
		String[] commands = new String[]{"taskkill /im chromedriver.exe /f","taskkill /im IEDriverServer.exe /f","taskkill /im geckodriver.exe /f"};
		try {
			for(String command:commands)
				Runtime.getRuntime().exec(command);
			logger.info("Driver files killed");
		} catch (IOException e) {
			logger.info("Error on killing driver files",e);
		}
	}
	    
}
