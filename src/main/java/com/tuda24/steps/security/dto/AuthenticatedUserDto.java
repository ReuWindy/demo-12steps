package com.tuda24.steps.security.dto;

import com.tuda24.steps.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUserDto {
    private UUID id;
    private String username;
    private String password;
    private Set<String> roles;
}
