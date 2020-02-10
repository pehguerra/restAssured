package br.sp.pguerra.DemoProject;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import br.sp.pguerra.files.Resources;
import br.sp.pguerra.pojo.AddPlace;
import br.sp.pguerra.pojo.AddPlaceLocation;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SerializeTest {
	
	Properties prop = new Properties();

	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\br\\sp\\pguerra\\files\\env.properties");
		prop.load(fis);
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(io.restassured.filter.log.LogDetail.ALL);
	}

	@Test
	public void serializeBody() {
		RestAssured.baseURI = prop.getProperty("HOST");
		
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		
		AddPlaceLocation l = new AddPlaceLocation();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		
		AddPlace p = new AddPlace();
		
		p.setLocation(l);
		p.setAccuracy(50);
		p.setName("Frontline house");
		p.setPhone_number("(+91) 983 893 3937");
		p.setAddress("29, side layout, cohen 09");
		p.setTypes(myList);
		p.setWebsite("http://google.com");
		p.setLanguage("French-IN");
		
		Response response = given()
			.log().all()
			.queryParam("key", "qaclick123")
			.body(p)
		.when()
			.post(Resources.placePostData())
		.then()
			.log()
			.all()
			.assertThat()
			.statusCode(200)
			.extract()
			.response();
		
		System.out.println(response.asString());
	}
}