package com.ibsplc.common;

import com.ibsplc.common.enums.Browser;
import com.ibsplc.generic.Assertion;
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
import org.testng.ITestContext;
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
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by a-7681 on 12/11/2017.
 */
public abstract class BaseSetup {
    private final static Logger logger = Logger.getLogger(BaseSetup.class); 
    private static ExtentReports reports;
    private static String ScreenShotPath = "reports\\ScreenShots\\";
    private static String TOLRUNREQ = null;
    protected WebDriver driver;
    protected String browserName;
    private DesiredCapabilities capabilities;
    private ExtentTest test, childTest; 
    
    
//    public enum Browser{FIREFOX, CHROME, IE}

    @BeforeSuite(alwaysRun = true)
    @Parameters({"Version", "URL", "User Name", "DBreportReq"})
    public void initialSetup(String version, String url, String UserName, boolean dbReport, ITestContext ctx) {

        String userDir = System.getProperty("user.dir");
        String reportPath = userDir + "\\Reports\\report.html";
        reports = new ExtentReports(reportPath, true);
        reports.addSystemInfo("iCargo Version", version);
        reports.addSystemInfo("User Name", UserName);
        reports.addSystemInfo("URL", url);

        ReportValues.setAppVersion(version);
        ReportValues.setAppURL(url);
        ReportValues.setUserName(UserName);
        ReportValues.setSuiteName(ctx.getCurrentXmlTest().getSuite().getName());
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat dateNtime = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date date = new Date();
        ReportValues.setExecDate(formatter.format(date));
        ReportValues.setTestStart(dateNtime.format(date));

        //For DB entry
        if (dbReport) {

            String insert, delete;
            TOLRUNREQ = "ARUN1234";
//            logger.info("This is the Toolrun ID: " + TOLRUNREQ);
            DBUtility.connectToDB();
            delete = "DELETE FROM TOLRUNINFO WHERE TOLRUNREQID = 'ARUN1234'";
            DBUtility.executeUpdateStmnt(delete);
            delete = "DELETE FROM TOLRUNSTA WHERE TOLRUNREQID = 'ARUN1234'";
            DBUtility.executeUpdateStmnt(delete);
            insert = "INSERT INTO TOLRUNINFO(\"CMPCOD\",\"TOLRUNREQID\",\"ICOVER\",\"SEQNUM\",\"EMPID\",\"STRTIM\",\"REMARK\",\"SERVURL\",\"KEY\") VALUES ('AV','" + TOLRUNREQ.toUpperCase() + "','" + version + "','1','" + UserName + "',TO_DATE('" + dateNtime.format(date) + "','DD/MM/YYYY HH24:MI:SS'),'Remarks come here','" + url + "','" + TOLRUNREQ.substring(4) + "')";
            DBUtility.executeUpdateStmnt(insert);
            DBUtility.closeDB();

        }

        try {
            FileUtils.cleanDirectory(new File(new File(ScreenShotPath).getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser", "URL"})
    public void initialSetupClass(String browserName, String url, ITestContext ctx) {
        intializeLogger();
        //test = reports.startTest(getClass().getSimpleName());
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
            	 
            }
        }

        ReportValues.setTotalCount(getClass().getSimpleName(), methodNames); //krishna
                
       /* this.browserName = browserName;
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
                capabilities.setCapability("unexpectedAlertBehaviour", "accept");
                // Below line can be added to avoid full page load.
                // profile.setPreference("webdriver.load.strategy", "unstable");

                driver = new FirefoxDriver(capabilities);
                driver.manage().window().maximize();
                driver.get(url);
                break;
            case IE:
                capabilities = DesiredCapabilities.internetExplorer();

                String s2 = System.getProperty("user.dir");
                String path = s2 + "\\lib\\IEDriverServer.exe";

                System.out.println("@getCapabilities() - ie driver path :" + path);

                System.setProperty("webdriver.ie.driver", path);
                capabilities.setBrowserName("iexplore");

                capabilities.setPlatform(org.openqa.selenium.Platform.WINDOWS);
                capabilities.setCapability("unexpectedAlertBehaviour", "accept");

                driver = new InternetExplorerDriver(capabilities);
                driver.manage().window().maximize();
                driver.get(url);

                break;
            case CHROME:
                capabilities = DesiredCapabilities.chrome();
                String sc2 = System.getProperty("user.dir");
                String pathc = sc2 + "\\lib\\chromedriver.exe";

                System.setProperty("webdriver.chrome.driver", pathc);
                ChromeOptions options = new ChromeOptions();// Added for checking
                Proxy proxy = new Proxy();// Added for checking for proxy settings
                proxy.setProxyType(Proxy.ProxyType.SYSTEM);// Added for checking for
                // proxy settings
                capabilities.setBrowserName("chrome");
                capabilities.setPlatform(org.openqa.selenium.Platform.WINDOWS);
                capabilities.setCapability("proxy", proxy);// Added for checking for
                // proxy settings
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);// Added
                // for
                // checking
                capabilities.setCapability("chrome.switches",
                        Arrays.asList("--start-maximized"));
                capabilities.setCapability("unexpectedAlertBehaviour", "accept");

                driver = new ChromeDriver(capabilities);
                driver.manage().window().maximize();
                driver.get(url);
                break;*/
    }

    //  }
    

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "URL","EventLogReq"})
    public void initialSetupMethod(Method method, String browserName, String url, boolean eventLogReq) {
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
                capabilities.setBrowserName("iexplore");

                capabilities.setPlatform(org.openqa.selenium.Platform.WINDOWS);
                capabilities.setCapability("unexpectedAlertBehaviour", "ignore");
//                capabilities.setCapability("ignoreProtectedModeSettings", true);

                driver = new InternetExplorerDriver(capabilities);
                break;
            case CHROME:
                capabilities = DesiredCapabilities.chrome();
                String sc2 = System.getProperty("user.dir");
                String pathc = sc2 + "\\lib\\chromedriver.exe";

                System.setProperty("webdriver.chrome.driver", pathc);
                ChromeOptions options = new ChromeOptions();// Added for checking
                Proxy proxy = new Proxy();// Added for checking for proxy settings
                proxy.setProxyType(Proxy.ProxyType.SYSTEM);// Added for checking for
                // proxy settings
                capabilities.setBrowserName("chrome");
                capabilities.setPlatform(org.openqa.selenium.Platform.WINDOWS);
                capabilities.setCapability("proxy", proxy);// Added for checking for proxy settings

                options.addArguments("disable-infobar");

                options.addArguments("disable-infobars");

                capabilities.setCapability(ChromeOptions.CAPABILITY, options);// Added
                // for
                // checking
                capabilities.setCapability("chrome.switches",
                        Arrays.asList("--start-maximized"));
                capabilities.setCapability("unexpectedAlertBehaviour", "ignore");
                capabilities.setCapability("pageLoadStrategy", "normal");
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

    @AfterMethod(alwaysRun = true)
    @Parameters({"Version", "DBreportReq"})
    public void finalSetupMethod(String version, boolean dbReport, Method method, ITestResult results) {
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
            File destination = new File(ScreenShotPath + method.getName() + ".PNG");

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

            ReportValues.addFailCount(getClass().getSimpleName(), method.getName(), method.getAnnotation(Test.class).description(), results.getEndMillis() - results.getStartMillis(),
                    ExceptionUtils.getStackTrace(results.getThrowable()),"../Screenshots/"+destination.getName()); //krishna

            // System.out.println(results.getAttribute("dependsOnMethods").toString());

            /*try {
                String errorTxt = checkForError();
                if (errorTxt.contains("Internal")){
                    childTest.log(LogStatus.FAIL, childTest.addScreenCapture(destination.getAbsolutePath())+ "Internal Server Error Occured. Please check the application log for details.");
                }
                else {
                //	childTest.log(LogStatus.FAIL, childTest.addScreenCapture(destination.getAbsolutePath()) errorTxt + results.getName());
                //	childTest.log(LogStatus.FAIL, childTest.addScreenCapture(destination.getAbsolutePath()), errorTxt + ". Please check the details entered.");
                
                	childTest.log(LogStatus.FAIL, childTest.addScreenCapture(destination.getAbsolutePath())+ errorTxt + ". Please check the details entered.");
                }
            } catch (NullPointerException e) {
            	
                childTest.log(LogStatus.FAIL, childTest.addScreenCapture(destination.getAbsolutePath()) + results.getThrowable());
            }*/
        }
        if (results.getStatus() == ITestResult.SKIP) {
        	logger.info("The method ["+method.getName()+"] [SKIPPED]");
            //  childTest.log(LogStatus.SKIP, results.getThrowable());
            childTest.log(LogStatus.SKIP, "The Test Case is Skipped " + results.getName());

            //For DB entry
            if (dbReport) {

                String insert = "INSERT INTO TOLRUNSTA(\"CMPCOD\",\"TOLRUNREQID\",\"ICOVER\",\"SEQNUM\",\"TSTCASID\",\"STATUS\",\"TSTSCNID\",\"REDCASID\",\"REDSCNID\") VALUES ('AV','" + TOLRUNREQ + "','" + version + "','1','" + method.getName() + "','Skipped','" + getClass().getSimpleName() + "','ID','SCNID')";
                DBUtility.connectToDB();
                DBUtility.executeUpdateStmnt(insert);
                DBUtility.closeDB();
            }


            //childTest.log(LogStatus.PASS, "The Test Case is Passed..!!");


        }
        if (results.getStatus() == ITestResult.SUCCESS) {
        	logger.info("Excuted the method ["+method.getName()+"] with status [PASSED]");
            //childTest.log(LogStatus.PASS, "The Test Case is Passed..!!");
            ReportValues.addPassCount(getClass().getSimpleName(), method.getName(), method.getAnnotation(Test.class).description(), results.getEndMillis() - results.getStartMillis()); //krishna

            //For DB entry
            if (dbReport) {

                String insert = "INSERT INTO TOLRUNSTA(\"CMPCOD\",\"TOLRUNREQID\",\"ICOVER\",\"SEQNUM\",\"TSTCASID\",\"STATUS\",\"TSTSCNID\",\"REDCASID\",\"REDSCNID\") VALUES ('AV','" + TOLRUNREQ + "','" + version + "','1','" + method.getName() + "','Pass','" + getClass().getSimpleName() + "','ID','SCNID')";
                DBUtility.connectToDB();
                DBUtility.executeUpdateStmnt(insert);
                DBUtility.closeDB();
            }
        }

        //       test.appendChild(childTest);
//        reports.endTest(childTest);
        Assertion.setSoftAssert(null);
        driver.quit();
    }

