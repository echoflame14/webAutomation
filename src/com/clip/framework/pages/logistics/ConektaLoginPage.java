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

public class ConektaLoginPage extends MainDriver {

    @FindBy(how = How.CSS, using = "#user_email")
    WebElement userInput;
    @FindBy(how = How.CSS, using = "#user_password")
    WebElement passInput;

    public ConektaLoginPage(WebDriver driver){
        try{
            this.driver = driver;
            PageFactory.initElements(driver, this);
            new WebDriverWait(driver,20).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".login")));

            if(!driver.getCurrentUrl().contains("login")) {
                throw new IllegalStateException("This is not right page, was expecting login page");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not initiate login page " + e);
        }
    }

    public ConektaDashboardPage login(String user, String pass){
        try{
            userInput.sendKeys(user);
            passInput.sendKeys(pass);
            Thread.sleep(180000); //allow user to solve captcha
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not login " + e);
        }
        return new ConektaDashboardPage(driver);
    }

}
