package com.test.practice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class RemoveDuplicates {

	public static void removeDuplicateUsingJava8Streams(String str)
	{
		StringBuilder sb = new StringBuilder();
		str.chars().distinct().forEach(c -> sb.append((char)c));
		System.out.println(sb);
	}
	
	public static void usingStringIndex(String str)
	{
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<str.length();i++)
		{
			char ch = str.charAt(i);
			int idx = str.indexOf(ch,i+1); //checks the index position of the string from the next position the loop iteration.
			
			if(idx== -1)
				sb.append(ch);
		}
		System.out.println(sb);
	}
	
	public static void usingCharacterArray(String str)
	{
		char[] arr = str.toCharArray();
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<arr.length;i++)
		{
			boolean repeated = false;
			for(int j=i+1;j<arr.length;j++)
			{
				if(arr[i]==arr[j])
				{
					repeated = true;
					break;
				}
				if(!repeated)
				{
					sb.append(arr[i]);
				}
			}
			
			System.out.println(sb);
		}
	}
	
	
	public static void usingSetInterface(String str)
	{
		StringBuilder sb = new StringBuilder();
		Set<Character> set = new LinkedHashSet<>();
		for(int i=0; i< str.length(); i++)
		{
			set.add(str.charAt(i));
		}
		
		for(Character c: set)
		{
			sb.append(c); 
		
		}
		
		System.out.println(sb);
	}
	
	
	public static void findDuplicatesStringArray(String[] str)
	{
		for(int i=0;i<str.length;i++)
		{
			for(int j=i+1;j<str.length;j++)
			{
				if(str[i].equals(str[j]))
					System.out.println("Duplicate elements are: "+str[i]);
			}
		}
	}
	
	public static void findDuplicateUsingSet(String val)
	{
		String[] str=val.split(" ");
		Set<String> store = new HashSet<String>();
		for(String values: str)
		{
			if(store.add(values)==false)
				System.out.println("Duplicate entry is: "+str);
		}
		
	}
	
	public static void findDuplicateUsingMap(String val)
	{
		String[] str=val.split(" ");
		Integer count =0;
		Map<String,Integer> storeMap = new HashMap<String,Integer>();
		for(String values:str)
		{
			count = storeMap.get(values);
			if(count==null)
				System.out.println("The String doesn't have any duplicates as its occurrance is: "+storeMap.put(values, 1));
			else
				System.out.println("The String occurs with : "+storeMap.put(val, count++)+" times");
		}
		
		//printing these values using an entryset
		
		Set<Entry<String,Integer>> entrySet = storeMap.entrySet();
		for(Entry<String,Integer> entry: entrySet)
		{
			if(entry.getValue()>1)
				System.out.println("The String is duplicate: "+entry.getKey());
		}
		
		
	}
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Enter the String to check for the duplicates");
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		removeDuplicateUsingJava8Streams(str);
		usingStringIndex(str);
		usingCharacterArray(str);
		sc.close();
		
	}
}
