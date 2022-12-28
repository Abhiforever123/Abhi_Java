package com.ibsplc.common;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class with
 * static variables to store the test report values
 * static methods to get and set the values
 * static methods to initiate report generation
 *
 * @author a-7868
 */


public class ReportValues {

	public static Integer passCount=0;
	public static Integer failCount=0;
	public static Integer skipCount=0;
	public static Integer totalCount=0;
	public static Integer suitePassCount=0;
	public static Integer suiteFailCount=0;
	public static Integer suiteTotalCount=0;
	public static Integer suiteSkipCount=0;
	public static long scriptExecutionTime=0;
	public static String browserName="";
	public static String appVersion="";
	public static String appURL="";
	public static String execDate="";
	public static String suiteName="";
	public static String userName="";
	public static String testStartTime="";
	public static String testEndTime="";
	public static String reportFileName="";
	public static String implementation="";
	
	/*result stored as key value pair
        * HashMap<className, HashMap<methodName,{status values}>>

   */
	//public static HashMap<String, HashMap<String,String[]>> classStat = new HashMap<String, HashMap<String,String[]>>();
	public static Map<String, LinkedHashMap<String,String[]>> classStat = new LinkedHashMap<String, LinkedHashMap<String,String[]>>();

	//classwise method count stored as HashMap<className, methodCount>
	//public static HashMap<String, Integer> classTotalCount = new HashMap<String, Integer>();
	public static Map<String, Integer> classTotalCount = new LinkedHashMap<String, Integer>();

	/**
	 * Method to add pass count and corresponding pass status to the class,method
	 *
	 * @param className
	 * @param methodName
	 * @param time
	 */

	public static void addPassCount(String className, String methodName, String tcDesc, long time){
		passCount++;
		scriptExecutionTime += time;
		//Status[] structure should be {ExecStatus, Duration, StackTrace, ScreenshotPath}
		String[] stat = {"PASS",String.valueOf(time/1000),"","", tcDesc};
		classStat.get(className).put(methodName, stat);
		classTotalCount.put(className, classTotalCount.get(className)+1);
	}

	/**
	 * Method to add fail count and corresponding fail status to the class,method
	 * @param className
	 * @param methodName
	 * @param time
	 * @param trace
	 * @param path
	 */

	public static void addFailCount(String className, String methodName, String tcDesc, long time, String trace, String path){
		failCount++;
		scriptExecutionTime += time;
		//Status[] structure should be {ExecStatus, Duration, StackTrace, ScreenshotPath}
		String[] stat = {"FAIL",String.valueOf(time/1000),trace, path, tcDesc};
		classStat.get(className).put(methodName, stat);		
		classTotalCount.put(className, classTotalCount.get(className)+1);
	}

	/**
	 * Method to set the total method count for a specific class
	 * @param className
	 * @param methods
	 * @author a-7868
	 */

	public static void setTotalCount(String className, String[] methods){
		totalCount = methods.length;
		//classStat.put(className, new HashMap<String, String[]>());
		classStat.put(className, new LinkedHashMap<String, String[]>());
		classTotalCount.put(className, 0);
	}

	/**
	 * Method to set the skipped method count for a specific class
	 * @param className
	 * @param methods
	 */
	
	public static void setSkippedCount(String className, String method, String tcMap, String[] dependency, boolean enabledStat){
		//Currently SKIPPED cases needs to be reported as BLOCKED
		//Status[] structure should be {ExecStatus, Duration, StackTrace, ScreenshotPath}
		
		for(int i=0; i<dependency.length; i++){		
			System.out.println(dependency[i]);
			String[] temp = dependency[i].split("\\.",5);
			dependency[i] = temp[temp.length - 1];	
			System.out.println(dependency[i]);			
		}
		
		String txt="";
		if(!enabledStat)
			txt = "Test case disabled";
		else 
			txt = String.join("Dependency: " , dependency);			
		
		String[] stat = {"BLOCKED","0","","",tcMap, txt};
		classStat.get(className).putIfAbsent(method, stat);
		classTotalCount.put(className, classTotalCount.get(className)+1);
	}
	
	public static void setTestStart(String timeStamp) {
		testStartTime = String.valueOf(timeStamp);
	}
	public static void setTestEnd(String timeStamp) {
		testEndTime = String.valueOf(timeStamp);
	}

	public static String getTestStart() {
		return testStartTime;
	}
	public static String getTestEnd() {
		return testEndTime;
	}

