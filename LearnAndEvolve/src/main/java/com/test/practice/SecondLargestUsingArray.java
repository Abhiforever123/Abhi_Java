package com.test.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SecondLargestUsingArray {
	
	public static int getSecondLargest(int a[], int total)
	{
		
		
		//************************************** Sorting the array using conventional for loop **********************
		int temp=0;
		
		for(int i=0;i<total;i++)
		{
			for(int j = i+1;j<total;j++)
			{
				if(a[i]>a[j])
				{
					temp= a[i];
					a[i]=a[j];
					a[j]= temp;
				}
				
			}
			
			System.out.println(a[i]);
			
		}
		
		//*********************************************** Sort End ****************************************************
			return a[total-2];

	}
		
	
	public static int getSecondLargestUsingSort(int a[], int total)
	{
		Arrays.sort(a);  
		return a[total-2]; 
	}
	
	
	public static int getSecondLargetsUsingCollections(Integer a[], int total)
	{
//		List<int[]> list=Arrays.asList(a);  
		
		List<Integer> list = Arrays.asList(a);
		Collections.sort(list);  
		return list.get(total-2);  
	}

	
public static void main(String[] args) {
	int a[]= {1,2,5,6,3,2};
	Integer m[]= {1,2,5,6,4,8};
	System.out.println("The Second Largest is: "+getSecondLargetsUsingCollections(m, m.length));
	
	System.out.println("Second Largest using Sort: "+getSecondLargest(a, a.length));

}
}
