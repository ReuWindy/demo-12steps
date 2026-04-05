package com.tuda24.steps.security.dto;

import com.tuda24.steps.validation.annotation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRequest {
    private String oldpass;

    @ValidPassword
    private String newpass;
}