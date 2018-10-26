package com.clip.framework.pages.logistics;

import com.clip.framework.MainDriver;
import com.clip.framework.MainGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class NewOrderPage extends MainDriver {

    // Generated info from MainGenerator
    MainGenerator generator = new MainGenerator();
    Map<String, String> customerNameInfo = new HashMap<>();
    Map<String, String> customerAddressInfo = new HashMap<>();
    long customerPhoneInfo;

    @FindBy(how = How.CSS, using = ".section-title")
    WebElement sectionTitle;

    // Section 1 - Customer Details
    @FindBy(how = How.CSS, using = "input[value='on']")
    WebElement guestOrderCheckBox;
    @FindBy(how = How.CSS, using = "input[name='firstName']")
    WebElement firstNameInput;
    @FindBy(how = How.CSS, using = "input[name='lastName']")
    WebElement lastNameInput;
    @FindBy(how = How.CSS, using = "input[name='email']")
    WebElement emailInput;
    @FindBy(how = How.CSS, using = "input[name='phone']")
    WebElement phoneInput;
    @FindBy(how = How.CSS, using = ".showB")
    WebElement nextButton;
    @FindBy(how = How.CSS, using = "input[name=\"merchant_name\"]")
    WebElement merchantInput;

    // Section 2 - Product Details
    @FindBy(how = How.CSS, using = "div[name=\"product\"]")
    List<WebElement> productSelectBox;
    @FindBy(how = How.CSS, using = "div[name='productName']")
    List<WebElement> productNameList;
    @FindBy(how = How.CSS, using = " div div div[role='presentation'] div > div > span > div > div > div")
    List<WebElement> activeDropDowns; // This one is tricky, I think this is the best way to handle all drop downs
    @FindBy(how = How.CSS, using = "div[id^='basePrice']")
    List<WebElement> pricesInput;
    @FindBy(how = How.CSS, using = "#neworder_qty")
    List<WebElement> quantityInput;
    @FindBy(how = How.XPATH, using = "//p[text()='Add Product']/..//div//button")
    WebElement addProductButton;
    @FindBy(how = How.CSS, using = ".showB > button > div > div > span")
    WebElement nextButtonToShipping;
    @FindBy(how = How.CSS, using = "#neworder_price")
    WebElement totalPriceInput;

    // Section 3 - Shipping Details
    @FindBy(how = How.CSS, using = "input[name='reuse']")
    WebElement sameCheckboxInput;
    @FindBy(how = How.CSS, using = "input[name='address1']")
    WebElement adress1FieldInput;
    @FindBy(how = How.CSS, using = "input[name='colony']")
    WebElement colonyInput;
    @FindBy(how = How.CSS, using = "input[name='municipality']")
    WebElement municipalityInput;
    @FindBy(how = How.CSS, using = "input[name='city']")
    WebElement cityInput;
    @FindBy(how = How.CSS, using = "input[name='state']")
    WebElement stateInput;
    @FindBy(how = How.CSS, using = "input[name='country']")
    WebElement countryInput;
    @FindBy(how = How.CSS, using = "input[name='zipcode']")
    WebElement postalCodeInput;
    @FindBy(how = How.CSS, using = "input[name='reference_house']")
    WebElement referenceHouse;
    @FindBy(how = How.CSS, using = "input[name='reference_location']")
    WebElement referenceLocation;
    @FindBy(how = How.CSS, using = "textarea[name='comments']")
    WebElement commentsInput;
    @FindBy(how = How.CSS, using = "#submit_btn")
    WebElement reviewOrderButton;
    // Review Order Information
    @FindBy(how = How.XPATH, using = "//span[text()='Submit']")
    WebElement submitButton;
    @FindBy(how = How.XPATH, using = "//span[text()='Continue']")
    WebElement continueButton;

    public NewOrderPage(WebDriver driver){
        try {
            this.driver = driver;
            PageFactory.initElements(driver, this);
            new WebDriverWait(driver,20).until(ExpectedConditions.textToBePresentInElementLocated(By.className("section-title") , "CREATE NEW ORDER"));

            if(!driver.getCurrentUrl().contains("orders/new")) {
                throw new IllegalStateException("This is not right page, was expecting new orders page");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Issues pertaining to constructor on new orders page " + e);
        }
    }
    // Step one of new order flow
    public NewOrderPage fillOutGuestCustomerInformation() {

        try{
            guestOrderCheckBox.click();
            String customerFirstName = generator.generateNewNameStuff().get(0).getFirstName();
            String customerLastName = generator.generateNewNameStuff().get(0).getLastName();
            customerNameInfo = generator.generateNameInfo();
            customerPhoneInfo = generator.generateMexPhoneNumber();

            firstNameInput.sendKeys(customerFirstName);
            lastNameInput.sendKeys(customerLastName);
            emailInput.sendKeys(customerFirstName + "." + customerLastName + "@fakeemail.com");
            phoneInput.sendKeys(Long.toString(customerPhoneInfo));

        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not fill out guest customer information " + e);
        }
        return this;
    }

    public NewOrderPage clickNextButtonToProductDetails(){
        try{
            nextButton.click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("form h3") , "Product Details"));
        }catch(Exception e){
            throw new IllegalStateException("could not click next button " + e);
        }
        return this;
    }

    public NewOrderPage addAllProductstoOrder(){
        try{
            //Sticker
            productSelectBox.get(0).click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
            activeDropDowns.get(0).click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));

            //Default sticker name
            productNameList.get(0).click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
            activeDropDowns.get(0).click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));

            //Prices input selection
            pricesInput.get(0).click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
            activeDropDowns.get(0).click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));

            //quantity input
            quantityInput.get(0).sendKeys("1");
            addProductButton.click();


            // Loops for all readers
            for (int i = 1; i < 3; i++){

                //select product
                productSelectBox.get(i).click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                activeDropDowns.get(1).click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));

                //select product name
                productNameList.get(i).click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                activeDropDowns.get(i-1).click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));

                //selecting price
                pricesInput.get(i).click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                activeDropDowns.get(0).click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));
                //selecting 1 quantity
                quantityInput.get(i).sendKeys("1");
                if (i < 2) {
                    addProductButton.click();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not add all products to order " + e);
        }
        return this;
    }

    public NewOrderPage clickNextButtonToShippingInformation() {
        try{
            nextButtonToShipping.click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("form > div > div> div > h3") , "Shipping Information"));
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not click next button to shipping information " + e);
        }
        return this;
    }

    //Checks all prices of order and evaluates the total price amount
    public boolean isTotalPriceAccurate() {
        Boolean isCorrect = false;
        try{
            List<Integer> prices = new ArrayList<>();
            List<Integer> quantityList = new ArrayList<>();
            //creating list of prices and quantities
            for (WebElement price: pricesInput){
                prices.add(Integer.parseInt(price.getText().replaceAll("\\D+","")));
            }
            for(WebElement quantity: quantityInput){
                quantityList.add(Integer.parseInt(quantity.getAttribute("value")));
            }
            //total price starting at 0
            Integer total = 0;
            //quantity * price
            List<Integer> pricesUpdated = new ArrayList<>();
            for(int i = 0; i<prices.size();i++){
                pricesUpdated.add(prices.get(i) * quantityList.get(i));
            }
            for (Integer number:pricesUpdated){
                total += number;
            }
            System.out.println("Total Price: " + total);
            Integer totalViewedPrice = Integer.parseInt(totalPriceInput.getAttribute("value").replaceAll("\\D+" , ""));
            System.out.println("Viewed Price: " + totalViewedPrice);
            if (total.equals(totalViewedPrice)){
                isCorrect = true;
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not verify if total price is accurate " + e);
        }
        return isCorrect;
    }

    public NewOrderPage fillOutShippingInformation() {
        try{
            reuseReceiverSameAs();
            generateShippingInfo(true);
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not fill out shipping information " + e);
        }
        return this;
    }

    public NewOrderPage generateShippingInfo(Boolean isRodandoYDando) {
        try{
            Map<String, String> address = generator.generateAddressInfo();
            adress1FieldInput.sendKeys(address.get("addressLine1"));
            colonyInput.sendKeys(address.get("colony"));
            municipalityInput.sendKeys(address.get("municipality"));
            cityInput.sendKeys("CDMX");
            stateInput.sendKeys(address.get("state"));
            countryInput.sendKeys(address.get("country"));
            postalCodeInput.sendKeys(address.get("postalCode"));
            referenceHouse.sendKeys("reference house");
            referenceLocation.sendKeys("reference loc");
            commentsInput.sendKeys("this is an automation test order");
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not generate shipping info " + e);
        }
        return this;
    }

    public OrdersPage clickSubmitButton() {
        try{
            submitButton.click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".order_details")));
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Continue']")));
            continueButton.click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[text()='Continue']")));
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not click submit button " + e);
        }
        return new OrdersPage(driver);
    }

    public NewOrderPage clickReviewOrder() {
        try {
            reviewOrderButton.click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".order_details")));

        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not click review order");
        }
        return this;
    }

    // Method used for the checkbox to autopopulate name/email/phone inputs on step three
    public NewOrderPage reuseReceiverSameAs() {
        try{
            Thread.sleep(1000);
            //weird js behavior so this minimizes the errors
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", sameCheckboxInput);
//            sameCheckboxInput.click();
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not complete receiver same as input" + e);
        }
        return this;
    }

    public NewOrderPage addRandomProductsToOrder() {
        try {
            Random rand = new Random();
            Integer loop = rand.nextInt(2)+1;
            for(int i=0; i<loop;i++ ){
                //select product
                productSelectBox.get(i).click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                //TODO: Change back to -1
                Integer randProduct = rand.nextInt(activeDropDowns.size()-2)+1;
                activeDropDowns.get(randProduct).click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));

                //select product name
                productNameList.get(i).click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                System.out.println(activeDropDowns.size()-1);
                if((activeDropDowns.size()-1) == 0){
                    activeDropDowns.get(0).click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));
                }else {
                    Integer randName = rand.nextInt(activeDropDowns.size() - 1) + 1;
                    System.out.println("RANDNAME: " + randName);
                    activeDropDowns.get(randName).click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));
                }
                //selecting price
                pricesInput.get(i).click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                if((activeDropDowns.size()-1) == 0){
                    activeDropDowns.get(0).click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));
                }else {
                    Integer randPrice = rand.nextInt(activeDropDowns.size() - 1) + 1;
                    activeDropDowns.get(randPrice).click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));
                }
                    //selecting 1 quantity
                    Integer randQuanitity = rand.nextInt(2) + 1;
                    quantityInput.get(i).sendKeys(Integer.toString(randQuanitity));


                if (i<loop-1){
                    addProductButton.click();
                }
            }
        }catch (Exception e){
            throw new IllegalStateException("Could not add random products to order " + e);
        }
        return this;
    }

    public NewOrderPage fillOutMerchantInformation(Enviroment envo){
        String searchString = "";
        try{
            switch (envo){
                case DEV:
                    searchString = "ben.spurlock@payclip.com";
                    break;
                default:
                    searchString = "ben.spurlock@payclip.com";
                    break;
            }
            merchantInput.sendKeys(searchString);
            Thread.sleep(2000);
//          new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOf())


        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not fill out merchant information " + e);
        }
        return this;
    }

    public boolean isNewOrderPage() {
        Boolean isPage = false;
        try{
            if(sectionTitle.getText().equalsIgnoreCase("create new order")){
                isPage = true;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not see if page is correct " + e);
        }
        return isPage;
    }
}

