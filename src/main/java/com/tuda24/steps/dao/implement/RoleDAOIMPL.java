package com.tuda24.steps.dao.implement;

import com.tuda24.steps.dao.RoleDAO;
import com.tuda24.steps.entity.Role;
import com.tuda24.steps.repository.RoleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAOIMPL implements RoleDAO {
    final RoleRepository roleRepository;

    public RoleDAOIMPL(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }
}
