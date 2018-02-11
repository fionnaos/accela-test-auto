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
                (By.cssSelector("div > div:nth-child(1) > div.prd_details > div:nth-child(2) > span > span > a"))
                .click();
                // How robust is this selector going to be? This would be something to ask from the dev team
    }

    public void addPersonalisedMessage(int index, String message) {
        WebElement basketItem = this.getBasketItem(index);
        basketItem.findElement
                (By.cssSelector("div > div.row.additional_details > div.basket_step > div > textarea"))
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
                (By.cssSelector("#basket_summary > div > div.animate-fadeIn.ng-scope > div > div > div:nth-child(3) > span.cost_price > div.form-group.delivery_options > select"));
    }

    public void selectFromDeliveryDropDown(String valueToSelect) {
        Select dropdown = new Select(this.getDeliveryDropDown());
        dropdown.selectByVisibleText(valueToSelect);
    }

    public void clickBuySecurelyNowButton() {
        // this.driver.findElement(By.cssSelector("#basket_payment_options > a[title=\"Pay Securely Now\"]")).click(); // This brings up a popup that is tricky to get through
        BrowserDriver.loadPage("https://www.buyagift.co.uk/checkout");
    }


}
