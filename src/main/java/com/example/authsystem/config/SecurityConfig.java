package com.example.authsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // 1. ИЗМЕНЕНИЕ ДЛЯ H2: разрешаем доступ к консоли всем без логина
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()

                        // Разрешаем страницы входа и регистрации
                        .requestMatchers("/login", "/registration", "/css/**", "/js/**").permitAll()
                        // Все остальные страницы сайта требуют авторизации
                        .anyRequest().authenticated()
                )

                // 2. ИЗМЕНЕНИЕ ДЛЯ H2: отключаем CSRF-защиту только для адреса консоли
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                )

                // 3. ИЗМЕНЕНИЕ ДЛЯ H2: разрешаем отображение страниц в Frame (нужно для интерфейса H2)
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )

                // Настройка обычной формы входа для пользователей сайта
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/profile", true)
                        .permitAll()
                )

                // Настройка выхода с сайта
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    // Бин для автоматического шифрования паролей пользователей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
