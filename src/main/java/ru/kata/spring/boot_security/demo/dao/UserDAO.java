package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDAO {
    void addUser(User user);
    void deleteUser(Long id);
    void updateUser(User user);
    User findUser(Long id);
    List<User> getUserTable();
    public User findByUsername(String username);
}