
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


	public static void customHTMLReport(String html_class, String html_method, boolean isFinal) throws IOException{


		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HHmmss");
		String todaysDate = sdf.format(currentDate.getTime());
		String dateTimeStamp = dateFormat.format(currentDate.getTime());

		if(isFinal)
			ReportValues.reportFileName = fileName+" "+dateTimeStamp;
		else
			ReportValues.reportFileName = "interimReport";
		
		imgFolder = new File(".").getCanonicalPath()+ imgFolder;
		//logo	= imgFolder+"\\iCargo_logo.jpg";
		OUT_FOLDER = new File(".").getCanonicalPath()+ htmlDir;
		PrintWriter fstream = new PrintWriter(new BufferedWriter(
				new FileWriter(new File(OUT_FOLDER, ReportValues.reportFileName+".html"))));
		out = new BufferedWriter(fstream);

		out.write("<!DOCTYPE html>\n"+
					"<html lang='en'>\n"+
					"<head>\n"+
					  "<title>iCargo Report</title>\n"+
					  "<meta charset='utf-8'>\n"+
					  "<meta name='viewport' content='width=device-width, initial-scale=1'>\n"+
					  //"<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>\n"+
					  "<link rel='stylesheet' href='../img/bootstrap.min.css'>\n"+
					  "<link rel='stylesheet' href='../img/w3.css'>\n"+
					  //"<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>\n"+
					  "<script src='../img/jquery.min.js'></script>\n"+
					  //"<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>\n"+
					  "<script src='../img/bootstrap.min.js'></script>\n"+
					"</head>\n"+
					
					"<style>\n"+
					    /* Set height of the grid so .sidenav can be 100% (adjust if needed) */
					    ".row.content {height: 800px;}\n"+
					    
					    /* Set gray background color and 100% height */
					    ".sidenav {\n"+
					    "  background-color: #f3f3f3;\n"+
					    "  height: 100%;\n"+
					    "}\n"+
					    
					    /* Set black background color, white text and some padding */
					    "footer {\n"+
					    "  background-color: #555;\n"+
					    "  color: white;\n"+
					    "  padding: 15px;\n"+
					    "}\n\n"+
					    
					    /* On small screens, set height to 'auto' for sidenav and grid */
					    "@media screen and (max-width: 767px) {\n"+
					    "  .sidenav {\n"+
					    "    height: auto;\n"+
					    "    padding: 15px;\n"+
					    "  }\n"+
					    "  .row.content {height: auto;} \n"+
					    "}\n"+
						
						"table #t1 {\n"+
					    "border-collapse: collapse;\n"+
					    "width: 100%;\n"+
						"}\n"+
					
						"table#t1 th, table#t1 td {\n"+
						"	padding: 5px;\n"+
						"	text-align: left;\n"+
						"	border-bottom: 1px solid #ddd;\n"+
						"}\n"+
					
						".nav-pills>li.active>a, .nav-pills>li.active>a:hover, .nav-pills>li.active>a:focus{\n"+
						"	background-color:#f3f3f3;\n"+
						"	color:black;\n"+
						"}\n"+
						
						"li.active>a{\n"+
						"	background-color:#f3f3f3;\n"+
						"	color:black;\n"+
						"}\n"+
						".nav-pills>li>a {\n"+
						"	color:rgba(0,0,0,.5);padding:1px 10px;\n"+
						"}\n"+
						
						".overlay {\n"+
						"	position: fixed;\n"+    
						"	display: none;\n"+
						"	align: center; \n"+   
						"	width: 100%;\n"+ 
						"	height: 100%;\n"+   
						"	top: 0; \n"+  
						"	left: 0; \n"+   
						"	right: 0; \n"+   
						"	bottom: 0;\n"+   
						"	background-color: \n"+
						"	rgba(0,0,0,0.8); \n"+   
						"	z-index: 2;	\n"+ 
						"	transition: 2s ease; \n"+  
						"	cursor: pointer;\n"+
						"	overflow-y: scroll;\n"+	
						"}\n"+

						".overlay1 {\n"+
						"	position: fixed;\n"+    
						"	display: none;\n"+
						"	align: center; \n"+   
						"	width: 100%;\n"+ 
						"	height: 100%;\n"+   
						"	top: 0; \n"+  
						"	left: 0; \n"+   
						"	right: 0; \n"+   
						"	bottom: 0;\n"+   
						"	background-color: \n"+
						"	rgba(0,0,0,0.1); \n"+   
						"	z-index: 2;	\n"+ 
						"	transition: 2s ease; \n"+  
						"	cursor: pointer;\n"+
						"	overflow-y: scroll;\n"+	
						"}\n"+

						".text{  \n"+  
						"	position: absolute; \n"+   
						"	top: 50%;    \n"+
						"	left: 50%;    \n"+
						"	font-size: 15px;  \n"+  
						"	color: white;  \n"+  
						"	transform: translate(-50%,-50%);  \n"+  
						"	-ms-transform: translate(-50%,-50%);\n"+
						"}\n"+

						".text1{  \n"+  
						"	position: absolute; \n"+   
						"	top: 50%;    \n"+
						"	left: 50%;    \n"+
						"	font-size: 15px;  \n"+  
						"	color: black;  \n"+  
						"	transform: translate(-50%,-50%);  \n"+  
						"	-ms-transform: translate(-50%,-50%);\n"+
						"}\n"+
							
					  "</style>\n"+
					  
						"<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>\n"+
						//"<script type='text/javascript' src='../img/loader.js'></script>\n"+
						"<script type='text/javascript'>\n"+
						"	google.charts.load('current', {packages:['corechart']});\n"+
						"	google.charts.setOnLoadCallback(drawChart);\n"+
						"	function drawChart() {\n"+
						"		var data = google.visualization.arrayToDataTable([['Status', 'TCs', { role: 'style' }],['Pass', "+ReportValues.getPassCount()+", 'green'],['Fail', "+ReportValues.getFailCount()+", 'red'],['Block', "+ReportValues.getSuiteSkipCount()+", 'orange'], ]);\n"+
						"		var options = {title: '', 'width': 600, 'height':300, backgroundColor:'none', is3D: true, slices: {0:{color:'green'}, 1:{color:'red'}, 2:{color:'orange'}}};\n"+ 
						"		var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));chart.draw(data, options);\n"+ 
						"		var options = {title: '', 'width': 600, 'height':300};\n"+ 
						"		var chart2 = new google.visualization.ColumnChart(document.getElementById('barChart'));chart2.draw(data, options);\n"+ 
						"	}\n"+
						"</script>\n"+
						"<script>function on(id) {    document.getElementById(id).style.display = 'block';}function off(id) {    document.getElementById(id).style.display = 'none';}\n"+
						"</script>\n"+
					  
					"<body>\n"+
					
					"<div class='container-fluid'>\n"+
						"<div class='col-sm-3 sidenav'>\n"+
						"	<div class='row content', data-spy='affix'>\n"+
						"	  <br>\n"+
						"	  <div class='well well-sm title'>\n"+
						"	  <center><img style='border-radius: 40px;'src='../img/iCargo_logo.jpg' width=120px height=90px/></center>\n"+
						"		<center><h3 style='color:#337ab7;'>Test Automation Report</h3></center>\n"+
						"	  </div>\n"+	
						"	  <div style='overflow-x:scroll;'>\n"+	
						"	  <ul class='nav nav-pills nav-stacked'>\n"+
						"		<li class='active'><a data-toggle='tab' href='#details'>Execution Details</a></li><li class='nav-divider'></li>\n"+
						"		<li><a data-toggle='tab' href='#summary'>Test Summary</a></li><li class='nav-divider'></li>\n"+
						"		<li><a data-toggle='tab' href='#modSummary'>Module Summary</a></li><li class='nav-divider'></li>\n"+
					
						"			<li><a href='#'>Modules</a></li>\n"+
					/*	"				<div style='padding-left:30px;'>\n"+
						"					<ul id='drilldown-1' class='nav nav-pills nav-stacked collapse'>		"*/"");
		
						for(String className : ReportValues.classStat.keySet()){
							
							out.write("<li style='padding-left:10px;font-size:12px;'><a data-toggle='tab' href='#"+className+"'>"+className+"</a></li>\n");
						}
											                     
						out.write(/*"			</ul>\n"+
						"				</div>\n"+
						"			</li>\n"+*/
						"		</li>\n"+
						"	  </ul></div>\n"+
						"	</div>\n"+
						"</div>\n"+
						
						"<div class='col-sm-9'>\n"+
						"	  <div class='tab-content'>\n"+
								
						"		<div id='details' class='tab-pane fade in active'>\n"+
								  
						"			<br>\n"+
						"			<div class='well well-sm'><center><h2 style='color:#337ab7;'>Execution Details</h2></center></div>\n"+
						"			<table  class='table table-striped table-bordered table-hover table-condensed' >\n"+
						"				<tr> \n"+
						"					<td>\n"+
						"						<FONT COLOR=#000100 FACE=Arial SIZE=2>\n"+
						"						<b>Description</b>\n"+
						"					</td>\n"+
						"					<td>\n"+
						"						<FONT COLOR=#000100 FACE=Arial SIZE=2>\n"+
						"						<b>Result</b>\n"+
						"					</td>\n"+
						"				</tr>\n"+
						"				<tr> \n"+
						"					<td align=left width=5%>\n"+
						"						Implementation\n"+
						"					</td>\n"+
						"					<td align=left width=5%>\n"+
											ReportValues.getImplementation()+"\n"+
						"					</td>\n"+
						"				</tr>\n"+
						"				<tr> \n"+
						"					<td align=left width=5%>\n"+
						"						Suite\n"+
						"					</td>\n"+
						"					<td align=left width=5%>\n"+
											ReportValues.getSuiteName()+"\n"+
						"					</td>\n"+
						"				</tr>\n"+
						"				<tr> \n"+
						"					<td align=left width=5%>\n"+
						"						App URL\n"+
						"					</td>\n"+
						"					<td align=left width=5%>\n"+
						"						<a href='"+ReportValues.getAppURL()+"' target='_blank'>"+ReportValues.getAppURL()+"</a>\n"+
						"					</td>\n"+
						"				</tr>\n"+
						"				<tr> \n"+
						"					<td align=left width=5%>\n"+
						"						App User\n"+
						"					</td>\n"+
						"					<td align=left width=5%>\n"+
											ReportValues.getUserName()+"\n"+
						"					</td>\n"+
						"				</tr>\n"+
						"				<tr> \n"+
						"					<td align=left width=5%>\n"+
						"						App Version\n"+
						"					</td>\n"+
						"					<td align=left width=5%>\n"+
											ReportValues.getAppVersion()+"\n"+
						"					</td>\n"+
						"				</tr>\n"+
						"				<tr> \n"+
						"					<td align=left width=5%>\n"+
						"						Date\n"+
						"					</td>\n"+
						"					<td align=left width=5%>\n"+
											ReportValues.getExecDate()+"\n"+
						"					</td>\n"+
						"				</tr>\n"+
						"				<tr> \n"+
						"					<td align=left width=5%>\n"+
						"						Start Time\n"+
						"					</td>\n"+
						"					<td align=left width=5%>\n"+
											ReportValues.getTestStart()+"\n"+
						"					</td>\n"+
						"				</tr>\n"+
						"				<tr> \n"+
						"					<td align=left width=5%>\n"+
						"						End Time\n"+
						"					</td>\n"+
						"					<td align=left width=5%>\n"+
											ReportValues.getTestEnd()+"\n"+
						"					</td>\n"+
						"				</tr>\n"+
						"				<tr> \n"+
						"					<td align=left width=5%>\n"+
						"						Total TCs\n"+
						"					</td>\n"+
						"					<td align=left width=5%>\n"+
											ReportValues.getSuiteTotalCount()+"\n"+
						"					</td>\n"+
						"				</tr>\n"+
						"				<tr> \n"+
						"					<td align=left width=5%>\n"+
						"						Passed TCs\n"+
						"					</td>\n"+
						"					<td align=left width=5%>\n"+
											ReportValues.getPassCount()+"\n"+
						"					</td>\n"+
						"				</tr>\n"+
						"				<tr> \n"+
						"					<td align=left width=5%>\n"+
						"						Failed TCs\n"+
						"					</td>\n"+
						"					<td align=left width=5%>\n"+
											ReportValues.getFailCount()+"\n"+
						"					</td>\n"+
						"				</tr>\n"+
						"				<tr> \n"+
						"					<td align=left width=5%>\n"+
						"						Blocked TCs\n"+
						"					</td>\n"+
						"					<td align=left width=5%>\n"+
											ReportValues.getSuiteSkipCount()+"\n"+
						"					</td>\n"+
						"				</tr>\n"+
						"				<tr> \n"+
						"					<td align=left width=5%>\n"+
						"						Duration\n"+
						"					</td>\n"+
						"					<td align=left width=5%>\n"+
											(int)(ReportValues.getExecTime()/1000/60/60)+"h "+((int)ReportValues.getExecTime()/1000/60)%60+"m\n"+
						"					</td>\n"+
						"				</tr>\n"+
						"				<tr> \n"+
						"					<td align=left width=5%>\n"+
						"						Browser\n"+
						"					</td>\n"+
						"					<td align=left width=5%>\n"+
											ReportValues.getBrowserName()+"\n"+
						"					</td>\n"+
						"				</tr>\n"+
						"				<tr> \n"+
						"					<td align=left width=5%>\n"+
						"						User\n"+
						"					</td>\n"+
						"					<td align=left width=5%>\n"+
											System.getProperty("user.name")+"\n"+
						"					</td>\n"+
						"				</tr>\n"+
						"				<tr> \n"+
						"					<td align=left width=5%>\n"+
						"						Execution Log\n"+
						"					</td>\n"+
						"					<td align=left width=5%>\n"+
						"						<a href='../execution_log.log' target='_blank'>Execution.log</a>\n"+
						"					</td>\n"+
						"				</tr>\n"+
						"			</table>	\n"+		  
						"		</div>\n"+
								
						"		<div id='summary' class='tab-pane fade'><br>\n"+
						
						"			<div class='well well-sm'><center><h2 style='color:#337ab7;'>Test Summary</h2></center></div><br>\n"+
						"		  	<center><div id='piechart_3d' style='width: 600px; height: 300px;'></div></center>\n"+
						"		  	<center><div id='barChart' style='width: 600px; height: 300px;'></div></center>\n"+
						"		</div>\n"+
								
						"		<div id='modSummary' class='tab-pane fade'>\n"+
								  
						"			<br>\n"+
						"			<div class='well well-sm'><center><h2 style='color:#337ab7;'>Module Summary</h2></center></div>\n"+
						"			<table class='table table-striped table-bordered table-hover table-condensed' >\n"+
						"			<tr> \n"+
						"				<td align=center width=20% >\n"+
						"					<b>Module</b>\n"+
						"				</td>\n"+
						"				<td align=center width=20% >\n"+
						"					<b>Parent Module</b>\n"+
						"				</td>\n"+
						"				<td align=center width=15% >\n"+
						"					<b>TC Count</b>\n"+
						"				</td>\n"+
						"				<td align=center width=15% >\n"+
						"					<b>Pass Count</b>\n"+
						"				</td>\n"+
						"				<td align=center width=15% >\n"+
						"					<b>Fail Count</b>\n"+
						"				</td>\n"+
						"				<td align=center width=15% >\n"+
						"					<b>Block Count</b>\n"+
						"				</td>\n"+
						"			</tr>\n"+
						
									//class summary
									html_class +
									
						"		  </table>\n"+
						"		</div>\n"+
								
								//method summary
								html_method +
							  
						"	  </div>\n"+
						"</div>\n"+
						
					"</div>\n"+
					"<center>"
					+ "<div class='w3-container'>"
					+ "<div id='modal01' class='w3-modal' onclick=\"this.style.display='none'\">"
					+ "<span class='w3-button w3-hover-red w3-xlarge w3-display-topright'>&times;</span>"
					+ "<div class='w3-modal-content w3-animate-zoom'>"
					+ "<img id=img1 src='img_fjords.jpg' style='width:100%'>"
					+ "</div>"
					+ "</div>"
					+ "</div>\n"+
					"</body>\n"+
					"</html>"	);
		
		out.close();

		logger.info("Report written to HTML File - "+ReportValues.reportFileName);
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

	
}



 