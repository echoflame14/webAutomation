package com.clip.tests.logistics;

import com.clip.framework.MainDriver;
import com.clip.framework.pages.logistics.DashboardPage;
import com.clip.framework.pages.logistics.LoginPage;
import com.clip.framework.pages.logistics.NewOrderPage;
import com.clip.framework.pages.logistics.OrdersPage;
import config.PrivateConfig;
import org.testng.AssertJUnit;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CreateNewGuestOrder extends MainDriver {
    LoginPage lp;
    DashboardPage dp;
    OrdersPage op;
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
    public void createNewOrder(String platform, String browser, String version, String url) {
        try{
            int timesToRun = 10;
            for(int i = 0; i < timesToRun; i++){
                System.out.println("Creating order " + i + " of " + timesToRun);
                nop = dp.clickCreateNewOrderPage();
                nop.fillOutGuestCustomerInformation();
                nop.clickNextButtonToProductDetails();
                nop.addRandomProductsToOrder();
                if(!nop.isTotalPriceAccurate()){
                    AssertJUnit.fail();

                }
                nop.clickNextButtonToShippingInformation();
                nop.fillOutShippingInformation();
                nop.clickReviewOrder();
                op = nop.clickSubmitButton();
            }
//            updateSlackEvent("Successfully went through creating new orders ");
        }catch (Exception e){
            e.printStackTrace();
            updateSlackEvent(e.getMessage());
            throw new IllegalStateException("Could not create new order " + e);
        }
    }
}
