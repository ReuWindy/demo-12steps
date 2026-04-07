package com.tuda24.steps.repository.Role;

import com.tuda24.steps.entity.Role;

import java.util.Optional;

public interface RoleRepositoryCustom {
    Optional<Role> findRoleByNameNative(String name);
}
