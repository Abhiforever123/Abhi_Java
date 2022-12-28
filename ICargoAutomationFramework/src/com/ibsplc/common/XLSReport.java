package com.ibsplc.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.formula.functions.Hyperlink;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class XLSReport {

	public static String fileName = "CustomReport";
	public static String filePath = "/reports/CustomReport/";	
	static String OUT_FOLDER ="";
		
	public static void createXLSReport(Map<String, LinkedHashMap<String,String[]>> classStat) throws IOException{
		
		OUT_FOLDER = new File(".").getCanonicalPath()+ filePath;
		
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy"); 
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HHmmss");
		String todaysDate = sdf.format(currentDate.getTime());
		String dateTimeStamp = dateFormat.format(currentDate.getTime());
		
		String actualFileName = OUT_FOLDER + "\\" + ReportValues.reportFileName+".xls";
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet("Summary");

		int rownum = 0;
		HSSFRow row = sheet.createRow(rownum);
		HSSFCell cell = row.createCell(0);

		// Formating Cell Style
//		HSSFCellStyle cellStyle = workBook.createCellStyle();
		HSSFCellStyle cellStyle = workBook.createCellStyle();
//		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		// Formating Cell Font
		HSSFFont font = workBook.createFont();
		font.setFontHeightInPoints((short) 9);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
		font.setColor(HSSFColor.BLACK.index);
		cellStyle.setFont(font);

		// Formating Cell Style
		HSSFCellStyle cellStyleHead = workBook.createCellStyle();
		cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyleHead.setFillForegroundColor(HSSFColor.getIndexHash());
		cellStyleHead.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyleHead.setAlignment(CellStyle.ALIGN_CENTER);
		// Formating Cell Font
		HSSFFont fontHead = workBook.createFont();
		fontHead.setFontHeightInPoints((short) 20);
		fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontHead.setColor(HSSFColor.BLACK.index);
		cellStyleHead.setFont(fontHead);
		//alignment styles
		HSSFCellStyle style_alignLeft = workBook.createCellStyle();
		style_alignLeft.setAlignment(CellStyle.ALIGN_LEFT);
		HSSFCellStyle style_alignRight = workBook.createCellStyle();
		style_alignRight.setAlignment(CellStyle.ALIGN_RIGHT);
		HSSFCellStyle style_alignCenter = workBook.createCellStyle();
		style_alignCenter.setAlignment(CellStyle.ALIGN_CENTER);
		//initialize cells
		for(int i=0; i<=50; i++){
			row = sheet.createRow(i);
			for(int j=0; j<=20; j++)
				cell = row.createCell(j);			
		}
		
		//title
		HSSFRow TitleRow = sheet.createRow(2);
		cell = TitleRow.createCell(3);
		cell.setCellValue("Test Automation Report");
		cell.setCellStyle(cellStyleHead);
			
		//draw border
		CellRangeAddress  region = CellRangeAddress.valueOf("$D$3:$E$4");
		RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
        RegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
        RegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
        RegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
        
		sheet.addMergedRegion(new CellRangeAddress(2,3,3,4));	
				
		
/****************set summary table data*************************/

		
		//set col width
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 9000);
		sheet.setColumnWidth(6, 6000);
		
		//write summary table contents 
		sheet.getRow(5).getCell(3).setCellValue("Suite");
		sheet.getRow(5).getCell(3).setCellStyle(cellStyle);
		sheet.getRow(5).getCell(4).setCellValue(ReportValues.getSuiteName());		
		
		sheet.getRow(6).getCell(3).setCellValue("App URL");
		sheet.getRow(6).getCell(3).setCellStyle(cellStyle);
		sheet.getRow(6).getCell(4).setCellValue(ReportValues.getAppURL());		
		
		sheet.getRow(7).getCell(3).setCellValue("App Version");
		sheet.getRow(7).getCell(3).setCellStyle(cellStyle);
		sheet.getRow(7).getCell(4).setCellValue(ReportValues.getAppVersion());		
		
		sheet.getRow(8).getCell(3).setCellValue("Date");
		sheet.getRow(8).getCell(3).setCellStyle(cellStyle);
		sheet.getRow(8).getCell(4).setCellValue(todaysDate);		
		
		sheet.getRow(9).getCell(3).setCellValue("TC Count");
		sheet.getRow(9).getCell(3).setCellStyle(cellStyle);
		sheet.getRow(9).getCell(4).setCellValue(ReportValues.getSuiteTotalCount());
		sheet.getRow(9).getCell(4).setCellStyle(style_alignLeft);		
		
		sheet.getRow(10).getCell(3).setCellValue("Pass Count");
		sheet.getRow(10).getCell(3).setCellStyle(cellStyle);
		sheet.getRow(10).getCell(4).setCellValue(ReportValues.getPassCount());
		sheet.getRow(10).getCell(4).setCellStyle(style_alignLeft);		
		
		sheet.getRow(11).getCell(3).setCellValue("Fail Count");
		sheet.getRow(11).getCell(3).setCellStyle(cellStyle);
		sheet.getRow(11).getCell(4).setCellValue(ReportValues.getFailCount());
		sheet.getRow(11).getCell(4).setCellStyle(style_alignLeft);		
		
		sheet.getRow(12).getCell(3).setCellValue("Skip Count");
		sheet.getRow(12).getCell(3).setCellStyle(cellStyle);
		sheet.getRow(12).getCell(4).setCellValue(ReportValues.getSuiteSkipCount());
		sheet.getRow(12).getCell(4).setCellStyle(style_alignLeft);		
		
		sheet.getRow(13).getCell(3).setCellValue("Duration");
		sheet.getRow(13).getCell(3).setCellStyle(cellStyle);
		sheet.getRow(13).getCell(4).setCellValue((int)(ReportValues.getExecTime()/1000/60/60)+"h "+((int)ReportValues.getExecTime()/1000/60)%60+"m");
				
		sheet.getRow(14).getCell(3).setCellValue("Browser");
		sheet.getRow(14).getCell(3).setCellStyle(cellStyle);
		sheet.getRow(14).getCell(4).setCellValue(ReportValues.getBrowserName());		
		
		sheet.getRow(15).getCell(3).setCellValue("User");
		sheet.getRow(15).getCell(3).setCellStyle(cellStyle);
		sheet.getRow(15).getCell(4).setCellValue(System.getProperty("user.name"));		
		
		sheet.getRow(16).getCell(3).setCellValue("Start Time");
		sheet.getRow(16).getCell(3).setCellStyle(cellStyle);
		sheet.getRow(16).getCell(4).setCellValue(ReportValues.getTestStart());		
		
		sheet.getRow(17).getCell(3).setCellValue("End Time");
		sheet.getRow(17).getCell(3).setCellStyle(cellStyle);
		sheet.getRow(17).getCell(4).setCellValue(ReportValues.getTestEnd());		
		
		//draw border
		region = CellRangeAddress.valueOf("$D$6:$E$18");
		RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
        RegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
        RegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
        RegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
                       
        
/**********Module Level Status*******************/

        
		//write module details table header
		sheet.getRow(5).getCell(6).setCellValue("Module");
		sheet.getRow(5).getCell(6).setCellStyle(cellStyle);
		sheet.getRow(5).getCell(7).setCellValue("TC Count");
		sheet.getRow(5).getCell(7).setCellStyle(cellStyle);
		sheet.getRow(5).getCell(8).setCellValue("Pass");
		sheet.getRow(5).getCell(8).setCellStyle(cellStyle);		
		sheet.getRow(5).getCell(9).setCellValue("Fail");
		sheet.getRow(5).getCell(9).setCellStyle(cellStyle);
		sheet.getRow(5).getCell(10).setCellValue("Skip");
		sheet.getRow(5).getCell(10).setCellStyle(cellStyle);
		sheet.getRow(5).getCell(11).setCellValue("Details");
		sheet.getRow(5).getCell(11).setCellStyle(cellStyle);		
		
		int pass, fail, count;
		int rowIndex = 6;
		HSSFHyperlink linkDOC;
		//fetching module name and pass/fail count 
		for(String className : classStat.keySet()){
			
			pass=0; fail=0;	count=0;
			for(Object obj : classStat.get(className).keySet()){	
				
				String[] str = new String[4];
				str = classStat.get(className).get(obj);
				count++;
				
				if(str[0].toString().equals("PASS"))
					pass++;									
					
				if(str[0].toString().equals("FAIL"))
					fail++;				
			}
			
			//write row contents
			sheet.getRow(rowIndex).getCell(6).setCellValue(className);
			sheet.getRow(rowIndex).getCell(6).setCellStyle(style_alignLeft);
			sheet.getRow(rowIndex).getCell(7).setCellValue(count);
			sheet.getRow(rowIndex).getCell(7).setCellStyle(style_alignCenter);
			sheet.getRow(rowIndex).getCell(8).setCellValue(pass);
			sheet.getRow(rowIndex).getCell(8).setCellStyle(style_alignCenter);
			sheet.getRow(rowIndex).getCell(9).setCellValue(fail);
			sheet.getRow(rowIndex).getCell(9).setCellStyle(style_alignCenter);
			sheet.getRow(rowIndex).getCell(10).setCellValue(count-(pass+fail));
			sheet.getRow(rowIndex).getCell(10).setCellStyle(style_alignCenter);
			//hyperlink
			sheet.getRow(rowIndex).getCell(11).setCellValue("view");
			linkDOC = new HSSFHyperlink(HSSFHyperlink.LINK_DOCUMENT);			
			linkDOC.setAddress("'"+className+"'!A1");
			sheet.getRow(rowIndex).getCell(11).setHyperlink(linkDOC);	
			sheet.getRow(rowIndex).getCell(11).setCellStyle(style_alignCenter);
					 
			rowIndex++;
		}
		//draw border
		region = CellRangeAddress.valueOf("$G$6:$L$"+rowIndex);
		RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
        RegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
        RegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
        RegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
        
        
/*********************Test Case Level Details *************************/

        
        CellStyle styleWrap = workBook.createCellStyle(); //Create new style
        styleWrap.setWrapText(true);
        HSSFHyperlink linkURL;
        
        //for each class/module
        for(String className : classStat.keySet()){
        	
        	rowIndex = 5;
        	//create new sheet and initialize the cells
        	sheet = workBook.createSheet(className);
        	for(int i=0; i<=classStat.get(className).keySet().size()+7; i++){
    			row = sheet.createRow(i);
    			for(int j=0; j<=20; j++)
    				cell = row.createCell(j);
        	}	
        	
        	//Module header
        	sheet.getRow(2).getCell(3).setCellValue("Module : "+className);
        	sheet.getRow(2).getCell(3).setCellStyle(cellStyleHead);        	
        	sheet.addMergedRegion(new CellRangeAddress(2,3,3,6));
        	region = CellRangeAddress.valueOf("$D$3:$G$4");
//    		RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
        	RegionUtil.set
        	RegionUtil.setBorderBottom(cellStyle.getBorderBottom(), arg1, arg2);
    		RegionUtil.setBorderBottom(CellStyle, region, sheet, workBook);
            RegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
            RegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
            RegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
            //Details table header
        	sheet.getRow(rowIndex).getCell(3).setCellValue("Test Case");
    		sheet.getRow(rowIndex).getCell(3).setCellStyle(cellStyle);
    		sheet.getRow(rowIndex).getCell(4).setCellValue("Status");
    		sheet.getRow(rowIndex).getCell(4).setCellStyle(cellStyle);
    		sheet.getRow(rowIndex).getCell(5).setCellValue("Duration");
    		sheet.getRow(rowIndex).getCell(5).setCellStyle(cellStyle);		
    		sheet.getRow(rowIndex).getCell(7).setCellValue("Assertions");
    		sheet.getRow(rowIndex).getCell(7).setCellStyle(cellStyle);
    		sheet.getRow(rowIndex).getCell(6).setCellValue("Screenshot");
    		sheet.getRow(rowIndex).getCell(6).setCellStyle(cellStyle);
    		//set col width
    		sheet.setColumnWidth(3, 8000);
    		sheet.setColumnWidth(4, 3000);
    		sheet.setColumnWidth(6, 3000);
    		sheet.setColumnWidth(7, 8000);
    		//for each method within the module
    	   	for(Object obj : classStat.get(className).keySet()){	
        		
    	   		rowIndex++;
    	   		String[] str = new String[4];
				str = classStat.get(className).get(obj);
				//write the row contents
    	   		sheet.getRow(rowIndex).getCell(3).setCellValue(obj.toString());
    	   		sheet.getRow(rowIndex).getCell(3).setCellStyle(style_alignLeft);
    			sheet.getRow(rowIndex).getCell(4).setCellValue(str[0]);
    			sheet.getRow(rowIndex).getCell(4).setCellStyle(style_alignCenter);
    			sheet.getRow(rowIndex).getCell(5).setCellValue(str[1]+"s");
    			sheet.getRow(rowIndex).getCell(5).setCellStyle(style_alignCenter);
//    			sheet.getRow(rowIndex).getCell(7).setCellValue(str[2]);
    			sheet.getRow(rowIndex).getCell(7).setCellStyle(styleWrap);
    			//screenshot field
    			if(!str[3].equals("")){
    				sheet.getRow(rowIndex).getCell(6).setCellValue("screenshot");//link
    				sheet.getRow(rowIndex).getCell(6).setCellStyle(styleWrap);
    				linkURL = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
    				linkURL.setAddress(str[3]);
        			sheet.getRow(rowIndex).getCell(6).setHyperlink(linkURL);
        			sheet.getRow(rowIndex).getCell(6).setCellStyle(style_alignCenter);
    			}    
    			
    			str[4] = str[4].replaceAll("<td>", "");
    			str[4] = str[4].replaceAll("</td>", "");
    			str[4] = str[4].replaceAll("<tr>", "");
    			str[4] = str[4].replaceAll("</tr>", "");
    			str[4] = str[4].replaceAll("<font color=red>", "");
    			str[4] = str[4].replaceAll("<font color=green>", "");
    			str[4] = str[4].replaceAll("</font>", "");
    			str[4] = str[4].replaceAll("<center>", "");
    			str[4] = str[4].replaceAll("</center>", "");
    			
    			str[4] = str[4].replaceAll("PASS", "- PASS\n");
    			str[4] = str[4].replaceAll("FAIL", "- FAIL\n");
    			
    			sheet.getRow(rowIndex).getCell(7).setCellValue("  "+str[4].trim());
    			
        	}
    	   	//draw border
    	   	region = CellRangeAddress.valueOf("$D$6:$H$"+(rowIndex+1));
    		RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
            RegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
            RegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, region, sheet, workBook);
            RegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, region, sheet, workBook);	
        		
        }
        	
        //write created contents to file
		try {
			
			File fileToWrite = new File(actualFileName);
			Path path = fileToWrite.toPath();
			if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
				FileOutputStream out = new FileOutputStream(new File(actualFileName));
				workBook.write(out);
				out.close();
				System.out.println("Excel written successfully..");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
	}
	
	
	
//	
//	public static void main(String[] args) throws IOException{
//		createXLSReport(new HashMap<String,HashMap<String, String[]>>());
//	}
}
