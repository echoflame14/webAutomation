package com.clip.tests.logistics;

import com.clip.framework.MainDriver;
import com.clip.framework.pages.logistics.ConektaDashboardPage;
import com.clip.framework.pages.logistics.ConektaLoginPage;
import com.clip.framework.pages.logistics.ConektaOrdersPage;
import config.PrivateConfig;
import org.joda.time.DateTime;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Date;

public class RunPurchaseReports extends MainDriver {

    ConektaLoginPage lp;
    ConektaDashboardPage dp;
    ConektaOrdersPage op;
    String user = PrivateConfig.conektaUser;
    String pass = PrivateConfig.conektaPass;

    @Parameters({"platform", "browser", "version", "url"})
    @Test(priority = 0)
    public void loginConekta(String platform, String browser, String version, String url) {
        try{
//            Cookie cookie = new Cookie("AWSALB=","wduphhXVpN0UyzQH7Hhml2aKRfB/x993BeXq6OJsOhXLeHAlKYe5hPbpqM6vzQux+jmFhghU+AJdix2e770wGXTVLO34TrdZVk0OC/WiT4LvRQydkbzVXB2HxsNB; Expires=Mon, 06 Aug 2018 15:42:14 GMT; Path=/");
//            driver.manage().addCookie(cookie);
            lp = new ConektaLoginPage(getDriver());
            dp = lp.login(user,pass);
//            dashboardPage.setToEnglish();
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not login to logistics " + e);
        }
    }
    @Parameters({"platform", "browser", "version", "url"})
    @Test(priority = 1)
    public void getOrders(String platform, String browser, String version, String url) {
        try{
            op = dp.clickOrdersPage();
            Thread.sleep(10000);


            Date date = new Date();
            Integer todayInt = new DateTime(date).dayOfWeek().get();
            DateTime today = new DateTime(date).withTimeAtStartOfDay();
            System.out.println(today);
            Integer fromMinus = 0;
            if (todayInt == 1){
                fromMinus = 2;
            }else {
                fromMinus = 1;
            }
            DateTime fromField = new DateTime(date).minusDays(fromMinus).withTimeAtStartOfDay();
            System.out.println(fromField);
//            ordersPage.setOrdersFiltersForDateRange(fromField, today);
            op.getRowInformation(fromField, today);
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not get orders " + e);
        }
    }
}
