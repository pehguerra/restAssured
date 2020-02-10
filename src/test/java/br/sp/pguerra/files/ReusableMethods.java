package br.sp.pguerra.files;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReusableMethods {
	public static JsonPath rawToJson(Response res) {
		String responseString = res.asString();
		JsonPath js = new JsonPath(responseString);
		return js;
	}
	
	public static String getSessionKey() {
		RestAssured.baseURI = "http://127.0.0.1:8080/rest";
		
		// Create a session
		Response response = given()
			.header("Content-Type", "application/json")
			.body("{ \"username\": \"pehguerra\", \"password\": \"p01478965P\" }")
		.when()
			.post("/auth/1/session")
		.then()
			.statusCode(200)
			.extract()
			.response();
		
		JsonPath jp = ReusableMethods.rawToJson(response);
		return jp.getString("session.value");
	}
}