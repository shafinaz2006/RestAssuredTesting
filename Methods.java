package files;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class Methods {
	public static XmlPath xPath;
	public static JsonPath jPath;
	public static XmlPath rawToXML(Response r){
		String response = r.asString();
		XmlPath xPath = new XmlPath(response);
		return xPath;
	}
	public static JsonPath rawToJson(Response r){ 
		String response = r.asString();
		JsonPath jPath = new JsonPath(response);
		return jPath;
	}
}
