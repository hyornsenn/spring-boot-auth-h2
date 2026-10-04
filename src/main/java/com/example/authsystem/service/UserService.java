package com.example.authsystem.service;

import com.example.authsystem.model.Role;
import com.example.authsystem.model.User;
import com.example.authsystem.repository.RoleRepository;
import com.example.authsystem.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // Внедрение зависимостей через конструктор
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. МЕТОД ДЛЯ АВТОРИЗАЦИИ (Используется Spring Security при входе)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));

        // Преобразуем наши текстовые роли из БД в объекты GrantedAuthority для Spring Security
        var authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    // 2. МЕТОД ДЛЯ РЕГИСТРАЦИИ (Вызывается из контроллера при отправке формы)
    public boolean registerNewUser(String username, String password) {
        // Проверяем, существует ли уже пользователь с таким именем
        if (userRepository.findByUsername(username).isPresent()) {
            return false; // Регистрация отклонена, имя занято
        }

        User newUser = new User();
        newUser.setUsername(username);

        // ВАЖНО: Хешируем пароль перед записью в БД
        newUser.setPassword(passwordEncoder.encode(password));

        // Назначаем роль по умолчанию. Если роли ROLE_USER нет в БД, создаем её на лету
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_USER");
                    return roleRepository.save(role);
                });

        newUser.setRoles(Collections.singleton(userRole));

        userRepository.save(newUser);
        return true;
    }
}
