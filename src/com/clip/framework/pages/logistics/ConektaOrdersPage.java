package com.clip.framework.pages.logistics;

import com.clip.framework.MainDriver;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;

public class ConektaOrdersPage extends MainDriver {
    @FindBy(how = How.CSS, using = ".orders-filters__dropdown")
    WebElement filterButton;
    @FindBy(how = How.CSS, using = "#select-filter-4-0")
    WebElement dateCheckbox;
    @FindBy(how = How.CSS, using = ".orders-index__container table tbody tr")
    List<WebElement> ordersList;
    @FindBy(how = How.CSS, using = ".ion-chevron-right")
    WebElement nextPageButton;

    public ConektaOrdersPage(WebDriver driver) {
        try {
            this.driver = driver;
            PageFactory.initElements(driver, this);
            new WebDriverWait(driver, 20).until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".section-bar__title"), "Orders"));

            if (!driver.getCurrentUrl().contains("/orders?")) {
                throw new IllegalStateException("This is not right page, was expecting orders page");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not get orders page " + e);
        }
    }
    public ConektaOrdersPage setOrdersFiltersForDateRange(String from, String to){
        try{

            filterButton.click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(dateCheckbox));
            dateCheckbox.click();
            Select dateFilter = new Select(driver.findElement(By.cssSelector("select[name='date-filter']")));
            dateFilter.selectByVisibleText("Between");
            WebElement minDate = driver.findElement(By.cssSelector("input[name='min-date']"));
            WebElement maxDate = driver.findElement(By.cssSelector("input[name='max-date']"));
            minDate.sendKeys(from);
            maxDate.sendKeys(to);
            WebElement apply = driver.findElement(By.cssSelector("button[type='submit']"));
            apply.click();
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not set order filters " + e);
        }
        return this;
    }
    public ConektaOrdersPage getRowInformation(DateTime from, DateTime to){
        try{
            Boolean isFinished = false;
            WritableWorkbook workbook = null;
            workbook = Workbook.createWorkbook(new File("~/Desktop/test.xls"));
            WritableSheet sheet = workbook.createSheet("Sheet 1",0);

            while (!isFinished) {
                for (int i = 0; i < ordersList.size(); i++) { //each row
                    String date = ordersList.get(i).findElement(By.cssSelector("td:nth-child(1)")).getText();
                    System.out.println("FROM: " + from + "\n" + "TO: " + to);
                    System.out.println(date);
                    DateTime convertedFinal = DateTimeFormat.forPattern("dd MMMM yyyy hh:mm aa").parseDateTime(date);
                    System.out.println(convertedFinal.toString());

                    if (convertedFinal.isBefore(to) && convertedFinal.isAfter(from)) {
                        System.out.println(convertedFinal.toString() + " FITS CRITERIA ");

                    }else {
                        isFinished = true;
                        break;
                    }
                }
                nextPageButton.click();
                Thread.sleep(5000);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not get row information " + e);
        }
        return this;
    }
}
