
//logging included 

package basicTest;

import static io.restassured.RestAssured.given;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.BodyString;
import files.Methods;
import files.Resources;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class RATestNGLibraryAPI2 {

	Properties prop = new Properties();

	Response resPost, resGet;
	JsonPath jPath;
	String bodyString;
	String resourcesPost, resourcesGet;
	String msgPass, msgFail;
	String id = "";
	String authorName;
	String responseGet, responsePost;
	
	public static Logger log = LogManager.getLogger(RATestNGLibraryAPI2.class.getName());

	@DataProvider
	public String[][] getDataProvider(){
		
		log.info("getDataProvider is loaded");
		return new String[][] {{"Derek", "3453", "werw"}, {"Mark", "e345", "ggge"}};
		
	}

	@BeforeSuite
	public void BeforeSuite() {

		System.out.println("Start of Test");
		log.info("Test is started");
	}

	@BeforeTest
	public void getData() throws IOException {

		String filePath = "C:\\Users\\shafi\\eclipse-workspace\\RESTProject1\\src\\files\\env.properties";

		FileInputStream fis = new FileInputStream(filePath);

		prop.load(fis);
		
		log.debug("Properties File is loaded");

	}
	
//DATA from data provider:
	
	@Test(dataProvider = "getDataProvider")
	public void RATestNGLibraryApiPOSTDataProvider(String authorName, String isbn, String aisle) {

		RestAssured.baseURI = prop.getProperty("HOST");

		bodyString = "{\r\n\t\"name\" : \"My first book\",\r\n\t\"isbn\" : \"" + isbn + "\",\r\n\t\"aisle\": \"" + aisle + "\",\r\n\t\"author\": \" " + authorName + "\"\r\n}\r\n\r\n";

		resourcesPost = Resources.getResourcesLibraryPost();

		resPost = given().header("Content-Type", "application/json").body(bodyString).
				  when().post(resourcesPost).
				  then().extract().response();

		jPath = Methods.rawToJson(resPost);
		
		responsePost = resPost.asString();
		
		System.out.println(responsePost);
		
		//log.debug("Response is: " + responsePost);

		msgPass = jPath.get("Msg");

		try {

			id = jPath.get("ID");
			
			System.out.println("ID is:  " + id + " and message is: " + msgPass);
			
			log.debug("ID is:  " + id + " and message is: " + msgPass);
			
		} catch (Exception e) {

			msgFail = jPath.get("msg");
			
			System.out.println("Message is:  " + msgFail);
			
			log.debug("Message is:  " + msgFail);

		}
	}
	
//Data From Class: 
	
	@Test
	public void RATestNGLibraryApiPOSTClassData() {

		RestAssured.baseURI = prop.getProperty("HOST");

		bodyString = BodyString.getBodyStringLibrary("35467", "494");
		
		resourcesPost = Resources.getResourcesLibraryPost();

		resPost = given().header("Content-Type", "application/json").body(bodyString).when().post(resourcesPost).then()
				.extract().response();

		jPath = Methods.rawToJson(resPost);
		
		responsePost = resPost.asString();
		
		System.out.println(responsePost);
		
		log.info("Response is: " + responsePost);

		msgPass = jPath.get("Msg");

		try {

			id = jPath.get("ID");
			
			System.out.println("ID is:  " + id + " and message is: " + msgPass);
			
			log.debug("ID is:  " + id + " and message is: " + msgPass);

		} catch (Exception e) {

			msgFail = jPath.get("msg");
			
			System.out.println("Message is:  " + msgFail);
			
			log.debug("Message is:  " + msgFail);

		}
	}

	@Test
	public void RATestNGLibraryApiGET() {

		RestAssured.baseURI = prop.getProperty("HOST");

		authorName = prop.getProperty("AUTHORNAME");
		
		resourcesGet = Resources.getResourcesLibraryGet();
		
		resGet = given().param("AuthorName", authorName).when().get(resourcesGet).then().assertThat().statusCode(200)
				.and().extract().response();

		responseGet = resGet.asString();
		
		System.out.println(responseGet);
		
		//log.debug("Get response is: " + responseGet);
	}

	@AfterSuite
	public void afterSuite() {

		System.out.println("End of Test");
		log.info("End of Test");

	}

}

