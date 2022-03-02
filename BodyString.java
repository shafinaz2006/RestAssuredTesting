package files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BodyString {
	public static String bodyString;
	public static String getBodyStringJSON() {
		bodyString = "{" +
				"\"location\":{" +
				"\"lat\" : -38.383494," +
				"\"lng\" : 33.427362" +
				"}," +
				"\"accuracy\":50," +
				"\"name\":\"Frontline house\"," +
				"\"phone_number\":\"(+91) 983 893 3937\"," +
				"\"address\" : \"29, side layout, cohen 09\"," +
				"\"types\": [\"shoe park\",\"shop\"]," +
				"\"website\" : \"http://google.com\"," +
				"\"language\" : \"French-IN\"" +
				"}";
		return bodyString;
	}
	
	public static String getBodyStringLibrary(String isbn, String aisle) {
		bodyString = "{\r\n\t\"name\" : \"My first book\",\r\n\t\"isbn\" : \"" 
                    + isbn 
                    + "\",\r\n\t\"aisle\": \"" 
                    + aisle 
                    + "\",\r\n\t\"author\": \"Derek Shepherd\"\r\n}\r\n\r\n";
		return bodyString;
	}
	
	public static String getBodyString(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}
	

}
