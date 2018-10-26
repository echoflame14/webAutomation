package testRail;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import com.gurock.testrail.APIClient;
import com.gurock.testrail.APIException;

public class TestRail {
	// Creating the API client
	public static APIClient client = new APIClient("https://clip.testrail.net");
	
	// Post the result of running a test to TestRail
	public static void submitResult( String runId, String caseId, int status_id, String comment ) throws MalformedURLException, IOException, APIException {
		// Formating comment and status_id data for the post request
		Map data = new HashMap();
		data.put("status_id", status_id);
		data.put("comment", comment);
		
		// Sending the post request
		JSONObject r = (JSONObject) client.sendPost("add_result_for_case/" + runId + "/" + caseId, data);
	}
	
	
	public static void main(String[] args) throws Exception
	{
		// Setting the Auth strings
		// V-- enter your credentials to login to TestRail here
		client.setUser("yourUsernameHere");
		client.setPassword("yourpasswordHere");
		
		//To call submitResult from a test class just use the following:
		//TestRail.submitResult("168", "10439", 7, "Testing failure call");
	}	//The parameters are:  ("run_id", "caseId", status_id, "comment");
	
	
}
