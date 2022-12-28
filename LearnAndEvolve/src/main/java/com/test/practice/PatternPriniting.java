package com.test.practice;

public class PatternPriniting {
	
	
	public static void main(String[] args) {
		
//		rightTriangle();
//		rightTraingleDown();
		rightTiangle();
		
		
	}
	
	
	
	/*
	 *   *
	 *   **
	 *   ***
	 *   ****
	 *   *****
	 *   ******
	 *   *******
	 *   
	 *   @type : Right - Triangle
	 *   @description : Print the above pattern
	 *   
	 *   
	 */
	public static void rightTriangle()
	{
		for(int i=1;i<=7;i++)
		{
			for(int j=1;j<=i;j++)
			{
				System.out.print("*"); //use System.out.PRINT not println()
				System.out.print(" "); //use System.out.PRINT not println()
			}
			//use Println() here as the next row should be generated.
			System.out.println();// Ends the line and starts the pointe to the next
		}	
		
	}
	/*
	 *   *******      
	 *   ******
	 *   *****
	 *   ****
	 *   ***
	 *   **
	 *   *
	 * 
	 * 	 @type : Right - Triangle
	 *   @description : Print the above pattern
	 * 
	 */
	public static void rightTraingleDown()
	{
		for(int i=7;i>0;i--)
		{
			for(int j=1;j<=i;j++)
			{
				System.out.print("*"); //use System.out.PRINT not println()
				System.out.print(" "); //use System.out.PRINT not println()
			}
			//use Println() here as the next row should be generated.
			System.out.println();// Ends the line and starts the pointe to the next
		}	
	}
	
	/*
	 *        *      
	 *       **
	 *      ***
	 *     ****
	 *    *****
	 *   ******
	 *  *******
	 * 
	 * 	@type : Right - Triangle
	 *  @description : Print the above pattern
	 * 
	 */
	public static void rightTiangle()
	{
System.out.println("Test");
	}
	
	

}
