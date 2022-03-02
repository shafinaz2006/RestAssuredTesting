

package basicTest;

import static io.restassured.RestAssured.given;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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


public class RATestNGLibraryAPI {
	Properties prop = new Properties();
	Response resPost, resGet, resDelete;
	JsonPath jPath;
	String bodyString;
	String resourcesPost, resourcesGet;
	String msgPass, msgFail;
	String id = "";
	String authorName;
	String responseGet, responsePost, responseDelete;
	

	@DataProvider
	public String[][] getDataProvider(){
		return new String[][] {{"Derek", "3853", "werw"}, {"John", "3333", "345"}};
	}

	@BeforeSuite
	public void BeforeSuite() {
		System.out.println("Start of Test");
	}

	@BeforeTest
	public void getData() throws IOException {
		String filePath = "C:\\Users\\shafi\\eclipse-workspace\\RESTProject1\\src\\files\\env.properties";
		FileInputStream fis = new FileInputStream(filePath);
		prop.load(fis);
	}
	
//DATA from data provider:
	
	@Test(dataProvider = "getDataProvider")
	public void RATestNGLibraryApiPOSTDataProvider(String authorName, String isbn, String aisle) {
		RestAssured.baseURI = prop.getProperty("HOST");
		bodyString = "{\r\n\t\"name\" : \"My first book\",\r\n\t\"isbn\" : \"" + isbn + "\",\r\n\t\"aisle\": \"" + aisle + "\",\r\n\t\"author\": \"" + authorName + "\"\r\n}\r\n\r\n";
		System.out.println(bodyString);
		resourcesPost = Resources.getResourcesLibraryPost();
		resPost = given().header("Content-Type", "application/json").body(bodyString).
				  when().post(resourcesPost).
				  then().extract().response();

		jPath = Methods.rawToJson(resPost);
		responsePost = resPost.asString();
		System.out.println("Post response is: " + responsePost);
		msgPass = jPath.get("Msg");
		try {
			id = jPath.get("ID");
			System.out.println("ID is:  " + id + " and message is: " + msgPass);
		} catch (Exception e) {
			msgFail = jPath.get("msg");
			System.out.println("Message is:  " + msgFail);
		}
	}
	
//Data From Class: 
	
	@Test
	public void RATestNGLibraryApiPOSTClassData() {
		RestAssured.baseURI = prop.getProperty("HOST");
		bodyString = BodyString.getBodyStringLibrary("354007", "494");
		System.out.println(bodyString);
		resourcesPost = Resources.getResourcesLibraryPost();
		resPost = given().header("Content-Type", "application/json").body(bodyString).when().post(resourcesPost).then()
				.extract().response();
		jPath = Methods.rawToJson(resPost);
		responsePost = resPost.asString();
		System.out.println("Post response is: " + responsePost);
		msgPass = jPath.get("Msg");
		try {
			id = jPath.get("ID");
			System.out.println("ID is:  " + id + " and message is: " + msgPass);
		} catch (Exception e) {
			msgFail = jPath.get("msg");
			System.out.println("Message is:  " + msgFail);
		}
	}

	@Test
	public void RATestNGLibraryApiGET() {
		RestAssured.baseURI = prop.getProperty("HOST");
		authorName = prop.getProperty("AUTHORNAME");
		resourcesGet = Resources.getResourcesLibraryGet();
		resGet = given().param("AuthorName", authorName)
             .when().get(resourcesGet)
             .then().assertThat().statusCode(200)
				     .and().extract().response();
		responseGet = resGet.asString();
		System.out.println("Get response is: " + responseGet);
	}
	
	@Test
	public void RATestNGLibraryApiDELETE() {
		RestAssured.baseURI = prop.getProperty("HOST");
		id = "354007494";
		if (!id.isEmpty()) {
			resDelete = given()
        .body("{\r\n" + 
							" \r\n" + 
							"\"ID\" : \"" + id + "\"\r\n" + 
							" \r\n" + 
							"} \r\n" )
      .when().get("/Library/DeleteBook.php")
      .then().assertThat().statusCode(200).and()
      .extract().response();
			responseDelete = resDelete.asString();
			System.out.println("Delete Response is: " + responseDelete);
		}
		else {
			System.out.println("ID is Null and not Deleted");
		}
	}

	@AfterSuite
	public void afterSuite() {
		System.out.println("End of Test");
	}
}

