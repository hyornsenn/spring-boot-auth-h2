package com.example.authsystem.controller;

import com.example.authsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    // Внедряем UserService через конструктор
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // 1. СТРАНИЦА ВХОДА
    // Spring Security сам обработает POST-запрос с этой формы, нам нужен только GET
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Вернет файл src/main/resources/templates/login.html
    }

    // 2. СТРАНИЦА РЕГИСТРАЦИИ (Отображение формы)
    @GetMapping("/registration")
    public String registrationPage() {
        return "registration"; // Вернет файл src/main/resources/templates/registration.html
    }

    // 3. ОБРАБОТКА РЕГИСТРАЦИИ (Отправка формы)
    @PostMapping("/registration")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {

        // Валидация на пустые поля
        if (username.isBlank() || password.isBlank()) {
            model.addAttribute("error", "Имя пользователя и пароль не могут быть пустыми");
            return "registration";
        }

        // Пытаемся зарегистрировать пользователя через сервис
        boolean isRegistered = userService.registerNewUser(username, password);

        if (!isRegistered) {
            // Если имя пользователя уже занято
            model.addAttribute("error", "Пользователь с таким именем уже существует");
            return "registration";
        }

        // Если регистрация успешна, перенаправляем на страницу входа с флагом успеха
        return "redirect:/login?success";
    }

    // 4. ГЛАВНАЯ СТРАНИЦА / ПРОФИЛЬ (Доступна только авторизованным)
    @GetMapping("/profile")
    public String profilePage() {
        return "profile"; // Вернет файл src/main/resources/templates/profile.html
    }
}
