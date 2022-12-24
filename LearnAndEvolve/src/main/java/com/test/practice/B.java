package com.test.practice;


public class B extends A{

	public static void main(String[] args)
	{
		
	
//		A a = new A();
//		a.test();
		B b = new B();
		b.test2();
		
	}
	public void test2()
	{
		A a = new A();
		//when an object is created new memory location is implemented
		//object creates an additional memory location
		a.test();
		// Parent reference is being called directly no additional memory is created
		test();
		// Same as above statement
		super.test();
//		System.out.println("Class B method");
		
		//Static method already overloaded in the parent class
		A.test("");
	}
	
	
}
