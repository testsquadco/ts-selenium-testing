package com.testsquad.factory;

import com.testsquad.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.testsquad.config.CloudCapabilities;

import java.net.URL;

public class DriverFactory {
    public static WebDriver createDriver() {
        String browser = ConfigReader.getProperty("browser").toLowerCase();
        String mode = ConfigReader.getProperty("browser.mode").toLowerCase();
        String cloudPlatform = ConfigReader.getProperty("cloud.platform");
        String gridEnabled = ConfigReader.getProperty("grid.enabled");
        boolean isGridEnabled = gridEnabled != null && Boolean.parseBoolean(gridEnabled);

        if (isGridEnabled) {
            return createGridDriver(browser, mode);
        }

        if (cloudPlatform != null && !cloudPlatform.equals("local")) {
            return createCloudDriver(cloudPlatform, browser, mode);
        }

        return createLocalDriver(browser, mode);
    }

    private static WebDriver createLocalDriver(String browser, String mode) {
        boolean isHeadless = "headless".equalsIgnoreCase(mode);

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new");
                }
                return new ChromeDriver(chromeOptions);
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) {
                    firefoxOptions.addArguments("-headless");
                }
                return new FirefoxDriver(firefoxOptions);
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless) {
                    edgeOptions.addArguments("--headless=new");
                }
                return new EdgeDriver(edgeOptions);
            case "safari":
                if (isHeadless) {
                    throw new RuntimeException("Safari does not support headless mode");
                }
                return new SafariDriver();
            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }
    }

    private static WebDriver createGridDriver(String browser, String mode) {
        boolean isHeadless = "headless".equalsIgnoreCase(mode);
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser);
            
            // Add headless configuration for Grid execution
            switch (browser.toLowerCase()) {
                case "chrome":
                    if (isHeadless) {
                        capabilities.setCapability("goog:chromeOptions", new ChromeOptions().addArguments("--headless=new"));
                    }
                    break;
                case "firefox":
                    if (isHeadless) {
                        capabilities.setCapability("moz:firefoxOptions", new FirefoxOptions().addArguments("-headless"));
                    }
                    break;
                case "edge":
                    if (isHeadless) {
                        capabilities.setCapability("ms:edgeOptions", new EdgeOptions().addArguments("--headless=new"));
                    }
                    break;
                case "safari":
                    if (isHeadless) {
                        throw new RuntimeException("Safari does not support headless mode");
                    }
                    break;
            }
            
            return new RemoteWebDriver(new URL(ConfigReader.getProperty("grid.url")), capabilities);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Grid driver: " + e.getMessage());
        }
    }

    private static WebDriver createCloudDriver(String platform, String browser, String mode) {
        DesiredCapabilities capabilities = CloudCapabilities.getCapabilities(platform, browser, mode);

        try {
            String cloudUrl;
            switch (platform.toLowerCase()) {
                case "browserstack":
                    cloudUrl = "https://" + ConfigReader.getProperty("browserstack.username") + ":" +
                            ConfigReader.getProperty("browserstack.key") + "@hub-cloud.browserstack.com/wd/hub";
                    break;
                case "lambdatest":
                    cloudUrl = "https://" + ConfigReader.getProperty("lambdatest.username") + ":" +
                            ConfigReader.getProperty("lambdatest.key") + "@hub.lambdatest.com/wd/hub";
                    break;
                default:
                    throw new RuntimeException("Unsupported cloud platform: " + platform);
            }

            return new RemoteWebDriver(new URL(cloudUrl), capabilities);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create cloud driver: " + e.getMessage());
        }
    }
} 