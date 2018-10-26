package com.clip.tests.logistics;

import com.clip.framework.MainDriver;
import com.clip.framework.pages.logistics.*;
import config.PrivateConfig;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CreateShippingInfo extends MainDriver {
    LoginPage lp;
    DashboardPage dp;
    OrdersPage op;
    LabelAndShipmentPage las;
    NewOrderPage nop;
    String user = PrivateConfig.qaEmail;
    String pass = PrivateConfig.qaPassword;

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
    @Test(priority = 1)
    public void createShippingInfo(String platform, String browser, String version, String url) {
        try{
            las = dp.clickLabelAndShipmentPage();
            las.createShippingInfoFullPage();
//            updateSlackEvent("Finished shipping info ");
        }catch (Exception e){
            e.printStackTrace();
            updateSlackEvent(e.getMessage());
            throw new IllegalStateException("Could not create shipping label for order" + e);
        }
    }

}
