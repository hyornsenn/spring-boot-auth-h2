package com.example.authsystem.cucumber.steps;

import com.example.authsystem.AuthSystemApplication; // Импортируйте ваш главный класс запуска приложения
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Класс конфигурации контекста Spring для тестов Cucumber.
 * Позволяет использовать @Autowired внутри классов со Step Definitions.
 */
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = AuthSystemApplication.class)
public class SpringCucumberConfiguration {
    // Класс должен оставаться пустым.
    // Он используется исключительно как точка входа для инициализации Spring Boot внутри Cucumber.
}
