package com.example.authsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthSystemApplication {

    public static void main(String[] args) {
        // Эта строчка запускает встроенный веб-сервер Tomcat и всё приложение
        SpringApplication.run(AuthSystemApplication.class, args);
    }
}
