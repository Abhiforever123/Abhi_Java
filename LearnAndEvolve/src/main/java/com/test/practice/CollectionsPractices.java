package com.test.practice;

import java.util.ArrayList;
import java.util.Collections;

public class CollectionsPractices {

	
	
	public static void arrayListReverse()
	{
		 ArrayList<String> listOfInts = new ArrayList<>();
	        listOfInts.add("1");
	        listOfInts.add("2");
	        listOfInts.add("3");
	        listOfInts.add("4");
	        listOfInts.add("5");
	        
	        System.out.println("Before Reversing : " + listOfInts);
	        Collections.reverse(listOfInts);
	        System.out.println("After Reversing : " + listOfInts);
	       

	    }
	
	
	public static void main(String[] args) {
		arrayListReverse();
	}

}

