package com.test.practice;

public class StringManipulation {
	
	public static void main(String[] args) {
		String str ="This is a String to test functionality";
		
		System.out.println("Lenght of the String is: "+str.length()); // Prints the length of the String using length() method
		
		System.out.println("The fifth Index from the sentense: "+str.charAt(5)); // Finds the Nth index using charAt() method
		
		System.out.println("The First Index of the character in the string "+str.indexOf('s')); //Identifies the first index of
		
		
		System.out.println("Find the Nth occurance of the character from a given index: "+str.indexOf('s', str.indexOf('s')+1));// the second occurance of the character s in the string 
	}

}
