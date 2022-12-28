package com.ibsplc.common;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader; 
import java.io.FileWriter;
import java.io.IOException; 
import java.util.ArrayList; 
import java.util.List; 
public class CSVRead {
	static final String SEP_DELIMITER = "="; 

    private static final String NEW_LINE_SEPARATOR = "\n"; 


    public static void writeCsvFile(String filename, String key) { 

    	String line = null;
    
        String cvsSplitBy = ",";

       FileReader fileReader = null; 
       BufferedReader bufferedReader = null; 
        try { 
        	fileReader = new FileReader(filename); 
        bufferedReader = 
                    new BufferedReader(fileReader);
        	 while ((line = bufferedReader.readLine()) != null) {

                 // use comma as separator
                 String[] values = line.split(cvsSplitBy);

                 System.out.println("property [id= " + values[4] + " , name=" + values[5] + "]");


        	 }

 
            System.out.println("CSV file was created successfully !!!"); 
    }  catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (bufferedReader != null) {
            try {
            	bufferedReader.close();
             } catch (IOException e) {
                e.printStackTrace();
             }
        }
    }
}
}