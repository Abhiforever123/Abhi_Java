package com.test.practice;

import java.util.Scanner;

public class NumberOfOccurrance {
	
	public static void numberOfOccurranceofCharacter(String a)
	{
		String str = "Java programming java oops as it is";
		int totalCount= str.length();
		int totalCountWithoutCharacher=str.replaceAll(a, "").length();
		System.out.println("total number of occurance : "+ (totalCount-totalCountWithoutCharacher));
	}
	
	
	public static void numofOccuranceUsingString(String str, char m)
	{
		int count=0;
		//char[] a = str.toCharArray();
		for(int i=0;i<str.length();i++)
		{
			if(str.charAt(i)==m)
			{
				count++;
			}
		}
		
		System.out.println("total count is: "+count);
		
	}
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		try {
		System.out.println("Enter the character to check: ");
		String requiredCharacter = scanner.nextLine();
		numberOfOccurranceofCharacter("a");
		numofOccuranceUsingString(requiredCharacter,'a');
		}
		finally {
			scanner.close();
		}
	}

}
