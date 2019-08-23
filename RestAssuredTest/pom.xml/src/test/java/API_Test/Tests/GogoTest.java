package API_Test.Tests;

import java.util.Random;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GogoTest {

	RequestSpecification request;
	Response response;
	JsonPath jsPath;

	Random r = new Random();
	
	String id, mailId, fName, lName, gender;

	@BeforeTest
	@Parameters("baseURI")
	public void beforeClass(String baseURI) {

		RestAssured.baseURI = baseURI;
		request = RestAssured.given();

		mailId = "mail" + r.nextInt() + "@gmail.com";
		fName = "fName" + r.nextInt();
		lName = "lName" + r.nextInt();
		gender = "male";

	}

	@Test(priority = 0)
	@Parameters({ "token", "usersAPI" })
	public void createUser(String token, String usersAPI) {

		// POST
		JSONObject requestParams = new JSONObject();

		requestParams.put("email", mailId);
		requestParams.put("first_name", fName);
		requestParams.put("last_name", lName);
		requestParams.put("gender", gender);

		request.contentType(ContentType.JSON);
		request.header("Authorization", "Bearer " + token);
		request.body(requestParams.toJSONString());

		response = request.post("/" + usersAPI);
		jsPath = response.jsonPath();

		if (jsPath.get("result.id") != null) {
			id = jsPath.get("result.id");
			System.out.println("id is:" + id);
		}

		System.out.println("Response is:" + response.prettyPrint());

		int statusCode = jsPath.get("_meta.code");

		Assert.assertEquals(statusCode, 201);

	}

	@Test(priority = 1)
	@Parameters("usersAPI")
	public void getUser(String usersAPI) {

		// GET
		System.out.println("baseURI:" + request.get(RestAssured.baseURI));

		response = request.request(Method.GET, "/" + usersAPI + "/" + id);
		jsPath = response.jsonPath();

		System.out.println("GET response:" + response.prettyPrint());

		int statusCode = jsPath.get("_meta.code");
		String statusMessage = jsPath.get("_meta.message");

		Assert.assertEquals(statusCode, 200);
		Assert.assertEquals(statusMessage, "OK. Everything worked as expected.");

		Assert.assertEquals(jsPath.get("result.email"), mailId);
		Assert.assertEquals(jsPath.get("result.first_name"), fName);
		Assert.assertEquals(jsPath.get("result.last_name"), lName);
		Assert.assertEquals(jsPath.get("result.gender"), gender);

	}

	@Test(priority = 2)
	@Parameters({ "token", "usersAPI" })
	public void createExistingUser(String token, String usersAPI) {

		// Create existing user again
		// POST
		JSONObject requestParams = new JSONObject();

		requestParams.put("email", mailId);
		requestParams.put("first_name", "duplicate");
		requestParams.put("last_name", "duplicate");
		requestParams.put("gender", gender);

		request.contentType(ContentType.JSON);
		request.header("Authorization", "Bearer " + token);
		request.body(requestParams.toJSONString());

		response = request.post("/" + usersAPI);
		jsPath = response.jsonPath();

		System.out.println("Exising user Response is:" + response.prettyPrint());

	}
	
	@Test(priority = 3)
	@Parameters({ "token", "usersAPI" })
	public void createUserInvalidToken(String token, String usersAPI) {

		// Create user with invalid token
		JSONObject requestParams = new JSONObject();

		requestParams.put("email", "mail" + r.nextInt() + "@gmail.com");
		requestParams.put("first_name", fName);
		requestParams.put("last_name", lName);
		requestParams.put("gender", gender);

		request.contentType(ContentType.JSON);
		request.header("Authorization", "Bearer " + "123");
		request.body(requestParams.toJSONString());

		response = request.post("/" + usersAPI);
		jsPath = response.jsonPath();

		System.out.println("Invallid Token Response is:" + response.prettyPrint());

	}
	
	@Test(priority = 4)
	@Parameters({ "token", "usersAPI" })
	public void createUserInvalidEmail(String token, String usersAPI) {

		// Create user with invalid email
		JSONObject requestParams = new JSONObject();

		requestParams.put("email", "mail" + r.nextInt());
		requestParams.put("first_name", fName);
		requestParams.put("last_name", lName);
		requestParams.put("gender", gender);

		request.contentType(ContentType.JSON);
		request.header("Authorization", "Bearer " + token);
		request.body(requestParams.toJSONString());

		response = request.post("/" + usersAPI);
		jsPath = response.jsonPath();

		System.out.println("Invalid Email Response is:" + response.prettyPrint());

	}
	
	@Test(priority = 5)
	@Parameters({ "token", "usersAPI" })
	public void createUserNoEmail(String token, String usersAPI) {

		// Create user without email
		JSONObject requestParams = new JSONObject();

		requestParams.put("first_name", fName);
		requestParams.put("last_name", lName);
		requestParams.put("gender", gender);

		request.contentType(ContentType.JSON);
		request.header("Authorization", "Bearer " + token);
		request.body(requestParams.toJSONString());

		response = request.post("/" + usersAPI);
		jsPath = response.jsonPath();

		System.out.println("Without Email Response is:" + response.prettyPrint());

	}

}
