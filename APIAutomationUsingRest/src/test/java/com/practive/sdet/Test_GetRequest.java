package com.practive.sdet;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

/*
 * 
 * given()
 * 		set cookies, add authentications, add parameters,  set headers info etc.
 * 
 * when()
 * 		perform operation using GET, POST, PUT, DELETE, PATCH
 * 
 * then()
 * 		validate status code, extract response, extract headers, cookies & response body\pay-load.
 * 
 * 
 */
public class Test_GetRequest {

	
	@Test(enabled = false)
	public void getWeatherDetails()
	{
		
		given().
			when()
			.get("https://demoqa.com/utilities/weather/city/Hyderabad")
			.then()
			.statusCode(200)
			.statusLine("HTTP/1.1 200 OK")
			.assertThat().body("City",equalTo( "Hyderabad"))
//			.header("Content-Type", "application/json");
			.header("Connection", "keep-alive");
			
		
		
	}
	
	

}
