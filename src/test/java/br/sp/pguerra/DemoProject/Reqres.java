package br.sp.pguerra.DemoProject;

import org.testng.annotations.Test;

import br.sp.pguerra.pojo.GetCourse;
import br.sp.pguerra.pojo.GetCourseData;

import static io.restassured.RestAssured.given;

import java.util.List;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

public class Reqres {

	@Test
	public void getListUsers() {
		RestAssured.baseURI = "https://reqres.in";
		RestAssured.basePath = "/api/users";
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(io.restassured.filter.log.LogDetail.ALL);
		
		GetCourse gc = given()
			.queryParam("page", "2")
			
			.expect()
			.defaultParser(Parser.JSON)
			
		.when()
			.get()
			//.as(GetCourse.class)
		.then()
			.log()
			.all()
			.assertThat()
			.statusCode(200)
			.extract()
			.response()
			.as(GetCourse.class);
		
		System.out.println(gc.getData().get(2).getFirst_name());
		
		List<GetCourseData> lgc = gc.getData();
		
		for(int i = 0; i < lgc.size(); i++) {
			if(lgc.get(i).getFirst_name().equals("Byron")) {
				System.out.println(lgc.get(i).getLast_name());
			}
		}
	}
}