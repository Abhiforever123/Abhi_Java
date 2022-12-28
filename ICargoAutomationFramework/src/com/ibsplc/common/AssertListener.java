package com.ibsplc.common;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.asserts.IAssertLifecycle;

import com.ibsplc.generic.AssertionList;

public class AssertListener extends Assertion implements IAssertLifecycle {

	public static final Logger logger = Logger.getLogger(AssertListener.class);
	
	@Override
	public void executeAssert(IAssert arg0) {
		// TODO Auto-generated method stub
		arg0.doAssert();
	}

	@Override
	public void onAfterAssert(IAssert arg0) {
		// TODO Auto-generated method stub
	}

	public void onAssertFailure(IAssert arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAssertFailure(IAssert arg0, AssertionError arg1) { 
		// TODO Auto-generated method stub
		AssertionList.addToList("<tr><td>"+arg0.getMessage() + 
				"</td><td><font color=red><center> FAIL</center></font></td></tr>");

		logger.info("ASSERTION ["+arg0.getMessage()+"] FAILED");
	}

	@Override
	public void onAssertSuccess(IAssert arg0) {
		// TODO Auto-generated method stub
		AssertionList.addToList("<tr><td>"+arg0.getMessage() + 
				"</td><td><font color=green><center> PASS</center></font></td></tr>");
		
		logger.info("ASSERTION ["+arg0.getMessage()+"] PASSED");
	}

	@Override
	public void onBeforeAssert(IAssert arg0) {
		// TODO Auto-generated method stub
	}

}
