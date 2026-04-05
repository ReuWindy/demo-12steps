package com.tuda24.steps.dto.role;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRoleRequest {

    @NotBlank(message = "Role name is required")
    private String name;
}
