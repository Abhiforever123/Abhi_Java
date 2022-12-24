/**
 * 
 *//*
package com.ibsplc.common;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.swing.JOptionPane;

*//**
 * @author A-6545
 *
 *//*
public class DynamicObjectCapture extends BasePage  {
	public DynamicObjectCapture(WebDriver driver) {
		super(driver);
		  this.driver = driver;
		// TODO Auto-generated constructor stub
	}
	InputStream input = null;
	
	 
		
	  String objType=null;
      String objTag=null;
	public static String Propertypath = "";
	public static String PropertyName = "";
    int excelRowIndex = 0;
    FileOutputStream fileOut = null;
    Workbook wb = null; 
    CreationHelper createHelper = null; // wb.getCreationHelper();
    Sheet sheetObjectDescriptiveTable = null; 
    
    HashMap excelColHeader = new HashMap();
    HashMap objProp = new HashMap();
    String objType=null;
    String objTag=null;
    @SuppressWarnings({ "unused", "unchecked" })
    public void testMain(Object[] args) throws SQLException 
    {
    	try{
    		
    		Properties prop = new Properties();
        	
        
            String value = null;
      
             
    	Propertypath = JOptionPane.showInputDialog(null, "Enter the FolderPath to create the Object Descriptive Sheet {Eg: C:\\\\FolderName) : ", "Folder Path to Create Descriptive File", JOptionPane.QUESTION_MESSAGE);
    	PropertyName = JOptionPane.showInputDialog(null, "Enter the Screen Name {Eg: OPR026) : ", "File Name", JOptionPane.QUESTION_MESSAGE);
                    if(Propertypath == null) Propertypath = "";

                    if(Propertypath.equalsIgnoreCase("")) {
                                    showMessage("Invalid Folder Path.. Exiting...", "Error", JOptionPane.ERROR_MESSAGE);
                    }else {
                    String temp=	Propertypath += "\\"+PropertyName+".properties";
                    Propertypath += "\\"+PropertyName+".properties";
                    input = getClass().getClassLoader().getResourceAsStream(temp);


                    }
                   

                    // load a properties file
                    prop.load(input);
    	} catch (IOException ex) {
    	    ex.printStackTrace();
    	} finally {
    	    if (input != null) {
    	        try {
    	            input.close();
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }
    	    }}
    	
                    String gettypes="Html.INPUT.text,Html.BUTTON,Html.SELECT,Html.TABLE,Html.INPUT.checkbox,Html.IMG";
                    String[] ty=gettypes.split(",");

       
                    System.out.println("Array Size "+ty.length);
                    for(int oInd=0; oInd<ty.length; oInd++)
                    { 


                                    System.out.println("Type-------"+ty[oInd]);
                                    String[] closeScreen = driver.findElements(By.class(ty[oInd]));
                                    if(ty[oInd].equalsIgnoreCase("Html.INPUT.text")){
                                                    objType="Text";
                                    }else if(ty[oInd].equalsIgnoreCase("Html.BUTTON")){
                                                    objType="BUTTON";
                                    }else if(ty[oInd].equalsIgnoreCase("Html.SELECT")){
                                                    objType="List";
                                    }else if(ty[oInd].equalsIgnoreCase("Html.TABLE")){
                                                    objType="TABLE";           
                                    }else if(ty[oInd].equalsIgnoreCase("Html.INPUT.checkbox")){
                                                    objType="ChkBox";
                                    }else if(ty[oInd].equalsIgnoreCase("Html.IMG")){
                                                    objType="IMG";
                                    }else if(ty[oInd].equalsIgnoreCase("Html.A")){
                                                    objType="IMG";
                                    }
                                    for(int i=0;i<closeScreen.length;i++)
                                    {
                                                    String desName=closeScreen[i].
                                                    String DOTid,DOTclass,DOTclassIndex,DOTtype,DOTvalue,DOTname,DOTtext,DOTtag;
                                                    
                                                    String temp="";
                                                    try{
                                                                    DOTid=closeScreen[i].getProperty(".id").toString();
                                                                    if(!DOTid.equalsIgnoreCase(""))
                                                                    {
                                                                                    temp += ".id='"+DOTid+"'";
                                                                    }
                                                    }
                                                    catch(Exception e){
                                                                    DOTid="null";
                                                    }
                                                    try{
                                                                    DOTclass=closeScreen[i].getProperty(".class").toString();
                                                                    if(!DOTclass.equalsIgnoreCase(""))
                                                                    {
                                                                                    temp += ",.class='"+DOTclass+"'";
                                                                    }
                                                    }
                                                    catch(Exception e)
                                                    {
                                                                    DOTclass="null";
                                                    }
                                                    try{
                                                                    DOTclassIndex=closeScreen[i].getProperty(".classIndex").toString();
                                                                    if(!DOTclassIndex.equalsIgnoreCase(""))
                                                                    {
                                                                                    temp += ",.classIndex='"+DOTclassIndex+"'";
                                                                    }
                                                    }
                                                    catch(Exception e)
                                                    {
                                                                    DOTclassIndex="null";
                                                    }
                                                    try{
                                                                    DOTtype=closeScreen[i].getProperty(".type").toString();
                                                                    if(!DOTtype.equalsIgnoreCase(""))
                                                                    {
                                                                                    temp += ",.type='"+DOTtype+"'";
                                                                    }
                                                    }
                                                    catch(Exception e)
                                                    {
                                                                    DOTtype="null";
                                                    }
                                                    try{
                                                                    DOTvalue=closeScreen[i].getProperty(".value").toString();
                                                                    if(!DOTvalue.equalsIgnoreCase(""))
                                                                    {
                                                                                    temp += ",.value='"+DOTvalue+"'";
                                                                    }
                                                    }
                                                    catch(Exception e)
                                                    {
                                                                    DOTvalue="null";
                                                    }
                                                    try{
                                                                    DOTname=closeScreen[i].getProperty(".name").toString();
                                                                    if(!DOTname.equalsIgnoreCase(""))
                                                                    {
                                                                                    temp += ",.name='"+DOTname+"'";
                                                                    }
                                                    }
                                                    catch(Exception e)
                                                    {
                                                                    DOTname="null";
                                                    }
                                                    
                                                    try{
                                                                    DOTtag=closeScreen[i].getProperty(".tag").toString();
                                                                    if(!DOTtag.equalsIgnoreCase(""))
                                                                    {
                                                                                    temp += ",.tag='"+DOTtag+"'";
                                                                    }
                                                    }
                                                    catch(Exception e)
                                                    {
                                                                    DOTtag="null";
                                                    }              
                                                    if(temp.startsWith(","))
                                                    {
                                                                    temp=temp.substring(1, temp.length());
                                                    }
                                                    
                                                    excelColHeader.put( excelRowIndex,excelBookName+"_"+objType+"_"+desName);
                                                    objProp.put(excelRowIndex, temp);
                                                    excelRowIndex++;
                                                    
                                    }
                    }
                    
                  
    }

   
    public void showMessage(String messageToShow, String messageTitle, int messageType) {
                    JOptionPane.showMessageDialog(null, messageToShow, messageTitle, messageType);
    }              

   

}
*/