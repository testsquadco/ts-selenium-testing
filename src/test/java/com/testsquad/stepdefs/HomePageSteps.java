package com.testsquad.stepdefs;

import com.testsquad.pages.HomePage;
import com.testsquad.utils.ConfigReader;
import com.testsquad.context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class HomePageSteps {
    private final WebDriver driver;
    private final HomePage homePage;

    public HomePageSteps(TestContext context) {
        this.driver = context.getDriver();
        this.homePage = new HomePage(driver);
    }

    @Given("I am on the TestSquad homepage")
    public void iAmOnTheTestSquadHomepage() {
        driver.get(ConfigReader.getProperty("url"));
    }

    @Then("the page title should be {string}")
    public void thePageTitleShouldBe(String expectedTitle) {
        Assert.assertEquals(homePage.getPageTitle(), expectedTitle);
    }
} 