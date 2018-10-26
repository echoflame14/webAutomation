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

public class LoginPage extends MainDriver {
// V- ELEMENT DECLARATION

	private final WebDriver driver;

	// v-- WebElement Declaration
	@FindBy(how = How.XPATH, using = "//input[@name='username']")
	WebElement usernameInput;

	@FindBy(how = How.XPATH, using = "//input[@name='password']")
	WebElement passwordInput;

	@FindBy(how = How.CSS, using = "#submit_btn")
	WebElement clipLoginButton;


// V- METHOD DECLARATION
	// V- General page methods
		// v-- Initializes the driver and checks that the url is correct after the page has loaded
		public LoginPage(WebDriver driver) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
//			driver.get(url);

			System.out.println(driver.getCurrentUrl());
			new WebDriverWait(driver,20).until(ExpectedConditions.visibilityOfElementLocated(By.className("login-container")));
			System.out.println(driver.getCurrentUrl());
		}

		// v-- Enters a username + password and tries to log in
		public DashboardPage login(String user, String pass) {
			try {
				usernameInput.sendKeys(user);
				passwordInput.sendKeys(pass);
				clipLoginButton.click();
			}catch(Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("Could not login to dashboard page " + e);
			}
			return new DashboardPage(driver);
		}

}