	public static void setExecTime(long time){
		
		scriptExecutionTime = time;
	}

	public static int getSuiteTotalCount(){
		return suiteTotalCount;
	}

	public static float getExecTime(){
		return scriptExecutionTime;
	}

	public static int getPassCount(){
		return passCount;
	}

	public static int getFailCount(){
		return failCount;
	}

	public static int getTotalCount(){
		return totalCount;
	}

	public static void setBrowserName(String browser){
		browserName = browser;
	}

	public static String getBrowserName(){
		return browserName;
	}

	public static void setAppVersion(String version){
		appVersion = version;
	}

	public static String getAppVersion(){
		return appVersion;
	}

	public static void setAppURL(String url){
		appURL = url;
	}

	public static String getAppURL(){
		return appURL;
	}

	public static void setExecDate(String date){
		execDate = date;
	}

	public static String getExecDate(){
		return execDate;
	}

	public static String getSuiteName(){
		return suiteName;
	}
	
	public static void setSuiteName(String suite){
		suiteName = suite;
	}

	public static String getUserName(){
		return userName;
	}
	
	public static void setUserName(String name){
		userName = name;
	}

	public static int getSuiteSkipCount(){
		return suiteSkipCount;
	}

	public static void setImplementation(String impl){
		implementation = impl;
	}
	
	public static String getImplementation(){
		return implementation;
	}

	public static void generateReport() {

		//sets the suite totals by adding the class-wise counts
		suiteTotalCount=0;
		for(String className : classStat.keySet())
			suiteTotalCount += classTotalCount.get(className);
		//total skip count
		suiteSkipCount = suiteTotalCount-(passCount+failCount);
		//generate HTML Report values
		System.out.println(suiteTotalCount);
		generateHTMLReport(true);
		//generate Excel Report values
		generateXLSReport(classStat);
		//zip
		try{
			Zipper.zip();
		}catch(Exception e){;}
	}

	/**
	 * Method to generate HTML Table content to be inserted into the HTML Report
	 */

