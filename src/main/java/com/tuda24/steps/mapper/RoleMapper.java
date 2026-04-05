package com.tuda24.steps.mapper;

import com.tuda24.steps.dto.role.CreateRoleRequest;
import com.tuda24.steps.dto.role.CreateRoleResponse;
import com.tuda24.steps.dto.role.UpdateRoleRequest;
import com.tuda24.steps.dto.role.UpdateRoleResponse;
import com.tuda24.steps.entity.Role;

public class RoleMapper {
    public static Role toEntity(CreateRoleRequest request) {
        return Role.builder()
                .name(request.getName())
                .build();
    }

    public static CreateRoleResponse toCreateResponse(Role role) {
        return CreateRoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public static void updateEntity(Role role, UpdateRoleRequest request) {
        role.setName(request.getName());
    }

    public static void updateStatus(Role role) {
        role.setActive(!role.isActive());
    }

    public static UpdateRoleResponse toUpdateResponse(Role role) {
        return UpdateRoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
