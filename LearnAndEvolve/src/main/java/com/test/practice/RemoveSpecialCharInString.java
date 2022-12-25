package com.test.practice;

import java.util.Scanner;

public class RemoveSpecialCharInString {
	
	
	public static void main(String[] args) {
		
		
		System.out.println("Please enter the String with Special characters");
		
		Scanner sc = new Scanner(System.in);
		removeSpecialChar(sc.nextLine());
		
		sc.close();
	}
	
	
	public static void removeSpecialChar(String str)
	{
		int count =0;
		String specialCharacters="";
		String removedString="";
		for(int i=0;i<str.length();i++)
		{
			if(!(Character.isDigit(str.charAt(i))) && !(Character.isLetter(str.charAt(i))) && !(Character.isWhitespace(str.charAt(i))))
			{
				count++;
				specialCharacters=specialCharacters+str.charAt(i);
			}
			else
			{
				removedString=removedString+str.charAt(i);
			}
		}
		
		System.out.println("The total number of Special Chars are: " +count);
		
		System.out.println("Special characters are: "+specialCharacters);
		
		System.out.println("Removed Special Character string is: "+removedString);
	}

}
