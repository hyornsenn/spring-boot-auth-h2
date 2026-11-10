package com.example.authsystem.cucumber.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement submitButton = $("button[type='submit']");
    private final SelenideElement errorAlert = $(".alert-danger");

    public LoginPage open() {
        Selenide.open("/login");
        return this;
    }

    public LoginPage enterCredentials(String username, String password) {
        usernameInput.shouldBe(visible).setValue(username);
        passwordInput.shouldBe(visible).setValue(password);
        return this;
    }

    public void submit() {
        submitButton.shouldBe(visible).click();
    }

    public void shouldDisplayErrorMessage(String expectedMessage) {
        errorAlert.shouldBe(visible).shouldHave(text(expectedMessage));
    }
}
