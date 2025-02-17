package com.testsquad.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    @FindBy(css = "h1")
    private WebElement mainHeading;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getMainHeadingText() {
        return waitUtils.waitForElementVisible(mainHeading).getText();
    }
} 