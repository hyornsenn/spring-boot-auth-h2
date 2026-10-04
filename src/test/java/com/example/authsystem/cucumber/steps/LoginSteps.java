package com.example.authsystem.cucumber.steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.example.authsystem.service.UserService;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.ru.*;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class LoginSteps {

    static {
        // Настройка Selenide: запускать тесты без открытия окна браузера (в фоне)
        Configuration.headless = true;
    }
    @Autowired
    private UserService userService;
    @Before
    public void setupTestData() {
        // Регистрируем админа для теста (если он уже есть, метод вернет false и ничего не сломает)
        userService.registerNewUser("admin", "password");
    }
    @Допустим("пользователь открывает страницу авторизации")
    public void openLoginPage() {
        // Предполагается, что ваше Spring Boot приложение запущено на порту 8080
        Selenide.open("http://localhost:8080/login");
    }

    @Когда("пользователь вводит имя {string} и пароль {string}")
    public void inputCredentials(String username, String password) {
        $(By.id("username")).setValue(username);
        $(By.id("password")).setValue(password);
    }

    @И("нажимает кнопку {string}")
    public void clickSubmit(String buttonText) {
        // Находим кнопку по типу submit
        $("button[type='submit']").click();
    }

    @Тогда("он должен быть перенаправлен в личный кабинет")
    public void verifyRedirectToProfile() {
        Selenide.webdriver().shouldHave(urlContaining("/profile"));
    }

    @And("видеть приветствие {string}")
    public void verifyGreeting(String expectedGreeting) {
        // Проверяем, что навигационная панель содержит нужный текст приветствия
        $(".navbar-text").shouldHave(Condition.text(expectedGreeting));
    }

    @Тогда("он должен видеть сообщение об ошибке {string}")
    public void verifyErrorMessage(String expectedErrorMessage) {
        // Ищем блок с ошибкой (alert-danger), который мы верстали в Bootstrap
        $(".alert-danger").shouldHave(Condition.text(expectedErrorMessage));
    }
}
