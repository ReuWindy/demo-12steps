package com.tuda24.steps.controller;

import com.tuda24.steps.dto.role.CreateRoleRequest;
import com.tuda24.steps.dto.role.CreateRoleResponse;
import com.tuda24.steps.dto.role.UpdateRoleRequest;
import com.tuda24.steps.dto.role.UpdateRoleResponse;
import com.tuda24.steps.service.RoleService;
import com.tuda24.steps.utils.ApiSuccessResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping()
    public ResponseEntity<ApiSuccessResponse<List<CreateRoleResponse>>> getAll() {

        List<CreateRoleResponse> roles = roleService.getAllRoles();

        ApiSuccessResponse<List<CreateRoleResponse>> response =
                ApiSuccessResponse.<List<CreateRoleResponse>>builder()
                        .message("Get all roles successfully")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .timestamp(LocalDateTime.now())
                        .data(roles)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<CreateRoleResponse>> createRole(
            @RequestBody @Valid CreateRoleRequest request
    ) {

        CreateRoleResponse role = roleService.createRole(request);

        ApiSuccessResponse<CreateRoleResponse> response =
                ApiSuccessResponse.<CreateRoleResponse>builder()
                        .message("Create role successfully")
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED.name())
                        .timestamp(LocalDateTime.now())
                        .data(role)
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<ApiSuccessResponse<UpdateRoleResponse>> updateRoleStatus(
            @PathVariable UUID id
    ) {
        UpdateRoleResponse role = roleService.updateRoleStatus(id);

        ApiSuccessResponse<UpdateRoleResponse> response =
                ApiSuccessResponse.<UpdateRoleResponse>builder()
                        .message("Update role successfully")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .timestamp(LocalDateTime.now())
                        .data(role)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<UpdateRoleResponse>> updateRole(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateRoleRequest request
    ) {

        UpdateRoleResponse role = roleService.updateRole(id, request);

        ApiSuccessResponse<UpdateRoleResponse> response =
                ApiSuccessResponse.<UpdateRoleResponse>builder()
                        .message("Update role successfully")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .timestamp(LocalDateTime.now())
                        .data(role)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<CreateRoleResponse>> getRoleById(@PathVariable UUID id) {

        CreateRoleResponse role = roleService.getRoleById(id);

        ApiSuccessResponse<CreateRoleResponse> response =
                ApiSuccessResponse.<CreateRoleResponse>builder()
                        .message("Get role successfully")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .timestamp(LocalDateTime.now())
                        .data(role)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiSuccessResponse<List<CreateRoleResponse>>> searchRoles(
            @RequestParam String keyword
    ) {

        List<CreateRoleResponse> roles = roleService.searchRoles(keyword);

        ApiSuccessResponse<List<CreateRoleResponse>> response =
                ApiSuccessResponse.<List<CreateRoleResponse>>builder()
                        .message("Search roles successfully")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .timestamp(LocalDateTime.now())
                        .data(roles)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}