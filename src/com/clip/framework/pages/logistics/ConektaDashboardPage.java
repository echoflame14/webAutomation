package com.clip.framework.pages.logistics;

import com.clip.framework.MainDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConektaDashboardPage extends MainDriver {

    @FindBy(how = How.CSS, using = "a[href='/orders?']")
    WebElement ordersPageButton;
    @FindBy(how = How.CSS, using = "#locale")
    Select languageSelect;

    public ConektaDashboardPage(WebDriver driver){
        try{
            this.driver = driver;
            PageFactory.initElements(driver, this);
            new WebDriverWait(driver,20).until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".section-bar__title"),"Dashboard"));

            if(!driver.getCurrentUrl().contains("admin.conekta.com")) {
                throw new IllegalStateException("This is not right page, was expecting login page");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not initiate login page " + e);
        }
    }
    public ConektaOrdersPage clickOrdersPage(){
        try{
            ordersPageButton.click();
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not click orders page " + e);
        }
        return new ConektaOrdersPage(driver);
    }

    public ConektaDashboardPage setToEnglish() {
        try{
            languageSelect.deselectByValue("en");
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not set to english " + e);
        }
        return this;
    }
}
