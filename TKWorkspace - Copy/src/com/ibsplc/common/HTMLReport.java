
package com.ibsplc.common;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.Logger;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 *
 * @author a-7868
 *
 */
public class HTMLReport {

	static String htmlDir="/reports/CustomReport/";
	static String OUT_FOLDER ="";
	static String imgFolder = "/reports/img/";
	static String fileName = "CustomReport";
	static BufferedWriter out = null;
	static String logo = "../img/iCargo_logo.jpg";
	final static Logger logger = Logger.getLogger(HTMLReport.class);


	public static void customHTMLReport(String html_class, String html_method) throws IOException{


		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HHmmss");
		String todaysDate = sdf.format(currentDate.getTime());
		String dateTimeStamp = dateFormat.format(currentDate.getTime());

		ReportValues.reportFileName = fileName+" "+dateTimeStamp;

		imgFolder = new File(".").getCanonicalPath()+ imgFolder;
		//logo	= imgFolder+"\\iCargo_logo.jpg";
		OUT_FOLDER = new File(".").getCanonicalPath()+ htmlDir;
		PrintWriter fstream = new PrintWriter(new BufferedWriter(
				new FileWriter(new File(OUT_FOLDER, ReportValues.reportFileName+".html"))));
		out = new BufferedWriter(fstream);

		out.write(""+
				"<!DOCTYPE html>"+
				"<html>"+
				"<head>"+
				//"<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet'>"+
				"<link href='../img/Roboto.css' rel='stylesheet'>"+
				//https://www.w3schools.com/w3css/4/w3.css
				"<link rel='stylesheet' href='../img/w3.css'>"+
				"<link rel='icon' href='../img/iCargo_icon.jpg'>"+
				"<style>"+
				"table#table{"+
				"border:0px;"+
				"border-right :1px dotted;"+
				"}"+
				"table#table td {"+
				"padding: 4px 24px;"+
				"border-bottom: 1px dotted;"+
				"border-top: 0px;"+
				"border-left: 1px dotted;"+
				"border-right: 0px;"+
				"font-size: 13px;"+
				"}	"+
				"h1 {"+
				"font-size:25px;"+
				"font-color:grey;"+
				"}"+
				"h2 {"+
				"font-size:20px;"+
				"font-color:grey;"+
				"}"+
				"h3 {"+
				"font-size:15px;"+
				"font-color:grey;"+
				"}"+
				"h4 {"+
				"font-size:14px;"+
				"font-color:grey;"+
				"}"+
				"table#table tr:hover{"+
				"background-color: aliceblue;"+
				"table-layout: fixed;"+
				"width: 100px;"+
				"}"+
				"body{"+
				"font-family: 'Roboto', sans-serif !important;"+
				"font-weight:100;"+
				"}"+
				"a:link {"+
				"text-decoration: none;"+
				"color: blue;"+
				"}"+

				"a:visited {"+
				"text-decoration: none;"+
				"color: blue;"+
				"}"+

				"a:hover {"+
				"text-decoration: underline;"+
				"}"+

				"a:active {"+
				"text-decoration: underline;"+
				"}"+
				"body {"+
				"background: rgb(204,204,204); "+
				"}"+

				"page {"+
				"background: Ivory ;"+
				"display: block;"+
				"margin: 0 auto;"+
				"margin-bottom: 0.5cm;"+
				"box-shadow: 0 0 0.5cm rgba(0,0,0,0.5);"+
				"}"+
				"page[size='A4'] {  "+

				"}"+

				".tooltip {"+
				"position: relative;"+
				"display: inline-block;"+
				"border-bottom: 1px dotted black;"+
				"}"+
				".tooltip .tooltiptext {"+
				"visibility: hidden;"+
				"width: 400px;"+
				"height: 150px;"+
				"overflow: scroll;"+
				//"background-color: #fcffc9;"+
				//"color: #676262;"+
				"background-color: #160a58;"+
				"color: #ded6d6;"+
				"text-align: left;"+
				"border-radius: 6px;"+
				"padding: 5px 0;"+
				"position: absolute;"+
				"z-index: 1;"+
				"}"+
				".tooltip:hover .tooltiptext {"+
				"visibility: visible;"+
				"}"+
				
				"</style>"+

				"</head>"+

				"<body>"+

				"<center>"+
				"<page >"+
				"<table width=75%>"+
				"<tr>"+
				"<td style='padding-top:5px;text-align: left'>"+
				"<img style='border-radius: 50px;'src='"+logo+"' width=120px height=90px/>"+
				"</td>"+
				"<td align='right' style='padding-top:1px'><h1>"+
				"<u>iCargo Automation Report</u></h1>"+
				"</td>"+
				"</tr>"+
				"</table>"+

				//Summary Table
				"<table name=ExecutionSummary width=75%>"+
				"<tr>"+
				"<td name = summaryTable>"+
				"<h4>Execution Summary</h4>"+
				"<table  id=table border=1 cellspacing=1   style = 'width:initial;'  width=35%>"+
				"<tr> "+
				"<td align=left width=5% align=center bgcolor=#566D7E>"+
				"<FONT COLOR=#FFF3FF FACE=Arial SIZE=2>"+
				"<b>Description</b>"+
				"</td>"+
				"<td align=left width=5% align=center bgcolor=#566D7E>"+
				"<FONT COLOR=#FFF3FF FACE=Arial SIZE=2>"+
				"<b>Result</b>"+
				"</td>"+
				"</tr>"+

				//suite name
				"<tr> "+
				"<td align=left width=5%>"+
				"<FONT COLOR=#0000CD FACE=Arial SIZE=2>"+
				"<b>Suite</b>"+
				"</td>"+
				"<td align=left width=5%>"+
				"<FONT COLOR=#FF0000 FACE=Arial SIZE=2>"+
				"<b>"+ ReportValues.getSuiteName() + "</b>"+
				"</td>"+
				"</tr>"+

				//URL
				"<tr> "+
				"<td align=left width=5%>"+
				"<FONT COLOR=#0000CD FACE=Arial SIZE=2>"+
				"<b>App URL</b>"+
				"</td>"+
				"<td align=left width=5%>"+
				"<FONT COLOR=#FF0000 FACE=Arial SIZE=2>"+
				"<b><a href='"+ ReportValues.getAppURL() + "'>"+ReportValues.getAppURL()+"</a></b>"+
				"</td>"+
				"</tr>"+

				//User
				"<tr> "+
				"<td align=left width=5%>"+
				"<FONT COLOR=#0000CD FACE=Arial SIZE=2>"+
				"<b>App User</b>"+
				"</td>"+
				"<td align=left width=5%>"+
				"<FONT COLOR=#FF0000 FACE=Arial SIZE=2>"+
				"<b>" + ReportValues.getUserName() + "</b>"+
				"</td>"+
				"</tr>"+

				//Version
				"<tr> "+
				"<td align=left width=5%>"+
				"<FONT COLOR=#0000CD FACE=Arial SIZE=2>"+
				"<b>App Version</b>"+
				"</td>"+
				"<td align=left width=5%>"+
				"<FONT COLOR=#FF0000 FACE=Arial SIZE=2>"+
				"<b>"+ ReportValues.getAppVersion() +"</b>"+
				"</td>"+
				"</tr>"+

				//Execution Date
				"<tr> "+
				"<td align=left width=5%>"+
				"<FONT COLOR=#0000CD FACE=Arial SIZE=2>"+
				"<b>Date</b>"+
				"</td>"+
				"<td align=left width=5%>"+
				"<FONT COLOR=#FF0000 FACE=Arial SIZE=2>"+
				"<b>"+ todaysDate +"</b>"+
				"</td>"+
				"</tr>"+

				//Execution Start
				"<tr> "+
				"<td align=left width=5%>"+
				"<FONT COLOR=#0000CD FACE=Arial SIZE=2>"+
				"<b>Start Time</b>"+
				"</td>"+
				"<td align=left width=5%>"+
				"<FONT COLOR=#FF0000 FACE=Arial SIZE=2>"+
				"<b>"+ ReportValues.getTestStart() +"</b>"+
				"</td>"+
				"</tr>"+

				//Execution End
				"<tr> "+
				"<td align=left width=5%>"+
				"<FONT COLOR=#0000CD FACE=Arial SIZE=2>"+
				"<b>End Time</b>"+
				"</td>"+
				"<td align=left width=5%>"+
				"<FONT COLOR=#FF0000 FACE=Arial SIZE=2>"+
				"<b>"+ ReportValues.getTestEnd() +"</b>"+
				"</td>"+
				"</tr>"+

				//Total Count
				"<tr> "+
				"<td align=left width=5%>"+
				"<FONT COLOR=#0000CD FACE=Arial SIZE=2>"+
				"<b>Total TCs</b>"+
				"</td>"+
				"<td align=left width=5%>"+
				"<FONT COLOR=#FF0000 FACE=Arial SIZE=2>"+
				"<b>"+ ReportValues.getSuiteTotalCount() +"</b>"+
				"</td>"+
				"</tr>"+

				//Pass Count
				"<tr> "+
				"<td align=left width=5%>"+
				"<FONT COLOR=#0000CD FACE=Arial SIZE=2>"+
				"<b>Passed TCs</b>"+
				"</td>"+
				"<td align=left width=5%>"+
				"<FONT COLOR=#FF0000 FACE=Arial SIZE=2>"+
				"<b>"+ ReportValues.getPassCount() +"</b>"+
				"</td>"+
				"</tr>"+

				//Fail Count
				"<tr> "+
				"<td align=left width=5%>"+
				"<FONT COLOR=#0000CD FACE=Arial SIZE=2>"+
				"<b>Failed TCs</b>"+
				"</td>"+
				"<td align=left width=5%>"+
				"<FONT COLOR=#FF0000 FACE=Arial SIZE=2>"+
				"<b>"+ ReportValues.getFailCount() +"</b>"+
				"</td>"+
				"</tr>"+

				//Skip Count
				"<tr> "+
				"<td align=left width=5%>"+
				"<FONT COLOR=#0000CD FACE=Arial SIZE=2>"+
				"<b>Blocked TCs</b>"+
				"</td>"+
				"<td align=left width=5%>"+
				"<FONT COLOR=#FF0000 FACE=Arial SIZE=2>"+
				"<b>"+ ReportValues.getSuiteSkipCount() +"</b>"+
				"</td>"+
				"</tr>"+

				//Execution Time
				"<tr> "+
				"<td align=left width=5%>"+
				"<FONT COLOR=#0000CD FACE=Arial SIZE=2>"+
				"<b>Duration</b>"+
				"</td>"+
				"<td align=left width=5%>"+
				"<FONT COLOR=#FF0000 FACE=Arial SIZE=2>"+
				"<b>"+ (int)(ReportValues.getExecTime()/1000/60/60)+"h "+((int)ReportValues.getExecTime()/1000/60)%60+"m" +"</b>"+
				"</td>"+
				"</tr>"+

				//Browser
				"<tr> "+
				"<td align=left width=5%>"+
				"<FONT COLOR=#0000CD FACE=Arial SIZE=2>"+
				"<b>Browser</b>"+
				"</td>"+
				"<td align=left width=5%>"+
				"<FONT COLOR=#FF0000 FACE=Arial SIZE=2>"+
				"<b>"+ ReportValues.getBrowserName() +"</b>"+
				"</td>"+
				"</tr>"+

				//System User
				"<tr> "+
				"<td align=left width=5%>"+
				"<FONT COLOR=#0000CD FACE=Arial SIZE=2>"+
				"<b>User</b>"+
				"</td>"+
				"<td align=left width=5%>"+
				"<FONT COLOR=#FF0000 FACE=Arial SIZE=2>"+
				"<b>"+ System.getProperty("user.name") +"</b>"+
				"</td>"+
				"</tr>"+
				"</table>"+
				"</td>"+
				"<td name=summaryChart align=right>"+
				"<div id='piechart_3d' style='width: 500px; height: 500px;'></div>"+
				"</td>"+
				"</tr>"+
				"</table>");

		//detailed results

		if ((ReportValues.getSuiteTotalCount()) > 0) {

			/**************Module Level Details***************/
			out.write("<h2 id='results'> <u>Results</u> : <u>Module Level</u></h2>");
			out.write("<table id=table width=75%>");
			out.write("<tr> ");
			out.write("<td align=center width=20% align=center bgcolor=#566D7E><FONT COLOR=#FFF3FF FACE=Arial SIZE=2><b>Module</b></td>");
			out.write("<td align=center width=20% align=center bgcolor=#566D7E><FONT COLOR=#FFF3FF FACE=Arial SIZE=2><b>Parent Module</b></td>");
			out.write("<td align=center width=15% align=center bgcolor=#566D7E><FONT COLOR=#FFF3FF FACE=Arial SIZE=2><b>TC Count</b></td>");
			out.write("<td align=center width=15% align=center bgcolor=#566D7E><FONT COLOR=#FFF3FF FACE=Arial SIZE=2><b>Pass Count</b></td>");
			out.write("<td align=center width=15% align=center bgcolor=#566D7E><FONT COLOR=#FFF3FF FACE=Arial SIZE=2><b>Fail Count</b></td>");
			out.write("<td align=center width=15% align=center bgcolor=#566D7E><FONT COLOR=#FFF3FF FACE=Arial SIZE=2><b>Block Count</b></td>");
			out.write("</tr>");

			out.write(html_class);

			out.write("</tr>");
			out.write("</table><br><br>");


			/**************Test case Level Details***************/
			out.write("<h2> <u>Results</u> : <u>Test Case Level</u></h2><br>");

			out.write(html_method);
		}

		out.write(
				"<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'>"+
						"</script>"+
						"<script type='text/javascript'>"+
						"google.charts.load('current', {packages:['corechart']});"+
						"google.charts.setOnLoadCallback(drawChart);"+
						"function drawChart() {"+
						"var data = google.visualization.arrayToDataTable([['Status', 'TCs'],['Pass', "+ReportValues.getPassCount()+"],['Fail', "+ReportValues.getFailCount()+"],['Block',"+ReportValues.getSuiteSkipCount()+"], ]);"+
						"var options = {title: 'Execution Status', backgroundColor:'Ivory', pieHole: 0.4, slices: {0:{color:'green'}, 1:{color:'red'}, 2:{color:'orange'}}}; "+ //is3D: true,

						"var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));"+
						"chart.draw(data, options); "+
						"}"+
						"</script>"+
						"<br><br><br><br><br><br>"+
						"<hr width=85% align=center><br>"+
						"<a href='#'><span style='font-size:8pt'>&copy; IBS Software Services Pvt Ltd</span><center></a>"+
						"<br><br>"+
						/***********/
						"<div class='w3-container'>"+						  
						  "<div id='modal01' class='w3-modal' onclick=\"this.style.display='none'\">"+
						    "<span class='w3-button w3-hover-red w3-xlarge w3-display-topright'>&times;</span>"+
						    "<div class='w3-modal-content w3-animate-zoom'>"+
						      "<img id=img1 src='img_fjords.jpg' style='width:100%'>"+
						    "</div>"+
						  "</div>"+
						"</div>"+
						 /***********/ 
						"</page>"+
						"</body>"+
						"</html>");

		out.close();


	}

	public static void customPDFReport() throws DocumentException, IOException {

		System.out.println(OUT_FOLDER);
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(new File(OUT_FOLDER, fileName+".pdf")));
		document.open();
		HTMLWorker htmlWorker = new HTMLWorker(document);
//        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
//                new FileInputStream("index.html"));
		Reader reader = new FileReader(OUT_FOLDER+"/"+fileName+".html");
		htmlWorker.parse(reader);
		document.close();

	}

	public static void main(String[] args) throws Exception{
		try{customHTMLReport("", "");}catch(Exception e){e.printStackTrace();}
	}
}



 