package actualCode;

import io.restassured.RestAssured;import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

import static io.restassured.RestAssured.*;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class oauth {
	@Test
	public void Poauth() {
		String[] CourseTitle= {"Selenium Webdriver Java","Cypress","Protractor"};
		String Res=given().formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
		formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParams("grant_type", "client_credentials")
		.formParams("scope", "trust")
		.when().log().all()
		.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		System.out.println(Res);
		JsonPath jp= new JsonPath(Res);
		String AccessToken=jp.getString("access_token");
		 
	GetCourse Gc=given()
		.queryParam("access_token",AccessToken).when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);
		
		System.out.println("Linkdin Name:"+Gc.getLinkedIn());
		System.out.println("Instructor Name:"+Gc.getInstructor());
		System.out.println("Title:"+Gc.getCourses().getApi().get(1).getCourseTitle());
 List<Api> ApiCourse=Gc.getCourses().getApi();
 for(int i=0;i<ApiCourse.size();i++) {
	if(ApiCourse.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
	System.out.println(ApiCourse.get(i).getPrice()); 
	}
	ArrayList<String> a=new ArrayList<String>();
	List<WebAutomation> WebAutomation=Gc.getCourses().getWebAutomation();
	for( i=0;i<WebAutomation.size();i++) {
		System.out.println("Title"+(i+1)+":"+WebAutomation.get(i).getCourseTitle()); 
		a.add(WebAutomation.get(i).getCourseTitle());
		
		}
	List<String> expectedList=Arrays.asList(CourseTitle);
	Assert.assertTrue(a.equals(expectedList));
	 
 }
 
		
	}

}

