package org.fionna.test.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class BrowserDriver {

    private static final Logger LOGGER = Logger.getLogger(BrowserDriver.class.getName());

    private static WebDriver driver;

    public synchronized static WebDriver getCurrentDriver() {
        if (driver ==null) {
            System.out.println("Getting app config..." + System.getProperty("appConfig"));
            System.setProperty("webdriver.gecko.driver",System.getProperty("env.geckodriver"));
            try {
                driver = new FirefoxDriver();
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            } finally{
                Runtime.getRuntime().addShutdownHook(
                        new Thread(new BrowserCleanup()));
            }
        }
        return driver;
    }

    private static class BrowserCleanup implements Runnable {
        public void run() {
            LOGGER.info("Closing the browser");
            close();
        }
    }

    public static void loadPage(String url){;
        LOGGER.info("Directing browser to:" + url);
        getCurrentDriver().get(url);
    }

    static void close() {
        try {
            getCurrentDriver().quit();
            driver = null;
            LOGGER.info("closing the browser");
        } catch (UnreachableBrowserException e) {
            LOGGER.info("cannot close browser: unreachable browser");
        }
    }

}
