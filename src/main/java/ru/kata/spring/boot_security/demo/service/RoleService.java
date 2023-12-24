package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRole(String username);
    Role getRoleById(Long id);
    Set<Role> getAllRolesById(List<Long> ids);
    void addRole(Role role);

}