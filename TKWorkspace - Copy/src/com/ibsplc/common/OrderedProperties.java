package com.ibsplc.common;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
/**
 * 
 * @author A-7605
 *
 */
@SuppressWarnings("serial")
public class OrderedProperties extends Properties{

	/**
	 * Created by A-7605 on 17-5-18.
	 * Override the keys method in Property class to write the properties in sorted order.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Enumeration keys(){
		Enumeration keysEnum = super.keys();
	     Vector<String> keyList = new Vector<String>();
	     while(keysEnum.hasMoreElements()){
	       keyList.add((String)keysEnum.nextElement());
	     }
	     Collections.sort(keyList, String.CASE_INSENSITIVE_ORDER);
	     return keyList.elements();
	}
}
