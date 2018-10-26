package com.clip.framework;

import com.gurock.testrail.APIClient;
import com.gurock.testrail.APIException;
import config.PrivateConfig;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.joda.time.DateTime;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainDriver {
	//Deletes all cookies for new engagement
	protected WebDriver driver; // initializing the driver
	Long testRunId = 0L;
	public static final int TEST_CASE_PASSED_STATUS = 1;
	public static final int TEST_CASE_FAILED_STATUS = 5;
	String testRailUserName = PrivateConfig.testRailUsername;
	String testRailPassword = PrivateConfig.testRailPassword;
	String startingUrl = "";
	Integer percentUp = 0;
	Boolean hasBeenSet = false;
	String finalTestRun = "";

	
	@Parameters({ "platform","browser","version","url" })
	@BeforeMethod 
	public  void setUp(String platform, String browser, String version, String url) {
		try {
			System.out.println("trying before test");
			ChromeOptions options = new ChromeOptions();
			DesiredCapabilities capab = new DesiredCapabilities();
			//options.addArguments("--headless"); //run chrome headless
			options.addArguments("disable-infobars"); //gets rid of stupid infobar in chrome about automation
			options.addArguments("--window-size=1920,1080");
			options.addArguments("user-data-dir=/tmp/tarun");
//			options.addArguments("load-extension=/Users/benspurlock/Library/Application Support/Google/Chrome/Default/Extensions/cjpalhdlnbpafiamejdnhcphjbkeiagm/1.16.14_0");
			capab.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(capab); // Had to initialize chrome down here so it only opens one browser on start up
//			driver.manage().deleteAllCookies();
			startingUrl = url;
			driver.get(url);
			driver.manage().window().maximize();



		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public WebDriver getDriver(){
		return driver;
	}
	
	
	public void updateSlackEvent(String textToSendSlack){
		try{
			//post to slack endpoint
			HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
			HttpPost request = new HttpPost("https://hooks.slack.com/services/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");
			StringEntity params =new StringEntity("{\"text\":\"" + textToSendSlack +"\"}");
			request.addHeader("content-type", "application/x-www-form-urlencoded");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);

			System.out.println(response);
			//handle response here...
		}catch (Exception e){

		}
	}

	public Map<String, Object> getTestRun(String runId){
		Map<String, Object> testRun;
		try {
			APIClient client = new APIClient("https://clip.testrail.net/");
			client.setUser(testRailUserName);
			client.setPassword(testRailPassword);
			testRun = (Map<String,Object>) client.sendGet("get_run/" + runId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Could not get test run " + e);
		}
		return testRun;

	}


	public Long createNewTestRun(String testSuite) throws IOException, APIException {
		APIClient client = new APIClient("https://clip.testrail.net/");
		DateTime today = new DateTime();
		client.setUser(testRailUserName);
		client.setPassword(testRailPassword);
		Map data = new HashMap<>();
		data.put("suite_id", testSuite);
		System.out.println(startingUrl + " starting");
		String[] urls = startingUrl.split("\\.");
		System.out.println(urls.length);
		data.put("name", "Logistics - Automation Smoke Test - "+ urls[0].replaceAll("https://","") +" - " + today.toString());
		Map<String, Object> response = (Map<String, Object>) client.sendPost("add_run/2", data);
		testRunId = (Long) response.get("id");
		System.out.println(testRunId);
		finalTestRun = Long.toString(this.testRunId);
		setTestId(testRunId);
		return testRunId;
	}
	public Integer getPercentTestRailDone(String testRun){
		Integer decimal = 0;
		try {
			Map<String, Object> testRunInfo = getTestRun(testRunId.toString());
			Double passed = (double)(long)testRunInfo.get("passed_count");
			System.out.println(passed + " passed num");
			Double untested = (double)(long)testRunInfo.get("untested_count");
			System.out.println(untested + " untested num");
			decimal = (int)((passed/untested)*100);
			System.out.println(decimal);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Could not get decimal " + e);
		}
		return decimal;
}A


	public void addResultToTestRail(String testCaseId, int status, String error) throws IOException, APIException {

		APIClient client = new APIClient("https://clip.testrail.net/");
		final String testRunId = getTestId();
		client.setUser(testRailUserName);
		client.setPassword(testRailPassword);
		Map data = new HashMap<>();
		data.put("status_id", status);
		data.put("comment","Test Executed - Status updated automatically from Selenium test automation " + error);
		data.put("error",error);
		System.out.println("testRunId = " + testRunId);
		client.sendPost("add_result_for_case/"+testRunId+"/"+testCaseId+"",data);
		String statusMessage = "";
		if (status == 1){
			statusMessage = "passed";
		}else {
			statusMessage = "failed";
		}
		updateSlackEvent("Test run: https://clip.testrail.net/index.php?/runs/view/" + testRunId + " \nTest case: https://clip.testrail.net/index.php?/cases/results/" + testCaseId + "\nThis test case *" + statusMessage + "*");
		Integer percentDone = getPercentTestRailDone(testRunId);
		System.out.println(percentDone+"%" + "\nPercent Up " + percentUp);

		if(percentDone == percentUp && !hasBeenSet) {
			updateSlackEvent("Testrun " + testRunId + " is " + percentDone + "% done");
			percentUp += percentUp+5;
			this.hasBeenSet = true;
		}else {
			this.hasBeenSet = false;
		}

	}

	public void setTestId(Long id){
		finalTestRun = Long.toString(id);
	}

	public String getTestId(){
		return finalTestRun;
	}

	public enum Permissions{
		CH,CH_SALES,SUPERVISOR,MASTER,ADMIN;
	}

	public enum Enviroment{
		DEV,STAGE,PROD;
	}


	@AfterMethod 
	public  void closeBrowser() {
		try {
			System.out.println("trying after test");
			driver.manage().deleteAllCookies();
			driver.close();
			driver.quit();
		}catch(Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Could not close browser " + e);
		}
	}


}