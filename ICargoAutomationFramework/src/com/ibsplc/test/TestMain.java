package com.ibsplc.test;

import com.ibsplc.common.Constants;
import com.ibsplc.common.ExcelOps;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
			String fileName= Constants.testDataDir+"\\SampleTest.xlsx";
			
			System.out.println(ExcelOps.getCellData(fileName,"Sheet1", 2, 2));

	}

}
