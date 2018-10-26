package com.clip.framework.pages.logistics;

import com.clip.framework.MainDriver;
import config.PrivateConfig;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class OrderDetailsPage extends MainDriver {

    String devDBConn = PrivateConfig.devDB;
    String devDBPass = PrivateConfig.devDBPass;
    String devDBUser = PrivateConfig.qaEmail;
    String stageDBConn = PrivateConfig.stageDB;
    String stageDBUser = PrivateConfig.qaEmail;
    String stageDBPass = PrivateConfig.stageDBPass;
    String dbConn = "";
    String dbUser = "";
    String dbPass = "";
    String table = "";

    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Customer Information')]/parent::div/div[1]/div/div[1]//div[not(self::strong)]")
    WebElement customerName;
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Customer Information')]/parent::div/div[1]/div/div[2]/*[not(self::strong)]")
    WebElement customerEmail;
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Customer Information')]/parent::div/div[1]/div/div[3]/*[not(self::strong)]")
    WebElement customerPhone;
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Customer Information')]/parent::div/div[1]/div/div[4]/*[not(self::strong)]")
    WebElement customerMerchantID;

    //Product Information
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Product Information')]/parent::div/div[2]/div/div[1]/*[not(self::strong)]")
    List<WebElement> productTypeList;
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Product Information')]/parent::div/div[2]/div/div[2]/*[not(self::strong)]")
    List<WebElement> productNameList;
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Product Information')]/parent::div/div[2]/div/div[3]/*[not(self::strong)]")
    List<WebElement> productQuantityList;
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Product Information')]/parent::div/div[2]/div/div[4]/*[not(self::strong)]")
    List<WebElement> calculatedAmountList;
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Product Information')]/parent::div/div[2]/div/div[5]/*[not(self::strong)]")
    List<WebElement> createdAtDateList;
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Product Information')]/parent::div/div[2]/div/div[6]/*[not(self::strong)]")
    List<WebElement> updatedAtDateList;
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Product Information')]/parent::div/div[2]/div/div[7]/*[not(self::strong)]")
    List<WebElement> pspList;

    //Order Status
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Order status')]/parent::div/div[3]/div/div[1]/div[1]/*[not(self::strong)]")
    WebElement paymentStatus;
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Order status')]/parent::div/div[3]/div/div[2]/*[not(self::strong)]")
    WebElement shippingStatus;
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Order status')]/parent::div/div[3]/div/div[3]/*[not(self::strong)]")
    WebElement trackingNumber;
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Order status')]/parent::div/div[3]/div/div[4]/*[not(self::strong)]")
    WebElement deliveryCompany;

    //Shipping Information
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Order status')]/parent::div/div[4]/div/div[1]/div[1]")
    WebElement shippingFirstRow;
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Order status')]/parent::div/div[4]/div/div[1]/div[2]")
    WebElement shippingSecondRow;
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Order status')]/parent::div/div[4]/div/div[1]/div[3]")
    WebElement shippingThirdRow;



    public OrderDetailsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        new WebDriverWait(driver,20).until(ExpectedConditions.textToBePresentInElementLocated(By.className("section-title") , "ORDER DETAILS"));

    }

    public Map<String, String> getOrderInfo(){
        Map<String, String> orderDeets = new HashMap<>();
        try{
            System.out.println(driver.getCurrentUrl());
            if (driver.getCurrentUrl().contains("dev")){
                dbConn = devDBConn;
                dbUser = devDBUser;
                dbPass = devDBPass;
                table = "dev";
            }else if (driver.getCurrentUrl().contains("stage")){
                dbConn = stageDBConn;
                dbUser = stageDBUser;
                dbPass = stageDBPass;
                table = "stage";
            }

            String url = driver.getCurrentUrl();
            String[] splitUrl = url.split("order/");
            String sql = "SELECT * FROM clip"+table+"_logistics.order_batch where order_batch_id=\""+splitUrl[1]+"\"";
            System.out.println(sql);
            Connection con = DriverManager.getConnection("JDBC:mysql://"+dbConn,dbUser,dbPass);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            Integer columns = md.getColumnCount();
            while (rs.next()){
                for(int i = 1; i <= columns; i++){
                    if(rs.getObject(i) != null){
                        System.out.println(md.getColumnName(i) + " - " + rs.getObject(i).toString());
                        orderDeets.put(md.getColumnName(i),rs.getObject(i).toString());
                    }
                }
                //TODO: find way to return all columns
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not get order info");
        }
        return orderDeets;
    }

    public OrderDetailsPage verifyOrderDetails(Map<String, String> orderDetails) {
        try{
            //concatenating name for testing
            String customerFullName = orderDetails.get("customer_first_name") + " " + orderDetails.get("customer_last_name");
            String createdAtDate = createdAtDateList.get(0).getText().replaceAll("Created At: ","");
            createdAtDate = convertOrderDetailsDateTime(createdAtDate);
            String updatedDate = updatedAtDateList.get(0).getText().replaceAll("Updated At: ", "");
            updatedDate = convertOrderDetailsDateTime(updatedDate);
            Integer calculatedAmountTotal = 0;
            for(WebElement we: calculatedAmountList){
                calculatedAmountTotal += Integer.parseInt(we.getText().replaceAll("Calculated Amount: ", ""));
            }

            //turning string date/time into DateTime





            assertEquals(customerFullName, customerName.getText().replaceAll("Name: ", ""),"Customer name did not match");
            assertEquals(orderDetails.get("customer_id"), customerEmail.getText().replaceAll("Email: ", ""), "Customer email did not match");
            assertEquals(orderDetails.get("customer_phone"), customerPhone.getText().replaceAll("Phone: ", ""), "Customer phone did not match");
            assertEquals(orderDetails.get("created_at").substring(0, orderDetails.get("created_at").length()-5), createdAtDate, "Created at dates did not match");
//            assertEquals(orderDetails.get("updated_at").substring(0, orderDetails.get("created_at").length()-5), updatedDate, "Updated at dates did not match");
            if(orderDetails.get("psp_type") != null){
                assertEquals(orderDetails.get("psp_type"), pspList.get(0).getText().replaceAll("PSP: ", ""), "PSP types did not match");
            }
            assertEquals(orderDetails.get("total_calculated_amount").substring(0, orderDetails.get("total_calculated_amount").length() -  3), String.valueOf(calculatedAmountTotal));

        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not verify order details page " + e);
        }
        return this;
    }

    public String convertOrderDetailsDateTime(String before){
        String finalDateTime = null;
        try {
            if(before.contains("th")){
                before = before.replaceAll("th","");
            }else if(before.contains("rd")){
                before = before.replaceAll("rd","");
            }else if(before.contains("st")){
                before = before.replaceAll("st", "");
            }else if(before.contains("nd")){
                before = before.replaceAll("nd","");
            }
            DateTimeFormatter formatter = DateTimeFormat.forPattern("MMM dd yyyy - hh:mm a");
            DateTime dt = formatter.parseDateTime(before);
            finalDateTime = dt.toString().replaceAll("T", " ").replaceAll(":00.000-06:00", "");
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not convert string " + e);
        }
        return finalDateTime;
    }
}
