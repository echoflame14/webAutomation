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

import java.util.*;

public class OrdersPage extends MainDriver {

    //orders page
    @FindBy(how = How.XPATH, using = "//*[text()='Criteria']/..")
    WebElement criteriaInput;
    @FindBy(how = How.CSS, using = ".clip-table tbody tr")
    List<WebElement> ordersList;
    @FindBy(how = How.CSS, using = "div[role='presentation'] > div")
    List<WebElement> popUpWindow;

    //Pay by card section
    @FindBy(how = How.CSS, using = "input[name=\"radio-button-debit\"]")
    WebElement debitRadio;
    @FindBy(how = How.CSS, using = "input[name=\"radio-button-credit\"]")
    WebElement creaditRadio;
    @FindBy(how = How.CSS, using = "div[name='ch_payments']")
    WebElement chPayments;
    @FindBy(how = How.CSS, using = "input[name='ch_name']")
    WebElement chName;
    @FindBy(how = How.CSS, using = "input[name='ch_email']")
    WebElement chemail;
    @FindBy(how = How.CSS, using = "input[name='ch_cardnumber']")
    WebElement chcardNumber;
    @FindBy(how = How.CSS, using = "input[name='ch_cvv']")
    WebElement chCVV;
    @FindBy(how = How.CSS, using = "div[name='ch_exp_month']")
    WebElement chExpMonth;
    @FindBy(how = How.CSS, using = "div[name='ch_exp_year']")
    WebElement chExpYear;
    @FindBy(how = How.CSS, using = "input[name='ch_add_1']")
    WebElement chAddressField1;
    @FindBy(how = How.CSS, using = "input[name='ch_add_2']")
    WebElement chAddressField2;
    @FindBy(how = How.CSS, using = "input[name='ch_add_3']")
    WebElement chAddressField3;
    @FindBy(how = How.CSS, using = "input[name='ch_state']")
    WebElement chStateInput;
    @FindBy(how = How.CSS, using = "input[name='ch_city']")
    WebElement chCityInput;
    @FindBy(how = How.CSS, using = "input[name='ch_zipcode']")
    WebElement chPostalCodeInput;
    @FindBy(how = How.CSS, using = "div[name='ch_country_code']")
    WebElement chCountryInput;
    @FindBy(how = How.CSS, using = "textarea[name='ch_comments']")
    WebElement chCommentsInput;
    @FindBy(how = How.XPATH, using = "//span[text()='Submit']")
    WebElement submitButton;
    @FindBy(how = How.CSS, using = " div div div[role='presentation'] div > div > span > div > div > div")
    List<WebElement> activeDropDowns;

    //Buttons
    @FindBy(how = How.XPATH, using = "//span[text()='Continue']")
    WebElement continueButton;

    @FindBy(how = How.XPATH, using = "//span[text()='Close']")
    List<WebElement> closeButton;

    @FindBy(how = How.XPATH, using = "//span[text()='All details']")
    WebElement allDetailsButton;


    public OrdersPage(WebDriver driver){
        try {
            this.driver = driver;
            PageFactory.initElements(driver, this);
            new WebDriverWait(driver,20).until(ExpectedConditions.textToBePresentInElementLocated(By.className("section-title") , "ORDERS"));

            if(!driver.getCurrentUrl().contains("orders/list")) {
                throw new IllegalStateException("This is not right page, was expecting dashboard");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Issues pertaining to constructor on orders page " + e);
        }
    }
    //Pay prders by card bool is for if we are doing negative testing or not
    public OrdersPage payByCard(Boolean monthlyInstallments, Boolean goingToWork){
        try{
            Random rand = new Random();
            List<String> cardNums = new ArrayList<>();
            cardNums.add("4111111111111111");
            cardNums.add("4242424242424242");
            cardNums.add("5555555555554444");
            cardNums.add("5105105105105100");
            cardNums.add("345678000000007");
            cardNums.add("341111111111111");
            cardNums.add("343434343434343");
            if(goingToWork){
                if(!monthlyInstallments){
                    debitRadio.click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(chName));
                }else {
                    creaditRadio.click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(chName));
                    chPayments.click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                    activeDropDowns.get(2).click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));
                }
                String card = cardNums.get(rand.nextInt(cardNums.size() -1 ));
                chName.sendKeys("Automation Tester");
                chemail.sendKeys("auto.mater@payclip.com");
                chcardNumber.sendKeys(card);
                if(card.length() == 15){
                    chCVV.sendKeys("1234");
                }else {
                    chCVV.sendKeys("123");
                }
                //month and year section
                chExpMonth.click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                activeDropDowns.get(rand.nextInt(11)).click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));
                chExpYear.click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                activeDropDowns.get(rand.nextInt(7 - 1 ) + 1 ).click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));

                //addressfields
                chAddressField1.sendKeys("Address 1");
                chAddressField2.sendKeys("Municipality");
                chAddressField3.sendKeys("City");
                chStateInput.sendKeys("Utah");
                chCityInput.sendKeys("Draper");
                chPostalCodeInput.sendKeys("84115");
                chCountryInput.click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                activeDropDowns.get(rand.nextInt(11)).click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));
                chCommentsInput.sendKeys("this is a payment through automation");

                //submit then confirmation of success
                submitButton.click();
