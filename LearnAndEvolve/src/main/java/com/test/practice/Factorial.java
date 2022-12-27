package com.test.practice;

import java.util.Scanner;

public class Factorial {

	public static int getFactorial(int num)
	{
		int fact=1;
		for(int i=1;i<=num;i++)
		{
			fact=fact*i;
		}
		return fact;
	}
	
	public static int getFactorialUsingRecursion(int num)
	{
		if(num==0)
			return 1;
		else
			return(num * getFactorialUsingRecursion(num-1));
	}
	
	
	public static void main(String[] args) {
		
		System.out.println("Enter the number: ");
		Scanner sc = new Scanner(System.in);
		System.out.println("The factorial is : "+getFactorial(sc.nextInt()));
		sc.close();
	}
}
