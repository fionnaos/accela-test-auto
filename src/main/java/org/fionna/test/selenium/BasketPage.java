package org.fionna.test.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class BasketPage extends BasePage {

    public BasketPage (WebDriver driver) {
        this.driver = driver;
    }

    private WebElement getBasketItem(int index) {
        return this.driver.findElement(By.cssSelector("#basket_contents > div > form > div:nth-child(" + index + ")"));
    }

    public void removeBasketItem(int index) {
        WebElement basketItem = this.getBasketItem(index);
        basketItem.findElement
                (By.partialLinkText("x remove"))
                .click();
            // TODO make this locator generic enough to work in different languages!
    }

    public void addPersonalisedMessage(int index, String message) {
        WebElement basketItem = this.getBasketItem(index);
        basketItem.findElement
                (By.cssSelector("div div.personalised_as_message > textarea"))
                .sendKeys(message);
    }

    public String getBasketItemPrice() {
        return this.driver.findElement(By.cssSelector("#basket_summary div.row.basket_totals > span.cost_price")).getText();
    }

    public String getTotalAmount() {
        return this.driver.findElement
                (By.cssSelector("#basket_summary div.row.final_totals > span.cost_price"))
                .getText();
    }

    private WebElement getDeliveryDropDown() {
        return this.driver.findElement
                (By.cssSelector("#basket_summary div.delivery_options > select"));
    }

    public void selectFromDeliveryDropDown(String valueToSelect) {
        Select dropdown = new Select(this.getDeliveryDropDown());
        dropdown.selectByVisibleText(valueToSelect);
    }

    public void clickBuySecurelyNowButton() {
        // this.driver.findElement(By.cssSelector("#basket_payment_options > a[title=\"Pay Securely Now\"]")).click();
        // This brings up a popup that is tricky to get past, so going to just:
        BrowserDriver.loadPage("https://www.buyagift.co.uk/checkout");
    }


}
