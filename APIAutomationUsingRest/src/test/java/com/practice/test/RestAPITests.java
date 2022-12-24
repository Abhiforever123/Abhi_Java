package com.practice.test;

//import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matcher.*; 
import static org.hamcrest.Matchers.equalTo;

import java.util.concurrent.TimeUnit;

import io.restassured.response.Response;

public class RestAPITests {

	public static void main(String[] args) {
//		RestAssured.given()
		
		Response getResponse =
		given()
		.param("page", "2")
		.auth().none()
//		.auth().basic("admin", "pwd")
//		.auth().digest("", "")
//		.auth().form("username", "password","form")
//		.auth().oauth2("accesstoken")
		.when()
		.get("https://reqres.in/api/users");
//		.then()
//		.statusCode(200)
//		.body("page", equalTo(2));
		
		System.out.println("Response from getTime:"+getResponse.getTime());
		System.out.println("Response from getTime In:"+getResponse.getTimeIn(TimeUnit.MILLISECONDS));
		
		System.out.println("Response from Time:"+getResponse.time());
		System.out.println("Response from getTime In:"+getResponse.timeIn(TimeUnit.MILLISECONDS));
		
		
	}
}