    @AfterClass(alwaysRun = true)
    public void finalSetupClass() {

        Method[] methods = getClass().getDeclaredMethods();
        String[] methodNames = new String[methods.length];
        String[] tcMap = new String[methods.length];
        String[] dependency = new String[methods.length];
        String[] enabledStat = new String[methods.length];
        for (int count = 0; count < methods.length; count++) {
            methodNames[count] = methods[count].getName();
            tcMap[count] = methods[count].getAnnotation(Test.class).description();
            dependency[count] = org.apache.commons.lang3.StringUtils.join(methods[count].getAnnotation(Test.class).dependsOnMethods());
            enabledStat[count] = org.apache.commons.lang3.StringUtils.join(methods[count].getAnnotation(Test.class).enabled());
            
        }
        ReportValues.setSkippedCount(getClass().getSimpleName(), methodNames, tcMap, dependency, enabledStat); //krishna

        //reports.endTest(test);

        driver.quit();

    }

    @AfterSuite(alwaysRun = true)
    @Parameters({"Version", "DBreportReq"})
    public void finalSetup(String version, boolean dbReport) {

        // reports.flush();
        // reports.close();

        SimpleDateFormat dateNtime = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date date = new Date();
        ReportValues.setTestEnd(dateNtime.format(date));

        ReportValues.generateReport();
        //ReportValues.triggerReportMail();
        //For DB Entry
        if (dbReport) {

            String update = "UPDATE TOLRUNINFO SET ENDTIME = TO_DATE('" + dateNtime.format(date) + "','DD/MM/YYYY HH24:MI:SS') WHERE CMPCOD='AV' AND TOLRUNREQID='" + TOLRUNREQ + "' AND ICOVER='" + version + "' AND SEQNUM='1'";
            DBUtility.connectToDB();
            DBUtility.executeUpdateStmnt(update);
            DBUtility.closeDB();

        }
//        driver.quit();
    }

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
    private void intializeLogger() {
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
    private WebDriver registerEvent(WebDriver driver){
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
		} catch (Exception e) {
			logger.info("Error on killing driver files",e);
		}
    }
    
}
