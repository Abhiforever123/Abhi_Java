package com.practice.test;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matcher.*; 
import static org.hamcrest.Matchers.equalTo;

public class PostJsonData {

	public static void main(String[] args) {
		
		EmployeePOJO e= new EmployeePOJO("Abhilash", "IT Professional", new String[] {"java","Selenium"}, "xyz", "abhiforever12345@gmail.com");
		
		
//		Response postResponse =
				given().
				auth().none()
				.header("Content-Type","application/json")
				.when()
				.body(e).log().all()
				.post("https://reqres.in/api/users")
				.then().log().all();
	}
	
}



