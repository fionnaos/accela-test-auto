package org.fionna.test;

import org.fionna.test.selenium.*;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.Calendar;

public class AddProductTest {

    private MainPage mainPage = new MainPage(BrowserDriver.getCurrentDriver());
    private ExperiencePage experiencePage = new ExperiencePage(BrowserDriver.getCurrentDriver());
    private BasketPage basketPage = new BasketPage(BrowserDriver.getCurrentDriver());
    private PersonalDetailsPage personalDetailsPage = new PersonalDetailsPage(BrowserDriver.getCurrentDriver());

    @Test()
    public void addProductToBasketTest() {

        BrowserDriver.loadPage("http://www.buyagift.co.uk");
        mainPage.setSearchFieldText("Charming escape");

        experiencePage.waitForProductForm();
        Assert.assertEquals(experiencePage.getPageTitle(), "Charming Escape - Smartbox by Buyagift from Buyagift");
        Assert.assertEquals(experiencePage.getExperiencePrice(), "£69.99");

        experiencePage.clickBuyNow();
        Assert.assertEquals(basketPage.getBasketItemPrice(), "£69.99");

        basketPage.addPersonalisedMessage(1, "This is a test gift");
        basketPage.selectFromDeliveryDropDown("E-voucher (Free)");
        Assert.assertEquals(basketPage.getTotalAmount(), basketPage.getBasketItemPrice());
        basketPage.clickBuySecurelyNowButton();

        personalDetailsPage.continueAsGuest();
        personalDetailsPage.enterEmailAddress("f@f.com");
        personalDetailsPage.continueAsGuest();

        personalDetailsPage.waitForNameVisible();
        personalDetailsPage.selectTitleFromDropdown("3");
        personalDetailsPage.setFirstName("F");
        personalDetailsPage.setLastName("OS");
        personalDetailsPage.setPhoneNumber("12345");
        personalDetailsPage.clickContinue();

        // If the test is looking too verbose, methods can be combined in the page objects to create more complex test steps
        // The disadvantage is that if the test fails, there is less information on what exactly happened
        personalDetailsPage.searchAddress("1A", "BT36 4PE");
        Assert.assertEquals(personalDetailsPage.getAddressFirstLine(), "Parcel Motel");

        personalDetailsPage.setCreditCardName("F OS");
        personalDetailsPage.setCreditCardNumber("4222222222222222");
        setCardExpiryToNextYear();
        personalDetailsPage.setCVVNumber("345");

        personalDetailsPage.clickPlaceOrderButton();
        Assert.assertEquals(personalDetailsPage.getErrorMessages(), "The card number is not valid, please check the details and try again.");

    }

    private void setCardExpiryToNextYear() {
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int nextYear = currentYear+1;

        personalDetailsPage.selectExpiryMonth("11");
        personalDetailsPage.selectExpiryYear(String.valueOf(nextYear));
    }

    @AfterMethod
    public void takeScreenshotWhenFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            String fileAppend = "test123";
            mainPage.takeScreenshot(fileAppend);
        }
    }

}
