package com.test.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StringContainsNumber {
	
	public static void verifyNumberInString(String str)
	{
		char[] ch = str.toCharArray();
		int count=0;
		StringBuilder sb = new StringBuilder();
		for(char chr:ch)
		{
			if(Character.isDigit(chr)) {
			sb.append(chr);
			count++;
			}
		}
		System.out.println(sb);
		System.out.println("Total count is :"+count);
	}
	
	
	public static boolean usingArrayListAndStrings(String str)
	{
		String numbersInString ="1234567890";
		List<Character> numbers = new ArrayList<>();
		
		for(int i=0;i<numbersInString.length();i++)
		{
			numbers.add(numbersInString.charAt(i));
		}
		for(int i=0;i<str.length();i++)
		{
			if(!(numbers.contains(str.charAt(i))))
				return false;
		}
		
		return true;
	}
	
	public static void main(String[] args)
	{
		System.out.println("Enter the String with number: ");
		Scanner sc = new Scanner(System.in);
		verifyNumberInString(sc.nextLine());
		
	}

}
