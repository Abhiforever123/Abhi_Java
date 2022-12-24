package com.test.practice;

import java.util.Scanner;

public class Palindrome {
	
	public static void main(String[] args) {
		
		
//		System.out.println("Enter the number");
		System.out.println("Enter the string: ");
		Scanner sc = new Scanner(System.in);
//		checkPalindrome(sc.nextInt());
		checkPalindromeString(sc.nextLine());
		sc.close();
	}
	
	
	public static void checkPalindrome(int num)
	{
		int temp = num;
		int rev = 0;
		while(num!=0)
		{
			int mod = num%10;
			rev = rev*10+mod;
			num = num/10;
								
			
		}
		if(rev==temp)
			System.out.println("The number is palindrome");
		else
			System.out.println("The number is not palindrome");
	}
	
	public static void checkPalindromeString(String str)
	{
		String rev ="";
		
		for(int i=str.length()-1;i>=0;i--)
		{
			rev= rev+str.charAt(i);
			
		}
		System.out.println("reversed string: "+rev);
		if(rev.equalsIgnoreCase(str))
			System.out.println("String is palindrome");
		else
			System.out.println("String is not palindrome");
	}
	
	
}
