package com.ibsplc.common.Exceptions;

/**
 * Created by a-7681 on 2/28/2018.
 */
public class PopUpLooping extends Exception {

    public String toString(){
        return("PopUpLooping Exception occured. The pop up is occuring even after 10 times of trying.");
    }
}
