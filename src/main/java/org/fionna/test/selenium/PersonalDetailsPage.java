package org.fionna.test.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class PersonalDetailsPage extends BasePage {

    public PersonalDetailsPage (WebDriver driver) {
        this.driver = driver;
    }

    public void continueAsGuest() {
        this.driver.findElement(By.cssSelector("#checkout_capture button.btn.login_guest")).click();
    }

    public void enterEmailAddress(String email) {
        this.driver.findElement(By.cssSelector("#account_email_field")).sendKeys(email);
    }

    public void waitForNameVisible() {
        waitForElement(this.driver.findElement(By.name("FirstName")));
    }

    public void selectTitleFromDropdown(String index) {
        Select dropdown = new Select(this.driver.findElement(By.id("titlefield")));
        dropdown.selectByValue(index);
    }

    public void setFirstName(String firstName) {
        this.driver.findElement(By.name("FirstName")).sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        this.driver.findElement(By.name("LastName")).sendKeys(lastName);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.driver.findElement(By.name("PhoneNumber")).sendKeys(phoneNumber);
    }

    public void clickContinue() {
        this.driver.findElement(By.cssSelector("#guest_personal_details > div.form-group.external_links > button")).click();
    }

    public void enterHouseNumber(String number) { // leaving public access for future testing
        this.driver.findElement(By.cssSelector("#postCodeSearchBilling > div > div.form-group.housenumbersearch > div > input")).sendKeys(number);
    }

    public void enterPostCode(String postcode) { // leaving public access for future testing
        this.driver.findElement(By.cssSelector("#postCodeSearchBilling > div > div.form-group.postcodesearch > div > input")).sendKeys(postcode);
    }

    public void clickSearchButton() { // leaving public access for future testing
        this.driver.findElement(By.cssSelector("#postCodeSearchBilling > div > div.form-group.postcodesearch > div.continue_area_buttons > button")).click();
    }

    public void searchAddress(String houseNumber, String postcode) {
        WebElement houseNumberField = this.driver.findElement(By.cssSelector("#postCodeSearchBilling > div > div.form-group.housenumbersearch > div > input"));
        waitForElement(houseNumberField);
        this.enterHouseNumber(houseNumber);
        this.enterPostCode(postcode);
        clickSearchButton();
    }

    public String getAddressFirstLine() {
        //WebElement addressFirstLine = this.driver.findElement(By.name("AddressLine1"));
        WebElement addressFirstLine = this.driver.findElement(By.cssSelector("#new_card_selectaddress > ul > li.row.newcard_newaddress.subpanel-inner > div > div > div > div > div:nth-child(3) > div > input"));
        waitForElementAttribute(addressFirstLine, "value");
        return addressFirstLine.getAttribute("value");
    }

    public void setCreditCardName(String creditCardName) {
        this.driver.findElement(By.id("cardholdername")).sendKeys(creditCardName);
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.driver.findElement(By.id("cardnumber")).sendKeys(creditCardNumber);
    }

    public void selectExpiryMonth(String month) {
        Select dropdown = new Select(this.driver.findElement(By.id("expirymonth")));
        dropdown.selectByVisibleText(month);
    }

    public void selectExpiryYear(String year) {
        Select dropdown = new Select(this.driver.findElement(By.id("expiryyear")));
        dropdown.selectByVisibleText(year);
    }

    public void setCVVNumber(String cvvNumber) {
        this.driver.findElement(By.id("cvvnumber")).sendKeys(cvvNumber);
    }

    public void clickPlaceOrderButton() {
        this.driver.findElement(By.id("btnPlaceOrderButton")).click();
    }

    public String getErrorMessages() {
        return this.driver.findElement(By.cssSelector("[ng-show=\"FlowData.PaymentErrors!=null\"] > span")).getText();
    }

}
