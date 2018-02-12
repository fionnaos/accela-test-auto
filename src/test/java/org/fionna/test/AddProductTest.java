package org.fionna.test;

import org.fionna.test.selenium.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Properties;

public class AddProductTest {

    private MainPage mainPage = new MainPage(BrowserDriver.getCurrentDriver());
    private ExperiencePage experiencePage = new ExperiencePage(BrowserDriver.getCurrentDriver());
    private BasketPage basketPage = new BasketPage(BrowserDriver.getCurrentDriver());
    private PersonalDetailsPage personalDetailsPage = new PersonalDetailsPage(BrowserDriver.getCurrentDriver());
    private Properties properties = new Properties();
    private final String appConfigPath = System.getProperty("appConfig");

    public AddProductTest() {
        try {
            FileInputStream propIn = new FileInputStream(appConfigPath);
            properties.load(new InputStreamReader(propIn, Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DataProvider(name="addProductOptions")
    public static Object[][] addProductOptions() {

        return new Object[][] { { "This is a test gift", "E-voucher (Free)",
                                    "f@f.com", "Ms.", "F", "OS", "12345",
                                    "1A", "BT36 4PE", "Parcel Motel",
                                    "4222222222222222", "345"}};

    }

    @Test(dataProvider = "addProductOptions")
    public void addProductToBasketTest(String message, String deliveryMethod,
                                       String email, String title, String firstName, String lastName, String phone,
                                       String houseNumber, String postcode, String addressFirstLine,
                                       String cardNumber, String cvv) {
        // Load main page and search for product
        BrowserDriver.loadPage(properties.getProperty("url"));
        mainPage.setSearchFieldText(properties.getProperty("searchString"));

        // Correct product is found and added to basket
        experiencePage.waitForProductForm();
        Assert.assertEquals(experiencePage.getPageTitle(), properties.getProperty("productTitle"));
        Assert.assertEquals(experiencePage.getExperiencePrice(), properties.getProperty("productPrice"));

        experiencePage.clickBuyNow();
        Assert.assertEquals(basketPage.getBasketItemPrice(), properties.getProperty("productPrice"));

        editBasket(message, deliveryMethod);

        loginAsGuest(email);

        enterName(title, firstName, lastName, phone);

        personalDetailsPage.searchAddress(houseNumber, postcode);
        Assert.assertEquals(personalDetailsPage.getAddressFirstLine(), addressFirstLine);

        enterCreditCardDetails(firstName, lastName, cardNumber, cvv);

        personalDetailsPage.clickPlaceOrderButton();
        Assert.assertEquals(personalDetailsPage.getErrorMessages(), "The card number is not valid, please check the details and try again.");

    }

    private void enterCreditCardDetails(String firstName, String lastName, String cardNumber, String cvv) {
        personalDetailsPage.setCreditCardName(firstName + " " + lastName);
        personalDetailsPage.setCreditCardNumber(cardNumber);
        setCardExpiryToNextYear();
        personalDetailsPage.setCVVNumber(cvv);
    }

    private void enterName(String title, String firstName, String lastName, String phone) {
        personalDetailsPage.waitForNameVisible();
        personalDetailsPage.selectTitleFromDropdown(title);
        personalDetailsPage.setFirstName(firstName);
        personalDetailsPage.setLastName(lastName);
        personalDetailsPage.setPhoneNumber(phone);
        personalDetailsPage.clickContinue();
    }

    private void loginAsGuest(String email) {
        personalDetailsPage.continueAsGuest();
        personalDetailsPage.enterEmailAddress(email);
        personalDetailsPage.continueAsGuest();
    }

    private void editBasket(String message, String deliveryMethod) {
        basketPage.addPersonalisedMessage(1, message);
        basketPage.selectFromDeliveryDropDown(deliveryMethod);
        Assert.assertEquals(basketPage.getTotalAmount(), basketPage.getBasketItemPrice());
        basketPage.clickBuySecurelyNowButton();
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
            String fileAppend = String.valueOf(System.currentTimeMillis());
            mainPage.takeScreenshot(fileAppend);
        }
    }

}
