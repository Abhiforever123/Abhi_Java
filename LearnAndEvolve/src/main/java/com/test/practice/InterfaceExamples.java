package com.test.practice;

public interface InterfaceExamples {
	
	
	//Only method declaration or prototype is allowed in interface
	public void method1();
	public void method2(); 
	
	//Java 8 allows static method definition inside interface since It can be accessed without instantiation
	
	public static void method3()
	{
		System.out.println("This is a Static Method");
	}
	
	

}
