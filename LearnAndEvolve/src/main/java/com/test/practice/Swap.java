package com.test.practice;

import java.util.Scanner;

public class Swap {
	
	
	public static void withoutThirdVariable(String str1, String str2) 
	{
		System.out.println("String str1 is:  "+str1+" and str2 is: "+str2+" before swapping.");
		str1= str1+str2;
		str2= str1.substring(0, (str1.length()-str2.length()));
		str1=str1.substring(str2.length());
		
		System.out.println("Swapped result of str1 is: "+str1+" and str2 is: "+str2);
		
	}
	
	public static void numberSwapWithTemp(int a, int b)
	{
		System.out.println("Enter the numbers to swap in order a followed by b");
		int temp=0;
		temp=a;
		a=b;
		b=temp;
		
		System.out.println("The numbers after swapping: a: "+a+" and b: "+b);
	}

	
	public static void numberSwapWithOutTemp(int a, int b)
	{
		System.out.println("Enter the numbers to swap in order a followed by b");
		a= a+b;
		b=a-b;
		a=a-b;
		
	}
	
	public static void numberSwapWithOutTemp2(int a, int b)
	{
		System.out.println("Enter the numbers to swap in order a followed by b");
		a= a*b;
		b=a/b;
		a=a/b;
	}
	
	public static void numberSwapWithExOR(int a, int b)
	{
		System.out.println("Enter the numbers to swap in order a followed by b");
		a=a^b;
		b=a^b;
		a=a^b;
	}
	
	public static void numberSwapOneLinerCode(int a, int b)
	{
		System.out.println("Enter the numbers to swap in order a followed by b");
		b=a+b-(a=b);
	}
	public static void main(String[] args) {
		System.out.println("enter the two strings required to swap");
		
		Scanner sc = new Scanner(System.in);
		String str1= sc.nextLine();
		String str2= sc.nextLine();
		withoutThirdVariable(str1, str2);
		sc.close();
		
		Scanner sc1 = new Scanner(System.in);
		int a= sc1.nextInt();
		int b= sc1.nextInt();
		numberSwapWithTemp(a,b);
		sc1.close();
	}
}


