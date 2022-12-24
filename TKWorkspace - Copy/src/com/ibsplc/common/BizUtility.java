package com.ibsplc.common;

import com.ibsplc.common.enums.StockType;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Created by a-7868 on 12/12/2017.
 */

public class BizUtility {
    private final static Logger logger = Logger.getLogger(BizUtility.class);


    public static String createAWBNum(StockType stockType) {


        int awb_no;
        String str_awb;

        int mod_value = 7;

        String filepath = PropertyHandler.getPropValue("resources\\EnvSetup.properties", "testDataDirectory") + "stock.properties";

        //read stock ranges from stock.properties file
        String stk_from = PropertyHandler.getPropValue(filepath, stockType.name() + "_range_from");
        String stk_to = PropertyHandler.getPropValue(filepath, stockType.name() + "_range_to");
        String stk_next = PropertyHandler.getPropValue(filepath, stockType.name() + "_range_next");

        int stock_from = Integer.parseInt(stk_from);
        int stock_to = Integer.parseInt(stk_to);
        int stock_next = Integer.parseInt(stk_next);
        int mod;


        //set incremented value for stock_next
        if (stock_from <= stock_next && stock_next <= stock_to) {

            mod = stock_next % mod_value;
            awb_no = stock_next * 10;
            awb_no += mod;

            str_awb = String.valueOf(awb_no);
            int strlen = str_awb.length();


            //if there are any starting zeroes
            if (strlen < 8) {
                int digits_missing = 8 - strlen;
                for (int i = 1; i <= digits_missing; i++)
                    str_awb = "0" + str_awb;
            }

            awb_no = awb_no / 10;
            awb_no++;
            String str_awb_next = String.valueOf(awb_no);
            strlen = str_awb_next.length();


            //if there are any starting zeroes
            if (strlen < 7) {
                int digits_missing = 7 - strlen;
                for (int i = 1; i <= digits_missing; i++)
                    str_awb_next = "0" + str_awb_next;
            }

            PropertyHandler.setPropValue(filepath, stockType + "_range_next", str_awb_next.substring(0, 7));
            logger.info("AWB no. created = " + str_awb);

            return str_awb;
        } else {
            logger.error("Stock empty..!");
            return null;
        }

    }

    public static String createFlightNum() {

        int digit = 4;
        long value1 = 1;
        long value2 = 9;
        for (int i = 1; i < digit; i++) {
            value1 = value1 * 10;
            value2 = value2 * 10;
        }
        if (digit == 0) {
            System.out.println("Field value is 0; Enter valid number of digits");

        }
        Long randomlong = (long) (value1 + Math.random() * value2);
        String randStr = randomlong.toString();

        return randStr;
    }

    public static String createInvoiceNumber() {

        int digit = 8;
        long value1 = 1;
        long value2 = 9;
        for (int i = 1; i < digit; i++) {
            value1 = value1 * 10;
            value2 = value2 * 10;
        }
        if (digit == 0) {
            System.out.println("Field value is 0; Enter valid number of digits");

        }
        Long randomlong = (long) (value1 + Math.random() * value2);
        String randStr = randomlong.toString();

        return randStr;
    }

    public static String createConsignmentNumber_Mail() {

        int digit = 4;
        long value1 = 1;
        long value2 = 5;
        for (int i = 1; i < digit; i++) {
            value1 = value1 * 10;
            value2 = value2 * 10;
        }
        if (digit == 0) {
            System.out.println("Field value is 0; Enter valid number of digits");

        }
        Long randomlong = (long) (value1 + Math.random() * value2);
        String randStr = randomlong.toString();

        return randStr;
    }

    public static String Mail_rsn() {

        int digit = 3;
        long value1 = 1;
        long value2 = 4;
        for (int i = 1; i < digit; i++) {
            value1 = value1 * 10;
            value2 = value2 * 10;
        }
        if (digit == 0) {
            System.out.println("Field value is 0; Enter valid number of digits");

        }
        Long randomlong = (long) (value1 + Math.random() * value2);
        String randStr = randomlong.toString();

        return randStr;
    }

