package com.ibsplc.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ibsplc.generic.Message;

/**
 * Created by a-7681 on 9/19/2017.
 */
public class PropertyHandler {
	private final static Logger logger = Logger.getLogger(PropertyHandler.class);
    public static String getPropValue(String filename, String key){

        InputStream is = null;
        String value = null;

        Properties prop = new Properties();

        try {
            is = new FileInputStream(filename);
            prop.load(is);
            value = prop.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error: The file path is '"+filename+"'");
        } finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("Error: The file path is '"+filename+"'");
                }
            }
        }

        return value;
    }
    
    
    public static String setPropValue(String filename, String key, String value){
    	
    	
    	Properties prop = new OrderedProperties();
    	OutputStream output = null;
    	InputStream input = null;
    	
    	try{
    		input = new FileInputStream(filename);
            prop.load(input);
            
    	}catch (IOException io) {
    		io.printStackTrace();
    	} finally {
    		if (input != null) {
    			try {    				
    				input.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	
    	try {
    		output = new FileOutputStream(filename);    		
    		prop.setProperty(key,value);    		
    		prop.store(output, null);

    	} catch (IOException io) {
    		io.printStackTrace();
    	} finally {
    		if (output != null) {
    			try {    				
    				output.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	
    	return value;
    	}
   }

    
