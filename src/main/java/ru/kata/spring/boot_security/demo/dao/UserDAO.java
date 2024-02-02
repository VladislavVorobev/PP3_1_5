package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDAO {
    void addUser(User user);
    void deleteUser(Long id);
    void updateUser(User user, Long id);
    User findUser(Long id);
    List<User> getUserTable();
    List<User>  findByUsername(String username);
}