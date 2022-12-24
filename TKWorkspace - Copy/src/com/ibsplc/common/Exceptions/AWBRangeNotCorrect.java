package com.ibsplc.common.Exceptions;

/**
 * Created by a-7681 on 2/28/2018.
 */
public class AWBRangeNotCorrect extends Exception {

    public String toString(){
        return("AWBRangeNotCorrect Exception occured. Please correct the AWB stock range.");
    }
}
