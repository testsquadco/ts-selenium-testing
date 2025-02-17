package com.testsquad.config;

import org.openqa.selenium.remote.DesiredCapabilities;
import com.testsquad.utils.ConfigReader;

public class CloudCapabilities {
    
    public static DesiredCapabilities getCapabilities(String platform, String browser, String mode) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
        boolean isHeadless = "headless".equalsIgnoreCase(mode);
        
        switch (platform.toLowerCase()) {
            case "browserstack":
                capabilities.setCapability("browser_version", "latest");
                capabilities.setCapability("os", "Windows");
                capabilities.setCapability("os_version", "10");
                if (isHeadless) {
                    capabilities.setCapability("browserstack.headless", true);
                }
                break;
                
            case "lambdatest":
                capabilities.setCapability("version", "latest");
                capabilities.setCapability("platform", "Windows 10");
                if (isHeadless) {
                    capabilities.setCapability("headless", true);
                }
                break;
        }
        
        return capabilities;
    }
} 