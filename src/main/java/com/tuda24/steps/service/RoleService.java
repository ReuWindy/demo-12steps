package com.tuda24.steps.service;

import com.tuda24.steps.dao.RoleDAO;
import com.tuda24.steps.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    final RoleDAO roleDAO;

    public RoleService(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    public List<Role> getAlls() {
        return roleDAO.getAllRole();
    };
}
