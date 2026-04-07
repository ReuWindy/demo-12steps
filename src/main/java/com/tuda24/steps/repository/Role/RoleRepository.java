package com.tuda24.steps.repository.Role;

import com.tuda24.steps.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID>, RoleRepositoryCustom {

    Optional<Role> findByName(String name);

    boolean existsByName(String name);

    List<Role> findByNameContainingIgnoreCase(String keyword);
}
