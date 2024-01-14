package ru.kata.spring.boot_security.demo.dao;



import javax.persistence.EntityGraph;
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


    public List<User> findByUsername(String username) {
        EntityGraph<User> graph = entityManager.createEntityGraph(User.class);
        graph.addSubgraph("roles");

        return entityManager.createQuery("SELECT DISTINCT u FROM User u JOIN FETCH u.roles WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .setHint("javax.persistence.fetchgraph", graph)
                .getResultList();
    }

}