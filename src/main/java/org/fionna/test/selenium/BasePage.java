package org.fionna.test.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.fionna.test.selenium.BrowserDriver.getCurrentDriver;

public class BasePage {

    private static final Logger LOGGER = Logger.getLogger(BrowserDriver.class.getName());
    protected WebDriver driver;


    public String getPageTitle() {
        return driver.getTitle();
    }

    public static WebElement waitForElement(WebElement elementToWaitFor){
        return waitForElement(elementToWaitFor, null);
    }

    public static WebElement waitForElement(WebElement elementToWaitFor, Integer waitTimeInSeconds) {
        if (waitTimeInSeconds == null) {
            waitTimeInSeconds = 10;
        }

        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), waitTimeInSeconds);
        return wait.until((Function<? super WebDriver, WebElement>) ExpectedConditions.visibilityOf(elementToWaitFor));
    }

    public static Boolean waitForElementAttribute(WebElement elementToWaitFor, String attribute) {
        int waitTimeInSeconds = 10;

        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), waitTimeInSeconds);
        return wait.until((Function<? super WebDriver, Boolean>)
                ExpectedConditions.attributeToBeNotEmpty(elementToWaitFor, attribute));
    }

    public static WebElement getParent(WebElement element) {
        return element.findElement(By.xpath(".."));
    }

    public static List<WebElement> getDropDownOptions(WebElement webElement) {
        Select select = new Select(webElement);
        return select.getOptions();
    }

    public static WebElement getDropDownOption(WebElement webElement, String value) {
        WebElement option = null;
        List<WebElement> options = getDropDownOptions(webElement);
        for (WebElement element : options) {
            if (element.getAttribute("value").equalsIgnoreCase(value)) {
                option = element;
                break;
            }
        }
        return option;
    }

    public void takeScreenshot(String date) {
        File scrFile = ((TakesScreenshot)getCurrentDriver()).getScreenshotAs(OutputType.FILE);

        try {
            Files.copy(scrFile.toPath(),
                    new File(System.getProperty("env.screenshots") + "/shot_" + date + ".png").toPath(),
                    (CopyOption) REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
