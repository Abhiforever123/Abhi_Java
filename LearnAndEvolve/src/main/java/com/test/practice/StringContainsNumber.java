package com.test.practice;

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
	
	public static void main(String[] args)
	{
		System.out.println("Enter the String with number: ");
		Scanner sc = new Scanner(System.in);
		verifyNumberInString(sc.nextLine());
		
	}

}
