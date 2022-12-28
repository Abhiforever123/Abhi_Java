package com.test.practice;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Set_Examples {
	
	public static void main(String[] args) {
		
		hashSet();
		
		System.out.println("*************************");
		
		linkedHashSet();
		
		System.out.println("*************************");
		
		treeSet();

	}
	
	/**
	 * 		Key				Value
	 * 		----			-------
	 * 		ONE				PRESENT
	 * 		TWO				PRESENT
	 * 		THREE			PRESENT
	 * 		FOUR			PRESENT
	 * 		FIVE			PRESENT
	 * 		SIX				PRESENT
	 * 		SEVEN			PRESENT	
	 */
	public static void hashSet()
	{
		Set<String> hashSetExample = new HashSet<String>();
		
		hashSetExample.add("ONE");
		hashSetExample.add("TWO");
		hashSetExample.add("THREE");
		hashSetExample.add("FOUR");
		hashSetExample.add("FIVE");
		hashSetExample.add("SIX");
		hashSetExample.add("SEVEN");
		//Duplicate value will not be printed
		hashSetExample.add("ONE");
		
		Iterator<String> itr = hashSetExample.iterator();
		
		while(itr.hasNext())
		{
			System.out.println(itr.next());
		}
	}
	/**
	 * 	HeadValue of prev node   	  HashCode		Key		 	Value			Next node Address		TailValue of next node
	 * --------------------------     --------      ----       -------         ------                   -------------------------
	 * 	   100		                     567        ONE        PRESENT          Next					101
	 */
	public static void linkedHashSet()
	{
		Set<String> linkedHashSetExample = new HashSet<String>();
		
		linkedHashSetExample.add("ONE");
		linkedHashSetExample.add("TWO");
		linkedHashSetExample.add("THREE");
		linkedHashSetExample.add("FOUR");
		linkedHashSetExample.add("FIVE");
		linkedHashSetExample.add("SIX");
		linkedHashSetExample.add("SEVEN");
		//Duplicate value will not be printed
		linkedHashSetExample.add("ONE");
		
		Iterator<String> itr = linkedHashSetExample.iterator();
		
		while(itr.hasNext())
		{
			System.out.println(itr.next());
		}
	}
	
	//Sorted in Alphabetical order not numerical. Don't get confused we are passing String.
	public static void treeSet()
	{
		Set<String> treeSetExample = new TreeSet();
		
		treeSetExample.add("ONE");
		treeSetExample.add("TWO");
		treeSetExample.add("THREE");
		treeSetExample.add("FOUR");
		treeSetExample.add("FIVE");
		treeSetExample.add("SIX");
		treeSetExample.add("SEVEN");
		//Duplicate value will not be printed
		treeSetExample.add("ONE");
		
		Iterator<String> itr = treeSetExample.iterator();
		
		while(itr.hasNext())
		{
			System.out.println(itr.next());
		}
	}
	
	
}
