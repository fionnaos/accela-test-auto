package org.fionna.test.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CategoryPage extends BasePage {

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getExperienceByIndex(int index) {
        return driver.findElement(By.cssSelector(String.format("#productlist-results > a:nth-child(%d)", index)));
    }

    public void selectExperienceByIndex(int index) {
        this.getExperienceByIndex(index).click();
    }

}
