package com.ibsplc.common;
import java.io.FileWriter; 

import java.io.IOException; 

import java.util.ArrayList; 

import java.util.List; 
public class CSVWrite {

 static final String SEP_DELIMITER = "="; 

    private static final String NEW_LINE_SEPARATOR = "\n"; 


    public static void writeCsvFile(String filename, String key, String value) { 

 
       FileWriter fileWriter = null; 
 
        try { 
         fileWriter = new FileWriter(filename); 
     
        fileWriter.append(NEW_LINE_SEPARATOR); 
        fileWriter.append(key);
        fileWriter.append(SEP_DELIMITER); 
        	 fileWriter.append(value);
           
       
        //        fileWriter.append(COMMA_DELIMITER); 
     
       //    fileWriter.append(NEW_LINE_SEPARATOR); 

       

            System.out.println("CSV file was created successfully !!!"); 
    } catch (Exception e) { 

            System.out.println("Error in CsvFileWriter !!!"); 
        e.printStackTrace(); 

        } finally { 

            try { 

             fileWriter.flush(); 
 
                fileWriter.close(); 
 
            } catch (IOException e) { 

                System.out.println("Error while flushing/closing fileWriter !!!"); 




                e.printStackTrace(); 




            } 
      } 

    } 

} 


