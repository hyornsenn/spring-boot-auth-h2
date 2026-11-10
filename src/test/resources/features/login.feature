Feature: User Authentication

  Background:
    Given the user opens the login page

  Scenario: Successful login with valid credentials
    When the user enters username "admin" and password "password"
    And clicks the login button
    Then the user should be redirected to the profile page
    And see the greeting message "Привет, admin!"

  Scenario: Failed login with invalid password
    When the user enters username "admin" and password "wrong_password"
    And clicks the login button
    Then the user should see the error message "Неверное имя пользователя или пароль."
