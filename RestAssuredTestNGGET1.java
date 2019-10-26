package basicTest;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestAssuredTestNGGET1 {
	
	Response res;
	String response;
	
	@BeforeSuite
	public void BeforeSuite() {
		
		System.out.println("Start of Test");
	}
	
	@Test
	public void RestTestingGET1() {
	
		RestAssured.baseURI = "https://maps.googleapis.com";
		
		res = given().
		     param("location", "-33.8670522,151.1957362").
		     param("radius", "500").
		     param("key", "MyGoogleAPIKEY").
		 when().
		     get("maps/api/place/nearbysearch/json").
		 then().
		 	assertThat().
		 		statusCode(200).and().
		 		contentType(ContentType.JSON).and().
		 		header("Server", "scaffolding ").and().
		 		extract().response();
		
		response = res.asString();
		System.out.println(response);
		
	}
	
	@AfterSuite
	public void AfterSuite() {
		
		System.out.println("End of Test");

	}
}
