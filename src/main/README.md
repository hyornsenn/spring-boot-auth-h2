# Spring Boot Auth System & BDD E2E Test Automation Framework

![Java CI](https://github.com)

A full-stack demo application built with **Spring Boot 3**, **Spring Security**, **Spring Data JPA (H2)**, and **Thymeleaf**, paired with an automated end-to-end (E2E) test automation framework powered by **Java 17**, **Cucumber (BDD)**, **JUnit 5**, and **Selenide**.

This repository demonstrates clean test automation architecture: **Page Object Model (POM)**, dynamic port allocation (`RANDOM_PORT`), programmatic test data provisioning via Spring context injection, and automated CI execution via **GitHub Actions**.

---

## Tech Stack

### Application Under Test (AUT)
* **Java 17**
* **Spring Boot 3.3.4** (Web, Security, Data JPA, Thymeleaf)
* **H2 Database** (in-memory relational database for fast, self-contained execution)
* **Lombok** & **Bootstrap**

### Test Automation Framework
* **Cucumber 7.15.0** (`cucumber-java`, `cucumber-spring`, `cucumber-junit-platform-engine`)
* **JUnit 5 Platform Suite** (`@Suite` test engine)
* **Selenide 7.2.0** (fluent UI automation, smart waits, automatic WebDriver management)
* **Maven** & **GitHub Actions** (CI/CD pipeline with HTML report & screenshot artifacts)

---

## Project Structure

```text
spring-boot-auth-h2/
├── .github/workflows/ci.yml                       # GitHub Actions CI pipeline
├── pom.xml                                        # Maven dependencies & Surefire configuration
└── src/
    ├── main/
    │   ├── java/com/example/authsystem/
    │   │   ├── config/SecurityConfig.java         # Spring Security rules & authentication setup
    │   │   ├── controller/AuthController.java     # Web endpoints (/login, /registration, /profile)
    │   │   ├── model/                             # JPA Entities (User, Role)
    │   │   ├── repository/                        # Spring Data JPA Repositories
    │   │   └── service/UserService.java           # User registration & business logic
    │   └── resources/
    │       ├── application.properties             # H2 & server configuration
    │       └── templates/                         # Thymeleaf HTML views (login, profile, registration)
    └── test/
        ├── java/com/example/authsystem/cucumber/
        │   ├── CucumberTestRunner.java            # JUnit 5 Platform Suite entry point
        │   ├── config/
        │   │   └── SpringCucumberConfiguration.java # @SpringBootTest(RANDOM_PORT) context bridge
        │   ├── pages/
        │   │   ├── LoginPage.java                 # Page Object for /login
        │   │   └── ProfilePage.java               # Page Object for /profile
        │   └── steps/
        │       └── LoginSteps.java                # Gherkin step definitions & @Before data setup
        └── resources/
            ├── features/login.feature             # English Gherkin BDD scenarios
            ├── junit-platform.properties          # Cucumber HTML/JSON reporting config
            └── selenide.properties                # Selenide browser & timeout properties
```

---

## Key Architecture Highlights

* **Zero Port Collisions (`RANDOM_PORT`):** The test suite starts the embedded Spring Boot server on a random available port and dynamically injects `Configuration.baseUrl` via `@LocalServerPort`, making tests safe for parallel CI environments.
* **Page Object Model (POM):** UI locators and page interactions are encapsulated inside `LoginPage` and `ProfilePage`, keeping step definitions concise, readable, and easy to maintain.
* **Programmatic Test Data Setup:** `UserService` is injected directly into `LoginSteps` via `@Autowired` (`cucumber-spring`), provisioning test users in the H2 database prior to test execution without brittle UI setup steps.
* **CI/CD Ready:** Every push triggers a GitHub Actions workflow that runs the suite in headless Chrome and archives Cucumber HTML reports and Selenide failure screenshots.

---

## Getting Started

### Prerequisites
* **JDK 17+**
* **Maven 3.8+**
* **Google Chrome / Chromium**

### Running Tests (Headless Mode — Default)
```bash
mvn clean test
```

### Running Tests with Visible Browser
```bash
mvn clean test -Dselenide.headless=false
```

### Viewing Test Reports
After test execution, open the generated Cucumber HTML report at:
```text
target/cucumber-reports/cucumber.html
```
