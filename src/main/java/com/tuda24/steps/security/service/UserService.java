package com.tuda24.steps.security.service;

import com.tuda24.steps.dto.user.UserFilterRequest;
import com.tuda24.steps.dto.user.UserResponse;
import com.tuda24.steps.entity.User;
import com.tuda24.steps.security.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User findByUsername(String username);

    List<UserResponse> listAllUser();

    UpdateUserResponse updateUserByUserName(String username, UpdateUserRequest userDto);

    void changePassword(String token, PasswordRequest request);

    RegistrationResponse registration(RegistrationRequest registrationRequest);

    AuthenticatedUserDto findAuthenticatedUserByUsername(String username);

    User findByPhoneNumber(String phoneNumber);

    User findByEmail(String email);

    Page<UserResponse> getUsers(UserFilterRequest filter, Pageable pageable);
}
