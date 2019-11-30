package test.Evaluation;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Evaluation2 {

	public HttpClient client;
	int Rule1 = 0;

	String userId = "f9d54ec1-3a7e-4a79-ae3c-f1fe1b2a8b30";
	String url = "https://e018fed1-c416-4c3e-ba7d-66bfa479a5a9.mock.pstmn.io/rideshares?user_id=f9d54ec1-3a7e-4a79-ae3c-f1fe1b2a8b30";
	URL jsonResponse;
//step 1: Capturing the data from the API end point response
	@Test(priority = 0)
	public void getResponse() {
		try {

			jsonResponse = getJsonResponse(url, userId);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//step 2:calculating the actual points and validating with the points mentioned in the API 
	@Test(priority = 1)
	public void validateResponse() throws IOException, ParseException {

		BigDecimal points1;
		
		Scanner scan = new Scanner(jsonResponse.openStream());
		String entireResponse = new String();
		while (scan.hasNext())
			entireResponse += scan.nextLine();
		scan.close();

		JSONParser parse = new JSONParser();
		JSONObject jobj = (JSONObject) parse.parse(entireResponse);
		JSONArray rideshares = (JSONArray) jobj.get("rideshares");

		for (int i = 0; i < rideshares.size(); i++) {

			BigDecimal calculatedPoints = BigDecimal.ZERO;
			JSONObject sharedDrive = (JSONObject) rideshares.get(i);

			// Check if we have a driver
			JSONArray people = (JSONArray) sharedDrive.get("people");
			boolean isDriverpresent = isDriver(i, people);
			System.out.println("Is driver present-->" + isDriverpresent);

			// Get the distance
			String distance = sharedDrive.get("travel_distance").toString();
			BigDecimal bigDecimaldis = new BigDecimal(distance);
			BigDecimal bg2 = new BigDecimal("1000");
			BigDecimal distance2 = bigDecimaldis.divideToIntegralValue(bg2);
			// System.out.println("Distance-->" + distance2);

			// Calculate points
			BigDecimal bg3 = new BigDecimal("3");
			BigDecimal bg4 = distance2.divideToIntegralValue(bg3);
			points1 = bg4.setScale(0, RoundingMode.DOWN);
			// System.out.println("Points-->" + points1);

			// Get displayed points and final calculated points
			for (int j = 0; j < people.size(); j++) {
				JSONObject peoples = (JSONObject) people.get(j);
				int displayedPoints = Integer.parseInt(peoples.get("points").toString());
				System.out.println("Displayed points in API-->" + displayedPoints);

				// Get final points
				if (isDriverpresent) {
					if (peoples.get("is_driver").toString() == "false") {
						calculatedPoints = points1;
					} else {
						BigDecimal b2 = new BigDecimal(people.size() - 1);
						calculatedPoints = points1.multiply(b2);
					}
				}
				System.out.println("Final calculated points-->" + calculatedPoints);
				// Assert actual and expected result
				Assert.assertEquals(displayedPoints, calculatedPoints.intValue());

			}
		}

	}

	private URL getJsonResponse(String url, String userID)
			throws MalformedURLException, IOException, ProtocolException {
		URL jsonResponse = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) jsonResponse.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("user_id", userID);
		Assert.assertTrue(conn.getResponseCode() == 200);
		return jsonResponse;
	}

	private boolean isDriver(int i, JSONArray people) {

		String isDriver;
		boolean flag = false;
		for (int j = 0; j < people.size(); j++) {
			JSONObject peoples = (JSONObject) people.get(i);
			isDriver = peoples.get("is_driver").toString();
			System.out.println(peoples.get("is_driver"));
			if (isDriver.equals("true")) {
				Rule1 = 1;
				flag = true;
				break;
			}

		}
		return flag;
	}

	public JSONObject getMethodCall(String urlTail) throws ClientProtocolException, IOException, ParseException {

		HttpGet get = new HttpGet(urlTail);
		HttpResponse response = client.execute(get);

		String status = response.getStatusLine().toString();

		Assert.assertTrue(status.contains("200"), "Verifying the post call response");

		HttpEntity entity = response.getEntity();
		JSONObject returnData = new JSONObject();
		JSONParser parser = new JSONParser();
		returnData = (JSONObject) parser.parse(EntityUtils.toString(entity));

		return returnData;
	}
}
