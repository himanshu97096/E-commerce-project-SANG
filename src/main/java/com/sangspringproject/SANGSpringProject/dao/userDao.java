package com.sangspringproject.SANGSpringProject.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.sangspringproject.SANGSpringProject.models.User;

@Repository
@Transactional
public class userDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getAllUser() {
        TypedQuery<User> query =
                entityManager.createQuery("from User", User.class);
        return query.getResultList();
    }

    public User saveUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        if (user.getId() == 0) {
            entityManager.persist(user);   // INSERT
        } else {
            entityManager.merge(user);     // UPDATE
        }

        System.out.println("User saved with encrypted password: " + user.getId());
        return user;
    }

    public boolean userExists(String username) {
        Long count = entityManager.createQuery(
                "select count(u) from User u where u.username = :username",
                Long.class)
                .setParameter("username", username)
                .getSingleResult();

        return count > 0;
    }

    public boolean userExistsByEmail(String email) {
        Long count = entityManager.createQuery(
                "select count(u) from User u where u.email = :email",
                Long.class)
                .setParameter("email", email)
                .getSingleResult();

        return count > 0;
    }

    public User getUserByEmail(String email) {
        try {
            return entityManager.createQuery(
                    "from User u where u.email = :email",
                    User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public User updateUser(User user) {
        return entityManager.merge(user);
    }
}
