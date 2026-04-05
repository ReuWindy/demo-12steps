package com.tuda24.steps.repository;

import com.tuda24.steps.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
