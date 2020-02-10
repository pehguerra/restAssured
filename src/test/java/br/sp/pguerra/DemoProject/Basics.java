package br.sp.pguerra.DemoProject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import br.sp.pguerra.files.ReusableMethods;

public class Basics {
	
	@Test
	public void Test1() {
		//BaseURL
		RestAssured.baseURI = "https://reqres.in";
		RestAssured.basePath = "/api/users";
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(io.restassured.filter.log.LogDetail.ALL);
		
		Response response = given()
			//.log().all()
			.queryParam("page", 2)
		.when()
			.get()
		.then()
			//.log().ifValidationFails()
			.assertThat()
			.statusCode(200)
			.and()
			.contentType(ContentType.JSON)
			.and()
			.body("data[0].first_name", equalTo("Michael"))
			.and()
			.header("Server", "cloudflare")
			.extract()
			.response();
		
		JsonPath jp = ReusableMethods.rawToJson(response);
		int count = jp.get("data.size()");
		for(int i=0; i<count; i++) {
			System.out.println(jp.getString("data[" + i + "].first_name"));
		}
		System.out.println(count);
	}
}
