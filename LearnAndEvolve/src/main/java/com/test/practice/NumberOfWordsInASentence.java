package com.test.practice;

import java.util.Scanner;

public class NumberOfWordsInASentence {
	/**
	 * @author F.R.I.D.A.Y
	 * @desc counts the number of words used in a sentence
	 * @param str
	 * @return
	 */
	public static int wordsInASentence(String str)
	{
		int count =1;
		for(int i=0;i<str.length()-1;i++)
		{
			if(str.charAt(i)==' ' && !(str.charAt(i+1)==' '))
					count++;
					
		}
		return count;
	}

	public static void main(String[] args) {
		System.out.println("Enter the sentence");
		Scanner scanner = new Scanner(System.in);
		String str = scanner.nextLine();
		System.out.println("The number of words in the sentence is : "+ wordsInASentence(str));
	}
	
}
