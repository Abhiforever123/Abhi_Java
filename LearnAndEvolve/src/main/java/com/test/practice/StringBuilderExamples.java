package com.test.practice;

public class StringBuilderExamples {
	
	
	public static void main(String[] args) {
		
		
		StringBuilder sb = new StringBuilder("Abhi");
		
		
		System.out.println(sb.charAt(0)); // Character A from Abhi is printed
		
		sb.setCharAt(0, 'E');
		System.out.println(sb);// A is replaced in Abhi with E as Ebhi
		
		sb.setCharAt(0, 'A');
		System.out.println(sb);//
		
		// insert a characted into string at a given index
		
		sb.insert(0, 'K'); //inserts K into the first index position of the String returns KAbhi
		System.out.println(sb);
		
	}

}
