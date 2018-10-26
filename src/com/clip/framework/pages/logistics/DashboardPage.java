package com.clip.framework.pages.logistics;

import com.clip.framework.MainDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class DashboardPage extends MainDriver {
	// V- ELEMENT DECLARATION

		// v-- The assigned URL for this page (should be a hashmap later for env selection)
		String urls[] = {"https://dev-logistics.payclip.com/#/","https://stage-logistics.payclip.com/#/login"};

		
		// v-- WebElement Declaration
		@FindBy(how = How.XPATH, using = "//*[text()='Logistics Dashboard']")
		WebElement logisticsDashboardName;

		@FindBy(how = How.CSS, using = ".menu-link div div div div")
		List<WebElement> sidePanelPages;
		@FindBy(how = How.CSS, using = ".statusMenuLink")
		WebElement userButton;
		@FindBy(how = How.CSS, using = "a[href=\"#/logout\"] > div")
		WebElement logoutButton;



// V- METHOD DECLARATION
	// V- General page methods
		// v-- Initializes the driver and checks that the url is correct after the page has loaded	
		public DashboardPage(WebDriver driver) {
			try {
				this.driver = driver;
				PageFactory.initElements(driver, this);
				new WebDriverWait(driver,20).until(ExpectedConditions.visibilityOf(logisticsDashboardName));
				
				if(!driver.getCurrentUrl().contains("logistics")) {
					throw new IllegalStateException("This is not right page, was expecting dashboard");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public Boolean isDashBoardPage(){
			Boolean isTrue = false;
			try {
				if (driver.getCurrentUrl().contains("/#/")){
					isTrue = true;
				}
			}catch (Exception e){
				e.printStackTrace();
				throw new IllegalStateException("This is not the dashboard page");
			}
			return isTrue;
		}
		// v-- Basic click method to return orders page from for loop used for reusable code
		public OrdersPage clickOrdersPage(){
			try{
				for(WebElement link: sidePanelPages){
					if(link.getText().equalsIgnoreCase("Orders")){
						link.click();
						break;
					}
				}
			}catch (Exception e){
				throw new IllegalStateException("Could not click on the orders page " + e);
			}
			return new OrdersPage(driver);
		}
		// v-- Basic method to return new orders page
		public NewOrderPage clickCreateNewOrderPage(){
			try{
				for(WebElement link: sidePanelPages){
					if(link.getText().equalsIgnoreCase("Create new order")){
						link.click();
						break;
					}
				}
			}catch (Exception e){
				throw new IllegalStateException("Could not click on the create new orders page " + e);
			}
			return new NewOrderPage(driver);
		}

		public LabelAndShipmentPage clickLabelAndShipmentPage() {
			try{
				for(WebElement link: sidePanelPages){
					if(link.getText().equalsIgnoreCase("Awaiting Label & Shipment")){
						link.click();
						break;
					}
				}
			}catch (Exception e){
				e.printStackTrace();
				throw new IllegalStateException("Could not click label and shipment page " + e);
			}
			return new LabelAndShipmentPage(driver);
		}
	// User is passing in information on what user they are using
    public boolean isUserSeeingProperly(Permissions user) {
		Boolean isCorrect = false;
			try{
				Integer count = 0;
				switch (user){

					case SUPERVISOR:
						for (WebElement link: sidePanelPages){
							String text = link.getText();
							if (text.equalsIgnoreCase("Dashboard") || text.equalsIgnoreCase("Orders") || text.equalsIgnoreCase("Create New Order") || text.equalsIgnoreCase("Reports")){
								count++;
							}else if (text.equalsIgnoreCase("Awaiting Label & Shipment") || text.equalsIgnoreCase("Require Shipped Attention") || text.equalsIgnoreCase("Products") || text.equalsIgnoreCase("Products Prices") || text.equalsIgnoreCase("Label Print Queue")){
								count--;
							}
						}
						if (count < 4){
							isCorrect = false;
						}else {
							isCorrect = true;
						}
						break;

					case MASTER:
						for (WebElement link: sidePanelPages){
							String text = link.getText();
							if(text.equalsIgnoreCase("Dashboard") || text.equalsIgnoreCase("Orders") || text.equalsIgnoreCase("Create New Order") || text.equalsIgnoreCase("Reports") || text.equalsIgnoreCase("Awaiting Label & Shipment") || text.equalsIgnoreCase("Require Shipped Attention") || text.equalsIgnoreCase("Products") || text.equalsIgnoreCase("Products Prices") || text.equalsIgnoreCase("Label Print Queue")){
								count++;
							}
						}
						if (count < 9){
							isCorrect = false;
						}else {
							isCorrect = true;
						}
						break;

					case ADMIN:
						for (WebElement link: sidePanelPages){
							String text = link.getText();
							if(text.equalsIgnoreCase("Dashboard") || text.equalsIgnoreCase("Orders") || text.equalsIgnoreCase("Create New Order") || text.equalsIgnoreCase("Reports") || text.equalsIgnoreCase("Awaiting Label & Shipment") || text.equalsIgnoreCase("Require Shipped Attention") || text.equalsIgnoreCase("Products") || text.equalsIgnoreCase("Products Prices") || text.equalsIgnoreCase("Label Print Queue")){
								count++;
							}
						}
						if (count < 9){
							isCorrect = false;
						}else {
							isCorrect = true;
						}
						break;

					default:
						for (WebElement link: sidePanelPages){
							String text = link.getText();
							if (text.equalsIgnoreCase("Dashboard") || text.equalsIgnoreCase("Orders") || text.equalsIgnoreCase("Create New Order")){
								count++;
							}else if (text.equalsIgnoreCase("Awaiting Label & Shipment") || text.equalsIgnoreCase("Require Shipped Attention") || text.equalsIgnoreCase(("Reports")) || text.equalsIgnoreCase("Products") || text.equalsIgnoreCase("Products Prices") || text.equalsIgnoreCase("Label Print Queue")){
								count--;
							}
						}
						if (count < 3){
							isCorrect = false;
						}else {
							isCorrect = true;
						}
						break;

				}
			}catch (Exception e){

			}
		return isCorrect;
    }

	public LoginPage logOut() {
		try{
			userButton.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(logoutButton));
			logoutButton.click();

		}catch (Exception e){

		}
		return new LoginPage(driver);
	}


	// v-- Loads the assigned url for this page
}