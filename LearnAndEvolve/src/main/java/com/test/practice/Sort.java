package com.test.practice;

import java.util.Arrays;
import java.util.Scanner;

public class Sort {

	
	public static void main(String[] args) {
		
		System.out.println("Enter the String to get sorted");
		Scanner sc = new Scanner(System.in);
		sortCharactersInAlphabeticalOrder(sc.nextLine());
		sc.close();
		
	}
	
	
	public static void sortCharactersInAlphabeticalOrder(String str)
	{
		char a[] = str.toCharArray(); //convert the string to character array
		
		
		//Arrays.sort(a); // ------>using this single line of code we can achieve the below functionality
		char temp;
		
		for(int i=0;i<a.length;i++)
		{
			for(int j=i+1;j<a.length;j++)
			{
				if(a[i]>a[j])
				{
					temp= a[i];
					a[i]=a[j];
					a[j]=temp;
				}	
			}			
		}
		
		System.out.println("Elements in Sorted order: "+new String(a)); // converts Character array to a String format
	}
}
