package org.fionna.test.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage extends BasePage {

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getCategoryByIndex(int index) {
        WebElement categoryElement = driver.findElement(By.cssSelector(String.format("#homepage_categories > li:nth-child(%d)", index)));
        return categoryElement;
    }

    public void selectCategoryByIndex(int index) {
        this.getCategoryByIndex(index).click();
    }

    public WebElement getSearchField() {
        return driver.findElement(By.id("search-field"));
    }

    public void setSearchFieldText(String searchTextValue) {
        this.getSearchField().sendKeys(searchTextValue + Keys.RETURN);
    }

    //TODO: Carry on filling in page object

}
