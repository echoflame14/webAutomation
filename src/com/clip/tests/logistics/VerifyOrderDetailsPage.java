package com.clip.tests.logistics;

import com.clip.framework.MainDriver;
import com.clip.framework.pages.logistics.DashboardPage;
import com.clip.framework.pages.logistics.LoginPage;
import com.clip.framework.pages.logistics.OrderDetailsPage;
import com.clip.framework.pages.logistics.OrdersPage;
import config.PrivateConfig;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Map;

public class VerifyOrderDetailsPage extends MainDriver {

    String user = PrivateConfig.qaEmail;
    String pass = PrivateConfig.qaPassword;
    LoginPage lp;
    DashboardPage dp;
    OrdersPage op;
    OrderDetailsPage odp;


    @Parameters({"platform", "browser", "version", "url"})
    @Test(priority = 0)
    public void loginLogistics(String platform, String browser, String version, String url) {
        try{
            lp = new LoginPage(getDriver());
            dp = lp.login(user,pass);
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not login to logistics " + e);
        }
    }
    @Parameters({"platform", "browser", "version", "url"})
    @Test(priority = 0)
    public void verifyOrderDetails(String platform, String browser, String version, String url) {
        op = dp.clickOrdersPage();
        odp = op.clickOrderByRowNum(0);
        Map<String, String> orderDetails = odp.getOrderInfo();
        odp.verifyOrderDetails(orderDetails);
    }
}
