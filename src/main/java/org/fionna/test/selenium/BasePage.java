package org.fionna.test.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.fionna.test.selenium.BrowserDriver.getCurrentDriver;

public class BasePage {

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

    public void takeScreenshot(String date) {
        File scrFile = ((TakesScreenshot)getCurrentDriver()).getScreenshotAs(OutputType.FILE);
        String pathName = System.getProperty("env.screenshot");
        File fileAtPath = new File(pathName);
        String fileName = pathName + "/shot_" + date + ".png";

        if (!Files.exists(fileAtPath.toPath())) {
            try {
                Files.createDirectories(fileAtPath.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Files.copy(scrFile.toPath(),
                    new File(fileName).toPath(),
                    (CopyOption) REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
