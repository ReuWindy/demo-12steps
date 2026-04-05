package com.tuda24.steps.controller;

import com.tuda24.steps.security.dto.*;
import com.tuda24.steps.security.jwt.JwtTokenService;
import com.tuda24.steps.security.service.UserServiceImpl;
import com.tuda24.steps.utils.ApiSuccessResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenService jwtTokenService;
    private final UserServiceImpl userService;

    public AuthController(JwtTokenService jwtTokenService, UserServiceImpl userService) {
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiSuccessResponse<RegistrationResponse>> register(
            @RequestBody @Valid RegistrationRequest request
    ) {

        RegistrationResponse responseData = userService.registration(request);

        ApiSuccessResponse<RegistrationResponse> response =
                ApiSuccessResponse.<RegistrationResponse>builder()
                        .message("Registration successfully")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .timestamp(LocalDateTime.now())
                        .data(responseData)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResponse<LoginResponse>> login(
            @RequestBody @Valid LoginRequest request
    ) {

        LoginResponse responseData = jwtTokenService.login(request);

        ApiSuccessResponse<LoginResponse> response =
                ApiSuccessResponse.<LoginResponse>builder()
                        .message("Login successfully")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .timestamp(LocalDateTime.now())
                        .data(responseData)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiSuccessResponse<LoginResponse>> refresh(
            @RequestBody @Valid RefreshTokenRequest request
    ) {

        LoginResponse responseData = jwtTokenService.refreshToken(request);

        ApiSuccessResponse<LoginResponse> response =
                ApiSuccessResponse.<LoginResponse>builder()
                        .message("Refresh token successfully")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .timestamp(LocalDateTime.now())
                        .data(responseData)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiSuccessResponse<LogoutResponse>> logout(
            @RequestBody @Valid RefreshTokenRequest request
    ) {

        LogoutResponse responseData = jwtTokenService.logout(request);

        ApiSuccessResponse<LogoutResponse> response =
                ApiSuccessResponse.<LogoutResponse>builder()
                        .message("Logout successfully")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .timestamp(LocalDateTime.now())
                        .data(responseData)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
