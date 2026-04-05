package com.tuda24.steps.dao;


import com.tuda24.steps.dto.role.*;
import com.tuda24.steps.entity.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleDAO {
    Role save(Role role);

    Optional<Role> findById(UUID id);

    Optional<Role> findByName(String name);

    Optional<Role> findByNameNative(String name);

    boolean existsByName(String name);

    List<Role> findAll();

    List<Role> searchByName(String keyword);

}
