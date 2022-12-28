package com.test.practice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Map_Examples {

	
	public static void main(String[] args) {
		
		
		hashMap();
		System.out.println("**************************");
		linkedHashMap();
		System.out.println("**************************");
		treeMap();
	}
	
	
	public static void hashMap()
	{
		Map<Integer, String> hashMap	= new HashMap<Integer, String>();
		
		hashMap.put(100, "ABHI");
		hashMap.put(101, "ABC");
		hashMap.put(102, "XYZ");
		hashMap.put(102, "WXYZ");
		
		System.out.println(hashMap);
		
	}
	
	public static void linkedHashMap()
	{
		Map<Integer, String> linkedHashMap	= new LinkedHashMap<Integer, String>();
		
		linkedHashMap.put(102, "XYZ");
		linkedHashMap.put(100, "ABHI");
		linkedHashMap.put(101, "ABC");
	
		linkedHashMap.put(102, "WXYZ");
		System.out.println(linkedHashMap);
	}
	
	public static void treeMap()
	{
		Map<Integer, String> treeMap	= new TreeMap<Integer, String>();
		
		treeMap.put(101, "ABC");
		treeMap.put(100, "ABHI");		
		treeMap.put(102, "XYZ");
		treeMap.put(102, "WXYZ");
		System.out.println(treeMap);
	}
	
	
	
}
