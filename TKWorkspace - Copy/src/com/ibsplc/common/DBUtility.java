package com.ibsplc.common;

import org.apache.log4j.Logger;

import java.sql.*;

public class DBUtility {
	
	static Connection con;
	private  static Logger logger = Logger.getLogger(DBUtility.class);
	
	public static void connectToDB() {

		try{
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url="jdbc:oracle:thin:@192.168.45.3:1521:PRFICODB01";
            String username="AUT_IVV_PORTAL";
            String password="AUT_IVV_PORTAL";
            
            con = DriverManager.getConnection(url, username, password);
		}
		catch(Exception e){
			logger.info(""+e);
		}
		
	}
	
	public static void connectToDB(String url, String username, String password) {

		try{
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			            
            con = DriverManager.getConnection(url, username, password);
		}
		catch(Exception e){
			logger.info("Connection Failed.............."+e);
		}
		
	}
	
	public static void executeUpdateStmnt(String stmnt){
		
		try{
		Statement query = con.createStatement();
		
        query.executeUpdate(stmnt);
        
        logger.info("Insert Done......................\n\n\n\n");
		}
		catch(Exception e){
			logger.info("Execute Query failed............."+e);
		}
	}
	
	public static ResultSet executeQury(String stmnt) throws SQLException{
		
		Statement query = con.createStatement();
		
        ResultSet rset = query.executeQuery(stmnt);
        
        logger.info("Got Result......................\n\n\n\n");
        
        return rset;
		
	}
	
	/*public static void executeStmnt(String stmnt){
		
		try{
		PreparedStatement  query = con.prepareStatement(stmnt);
		
        query.executeUpdate(stmnt);
        
        logger.info("Insert Done......................\n\n\n\n");
		}
		catch(Exception e){
			logger.info(""+e);
		}
	}*/
	
	
	public static void closeDB(){
		try{
			con.close();
		}
		catch(Exception e){
			logger.info("CloseDB failed"+e);
		}
	}

}
