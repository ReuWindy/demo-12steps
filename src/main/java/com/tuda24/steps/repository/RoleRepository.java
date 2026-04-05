package com.tuda24.steps.repository;

import com.tuda24.steps.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(String name);

    boolean existsByName(String name);

    List<Role> findByNameContainingIgnoreCase(String keyword);

    @Query(value = "SELECT * FROM roles WHERE name = :name", nativeQuery = true)
    Optional<Role> findRoleByNameNative(@Param("name") String name);
}
