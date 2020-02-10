package br.sp.pguerra.DemoProject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import br.sp.pguerra.files.Resources;
import br.sp.pguerra.pojo.AddPlace;
import br.sp.pguerra.pojo.AddPlaceLocation;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilders {
	
	Properties prop = new Properties();
	RequestSpecification reqSpecBuilder;
	ResponseSpecification resSpecBuilder;

	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\br\\sp\\pguerra\\files\\env.properties");
		prop.load(fis);
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(io.restassured.filter.log.LogDetail.ALL);
		reqSpecBuilder = new RequestSpecBuilder()
				.log(io.restassured.filter.log.LogDetail.ALL)
				.setBaseUri(prop.getProperty("HOST"))
				.addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON)
				.build();
		
		resSpecBuilder = new ResponseSpecBuilder()
				.log(io.restassured.filter.log.LogDetail.ALL)
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON)
				.build();
	}

	@Test
	public void serializeBody() {
		
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
			.spec(reqSpecBuilder)
			.body(p)
		.when()
			.post(Resources.placePostData())
		.then()
			.spec(resSpecBuilder)
			.and()
//			.time(lessThan(1800L))  //millisecond
			.time(lessThan(2L), TimeUnit.SECONDS)
			.extract()
			.response();
		
		System.out.println("Response Time: " + response.getTimeIn(TimeUnit.MILLISECONDS));
		System.out.println(response.prettyPrint());
	}
}