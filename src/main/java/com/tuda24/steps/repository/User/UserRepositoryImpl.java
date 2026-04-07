package com.tuda24.steps.repository.User;

import com.tuda24.steps.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findByUsernameWithRoles(String username) {
        try {
            User user = entityManager.createQuery(
                            "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username",
                            User.class
                    )
                    .setParameter("username", username)
                    .getSingleResult();

            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByPhoneNumberNative(String phoneNumber) {
        String sql = "SELECT id, active, created_at, date_of_birth, email, " +
                "first_name, gender, last_name, password, phone_number, " +
                "updated_at, username, version FROM users WHERE phone_number = ?1";

        return entityManager
                .createNativeQuery(sql, User.class)
                .setParameter(1, phoneNumber)
                .getResultStream()
                .findFirst();
    }
}
