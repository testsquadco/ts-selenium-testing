package com.testsquad.runners;

import com.testsquad.utils.ConfigReader;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Listeners;
import com.testsquad.reporting.ExtentManager;
import com.testsquad.factory.DriverManager;
import org.openqa.selenium.WebDriver;

@Listeners(com.testsquad.listeners.TestListener.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.testsquad.stepdefs",
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber-pretty.html",
        "json:target/cucumber-reports/CucumberTestReport.json",
        "junit:target/cucumber-reports/CucumberTestReport.xml",
        "com.testsquad.reporting.CucumberExtentReporter"
    },
    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    
    private static boolean shutdownHookAdded = false;
    
    @BeforeClass
    @Parameters({"env"})
    public void setup(String environment) {
        if (environment == null || environment.trim().isEmpty()) {
            environment = "qa";
        }
        ConfigReader.initializeProperties(environment);
        ExtentManager.configureExtent();
        
        if (!shutdownHookAdded) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                DriverManager.forceQuit();
            }));
            shutdownHookAdded = true;
        }
    }

    @org.testng.annotations.AfterClass
    public void tearDown() {
        DriverManager.forceQuit();
    }
} 