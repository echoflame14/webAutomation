package com.clip.framework.pages.logistics;

import com.clip.framework.MainDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelAndShipmentPage extends MainDriver {

    @FindBy(how = How.CSS, using = ".clip-table tbody tr")
    List<WebElement> ordersList;
    @FindBy(how = How.CSS, using = " div div div[role='presentation'] div > div > span > div > div > div")
    List<WebElement> activeDropDowns;
    @FindBy(how = How.CSS, using = "div[role='presentation'] > div")
    List<WebElement> popUpWindow;
    @FindBy(how = How.XPATH, using = "//span[text()='Continue']")
    WebElement continueButton;
    @FindBy(how = How.XPATH, using = "//span[text()='Close']")
    List<WebElement> closeButton;


    public LabelAndShipmentPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        new WebDriverWait(driver,20).until(ExpectedConditions.textToBePresentInElementLocated(By.className("section-title") , "AWAITING LABEL & SHIPMENT"));
        if (!driver.getCurrentUrl().contains("list/awaitingShipping")){
            Assert.fail("This is not the label and shipment page");
        }
        
    }

    public LabelAndShipmentPage createShippingInfoFullPage() {
        try{
            List<Map> rowData = getAllRowData();
            Integer completed = 0;
            Integer failed = 0;
            for (int i = 0; i < rowData.size(); i++) {
                if (rowData.get(i).get("status").toString().equalsIgnoreCase("paid")){
                    ordersList.get(i).findElement(By.cssSelector("button")).click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                    popUpWindow.get(1).findElement(By.xpath("//div[text()='Domestic Express']")).click();
                    new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".show")));
                    new WebDriverWait(driver, 30).until(ExpectedConditions.or(
                            ExpectedConditions.visibilityOf(continueButton),
                            ExpectedConditions.visibilityOfAllElements(closeButton)
                    ));
                    if (closeButton.size() <  1){
                        continueButton.click();
                        completed++;
                    }else {
                        closeButton.get(0).click();
                        failed++;
                    }
                    Thread.sleep(1000);
//                    new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOf(continueButton));
                }
            }
            updateSlackEvent("Completed Shipping: " + completed + "\n" + "Failed Shipping: " + failed);
            System.out.println("Completed: " + completed);
            System.out.println("Failed: " + failed);
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not create shipping info full page " + e);
        }
        return this;
    }

    public Boolean createShippingInfo(Integer row){
        Boolean worked = false;
        List<Map> rowData = getAllRowData();
        try {
            if (rowData.get(row).get("status").toString().equalsIgnoreCase("paid")){
                ordersList.get(row).findElement(By.cssSelector("button")).click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                popUpWindow.get(1).findElement(By.xpath("//div[text()='Domestic Express']")).click();
                new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".show")));
                new WebDriverWait(driver, 30).until(ExpectedConditions.or(
                        ExpectedConditions.visibilityOf(continueButton),
                        ExpectedConditions.visibilityOfAllElements(closeButton)
                ));
                if (closeButton.size() <  1){
                    continueButton.click();
                    worked = true;
                }else {
                    closeButton.get(0).click();
                    worked = false;
                }
                Thread.sleep(1000);
//                    new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOf(continueButton));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return worked;
    }

    public List<Map> getAllRowData() {
        Map<String, String> row = new HashMap<>();
        List<Map> rowsData = new ArrayList<>();
        try{
            for (int i = 0; i < ordersList.size(); i++){
                row.put("ordernumber", ordersList.get(i).findElement(By.cssSelector(".clip-table tbody tr td:nth-child(2) div > div")).getText());
                row.put("status", ordersList.get(i).findElement(By.cssSelector(".clip-table tbody tr td:nth-child(4) span")).getText());
                row.put("shippingstatus",ordersList.get(i).findElement(By.cssSelector(".clip-table tbody tr td:nth-child(9) span")).getText());
                row.put("ordertype", ordersList.get(i).findElement(By.cssSelector(".clip-table tbody tr td:nth-child(12) span")).getText());
                rowsData.add(row);
                row = new HashMap<>();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not get all row data " + e );
        }
        return rowsData;
    }
}
