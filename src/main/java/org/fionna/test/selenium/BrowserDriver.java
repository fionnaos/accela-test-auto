package org.fionna.test.selenium;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;


import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class BrowserDriver {

    private static final Logger LOGGER = Logger.getLogger(BrowserDriver.class.getName());

    private static WebDriver mDriver;

    public synchronized static WebDriver getCurrentDriver() {
        if (mDriver==null) {
            System.setProperty("webdriver.gecko.driver","/Users/fionna/IdeaProjects/geckodriver");
            try {
                mDriver = new FirefoxDriver();
                mDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                mDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            } finally{
                Runtime.getRuntime().addShutdownHook(
                        new Thread(new BrowserCleanup()));
            }
        }
        return mDriver;
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
            mDriver = null;
            LOGGER.info("closing the browser");
        } catch (UnreachableBrowserException e) {
            LOGGER.info("cannot close browser: unreachable browser");
        }
    }

}
