package com.example.authsystem.cucumber.steps;

import com.example.authsystem.cucumber.pages.LoginPage;
import com.example.authsystem.cucumber.pages.ProfilePage;
import com.example.authsystem.service.UserService;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginSteps {

    @Autowired
    private UserService userService;

    private final LoginPage loginPage = new LoginPage();
    private final ProfilePage profilePage = new ProfilePage();

    @Before(order = 1)
    public void setupTestData() {
        userService.registerNewUser("admin", "password");
    }

    @Given("the user opens the login page")
    public void openLoginPage() {
        loginPage.open();
    }

    @When("the user enters username {string} and password {string}")
    public void inputCredentials(String username, String password) {
        loginPage.enterCredentials(username, password);
    }

    @And("clicks the login button")
    public void clickSubmit() {
        loginPage.submit();
    }

    @Then("the user should be redirected to the profile page")
    public void verifyRedirectToProfile() {
        profilePage.shouldBeOpened();
    }

    @And("see the greeting message {string}")
    public void verifyGreeting(String expectedGreeting) {
        profilePage.shouldDisplayGreeting(expectedGreeting);
    }

    @Then("the user should see the error message {string}")
    public void verifyErrorMessage(String expectedErrorMessage) {
        loginPage.shouldDisplayErrorMessage(expectedErrorMessage);
    }
}
