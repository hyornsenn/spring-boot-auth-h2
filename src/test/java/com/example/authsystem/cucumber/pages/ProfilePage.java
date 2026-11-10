package com.example.authsystem.cucumber.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class ProfilePage {

    private final SelenideElement greetingBanner = $(".navbar-text");

    public ProfilePage shouldBeOpened() {
        Selenide.webdriver().shouldHave(urlContaining("/profile"));
        return this;
    }

    public void shouldDisplayGreeting(String expectedGreeting) {
        greetingBanner.shouldBe(visible).shouldHave(text(expectedGreeting));
    }
}
