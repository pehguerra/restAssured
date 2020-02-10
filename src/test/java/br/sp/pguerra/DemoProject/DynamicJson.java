package br.sp.pguerra.DemoProject;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.sp.pguerra.files.Payload;
import br.sp.pguerra.files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class DynamicJson {

	Properties prop = new Properties();

	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\br\\sp\\pguerra\\files\\env.properties");
		prop.load(fis);
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(io.restassured.filter.log.LogDetail.ALL);
	}

	@Test(dataProvider = "BooksData")
	public void createPlaceAPI(String isbn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";

		Response response = given().header("Content-Type", "application/json").body(Payload.addBook(isbn, aisle)).when()
				.post("/Library/Addbook.php").then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.and().extract().response();

		System.out.println(response.asString());
		JsonPath js = ReusableMethods.rawToJson(response);
		String id = js.getString("ID");
		System.out.println(id);
	}

	@DataProvider(name = "BooksData")
	public Object[][] getData1() {
		return new Object[][] { { "afasf", "3562" }, { "gdsaj", "5489" }, { "bdjsf", "7458" } };
	}
}
