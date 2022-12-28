package com.ibsplc.generic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.common.AssertListener;

/**
 * Class to handle the assertion list
 * @author A-7868
 *
 */
public class AssertionList {

	private static List<String> assertionList = new ArrayList<>();
	
	/**
	 * Add assertion to list
	 * @param str
	 * @author A-7868
	 */
	public static void addToList(String str){

		assertionList.add(str);
		
	}
	
	/**
	 * returns the assertion list
	 * @return
	 * @author A-7868
	 */
	public static List<String> getAssertionList(){
		
		return assertionList;
	}
	
	/**
	 * Clear the list content
	 * @author A-7868
	 */
	public static void clearList(){
		
		assertionList.clear();
		
	}
}
