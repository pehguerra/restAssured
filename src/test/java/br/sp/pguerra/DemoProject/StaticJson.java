package br.sp.pguerra.DemoProject;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import br.sp.pguerra.files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class StaticJson {
	@Test
	public void createPlaceAPI() throws IOException {
		RestAssured.baseURI = "http://216.10.245.166";
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		Response response = given()
			.header("Content-Type", "application/json")
			.body(GenerateStringFromResource(System.getProperty("user.dir") + "\\AddBookDetails.json"))
		.when()
			.post("/Library/Addbook.php")
		.then()
			.assertThat()
			.statusCode(200)
			.and()
			.contentType(ContentType.JSON)
			.and()
			.extract()
			.response();

		System.out.println(response.asString());
		JsonPath js = ReusableMethods.rawToJson(response);
		String id = js.getString("ID");
		System.out.println(id);
	}
	
	public static String GenerateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}
}
