package ru.kata.spring.boot_security.demo.dao;



import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User findUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getUserTable() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }


    public User findByUsername(String username) {
        return (User) entityManager.createNativeQuery("SELECT * FROM users u WHERE u.username = ?", User.class)
                .setParameter(1, username)
                .getSingleResult();
    }
}