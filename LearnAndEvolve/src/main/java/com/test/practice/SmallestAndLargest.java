package com.test.practice;

import java.util.Arrays;

public class SmallestAndLargest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		getSmallestAndLargestNumberInAnArray();

	}

	
	public static void getSmallestAndLargestNumberInAnArray()
	{
		int numbers[] = {-100,-25,88,75,62,74589};
		System.out.println("Evaluated numbers are: "+Arrays.toString(numbers));
		
		int largest=numbers[0];
		int smallest=numbers[0];
		
		for(int i=1;i<numbers.length;i++) {
			if(numbers[i]>largest)
				largest=numbers[i];
			else
				if(numbers[i]<smallest)
					smallest=numbers[i];
		}
		
		
		System.out.println("The smallest number is : "+smallest);
		System.out.println("The largest number is: "+largest);
	
	
	}
	
	
}
