package com.ibsplc.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	

public class ExcelOps {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private static Workbook wb = null;
	private static Sheet sheet=null;
	private static String data= null;
	private static Row row= null;
	private static Cell cell=null;
	private static Date date= null;
	public static String readAllFromExcel(String fileName,String sheetName) throws IOException 
	{
		
		
		try {
				
				 FileInputStream iStr = new FileInputStream(new File(fileName));
				 wb=checkIndex(iStr,fileName); 				
				 sheet= wb.getSheet(sheetName);
				 
		//		int rowCount= sheet.getLastRowNum()-sheet.getFirstRowNum();
				int rowCount=sheet.getPhysicalNumberOfRows();
				
				for(int i=0;i<rowCount+1;i++)
				{
					row= sheet.getRow(i);
						for(int j=0;j<row.getLastCellNum();j++)
						{
						
							data= row.getCell(j).getStringCellValue();
						}
				}
				 
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return data;
	}
	
	
	private static Workbook checkIndex(FileInputStream inputStream,String fileName)
	{
		Workbook wbk=null;

		try 
		{
			String extn=fileName.substring(fileName.indexOf("."));
				if(extn.equals(".xlsx"))	
				return new XSSFWorkbook(inputStream);
				else
					return new HSSFWorkbook(inputStream);
			

		}
		catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
		
	}
	
	
	public static String getCellData(String fileName,String sheetName, int rowNum, int colNum,String ...dateFormat)
	{
		try 
		{
			 FileInputStream iStr = new FileInputStream(new File(fileName));
			 wb=checkIndex(iStr,fileName); 				
			 sheet= wb.getSheet(sheetName);
			 
	//		int rowCount= sheet.getLastRowNum()-sheet.getFirstRowNum();
			int rowCount=sheet.getPhysicalNumberOfRows();
			
			for(int i=0;i<rowCount+1;i++)
			{
				row= sheet.getRow(i);
					for(int j=0;j<row.getLastCellNum();j++)
					{
					
						data= row.getCell(j).getStringCellValue();
					}
			}
			
			sheet=wb.getSheet(sheetName);
			row = sheet.getRow(rowNum);
			cell = row.getCell(colNum);
			 
			 
//			 if(cell.getCellType()==cell.CELL_TYPE_STRING)
				 if(cell.getCellType()==CellType.STRING)	 
				 return cell.getStringCellValue();
			 else if(cell.getCellType()==cell.CELL_TYPE_NUMERIC || cell.getCellType()==cell.CELL_TYPE_FORMULA) 
			 {
				 String cellValue = String.valueOf(cell.getNumericCellValue());
				 if(HSSFDateUtil.isCellDateFormatted(cell))
				 {
					 if(!(dateFormat[0]==null))
					 cellValue=dateFormatConverter(dateFormat[0]);
				 
				 }
			 return cellValue;
			 }
			 else if(cell.getCellType()==cell.CELL_TYPE_BLANK)
				 return ""; 
			 else 
				 return String.valueOf(cell.getBooleanCellValue());
		}
		catch (Exception e) {
			// TODO: handle exception
			return "";
		}
		
	
		
		
		
		}
	
	
	public static String dateFormatConverter(String format)
	{	
		DateFormat df = new SimpleDateFormat(format);
		date = cell.getDateCellValue();		
		
		return String.valueOf(df.format(date));
	}


}
