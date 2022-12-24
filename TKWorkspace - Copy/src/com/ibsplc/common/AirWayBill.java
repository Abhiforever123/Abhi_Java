package com.ibsplc.common;

import com.ibsplc.common.PropertyHandler;


/**
 * Created by a-7868 on 12/12/2017.
 */

public class AirWayBill {


    public static String createAWB(String stockType) {

        int awb_no;
        String str_awb;
        int mod_value = 7;

        String filepath = PropertyHandler.getPropValue("resources\\EnvSetup.properties","testDataDirectory") +  "stock.properties";
        //read stock ranges from stock.properties file
        String stk_from = PropertyHandler.getPropValue(filepath, stockType + "_range_from");
        String stk_to = PropertyHandler.getPropValue(filepath, stockType + "_range_to");
        String stk_next = PropertyHandler.getPropValue(filepath, stockType + "_range_next");

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

//			System.out.println("AWB no. created = "+str_awb);
            return str_awb;
        } else {
            System.out.println("Stock empty..!");
            return null;
        }

    }

}
