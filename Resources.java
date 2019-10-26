package files;

public class Resources {
	
	public static String resources;
	public static String getResourcesJSON() {
		
		resources = "maps/api/place/add/json?key= qaclick123";
		
		return resources;
	}
	
	public static String getResourcesXML() {
		
		resources = "maps/api/place/add/xml?key= qaclick123";
		
		return resources;
	}
	
	public static String getResourcesLibraryPost() {
		
		resources = "/Library/Addbook.php";
		
		return resources;
	}
	
public static String getResourcesLibraryGet() {
		
		resources = "/Library/GetBook.php";
		
		return resources;
	}

}
