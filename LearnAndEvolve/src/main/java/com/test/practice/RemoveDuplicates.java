package com.test.practice;

import java.util.LinkedHashSet;
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
