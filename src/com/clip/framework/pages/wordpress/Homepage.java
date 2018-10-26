package com.clip.framework.pages.wordpress;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.clip.framework.MainDriver;

public class Homepage extends MainDriver{
	
	private final WebDriver driver;
	// V- ELEMENT DECLARATION
		// Main Navbar
			@FindBy(how = How.XPATH, using = "//a[@class='contact']")
			public
			WebElement contact;
			
			@FindBy(how = How.XPATH, using = "//div[@class='contact-information']//p")
			public
			WebElement contactParagraph;
			
			@FindBy(how = How.XPATH, using = "//a[@href='/ayuda']")
			public
			WebElement helpSite;
			
			@FindBy(how = How.CSS, using = "#button_header_sign_in")
			public
			WebElement myAccount;
			
			@FindBy(how = How.CSS, using = "#button_header_shop")
			public
			WebElement shopButton;
			
			@FindBy(how = How.XPATH, using = "//div[@class='navbar-header']//div[@class='logo']")
			public
			WebElement topLeftClipLogo;
			
		// Hero Image Section
			@FindBy(how = How.XPATH, using = "//div[@class='promo']")
			public
			WebElement heroBackground;
			
			@FindBy(how = How.XPATH, using = "//img[@class='pro-logo hidden-sm hidden-xs']")
			public
			WebElement clipProLogo; 
			
			@FindBy(how = How.XPATH, using = "/html[1]/body[1]/section[1]/div[1]/div[1]/div[1]/h1[1]")
			public
			WebElement tagline; // "La terminal portátil más completa "
			
			@FindBy(how = How.XPATH, using = "/html[1]/body[1]/section[1]/div[1]/div[1]/div[1]/ul[1]/img[1]")
			public
			WebElement creditCardImg;
			
			@FindBy(how = How.XPATH, using = "/html[1]/body[1]/section[1]/div[1]/div[1]/div[1]/ul[1]/li[1]")
			public
			WebElement allCardsAccepted; // "Acepta todas las tarjetas"
			
			@FindBy(how = How.XPATH, using = "//section[@class='hero-section promo-active']//ul//li[2]")
			public
			WebElement firstSeparationDot;
			
			@FindBy(how = How.XPATH, using = "/html[1]/body[1]/section[1]/div[1]/div[1]/div[1]/ul[1]/img[2]")
			public
			WebElement noPhoneImage;
			
			@FindBy(how = How.XPATH, using = "//section[@class='hero-section promo-active']//ul//li[3]")
			public
			WebElement noPhoneText;
			
			@FindBy(how = How.XPATH, using = "/html[1]/body[1]/section[1]/div[1]/div[1]/div[1]/ul[1]/li[4]")
			public
			WebElement secondSeparationDot;
			
			@FindBy(how = How.XPATH, using = "/html[1]/body[1]/section[1]/div[1]/div[1]/div[1]/ul[1]/img[3]")
			public
			WebElement calendarImage;
			
			@FindBy(how = How.XPATH, using = "/html[1]/body[1]/section[1]/div[1]/div[1]/div[1]/ul[1]/li[5]")
			public
			WebElement noMonthlyRent;
			
			@FindBy(how = How.CSS, using = "#button_hero_shop")
			public
			WebElement iWantMyClipButton;
			
			@FindBy(how = How.XPATH, using = "/html[1]/body[1]/section[1]/div[1]/div[1]/div[2]/img[1]")
			public
			WebElement rightSideClipProImg;
		
		// Brand Container
			
			@FindBy(how = How.CSS, using = "ody.landing:nth-child(2) section.cards-section.stop-opacity:nth-child(9) div.container.relative div.brands > div.cards")
			public
			WebElement cardLogos;
			
			@FindBy(how = How.XPATH, using = "//div[@class='banks']")
			public
			WebElement banks;
			
			@FindBy(how = How.XPATH, using = "//a[@class='scotia']")
			public
			WebElement scotia;
			
			@FindBy(how = How.XPATH, using = "//a[@class='santander']")
			public
			WebElement santander;
			
			@FindBy(how = How.XPATH, using = "//p[contains(text(),'Compartir:')]")
			public
			WebElement compartir;
			
			@FindBy(how = How.XPATH, using = "//a[@class='facebook']")
			public
			WebElement facebook;
			
			@FindBy(how = How.XPATH, using = "//a[@class='twitter']")
			public
			WebElement twitter;
			
			
	// V- METHOD DECLARATION
		public Homepage(WebDriver driver) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
			new WebDriverWait(driver, 10);
			System.out.println("current url = " + driver.getCurrentUrl());
		}
}
