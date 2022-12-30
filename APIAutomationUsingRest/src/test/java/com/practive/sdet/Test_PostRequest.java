package com.practive.sdet;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.test.utils.RestUtils;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Test_PostRequest {

	
	public static HashMap map = new HashMap();
	
	@BeforeClass
	public void postData()
	{
//		map.put("FirstName", RestUtils.getFirstName());
//		map.put("LastName", RestUtils.getLastName());
//		map.put("UserName", RestUtils.getUserName());
//		map.put("Email", RestUtils.getEmail());
		
		map.put("Name", RestUtils.getFirstName());
		map.put("Job", RestUtils.getLastName());
		
//		RestAssured.baseURI="http://restapi.demoqa.com/customer";
		RestAssured.baseURI="https://reqres.in";
		
		
//		RestAssured.basePath = "/register";
		RestAssured.basePath="/api/users";
	}

	
	
	@Test
	public void testPost()
	{
		
		given()
				.contentType("application/json")
				.body(map)
		
		.when()
				.post()
				 
		.then()
				.statusCode(201)
				.and()
				.body("name",equalTo("morpheus"))
				.and()
				.body("job",equalTo("leader"));
				
				;
		
	}
	
	}