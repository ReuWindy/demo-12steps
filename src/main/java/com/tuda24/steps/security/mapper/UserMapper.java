package com.tuda24.steps.security.mapper;

import com.tuda24.steps.dto.user.UserResponse;
import com.tuda24.steps.security.dto.UpdateUserRequest;
import com.tuda24.steps.security.dto.UpdateUserResponse;
import com.tuda24.steps.entity.Role;
import com.tuda24.steps.entity.User;
import com.tuda24.steps.security.dto.AuthenticatedUserDto;
import com.tuda24.steps.security.dto.RegistrationRequest;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User convertToUser(RegistrationRequest registrationRequest);

    @Mapping(target = "roles", expression = "java(mapRoleNames(user.getRoles()))")
    List<UserResponse> toUserResponseList(List<User> users);

    AuthenticatedUserDto convertToAuthenticatedUserDto(User user);

    @Mapping(target = "roles", expression = "java(mapToRoles(authenticatedUserDto.getRoles()))")
    User convertToUser(AuthenticatedUserDto authenticatedUserDto);

    @Mapping(target = "roles", expression = "java(mapRoleNames(user.getRoles()))")
    UserResponse toCreateResponse(User user);

    @Mapping(target = "roles", expression = "java(mapRoleNames(user.getRoles()))")
    UpdateUserResponse toUpdateResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget User user, UpdateUserRequest request);

    default Set<Role> mapToRoles(Set<String> roleNames) {
        if (roleNames == null) return null;
        return roleNames.stream()
                .map(name -> {
                    Role role = new Role();
                    role.setName(name);
                    return role;
                })
                .collect(Collectors.toSet());
    }

    // Set<Role> -> Set<String>
    default Set<String> mapRoleNames(Set<Role> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}