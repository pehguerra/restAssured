package br.sp.pguerra.DemoProject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import br.sp.pguerra.files.Resources;
import br.sp.pguerra.files.ReusableMethods;
import br.sp.pguerra.files.Payload;

public class Basics2 {
	
	Properties prop = new Properties();

	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\br\\sp\\pguerra\\files\\env.properties");
		prop.load(fis);
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(io.restassured.filter.log.LogDetail.ALL);
	}
	
	@Test
	public void createPlaceAPI() {
		RestAssured.baseURI = prop.getProperty("HOST");

		Response response = given()
			.queryParam("key", prop.getProperty("KEY"))
			.body(Payload.getPostData())
		.when()
			.post(Resources.placePostData())
		.then()
			.assertThat()
			.statusCode(200)
			.and()
			.contentType(ContentType.JSON)
			.and()
			.body("status", equalTo("OK"))
			.extract()
			.response();

		JsonPath js = ReusableMethods.rawToJson(response);
		String placeId = js.get("place_id");
		System.out.println(placeId);
	}
	
	
	@Test
	public void getMethod() {
		RestAssured.baseURI = prop.getProperty("HOST");
		RestAssured.basePath = "/Library/GetBook.php";
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		Response response = given()
				.queryParam("AuthorName", "Rahul")
			.when()
				.get()
			.then()
				.assertThat()
				.statusCode(200)
				.and()
				.contentType(ContentType.JSON)
				.extract()
				.response();

		JsonPath js = ReusableMethods.rawToJson(response);
		String placeId = js.get("[1].book_name");
		System.out.println(placeId);
		int count = js.get("size()");
		System.out.println(count);
	}
}
