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
		
		sb.delete(0,1); // Deletes the K inserted in KAbhi to Abhi delete starts from index 0 to position index 1	
		System.out.println(sb);
		
		
		StringBuilder sb2 = new StringBuilder("h");
		sb2.append("e");
		sb2.append("llo");
		System.out.println(sb2);
		
		reverseString("Abhil");
	}
	
	
	
	public static void reverseString(String str)
	{
		StringBuilder sb = new StringBuilder(str);
		
		for(int i=0;i<sb.length()/2;i++)
		{
			int firstCharIndex=i;
			
			int lastCharIndex = sb.length()-1-i;
			
			char firstChar= sb.charAt(firstCharIndex);
			
			char lastChar= sb.charAt(lastCharIndex);
			
			sb.setCharAt(firstCharIndex, lastChar);
			sb.setCharAt(lastCharIndex, firstChar);
			
		}
		
		System.out.println("Reversed string is: "+sb);
	}

}
