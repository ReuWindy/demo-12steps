package com.tuda24.steps.security.service;

import com.tuda24.steps.dao.RoleDAO;
import com.tuda24.steps.dao.UserDAO;
import com.tuda24.steps.dto.user.UserFilterRequest;
import com.tuda24.steps.dto.user.UserResponse;
import com.tuda24.steps.entity.Role;
import com.tuda24.steps.entity.User;
import com.tuda24.steps.exception.ConflictException;
import com.tuda24.steps.security.dto.*;
import com.tuda24.steps.security.jwt.JwtTokenManager;
import com.tuda24.steps.security.mapper.UserMapper;
import com.tuda24.steps.specification.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    private final JwtTokenManager jwtTokenManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;

    public UserServiceImpl(JwtTokenManager jwtTokenManager, BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper, UserDAO userDAO, RoleDAO roleDAO) {
        this.jwtTokenManager = jwtTokenManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public User findByUsername(String username) {
        if (username == null) {
            return null;
        }
        Optional<User> user = userDAO.findByUsername(username);
        return user.orElse(null);
    }

    @Override
    public List<UserResponse> listAllUser() {
        return userMapper.toUserResponseList(userDAO.findAll());
    }

    @Override
    public UpdateUserResponse updateUserByUserName(String username, UpdateUserRequest request) {

        User user = userDAO.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.isGender());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDateOfBirth(request.getDateOfBirth());

        if (request.getActive() != null) {
            user.setActive(request.getActive());
        }
        User saved = userDAO.save(user);
        return userMapper.toUpdateResponse(saved);
    }

    @Override
    public void changePassword(String token, PasswordRequest request) {

        String username = jwtTokenManager.getUsernameFromToken(token);

        User user = userDAO.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!bCryptPasswordEncoder.matches(request.getOldpass(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(bCryptPasswordEncoder.encode(request.getNewpass()));

        userDAO.save(user);
    }

    @Override
    public RegistrationResponse registration(RegistrationRequest registrationRequest) {
        if (userDAO.existsByPhoneNumber(registrationRequest.getPhone())) {
            throw new ConflictException("Phone number already exists");
        }

        if (registrationRequest.getEmail() != null && userDAO.existsByEmail(registrationRequest.getEmail())) {
            throw new ConflictException("Email already exists");
        }

        Role roleUser = roleDAO.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        User user = User.builder()
                .username(registrationRequest.getUsername())
                .password(bCryptPasswordEncoder.encode(registrationRequest.getPassword()))
                .phoneNumber(registrationRequest.getPhone())
                .email(registrationRequest.getEmail())
                .dateOfBirth(registrationRequest.getDob())
                .gender(registrationRequest.isGender())
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .lastName(registrationRequest.getLastName())
                .active(true)
                .roles(Set.of(roleUser))
                .build();

        userDAO.save(user);

        final String username = user.getUsername();
        final String registrationSuccessMessage = "Registration successful! " + username;
        return new RegistrationResponse(registrationSuccessMessage);
    }

    @Override
    public AuthenticatedUserDto findAuthenticatedUserByUsername(String username) {
        final User user = findByUsername(username);
        return userMapper.convertToAuthenticatedUserDto(user);
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        return userDAO.findByPhoneNumber(phoneNumber).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        if (email == null) {
            return null;
        }
        return userDAO.findByEmail(email).orElse(null);
    }

    @Override
    public Page<UserResponse> getUsers(UserFilterRequest filter, Pageable pageable) {
        Specification<User> spec = Specification
                .where(UserSpecification.hasUsername(filter.getUsername()))
                .and(UserSpecification.hasEmail(filter.getEmail()))
                .and(UserSpecification.hasPhoneNumber(filter.getPhoneNumber()))
                .and(UserSpecification.isActive(filter.getActive()))
                .and(UserSpecification.hasRole(filter.getRole()));

        return userDAO.findAll(spec, pageable)
                .map(userMapper::toCreateResponse);
    }
}
