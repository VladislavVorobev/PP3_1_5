package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.sql.DataSource;



@Component
public class Init {
    private final UserService userService;
    private final RoleService roleService;
    private final DataSource dataSource;

    @Autowired
    public Init(UserService userService, RoleService roleService, DataSource dataSource) {
        this.userService = userService;
        this.roleService = roleService;
        this.dataSource = dataSource;
    }

    @Transactional
    @PostConstruct
    public void run() {
        try {
            ClassPathResource scriptResource = new ClassPathResource("script.sql");
            ScriptUtils.executeSqlScript(dataSource.getConnection(), scriptResource);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Role adminRole = roleService.getRoleById(1L);
        Role userRole = roleService.getRoleById(2L);

        Set<Role> adminRoleSet = Stream.of(adminRole).collect(Collectors.toSet());
        Set<Role> userRoleSet = Stream.of(userRole).collect(Collectors.toSet());

        User user1 = new User("Vladislav", "Vorobev", "VladVor@mail.ru", "vorobev",
                "123", adminRoleSet);
        User user2 = new User("Alex", "Ivanov", "Alexivanov@yandex.ru", "ivanov",
                "123", userRoleSet);

        userService.addUser(user1);
        userService.addUser(user2);
    }

}