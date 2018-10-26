package com.clip.tests.logistics;
import com.clip.framework.MainDriver;
import com.clip.framework.pages.logistics.DashboardPage;
import com.clip.framework.pages.logistics.LoginPage;
import com.clip.framework.pages.logistics.NewOrderPage;
import com.clip.framework.pages.logistics.OrdersPage;
import config.PrivateConfig;
import org.testng.annotations.Test;

public class LoginLogistics extends MainDriver {

	LoginPage lp;
	DashboardPage dp;
	OrdersPage op;
	NewOrderPage nop;
	String user = PrivateConfig.qaEmail;
	String pass = PrivateConfig.qaPassword;

	@Test()
	public void loginLogistics() {
		try{
			lp = new LoginPage(getDriver());
			dp = lp.login(user,pass);
			if(!dp.isDashBoardPage()){
				addResultToTestRail("108990",TEST_CASE_FAILED_STATUS,"Could not verify the dashboard page");
			}else {
				addResultToTestRail("108990",TEST_CASE_PASSED_STATUS,"");
			}
		}catch (Exception e){
			e.printStackTrace();
			throw new IllegalStateException("Could not login to logistics " + e);
		}
	}
}