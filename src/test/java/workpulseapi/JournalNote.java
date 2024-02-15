package workpulseapi;

import static io.restassured.http.ContentType.JSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import org.json.*;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import baseclass.BearerToken;
import baseclass.ExtentReport;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JournalNote extends ExtentReport{
	
	public static String id;
	
	@Test(priority=2)
	public static void addjournalnote()throws IOException
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		extent = extentreport.createTest(name);
		Random r = new Random();
        String notemessage = "Note created by Automation";
        String AccessToken = BearerToken.generateToken();
		byte[] b = Files.readAllBytes(Paths.get("AddNote.json"));
		
		
	      String bdy = new String(b);
		  JSONObject json = new JSONObject(bdy);
		  json.put("notes", notemessage);
		  String strbdy = json.toString();

	     
	
				
	      
	      Response response = RestAssured.given().contentType(JSON).
	    		  header("Authorization", "Bearer " +AccessToken).body(strbdy)
	    		  .when().post("https://opsapi.workpulse.com/api/journalnote/add")
	    		  .then()
	    		  .statusCode(200)
	    		  .log().all().extract().response();
	      JsonPath jsonpath = response.jsonPath();
	      id = jsonpath.getString("id");
	      System.out.println("Note Id: " + id);  
	      extent.log(Status.INFO, "Note generated with id as " + id);
	      
	}

}
