package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    void addUser(User user);
    void deleteUser(Long id);
    void updateUser(User user);
    List<User> getUserTable();
    User findUser(Long id);

}