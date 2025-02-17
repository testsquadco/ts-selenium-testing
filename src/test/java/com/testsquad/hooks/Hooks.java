package com.testsquad.hooks;

import com.testsquad.context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import com.testsquad.reporting.ExtentManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.Scenario;

public class Hooks {
    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before
    public void setup() {}

    @After
    public void teardown(io.cucumber.java.Scenario scenario) {
        WebDriver driver = null;
        try {
            driver = context.getDriver();
        } catch (Exception e) {
            System.err.println("Could not get driver: " + e.getMessage());
        }

        if (scenario.isFailed()) {
            try {
                if (driver != null) {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", "Screenshot");
                }
            } catch (Exception e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
        }

        try {
            context.quitDriver();
        } catch (Exception e) {
            System.err.println("Error during driver cleanup: " + e.getMessage());
        }
    }
} 