
//Getting BodyString from raw XML file, raw JSON file

package basicTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import files.BodyString;
import files.Resources;
import files.Methods;

public class RestAssuredTestNGPOST1 {

	Properties prop = new Properties();
	
	String xmlFilePath = "C:\\Users\\shafi\\eclipse-workspace\\RESTProject1\\src\\files\\xmlBodyData.xml";
	String jsonFilePath = "C:\\Users\\shafi\\eclipse-workspace\\RESTProject1\\src\\files\\bodyString.json";
	
	JsonPath jPath;
	XmlPath xPath;
	String keyData;
	String bodyData;
	String statusResponse;
	Response res;
	
	@BeforeSuite
	public void beforeSuite() {

		System.out.println("Start of Test");
	}

	@BeforeTest
	public void getData() throws IOException {

		String filePath = "C:\\Users\\shafi\\eclipse-workspace\\RESTProject1\\src\\files\\env.properties";

		FileInputStream fis = new FileInputStream(filePath);

		prop.load(fis);
		
	}

	@Test
	public void restAssuredTestNGPOST_JSON() {

		RestAssured.baseURI = prop.getProperty("HOST");

		keyData = prop.getProperty("KEY");

		res = given().queryParam("key", keyData).body(BodyString.getBodyStringJSON()).log().all().
			  when()
			  .post(Resources.getResourcesJSON()).then().assertThat().statusCode(200).and()
			  .contentType(ContentType.JSON).and().body("status", equalTo("OK")).extract().response();

		jPath = Methods.rawToJson(res);

		statusResponse = jPath.get("place_id");

		System.out.println("place id is: " + statusResponse);
	}
	
	@Test
	public void restAssuredTestNGPOST_JSON_staticData() throws IOException {
		
		
		RestAssured.baseURI = prop.getProperty("HOST");

		bodyData = BodyString.getBodyString(jsonFilePath);
		
		keyData = prop.getProperty("KEY");

		res = given().
				queryParam("key", keyData).body(bodyData).
				when().post(Resources.getResourcesJSON()).
				then().
				assertThat().statusCode(200).and().
				contentType(ContentType.JSON).and().log().all().
				extract().response();

		System.out.println(res.asString());

		jPath = Methods.rawToJson(res);

		statusResponse = jPath.get("response.status");
		
		System.out.println("Status in Response is: " + statusResponse);
	}


	@Test
	public void restAssuredTestNGPOST_XML() throws IOException {
		

		RestAssured.baseURI = prop.getProperty("HOST");

		bodyData = BodyString.getBodyString(xmlFilePath);
		keyData = prop.getProperty("KEY");

		res = given().
				queryParam("key", keyData).body(bodyData).
				when().post(Resources.getResourcesXML()).
				then()
				.assertThat().statusCode(200).and().
				contentType(ContentType.XML).and().log().all().
				extract().response();

		System.out.println(res.asString());

		xPath = Methods.rawToXML(res);

		statusResponse = xPath.get("response.status");
		
		System.out.println("Status in Response is: " + statusResponse);

	}

	@AfterSuite
	public void afterSuite() {

		System.out.println("End of Test");

	}
}
