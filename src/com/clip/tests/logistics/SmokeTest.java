package com.clip.tests.logistics;

import com.clip.framework.MainDriver;
import com.clip.framework.pages.logistics.*;
import com.gurock.testrail.APIException;
import config.PrivateConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SmokeTest extends MainDriver {
    LoginPage loginPage;
    DashboardPage dashboardPage;
    OrdersPage ordersPage;
    NewOrderPage newOrderPage;
    OrderDetailsPage orderDetailsPage;
    LabelAndShipmentPage labelAndShipmentPage;
    String user = PrivateConfig.qaEmail;
    String pass = PrivateConfig.qaPassword;
    Permissions ch = Permissions.CH;
    Permissions sales = Permissions.CH_SALES;
    Permissions supervisor = Permissions.SUPERVISOR;
    Permissions master = Permissions.MASTER;
    Permissions admin = Permissions.ADMIN;
    Enviroment dev = Enviroment.DEV;
    Enviroment stage = Enviroment.STAGE;
    Enviroment prod = Enviroment.PROD;

    //Creation of the test run
    @BeforeClass
    public void SmokeTest(){
        try{
            //Input ID of test suite
            createNewTestRun("456");
        }catch(Exception e){
            throw new IllegalStateException("could not create new test run " + e);
        }
    }
    //first test that all others depend on for making sure user is logged into product
    @Test(groups = "a")
    public void login(){
        try {
            loginPage = new LoginPage(getDriver());
            dashboardPage = loginPage.login(user, pass);
            if(dashboardPage.isDashBoardPage()){
                addResultToTestRail("118278",TEST_CASE_PASSED_STATUS,"");
            }else{
                addResultToTestRail("118278",TEST_CASE_FAILED_STATUS,"Could not verify the dashboard page");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not login " + e);
        }

    }
    @Test(dependsOnGroups = "a", groups = "b")
    public void createNewOrder(){
        try{
            newOrderPage = dashboardPage.clickCreateNewOrderPage();
            if(newOrderPage.isNewOrderPage()){
                addResultToTestRail("118282",TEST_CASE_PASSED_STATUS,"");
            }else {
                addResultToTestRail("118282",TEST_CASE_FAILED_STATUS,"Could not verify the new orders page");
            }

            try {
                newOrderPage.fillOutGuestCustomerInformation();
                addResultToTestRail("118239", TEST_CASE_PASSED_STATUS, "");
            }catch (Exception e){
                addResultToTestRail("118239",TEST_CASE_FAILED_STATUS,"Could not verify guest order button worked " + e.getMessage());
            }

            try {
                newOrderPage.clickNextButtonToProductDetails();
                addResultToTestRail("118240", TEST_CASE_PASSED_STATUS, "");
            }catch(Exception e){
                addResultToTestRail("118240", TEST_CASE_FAILED_STATUS,e.getMessage());
            }

            //testing the add products information
            try {
                newOrderPage.addRandomProductsToOrder();
                addResultToTestRail("118241" , TEST_CASE_PASSED_STATUS,"");
                addResultToTestRail("118242" , TEST_CASE_PASSED_STATUS,"");
                addResultToTestRail("118243" , TEST_CASE_PASSED_STATUS,"");
                addResultToTestRail("118244" , TEST_CASE_PASSED_STATUS,"");
            } catch (Exception e) {
                addResultToTestRail("118241" , TEST_CASE_FAILED_STATUS,e.getMessage());
                addResultToTestRail("118242" , TEST_CASE_FAILED_STATUS,e.getMessage());
                addResultToTestRail("118243" , TEST_CASE_FAILED_STATUS,e.getMessage());
                addResultToTestRail("118244" , TEST_CASE_FAILED_STATUS,e.getMessage());
            }

            //Testing price calculations
            try{
                if(newOrderPage.isTotalPriceAccurate()){
                    addResultToTestRail("118246" , TEST_CASE_PASSED_STATUS,"");
                }else {
                    addResultToTestRail("118246" , TEST_CASE_FAILED_STATUS,"");
                }
            }catch (Exception e){
                addResultToTestRail("118246" , TEST_CASE_FAILED_STATUS,e.getMessage());
            }

            //reuse person name and info
            try {
                newOrderPage.clickNextButtonToShippingInformation();
                addResultToTestRail("118248" , TEST_CASE_PASSED_STATUS,"");
            } catch (Exception e) {
                addResultToTestRail("118248" , TEST_CASE_FAILED_STATUS, e.getMessage());
            }
            //filling out shipping information
            try {
                newOrderPage.fillOutShippingInformation();
                addResultToTestRail("118250", TEST_CASE_PASSED_STATUS, "");
                addResultToTestRail("118251", TEST_CASE_PASSED_STATUS, "");
                addResultToTestRail("118253" , TEST_CASE_PASSED_STATUS,"");
                addResultToTestRail("118255" , TEST_CASE_PASSED_STATUS,"");
                addResultToTestRail("118254", TEST_CASE_PASSED_STATUS, "");
            } catch (Exception e) {
                addResultToTestRail("118250", TEST_CASE_FAILED_STATUS, "");
                addResultToTestRail("118251", TEST_CASE_FAILED_STATUS, "");
                addResultToTestRail("118253" , TEST_CASE_FAILED_STATUS,"");
                addResultToTestRail("118255" , TEST_CASE_FAILED_STATUS,"");
                addResultToTestRail("118254", TEST_CASE_FAILED_STATUS, "");
            }
            //review order
            try {
                newOrderPage.clickReviewOrder();
                addResultToTestRail("118256", TEST_CASE_PASSED_STATUS,"");
                addResultToTestRail("118257", TEST_CASE_PASSED_STATUS, "");
            } catch (Exception e) {
                addResultToTestRail("118256" , TEST_CASE_FAILED_STATUS, e.getMessage());
                addResultToTestRail("118257", TEST_CASE_FAILED_STATUS, e.getMessage());
            }
            //submit order and return to ordersPage
            try {
                ordersPage = newOrderPage.clickSubmitButton();
                addResultToTestRail("118258" , TEST_CASE_PASSED_STATUS, "");
            } catch (Exception e) {
                addResultToTestRail("118258" , TEST_CASE_FAILED_STATUS, e.getMessage());
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not create new order " + e);
        }
    }

    @Test(groups = "c",dependsOnGroups = "a")
    public void payByCard() throws IOException, APIException {
        //testing only one paid order
        try {
            ordersPage = dashboardPage.clickOrdersPage();
            addResultToTestRail("118334" , TEST_CASE_PASSED_STATUS, "");
            ordersPage.setCriteria("Orders at Created State");
            addResultToTestRail("118379", TEST_CASE_PASSED_STATUS, "");
            ordersPage.payPendingOrderByCard(0);
            addResultToTestRail("118271",TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118272",TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118273",TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118274",TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118275",TEST_CASE_PASSED_STATUS,"");



        } catch (Exception e) {
            addResultToTestRail("118334" , TEST_CASE_FAILED_STATUS, e.getMessage());
            addResultToTestRail("118379", TEST_CASE_FAILED_STATUS, e.getMessage());
            addResultToTestRail("118271",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118272",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118273",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118274",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118275",TEST_CASE_FAILED_STATUS,e.getMessage());
        }

        //testing all sandbox card num
        try{
            ordersPage.setCriteria("Orders at Created State");
            ordersPage.testAllSandboxCardPayments();
            addResultToTestRail("118344", TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118345", TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118346", TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118347", TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118348", TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118349", TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118350", TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118351", TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118352", TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118353", TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118354", TEST_CASE_PASSED_STATUS,"");

        }catch (Exception e){
            addResultToTestRail("118344", TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118345", TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118346", TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118347", TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118348", TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118349", TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118350", TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118351", TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118352", TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118353", TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118354", TEST_CASE_FAILED_STATUS,e.getMessage());
        }

        try{
            ordersPage = dashboardPage.clickOrdersPage();

            orderDetailsPage = ordersPage.clickOrderByRowNum(0);
            addResultToTestRail("118225", TEST_CASE_PASSED_STATUS, "");
            Map<String, String> orderDetails = orderDetailsPage.getOrderInfo();
            orderDetailsPage.verifyOrderDetails(orderDetails);
            addResultToTestRail("118226", TEST_CASE_PASSED_STATUS, "");
            addResultToTestRail("118227", TEST_CASE_PASSED_STATUS, "");
            addResultToTestRail("118228", TEST_CASE_PASSED_STATUS, "");
            addResultToTestRail("118229", TEST_CASE_PASSED_STATUS, "");
        }catch (Exception e){
            addResultToTestRail("118225", TEST_CASE_FAILED_STATUS, e.getMessage());
            addResultToTestRail("118226", TEST_CASE_FAILED_STATUS, e.getMessage());
            addResultToTestRail("118227", TEST_CASE_FAILED_STATUS, e.getMessage());
            addResultToTestRail("118228", TEST_CASE_FAILED_STATUS, e.getMessage());
            addResultToTestRail("118229", TEST_CASE_FAILED_STATUS, e.getMessage());
        }


    }

    @Test(groups = "d" ,dependsOnGroups = "a")
    public void labelTesting() {
        try {
            //This test is checking all statuses on row
            labelAndShipmentPage = dashboardPage.clickLabelAndShipmentPage();
            List<Map> rows = labelAndShipmentPage.getAllRowData();
            Integer count = 0;
            for(Map<String, String> row: rows){
                if(row.get("status").equalsIgnoreCase("PAID")){
                    count++;
                }
            }
            if(labelAndShipmentPage.createShippingInfo(0)){
                addResultToTestRail("118284",TEST_CASE_PASSED_STATUS,"");
            }else {
                addResultToTestRail("118284",TEST_CASE_FAILED_STATUS,"");
            }
            if(count < rows.size()){
                addResultToTestRail("118283",TEST_CASE_PASSED_STATUS,"");
                addResultToTestRail("118285",TEST_CASE_PASSED_STATUS, "");
            }else {
                addResultToTestRail("118283",TEST_CASE_FAILED_STATUS,"");
                addResultToTestRail("118285",TEST_CASE_FAILED_STATUS,"");
            }


            //trying to generate shipping info for full page
            try {
                if (labelAndShipmentPage.createShippingInfo(0)){
                    addResultToTestRail("118284",TEST_CASE_PASSED_STATUS,"");
                }else {
                    addResultToTestRail("118284",TEST_CASE_FAILED_STATUS,"");
                }

            } catch (Exception e) {
                addResultToTestRail("118284",TEST_CASE_FAILED_STATUS,e.getMessage());
                e.printStackTrace();
//                throw new Exception("Could not test labels " + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Testing CH Permissions
    @Test(groups = "e", dependsOnGroups = "d")
    public void testCHRegular() throws IOException, APIException {

        try {
            loginPage = dashboardPage.logOut();
            dashboardPage = loginPage.login(PrivateConfig.testUserCH,PrivateConfig.testUserPass);
            if (dashboardPage.isDashBoardPage()){
                addResultToTestRail("118295",TEST_CASE_PASSED_STATUS,"");
                addResultToTestRail("118296",TEST_CASE_PASSED_STATUS,"");
            }else {
                addResultToTestRail("118295",TEST_CASE_FAILED_STATUS,"");
                addResultToTestRail("118296",TEST_CASE_PASSED_STATUS,"");
            }
            if (dashboardPage.isUserSeeingProperly(Permissions.CH)){
                System.out.println("PROPER PERMISSIONS FOR CH");
            }else {
                System.out.println("INCORRECT PERMISSIONS FOR CH");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addResultToTestRail("118295",TEST_CASE_FAILED_STATUS,e.getMessage());
        }

    }

    //Testing CH Sales
    @Test(groups = "f", dependsOnGroups = "e")
    public void testCHSales() throws IOException, APIException {
        try{
            loginPage = dashboardPage.logOut();
            dashboardPage = loginPage.login(PrivateConfig.testUserCHSales, PrivateConfig.testUserPass);
            addResultToTestRail("118304",TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118305",TEST_CASE_PASSED_STATUS,"");
            newOrderPage = dashboardPage.clickCreateNewOrderPage();
            addResultToTestRail("118306",TEST_CASE_PASSED_STATUS,"");
            ordersPage = dashboardPage.clickOrdersPage();
            addResultToTestRail("118307",TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118309",TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118310",TEST_CASE_PASSED_STATUS,"");
        }catch (Exception e){
            e.printStackTrace();
            addResultToTestRail("118304",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118305",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118306",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118307",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118309",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118310",TEST_CASE_FAILED_STATUS,e.getMessage());
        }
    }

    @Test(groups = "g", dependsOnGroups = "f")
    public void testCHSupervisor() throws IOException, APIException {
        try{
            loginPage = dashboardPage.logOut();
            dashboardPage = loginPage.login(PrivateConfig.testUserSupervisor, PrivateConfig.testUserPass);
            addResultToTestRail("118327",TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118328",TEST_CASE_PASSED_STATUS,"");
            newOrderPage = dashboardPage.clickCreateNewOrderPage();
            addResultToTestRail("118329",TEST_CASE_PASSED_STATUS,"");
            ordersPage = dashboardPage.clickOrdersPage();
            addResultToTestRail("118332",TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118333",TEST_CASE_PASSED_STATUS,"");
        }catch (Exception e){
            e.printStackTrace();
            addResultToTestRail("118327",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118328",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118329",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118332",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118333",TEST_CASE_FAILED_STATUS,e.getMessage());
        }
    }
    @Test(groups = "h", dependsOnGroups = "g")
    public void testCHMaster() throws IOException, APIException {
        try{
            loginPage = dashboardPage.logOut();
            dashboardPage = loginPage.login(PrivateConfig.testUserMaster, PrivateConfig.testUserPass);
            addResultToTestRail("118312",TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118313",TEST_CASE_PASSED_STATUS,"");
            newOrderPage = dashboardPage.clickCreateNewOrderPage();
            addResultToTestRail("118315",TEST_CASE_PASSED_STATUS,"");
            ordersPage = dashboardPage.clickOrdersPage();
            addResultToTestRail("118314",TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118316",TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118317",TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118318",TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118319",TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118320",TEST_CASE_PASSED_STATUS,"");
            addResultToTestRail("118321",TEST_CASE_PASSED_STATUS,"");


        }catch (Exception e){
            e.printStackTrace();
            addResultToTestRail("118312",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118313",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118315",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118314",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118316",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118317",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118318",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118319",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118320",TEST_CASE_FAILED_STATUS,e.getMessage());
            addResultToTestRail("118321",TEST_CASE_FAILED_STATUS,e.getMessage());

        }
    }

}
