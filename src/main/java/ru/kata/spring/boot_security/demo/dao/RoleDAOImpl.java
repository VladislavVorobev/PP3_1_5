package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDAOImpl implements RoleDAO{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    public Role getRole(String name) {
        return entityManager.createQuery("select r from Role r where r.name =: name", Role.class).setParameter("name", name).getSingleResult();
    }

    @Override
    public Role getRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Set<Role> getAllRolesById(List<Long> ids) {
        return new HashSet<>(entityManager.createQuery("select r from Role r where r.id in :ids ", Role.class)
                .setParameter("ids", ids)
                .getResultList());
    }

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }
}