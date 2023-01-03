package com.test.practice;

import java.util.HashMap;
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

	
	
	public static void numberOfOccurrenceOfEachCharacterUsingHashMap(String str)
	{
		
		HashMap<Character, Integer> charCountMap = new HashMap<>();
		
		char[] strArray = str.toCharArray();
		
		for (char c : strArray)
        {
            if(charCountMap.containsKey(c))
            {
                //If char 'c' is present in charCountMap, incrementing it's count by 1
  
                charCountMap.put(c, charCountMap.get(c)+1);
            }
            else
            {
                //If char 'c' is not present in charCountMap,
                //putting 'c' into charCountMap with 1 as it's value
  
                charCountMap.put(c, 1);
            }
        }
		
	}
}
