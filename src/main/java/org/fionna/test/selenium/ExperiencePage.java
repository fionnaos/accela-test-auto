package org.fionna.test.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ExperiencePage extends BasePage {

    public ExperiencePage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForProductForm() {
        waitForElement(this.driver.findElement(By.id("product-form")));
    }

    public String getExperiencePrice() {
        return driver.findElement(By.id("product-price-current")).getText();
    }

    public void clickBuyNow() {
        driver.findElement(By.cssSelector("#product-form > div > button")).click();
    }

    //TODO: Carry on filling in page object
}
