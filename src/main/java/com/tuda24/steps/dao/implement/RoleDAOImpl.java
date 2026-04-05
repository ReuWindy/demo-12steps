package com.tuda24.steps.dao.implement;

import com.tuda24.steps.dao.RoleDAO;
import com.tuda24.steps.dto.role.*;
import com.tuda24.steps.entity.Role;
import com.tuda24.steps.repository.RoleRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RoleDAOImpl implements RoleDAO {
    final RoleRepository roleRepository;

    public RoleDAOImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> findById(UUID id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Optional<Role> findByNameNative(String name) {
        return roleRepository.findRoleByNameNative(name);
    }

    @Override
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> searchByName(String keyword) {
        return roleRepository.findByNameContainingIgnoreCase(keyword);
    }

}
