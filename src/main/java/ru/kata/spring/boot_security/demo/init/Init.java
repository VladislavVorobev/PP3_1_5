package ru.kata.spring.boot_security.demo.init;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Component
public class Init {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Transactional
    @PostConstruct
    public void run() {
        Role adminRole = new Role(1L, "ROLE_ADMIN");
        Role userRole = new Role(2L, "ROLE_USER");
        roleService.addRole(adminRole);
        roleService.addRole(userRole);
        Set<Role> adminRoleSet = Stream.of(adminRole, userRole).collect(Collectors.toSet());
        Set<Role> userRoleSet = Stream.of(userRole).collect(Collectors.toSet());
        User user1 = new User("Vladislav", "Vorobev", "VladVor@mail.ru", "vorobev",
                "123", adminRoleSet);
        User user2 = new User("Alex", "Ivanov", "Alexivanov@yandex.ru", "ivanov",
                "123", userRoleSet);
        userService.addUser(user1);
        userService.addUser(user2);
    }
}