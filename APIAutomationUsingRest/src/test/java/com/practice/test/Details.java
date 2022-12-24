package com.practice.test;

public class Details {

	String companyName;
	
	public Details(String companyName, String email)
	{
		this.companyName=companyName;
		this.email=email;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	String email;
}
