package com.testsquad.factory;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    public static void removeDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            try {
                for (String handle : currentDriver.getWindowHandles()) {
                    try {
                        currentDriver.switchTo().window(handle);
                        currentDriver.close();
                    } catch (Exception e) {}
                }
                currentDriver.quit();
            } catch (Exception e) {
                System.err.println("Error cleaning up WebDriver: " + e.getMessage());
            } finally {
                driver.remove();
            }
        }
    }

    public static void forceQuit() {
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            try {
                currentDriver.quit();
            } catch (Exception e) {} finally {
                driver.remove();
            }
        }
    }
} 