package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.sql.DataSource;



@Component
public class Init {

    private final DataSource dataSource;

    @Autowired
    public Init(DataSource dataSource) {

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



    }

}

