package com.tuda24.steps.service;

import com.tuda24.steps.dao.RoleDAO;
import com.tuda24.steps.dto.role.*;
import com.tuda24.steps.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface RoleService {

    CreateRoleResponse createRole(CreateRoleRequest request);

    UpdateRoleResponse updateRoleStatus(UUID id);

    UpdateRoleResponse updateRole(UUID id, UpdateRoleRequest request);

    List<CreateRoleResponse> getAllRoles();

    CreateRoleResponse getRoleById(UUID id);

    List<CreateRoleResponse> searchRoles(String keyword);
}