    public static String getInvoiceFromDate_CASS(String date, String billingPeriod) throws ParseException {

	    	 /*Calendar calendar = Calendar.getInstance();

	    	    int lastDate = calendar.getActualMaximum(Calendar.DATE);
	    	    int firstDate = calendar.getActualMinimum(Calendar.DATE);

	    	    SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyy");
	    	    Date date1 =dt.parse(lastDate)*/
        String tdate = null;
        Date today = new SimpleDateFormat("dd-MMM-yyyy").parse(date);


        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //    calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);


        Date lastDayOfMonth = calendar.getTime();

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Today            : " + sdf.format(today));
        System.out.println("Last Day of Month: " + sdf.format(lastDayOfMonth));

        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        // Date firstOfMonth = calendar.getTime();
        //  System.out.println("First Day of Month: " + sdf.format(firstOfMonth));
        Date invFromDate = (calendar.getTime());
        System.out.println("First Day of Month: " + sdf.format(invFromDate));

        if (billingPeriod.equals("BiMonthly")) {
            calendar.add(Calendar.DAY_OF_MONTH, 15);
            Date invfromdate2 = (calendar.getTime());
            System.out.println("invfromdate2: " + sdf.format(invfromdate2));
            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            try {
                Date date1 = df.parse(date);

                if (date1.before(invfromdate2)) {
                    Date actualfromdate = invFromDate;

                    tdate = dateFormat.format(actualfromdate);
                } else {
                    Date actualfromdate = invfromdate2;
                    tdate = dateFormat.format(actualfromdate);
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else {

            Date actualfromdate = invFromDate;

            tdate = dateFormat.format(actualfromdate);

        }
        return tdate;

    }


    public static String getInvoiceToDate(String date, String billingPeriod) {

	   	 /*Calendar calendar = Calendar.getInstance();

	   	    int lastDate = calendar.getActualMaximum(Calendar.DATE);
	   	    int firstDate = calendar.getActualMinimum(Calendar.DATE);

	   	    SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyy");
	   	    Date date1 =dt.parse(lastDate)*/
        String tdate = null;
        Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        Date lastDayOfMonth = calendar.getTime();

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Today            : " + sdf.format(today));
        System.out.println("Last Day of Month: " + sdf.format(lastDayOfMonth));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        Date invToDate = (calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        invToDate = (calendar.getTime());
        if (billingPeriod.equals("BiMonthly")) {
            //  calendar.add(Calendar.DAY_OF_MONTH, 15);
            // Date invfromdate2 = (calendar.getTime());
            sdf.format(lastDayOfMonth);
            Date invTodate2 = (calendar.getTime());
            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            try {
                Date date1 = df.parse(date);

                if (date1.before(invTodate2)) {
                    Date actualTodate = invToDate;

                    tdate = dateFormat.format(actualTodate);
                } else {
                    Date actualtodate = lastDayOfMonth;
                    tdate = dateFormat.format(actualtodate);
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else {
            Date actualfromdate = lastDayOfMonth;
            tdate = dateFormat.format(actualfromdate);
        }

        return tdate;

    }


    public static String getInvoiceFromDate(String date, String billingPeriod) {
/*
             Calendar calendar = Calendar.getInstance();

	    	    int lastDate = calendar.getActualMaximum(Calendar.DATE);
	    	    int firstDate = calendar.getActualMinimum(Calendar.DATE);

	    	    SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyy");
	    	    Date date1 =dt.parse(lastDate)*/
        String tdate = null;
        Date today = new Date();


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);


        Date lastDayOfMonth = calendar.getTime();

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Today            : " + sdf.format(today));
        System.out.println("Last Day of Month: " + sdf.format(lastDayOfMonth));

        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        // Date firstOfMonth = calendar.getTime();
        //  System.out.println("First Day of Month: " + sdf.format(firstOfMonth));
        Date invFromDate = (calendar.getTime());
        System.out.println("First Day of Month: " + sdf.format(invFromDate));

        if (billingPeriod.equals("BiMonthly")) {
            calendar.add(Calendar.DAY_OF_MONTH, 15);
            Date invfromdate2 = (calendar.getTime());
            System.out.println("invfromdate2: " + sdf.format(invfromdate2));
            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            try {
                Date date1 = df.parse(date);

                if (date1.before(invfromdate2)) {
                    Date actualfromdate = invFromDate;

                    tdate = dateFormat.format(actualfromdate);
                } else {
                    Date actualfromdate = invfromdate2;
                    tdate = dateFormat.format(actualfromdate);
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else {

            Date actualfromdate = invFromDate;

            tdate = dateFormat.format(actualfromdate);

        }
        return tdate;

    }


    public static String createULDNum(String ULDtype, String Compcode) {

        int digits = 5;
        long lowerLim = 1;

        for (int i = 0; i < digits - 1; i++) {
            lowerLim *= 10;
        }
        long upperLim = lowerLim * 10;

        long randomlong = ThreadLocalRandom.current().nextLong(lowerLim, upperLim);

	       /*String Noofdigits="5";
	       int digit =Integer.parseInt(Noofdigits);
	       long value1 = 1;
	       long value2 = 9;
	       
	       for (int i=1;i<digit;i++){
	    	   value1 = value1 *10;
	           value2 = value2 * 10;
	       }		
		
	       if(digit==0)
	              System.out.println( "Field value is 0; Enter valid number of digits");
	              
	       Long randomlong = (long)(value1 + Math.random()* value2);*/

        String ULDNo = ULDtype + String.valueOf(randomlong) + Compcode;

        return ULDNo;
    }

    public static String generateRandomAlphaNumericValue(int length) {

        String alphabets = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        int n = alphabets.length();
        String result = new String();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            result = result + alphabets.charAt(random.nextInt(n));
        }
        return result;
    }


    public static String generateRandomAlphabets(int length) {

        String alphabets = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        int n = alphabets.length();
        String result = new String();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            result = result + alphabets.charAt(random.nextInt(n));
        }
        return result;
    }

    /**
     * @return
     * @author A-8260	 *
     */

    public static String getClearancePeriod() {
        Date date = new Date();
        LocalDate todaydate = LocalDate.now();
        String firstDayofMonth = todaydate.withDayOfMonth(1).toString();
        todaydate = LocalDate.parse(firstDayofMonth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate convertedDate = LocalDate.parse(firstDayofMonth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        convertedDate = convertedDate.withDayOfMonth(
                convertedDate.getMonth().length(convertedDate.isLeapYear()));
        String str[] = convertedDate.toString().split("-");
        String lastDayofMonth = str[2];
        int lastDate = Integer.parseInt(lastDayofMonth);
        Calendar now = Calendar.getInstance();
        String year = String.valueOf(now.get(Calendar.YEAR)).substring(2);
        //String month=String.valueOf((now.get(Calendar.MONTH) + 1));
        String arr[] = firstDayofMonth.split("-");
        String month = arr[1];
        String presentDay = String.valueOf(now.get(Calendar.DATE));

        String clearance = "";

        //for(int i=1;i<=31;i++)
        for (int i = 1; i <= lastDate; i++) {

            if (i == Integer.parseInt(presentDay)) {
                if (i <= 7) {

                    clearance = year + month + "01";
                    break;
                } else if (i > 7 && i <= 14) {

                    clearance = year + month + "02";
                    break;
                } else if (i > 14 && i <= 21) {

                    clearance = year + month + "03";
                    break;
                } else

                    clearance = year + month + "04";
                break;

            }

        }
        System.out.println("clearance is " + clearance);
        return clearance;
    }

}


/*
	public static String stringsplitstore(String Prop,int splitCounter,String splitCond) {

		try{
			
			if(splitCond.equalsIgnoreCase("space"))
		
			{
				splitCond="\\s";
			}
			
		//	String hashMapName=fieldValue.split(",")[3];
			
			GuiTestObject tst = new GuiTestObject(fieldName);
			tst.waitForExistence();
			String fullTextValue = (String)tst.getProperty(Prop).toString();

			if(fullTextValue == null)
			{
				Driver.setLogMsg("error", "Object "+fieldName+" does not have text property");
				result = false;
			}
			else
			{
				String ActualVal=fullTextValue.split(splitCond)[splitCounter];
				String valuetostoreinhash=hashMapName+",'"+ActualVal+"'";
				String ExcelColName=fieldValue.split("key=")[1];
				storeGeneratedValue(ExcelColName,ActualVal);
				result=store_data_in_hashMap(action, onFailureCol,fieldName,
						valuetostoreinhash,componentName,testCaseId);
			}
			return result;
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
				return false;
		}		
		
	}
*/