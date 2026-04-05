package com.tuda24.steps.service.implement;

import com.tuda24.steps.dao.RoleDAO;
import com.tuda24.steps.dto.role.CreateRoleRequest;
import com.tuda24.steps.dto.role.CreateRoleResponse;
import com.tuda24.steps.dto.role.UpdateRoleRequest;
import com.tuda24.steps.dto.role.UpdateRoleResponse;
import com.tuda24.steps.entity.Role;
import com.tuda24.steps.exception.NotFoundException;
import com.tuda24.steps.mapper.RoleMapper;
import com.tuda24.steps.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    final RoleDAO roleDAO;

    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public CreateRoleResponse createRole(CreateRoleRequest request) {

        if (roleDAO.existsByName(request.getName())) {
            throw new RuntimeException("Role name already exists!");
        }

        Role role = RoleMapper.toEntity(request);

        Role savedRole = roleDAO.save(role);

        return RoleMapper.toCreateResponse(savedRole);
    }

    @Override
    public UpdateRoleResponse updateRoleStatus(UUID id) {
        Role role = roleDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found!"));

        RoleMapper.updateStatus(role);

        Role updatedRole = roleDAO.save(role);

        return RoleMapper.toUpdateResponse(updatedRole);
    }

    @Override
    public UpdateRoleResponse updateRole(UUID id, UpdateRoleRequest request) {

        Role role = roleDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found!"));

        roleDAO.findByName(request.getName()).ifPresent(existingRole -> {
            if (!existingRole.getId().equals(id)) {
                throw new RuntimeException("Role name already exists!");
            }
        });

        RoleMapper.updateEntity(role, request);

        Role updatedRole = roleDAO.save(role);

        return RoleMapper.toUpdateResponse(updatedRole);
    }

    @Override
    public List<CreateRoleResponse> getAllRoles() {
        return roleDAO.findAll()
                .stream()
                .map(RoleMapper::toCreateResponse)
                .toList();
    }

    @Override
    public CreateRoleResponse getRoleById(UUID id) {
        Role role = roleDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found!"));

        return RoleMapper.toCreateResponse(role);
    }

    @Override
    public List<CreateRoleResponse> searchRoles(String keyword) {
        return roleDAO.searchByName(keyword)
                .stream()
                .map(RoleMapper::toCreateResponse)
                .toList();
    }
}