	public static void generateXLSReport(Map classStat){

		//create Excel Report
		try{
			System.out.println("in generateXLS...");
			XLSReport.createXLSReport(classStat);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void triggerReportMail() {

		//Executes a VBScript (triggerMail.vbs) for triggering outlook mail
		try{
			Process run = Runtime.getRuntime().exec("wscript triggerMail.vbs");
		}catch(Exception ioe){
			System.out.println(ioe.getMessage());;
		}
	}

	public static void generateHTMLReport(boolean isFinal){

		String html_class = "";
		String html_method = "";
		int pass, fail, count;
		String bgcolor="";
		String txt="";

		for(String className : classStat.keySet()){

			pass=0; fail=0;	count=0;

			String module = "";

			if ( className.toUpperCase().contains("OPS") )	module = "OPS";
			if ( className.toUpperCase().contains("SAL") )	module = "SALES";
			if ( className.toUpperCase().contains("CRA") )	module = "CRA";
			if ( className.toUpperCase().contains("MAIL") )	module = "MAIL";
			if ( className.toUpperCase().contains("MRA") )	module = "MRA";


			html_method += "<div id='"+className+"' class='tab-pane fade'>\n"+
					"<br>\n"+
					"<div class='well well-sm'><center><h2 style='color:#337ab7;'> Module : "+className+"</h2></center></div>\n"+
					"<table  class='table table-striped table-bordered table-hover table-condensed'>\n"+
					"<tr style='height:30px;'> \n"+
					"<td align=center width=3% ><b>No.</b></td>\n"+
					"<td align=center  width=30% ><b>Test Case</b></td>\n"+
					"<td align=center  width=20% ><b>Assertions</b></td>\n"+
					"<td align=center width=10% ><b>Status</b></td>\n"+
					"<td align=center width=7% ><b>Time</b></td>\n"+
					"<td align=center width=28% ><b>Details</b></td>\n"+
					"<td align=center width=7% ><b>Screenshot</b></td>\n"+
					"</tr>";

			for(Object obj : classStat.get(className).keySet()){

				String[] str = new String[4];
				str = classStat.get(className).get(obj);
				bgcolor="#f1f1bc";
				txt = "";
				count++;

				if(str[0].toString().equals("PASS")){
					pass++;
					bgcolor="#adeaad";
				}

				if(str[0].toString().equals("FAIL")){
					fail++;
					bgcolor="#fba8a8";	
					txt="<img src="+str[3].toString()+" width=30 height=20 style='cursor:zoom-in;border: #00008B 1px dotted;'"+
						  "onclick=\"document.getElementById('modal01').style.display='block';document.getElementById('img1').src='"+str[3].toString()+"'\">\n";
				}

				//Error Info.
				String errorInfo = "";

				if(str[0].toString().equals("BLOCKED") && !str[5].isEmpty()){
					errorInfo = "<font style='font-size:8pt;'> "+str[5]+"</font>"; //Dependency:
				}

				if(str[0].toString().equals("FAIL")){

					if(str[2].toString().equals(""))
						errorInfo="";
					else if(str[2].toString().contains("org.openqa.selenium.TimeoutException"))
						errorInfo = "Timeout Exception";
					else if(str[2].toString().contains("java.lang.AssertionError"))
						errorInfo = "Assertion Error";
					else if(str[2].toString().contains("java.lang.NullPointerException"))
						errorInfo = "Null Pointer Exception";
					else if(str[2].toString().contains("WebDriverException"))
						errorInfo = "WebDriver Exception";
					else if(str[2].toString().contains("StaleElementReferenceException"))
						errorInfo = "Stale Element Reference Exception";
					else if(str[2].toString().contains("ElementNotFoundException"))
						errorInfo = "Element Not Found Exception";
					else if(str[2].toString().contains("UnhandledAlertException"))
						errorInfo = "Unhandled Alert Exception";
					else if(str[2].toString().contains("ElementNotSelectableException"))
						errorInfo = "Element Not Selectable Exception";
					else
						errorInfo = "Unknown Exception";

					//errorInfo = "<div class='tooltip'>"+errorInfo+"<div class='tooltiptext'>"+str[2].toString()+"</div></div>";
					errorInfo = "<div onClick='on(\""+className+count+"\")' >"+errorInfo+"</div><div id='"+className+count+"' class='overlay' onClick='off(this.id)'><div class='text'>"+str[2].toString()+"</div></div>\n";
				}
				
				if(str[4].toString().equals("null"))
					str[4]="";

				str[4] = str[4].replace('[', ' ');
				str[4] = str[4].replace(',', ' ');
				str[4] = str[4].replace(']', ' ');
				str[4].trim();
				
				String assertList = "<div onClick='on(\""+className+count+"a\")' ><center><font color=blue>&#8801;</font></center></div>"
						+ "<div id='"+className+count+"a' class='overlay1' onClick='off(this.id)'><div class='text1'>"
								+ "<table class='table table-striped table-bordered table-hover table-condensed'><th><center>Assertion</center></th><th><center>Status</center></th>"+str[4]+"</table></div></div>\n";
				
				
				html_method += "<tr style='height:30px;'>\n"+
						"<td align=center>"+count+"</td>\n"+
						"<td>"+obj.toString()+"</td>\n"+
						"<td align=left>"+assertList+"</td>\n"+
						"<td align=center bgcolor=\n"+bgcolor+">"+str[0].toString()+"</td>\n"+
						"<td align=center>"+str[1].toString()+"s</td>\n"+
						"<td align=left onClick='on()'>"+errorInfo+"</td>\n"+
						//"<td align=center><a href='"+str[3].toString()+"'>"+txt+"</a></td>"+
						/*"<td align=center><img src="+str[3].toString()+" width=10 height=10 style='cursor:zoom-in;'"+
						  "onclick=\"document.getElementById('modal01').style.display='block';document.getElementById('img1').src='"+str[3].toString()+"'\"></td>"+*/
						"<td align=center>"+txt+"</td>\n"+												
						"</tr>\n";
			}

			//html_method += "</table><a href=#results><button type=button>&#9650;</button></a><br><br>";
			html_method += "</table></div>";

			
			html_class += "<tr style='height:30px;'>" +
					"<td><a href='#"+className+"'>"+className+"</a></td>\n"+
					"<td align=center>"+module+"</td>\n"+
					"<td align=center>"+classTotalCount.get(className)+"</td>\n"+
					"<td align=center>"+pass+"</td>\n"+
					"<td align=center>"+fail+"</td>\n"+
					"<td align=center>"+((classTotalCount.get(className))-(pass+fail))+"</td>\n"+
					"</tr>";

		}

		//Create the HTML Report 
		try{
			HTMLReport.customHTMLReport(html_class,html_method,isFinal);
		}catch(IOException io){;}
	}

	public static void generateIntermediateReport(){
		
		generateHTMLReport(false);
	}
}
