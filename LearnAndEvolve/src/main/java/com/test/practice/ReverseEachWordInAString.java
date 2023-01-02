package com.test.practice;

import java.util.Scanner;

public class ReverseEachWordInAString {
	
	
	public static void usingTraditionalMethod(String sen)
	{
		String[] words = sen.split(" ");
		String reverseString="";
		
		for(String w: words)
		{
			String reversedWord="";
			for(int i=w.length()-1; i>=0; i--)
			{
				reversedWord=reversedWord+w.charAt(i);
				
			}
			reverseString=reverseString+reversedWord+" ";
		}
		System.out.println("Reversed words in String :"+reverseString);
	}

	
	public static void main(String[] args) {
		System.out.println("Enter the string to reverse every word");
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		usingTraditionalMethod(str);
	}
	
	public static int reverseNumber(int num)
	{
		int temp = num;
		int rev=0;
		while(num!=0)
		{
			rev= num%10;
			
			rev= rev*10+num;
			
			num = num/10;
		}
		
		return rev;
	}
}
