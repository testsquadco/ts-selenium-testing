package com.testsquad.context;

import org.openqa.selenium.WebDriver;
import com.testsquad.factory.DriverFactory;
import com.testsquad.factory.DriverManager;

public class TestContext {
    private boolean isCleaned = false;
    private final Object cleanupLock = new Object();

    public TestContext() {
        DriverManager.setDriver(DriverFactory.createDriver());
    }

    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    public void quitDriver() {
        synchronized (cleanupLock) {
            if (!isCleaned) {
                try {
                    DriverManager.forceQuit();
                } finally {
                    isCleaned = true;
                }
            }
        }
    }

    @Override
    protected void finalize() {
        synchronized (cleanupLock) {
            quitDriver(); // Backup cleanup
        }
    }
} 