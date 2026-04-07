package com.tuda24.steps.repository.Role;

import com.tuda24.steps.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public RoleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Role> findRoleByNameNative(String name) {
        String sql = "SELECT id, name, active FROM roles WHERE name = ?1";

        return entityManager
                .createNativeQuery(sql, Role.class)
                .setParameter(1, name)
                .getResultStream()
                .findFirst();
    }
}