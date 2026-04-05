package com.tuda24.steps.dto.user;

import lombok.Data;

@Data
public class UserFilterRequest {
    private String username;
    private String email;
    private String phoneNumber;
    private String role;
    private Boolean active;
}