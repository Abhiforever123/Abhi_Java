package com.ibsplc.test;

import org.testng.annotations.Test;

import com.ibsplc.common.BaseSetup;
import com.ibsplc.generic.Generic;

public class ICargo_Test_BaseSample_Class extends BaseSetup {
	
	@Test(enabled = true)
	public void base_IBS_ICargo_01()
	{
		Generic gen = new Generic(driver,"browser","");
		gen.login();
		
	}

	@Test(enabled = false, dependsOnMethods = {"base_IBS_ICargo_01"}, dependsOnGroups = {""})
	public void issuePolicy()
	{
		
	}
	
	@Test(enabled = true)
	public void changePolicy()
	{}
	
	public void changePolicy(int a)
	{
		
	}
	
	@Test()
	public void cancelPolicy()
	{
		
	}
	
}
;