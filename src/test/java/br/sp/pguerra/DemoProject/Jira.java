package br.sp.pguerra.DemoProject;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import br.sp.pguerra.files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Jira {
	
	Properties prop = new Properties();

	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\br\\sp\\pguerra\\files\\env.properties");
		prop.load(fis);
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(io.restassured.filter.log.LogDetail.ALL);
	}
	
	@Test
	public void jiraApi() {
		RestAssured.baseURI = "http://127.0.0.1:8080/rest";
		
		Response response = given()
			.header("Content-Type", "application/json")
			.header("Cookie", "JSESSIONID=" + ReusableMethods.getSessionKey())
			.body("{\r\n" + 
					"  \"fields\": {\r\n" + 
					"    \"project\": {\r\n" + 
					"      \"key\": \"QAC\"\r\n" + 
					"    },\r\n" + 
					"    \"summary\": \"Issue for adding comment\",\r\n" + 
					"    \"description\": \"Creating my second bug\",\r\n" + 
					"    \"issuetype\": {\r\n" + 
					"    	\"name\": \"Bug\"\r\n" + 
					"    }\r\n" + 
					"  }\r\n" + 
					"}")
		.when()
			.post("api/2/issue")
		.then()
			.statusCode(201)
			.extract()
			.response();
		
		JsonPath jp = ReusableMethods.rawToJson(response);
		System.out.println(jp.getString("id"));
	}
}