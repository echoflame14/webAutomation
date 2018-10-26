package com.clip.tests.wordpress;

import static org.testng.Assert.assertThrows;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.clip.framework.MainDriver;
import com.clip.framework.pages.wordpress.Homepage;

public class HomePageElementTests extends MainDriver{
	
	
	@Test(groups = { "a" })
	public void elementCheckContactButton() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.contact.isDisplayed();
		}catch(Exception e){
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	
	}
	
	@Test(groups = { "a" })
	public void elementCheckContactParagraph() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.contact.click();
			homePage.contactParagraph.isDisplayed();
		}catch(Exception e){
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckHelpSite() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.helpSite.isDisplayed();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckMyAccount() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.myAccount.isDisplayed();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckShopButton() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.shopButton.isDisplayed();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckTopLeftClipLogo() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.topLeftClipLogo.isDisplayed();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckHeroBackground() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.heroBackground.isDisplayed();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckTagline() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.tagline.isDisplayed();
			
			assert homePage.tagline.getText().equals("La terminal portátil más completa");
			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckClipProLogo() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.clipProLogo.isDisplayed();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckCreditCardImg() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.creditCardImg.isDisplayed();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckAllCardsAccepted() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.allCardsAccepted.isDisplayed();
			assert homePage.allCardsAccepted.getText().equals("Acepta todas \n" + 
					"las tarjetas");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckNoPhoneImage() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.noPhoneImage.isDisplayed();
			assert homePage.noPhoneImage.getAttribute("src").equals("https://s3.amazonaws.com/test-wwwclipmx/images/hero/promo/pro/icon-mobile.svg");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckNoPhoneText() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.noPhoneText.isDisplayed();
			assert homePage.noPhoneText.getText().equals("Sin\n" + 
					"Celular");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckSecondSeparationDot() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.secondSeparationDot.isDisplayed();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckCalandarImage() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.calendarImage.isDisplayed();
			assert homePage.calendarImage.getAttribute("src").equals("https://s3.amazonaws.com/test-wwwclipmx/images/hero/promo/pro/icon-monthly.svg");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckNoMonthlyRent() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.noMonthlyRent.isDisplayed();
		assert homePage.noMonthlyRent.getText().equals("Sin rentas\n" + 
					"mensuales");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = "a")
	public void elementCheckIWantMyClipButton() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.iWantMyClipButton.isDisplayed();
			System.out.println("ran");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
			
		}
		try {
			homePage.iWantMyClipButton.click();
			new WebDriverWait(driver, 10);
			System.out.println(driver.getCurrentUrl());
			assert driver.getCurrentUrl().equals("https://dev-shop.payclip.com/?item=0003");
			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckRightSideClipProImg() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.rightSideClipProImg.isDisplayed();
			assert homePage.rightSideClipProImg.getAttribute("src").equals("https://s3.amazonaws.com/test-wwwclipmx/images/hero/promo/pro/banner-clip-pro-nuevo-wp-v2.png");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckCardLogos() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.cardLogos.isDisplayed();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckBanks() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.cardLogos.isDisplayed();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckScotia() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.scotia.isDisplayed();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckSantander() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.santander.isDisplayed();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckCompartir() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.compartir.isDisplayed();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	@Test(groups = { "a" })
	public void elementCheckFacebook() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.facebook.isDisplayed();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	@Test(groups = { "a" })
	public void elementCheckTwitter() {
		Homepage homePage = new Homepage(getDriver());
		try {
			homePage.twitter.isDisplayed();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
}

