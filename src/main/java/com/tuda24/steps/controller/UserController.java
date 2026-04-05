package com.tuda24.steps.controller;

import com.tuda24.steps.dto.user.UserFilterRequest;
import com.tuda24.steps.dto.user.UserResponse;
import com.tuda24.steps.entity.User;
import com.tuda24.steps.security.dto.*;
import com.tuda24.steps.security.service.UserService;
import com.tuda24.steps.utils.ApiSuccessResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<List<UserResponse>>> getAll() {
        List<UserResponse> users = userService.listAllUser();

        ApiSuccessResponse<List<UserResponse>> response =
                ApiSuccessResponse.<List<UserResponse>>builder()
                        .message("Get all users successfully")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .timestamp(LocalDateTime.now())
                        .data(users)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiSuccessResponse<User>> getUserByUsername(
            @PathVariable String username
    ) {
        User user = userService.findByUsername(username);

        ApiSuccessResponse<User> response =
                ApiSuccessResponse.<User>builder()
                        .message("Get user successfully")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .timestamp(LocalDateTime.now())
                        .data(user)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{username}")
    public ResponseEntity<ApiSuccessResponse<UpdateUserResponse>> updateUser(
            @PathVariable String username,
            @RequestBody @Valid UpdateUserRequest request
    ) {
        UpdateUserResponse user = userService.updateUserByUserName(username, request);

        ApiSuccessResponse<UpdateUserResponse> response =
                ApiSuccessResponse.<UpdateUserResponse>builder()
                        .message("Update user successfully")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .timestamp(LocalDateTime.now())
                        .data(user)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiSuccessResponse<Void>> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid PasswordRequest request
    ) {
        userService.changePassword(token, request);

        ApiSuccessResponse<Void> response =
                ApiSuccessResponse.<Void>builder()
                        .message("Change password successfully")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiSuccessResponse<RegistrationResponse>> register(
            @RequestBody @Valid RegistrationRequest request
    ) {
        RegistrationResponse registered = userService.registration(request);

        ApiSuccessResponse<RegistrationResponse> response =
                ApiSuccessResponse.<RegistrationResponse>builder()
                        .message("Registration successfully")
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED.name())
                        .timestamp(LocalDateTime.now())
                        .data(registered)
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/search/phone")
    public ResponseEntity<ApiSuccessResponse<User>> getUserByPhone(
            @RequestParam String phoneNumber
    ) {
        User user = userService.findByPhoneNumber(phoneNumber);

        ApiSuccessResponse<User> response =
                ApiSuccessResponse.<User>builder()
                        .message("Get user successfully")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .timestamp(LocalDateTime.now())
                        .data(user)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search/email")
    public ResponseEntity<ApiSuccessResponse<User>> getUserByEmail(
            @RequestParam String email
    ) {
        User user = userService.findByEmail(email);

        ApiSuccessResponse<User> response =
                ApiSuccessResponse.<User>builder()
                        .message("Get user successfully")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .timestamp(LocalDateTime.now())
                        .data(user)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/paging")
    public ResponseEntity<ApiSuccessResponse<PagedModel<EntityModel<UserResponse>>>> getUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            PagedResourcesAssembler<UserResponse> assembler
    ) {
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        UserFilterRequest filter = new UserFilterRequest();
        filter.setUsername(username);
        filter.setEmail(email);
        filter.setPhoneNumber(phoneNumber);
        filter.setRole(role);
        filter.setActive(active);

        Page<UserResponse> userPage = userService.getUsers(filter, pageable);
        PagedModel<EntityModel<UserResponse>> pagedModel = assembler.toModel(userPage);

        ApiSuccessResponse<PagedModel<EntityModel<UserResponse>>> response =
                ApiSuccessResponse.<PagedModel<EntityModel<UserResponse>>>builder()
                        .message("Get users successfully")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .timestamp(LocalDateTime.now())
                        .data(pagedModel)
                        .build();

        return ResponseEntity.ok(response);
    }
}
