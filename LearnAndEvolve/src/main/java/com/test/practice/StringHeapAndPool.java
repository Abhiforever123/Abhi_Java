package com.test.practice;

public class StringHeapAndPool {

	
	public static void main(String[] args) {
		
		//String created in Pool memory
		String a ="Abhilash";
		String b= "Abhilash";
		
		//String created in Heap memory
		String A= new String("Abhilash");
		String B= new String("Abhilash");
		
		//*************************************************************************************
		
		if(a==b)
			System.out.println("String stored in Pool and the pointed to the same memory location");
		
		
		//**************************************************************************************
		
		if(a.equals(b))
			System.out.println("Also the same because the same String in same pool only pointed to different variable");
		
		//**************************************************************************************
		
		if(A==B)
			System.out.println("False");
		
		//***************************************************************************************
		
		else if(A.equals(B))
			System.out.println("String stored in Heap memory hence pointed to a new location");
		else
			System.out.println("Strings are different");
	}
}
