package com.test.practice;

import java.util.Scanner;

public class AmstrongNumber {
	
	public static void main(String[] args) {
		
		System.out.println("Enter the number to check");
		
		Scanner sc = new Scanner(System.in);
		checkAmstrongNumber(sc.nextInt());
		sc.close();
	}
	
	public static void checkAmstrongNumber(int num)
	{
		int temp = num;
		int mod=0;
		int cube=0;
		while(num!=0)
		{
			mod= num%10;
			cube=cube+(mod*mod*mod);	
			num=num/10;
		}
		if(cube==temp)
		System.out.println("The Number is Amstrong");
		else
		System.out.println("The Number is not Amstrong");
	}
	

}
