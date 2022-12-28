package com.ibsplc.generic;

import org.testng.asserts.SoftAssert;
/**
 * 
 * @author A-7605
 *
 */
public class Assertion {
	
	private static SoftAssert softAssert;
	/**
	 * Created by A-7605
	 * This method creates SoftAssert object
	 * @return
	 */
	public static SoftAssert createSoftAssert(){
		return new SoftAssert();
	}
	/**
	 * Created by A-7605
	 * This method sets SoftAssert object value
	 * @param softAssertion
	 */
	public static void setSoftAssert(SoftAssert softAssertion){
		softAssert = softAssertion;
	}
	/**
	 * Created by A-7605
	 * This method returns SoftAssert object
	 * @return
	 */
	public static SoftAssert softAssert(){
		return softAssert;
	}
}
