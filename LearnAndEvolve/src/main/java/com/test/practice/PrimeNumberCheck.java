package com.test.practice;

import java.util.Scanner;

public class PrimeNumberCheck {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Enter the number to check");
		Scanner sc = new Scanner(System.in);
		checkPrimeNumber(sc.nextInt());
		sc.close();
	}
	
	
	public static void checkPrimeNumber(int num)
	{
		int count=0;
		for(int i=1;i<=num;i++)
		{
			if(num%i==0)
				count++;
			
		}
		if(count==2)
			System.out.println("The number is prime");
		else
			System.out.println("The number is not Prime");
		
	}

}