//                new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Continue']")));
                new WebDriverWait(driver, 30).until(ExpectedConditions.or(
                        ExpectedConditions.visibilityOf(continueButton),
                        ExpectedConditions.visibilityOfAllElements(closeButton)
                ));
                if (closeButton.size() > 0){
                    System.out.println("Error with payment");
                    closeButton.get(0).click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(closeButton));
                }else {
                    System.out.println("payment was successfull");
                    continueButton.click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[text()='Continue']")));
                }


            }

        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not use the pay by card system " + e);
        }
        return this;
    }
    //This method will pay all  orders by card on one page, does not refresh page with each order
    public OrdersPage payAllInternalOrdersByCard() {
        try{
            List<Map> orderRows = new ArrayList<>();
            Boolean installments = false;
            orderRows = getAllRowData();
            for (int i = 0; i < orderRows.size();i++){
                System.out.println("checking order list number " + i + " of " + orderRows.size());
                if (orderRows.get(i).get("status").toString().equalsIgnoreCase("Pending") && orderRows.get(i).get("ordertype").toString().equalsIgnoreCase("guest")){
                    Integer price = Integer.parseInt(orderRows.get(i).get("price").toString());
                    System.out.println(price);
                    if( price > 700){
                        installments = true;
                    }else {
                        installments = false;
                    }

                    ordersList.get(i).findElement(By.cssSelector("button")).click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                    popUpWindow.get(1).findElement(By.xpath("//div[text()='Pay By Card']")).click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".pcbForm")));
                    Thread.sleep(1000);
                    payByCard(installments,true);
                    System.out.println("Order "+ i +" was a success");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not pay all internal orders by card " + e);
        }

        return this;
    }
    //TODO: THIS
    public OrderDetailsPage clickOrderByRowNum(int row){
        try{
            ordersList.get(row).findElement(By.cssSelector(".clip-table tbody tr td:nth-child(2) div div")).click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".order_details span"), "ORDER DETAILS"));
            allDetailsButton.click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".order_details")));
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not click order by row num " + e);
        }
        return new OrderDetailsPage(driver);
    }

    public OrdersPage payPendingOrderByCard(Integer rowNum){
        try {
            ordersList.get(rowNum).findElement(By.cssSelector("button")).click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
            popUpWindow.get(1).findElement(By.xpath("//div[text()='Pay By Card']")).click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".pcbForm")));
            Thread.sleep(1000);
            payByCard(false,true);
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not pay pending order by card " + e);
        }
        return this;
    }

    //Used to help get info from each row. Can add to row for important information needed
    public List<Map> getAllRowData() {
        Map<String, String> row = new HashMap<>();
        List<Map> rowsData = new ArrayList<>();
        try{
            for (int i = 0; i < ordersList.size(); i++){
                row.put("ordernumber", ordersList.get(i).findElement(By.cssSelector(".clip-table tbody tr td:nth-child(2) a")).getText());
                row.put("status", ordersList.get(i).findElement(By.cssSelector(".clip-table tbody tr td:nth-child(4) span")).getText());
                row.put("shippingstatus",ordersList.get(i).findElement(By.cssSelector(".clip-table tbody tr td:nth-child(9) span")).getText());
                row.put("ordertype", ordersList.get(i).findElement(By.cssSelector(".clip-table tbody tr td:nth-child(12) span")).getText());
                row.put("price", ordersList.get(i).findElement(By.cssSelector("td:nth-child(5)")).getText());
                rowsData.add(row);
                row = new HashMap<>();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not get all row data " + e );
        }
        return rowsData;
    }
    //used to set criteria in header of orders page
    public OrdersPage setCriteria(String criteria){
        try {
            criteriaInput.click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
            for (WebElement we : activeDropDowns){
                if (we.getText().equalsIgnoreCase(criteria)){
                    we.click();
                    break;
                }
            }
            new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));


        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Could not set criteria " + e);
        }
        return this;
    }

    public OrdersPage testAllSandboxCardPayments() {
        Random rand = new Random();
        try {
            //Good cards
            Map<String, String> card = new HashMap<>();
            List<Map> cards = new ArrayList<>();
            card.put("number","4111111111111111");
            card.put("test","109131");
            card.put("error","no");
            card.put("credit","no");
            cards.add(card);
            card = new HashMap<>();
            card.put("number","5555555555554444");
            card.put("test","109133");
            card.put("error","no");
            card.put("credit","no");
            cards.add(card);
            card = new HashMap<>();
            card.put("number","341111111111111");
            card.put("test","109132");
            card.put("error","no");
            card.put("credit","yes");
            cards.add(card);
            //Bad cards
            card = new HashMap<>();
            card.put("number","4222222222222220");
            card.put("test","109134");
            card.put("error","yes");
            card.put("credit","no");
            cards.add(card);
//            card = new HashMap<>();
//            card.put("number","4444444444444448");
//            card.put("test","109136");
//            card.put("error","yes");
//            card.put("credit","no");
//            cards.add(card);
            card = new HashMap<>();
            card.put("number","4000000000000119");
            card.put("test","109137");
            card.put("error","yes");
            card.put("credit","no");
            cards.add(card);
            card = new HashMap<>();
            card.put("number","4000000000000044");
            card.put("test","109138");
            card.put("error","yes");
            card.put("credit","no");
            cards.add(card);
//            card = new HashMap<>();
//            card.put("number","340000000000009");
//            card.put("test","109139");
//            card.put("error","yes");
//            cards.add(card);
//            card = new HashMap<>();
//            card.put("number","373737373737374");
//            card.put("test","109140");
//            card.put("error","yes");
//            card.put("credit","no");
//            cards.add(card);
            card = new HashMap<>();
            card.put("number","370000000000002");
            card.put("test","109141");
            card.put("error","yes");
            card.put("credit","yes");
            cards.add(card);
            card = new HashMap<>();


            for (int i = 0; i < cards.size(); i++){
                try {
                    ordersList.get(0).findElement(By.cssSelector("button")).click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                    popUpWindow.get(1).findElement(By.xpath("//div[text()='Pay By Card']")).click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".pcbForm")));
                    Thread.sleep(1000);

                    //PBC Modal
                    if(cards.get(i).get("credit").equals("yes")){
                        creaditRadio.click();
                        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(chName));
                        chPayments.click();
                        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                        activeDropDowns.get(1).click();
                        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));
                    }else{
                        debitRadio.click();
                        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(chName));
                    }

                    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(chName));
                    chName.sendKeys("Automation Tester");
                    chemail.sendKeys("auto.mater@payclip.com");
                    chcardNumber.sendKeys(cards.get(i).get("number").toString());
                    if(cards.get(i).get("number").toString().length() == 15){
                        chCVV.sendKeys("1234");
                    }else {
                        chCVV.sendKeys("123");
                    }
                    //month and year section
                    chExpMonth.click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                    activeDropDowns.get(rand.nextInt(11)).click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));
                    chExpYear.click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                    activeDropDowns.get(rand.nextInt(7 - 1 ) + 1 ).click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));

                    //addressfields
                    chAddressField1.sendKeys("Address 1");
                    chAddressField2.sendKeys("Colony");
                    chAddressField3.sendKeys("Municipality");
                    chStateInput.sendKeys("Utah");
                    chCityInput.sendKeys("Draper");
                    chPostalCodeInput.sendKeys("84115");
                    chCountryInput.click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(activeDropDowns));
                    activeDropDowns.get(rand.nextInt(170)).click();
                    new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(activeDropDowns));
                    chCommentsInput.sendKeys("this is a payment through automation");

                    //submit then confirmation of success
                    submitButton.click();
                    //not expecting an error
                    if(cards.get(i).get("error").toString().equalsIgnoreCase("no")){
                        new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(continueButton));
                        continueButton.click();
                        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[text()='Continue']")));
                    }
                    //Expecting an error on sandbox
                    else {
                        new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfAllElements(closeButton));
                        closeButton.get(0).click();
                        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfAllElements(closeButton));
                    }
                    //add pass/fail
//                    System.out.println(cards.get(i).get("test").toString());
//                    addResultToTestRail(cards.get(i).get("test").toString(), TEST_CASE_PASSED_STATUS, "");
                } catch (Exception e) {
                    throw new Exception("Could not test all cards " + e);
//                    addResultToTestRail(cards.get(i).get("test").toString(), TEST_CASE_FAILED_STATUS, e.getMessage());
                }


            }




        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("could not test all sandbox card payments " + e);
        }
        return this;
    }
}
