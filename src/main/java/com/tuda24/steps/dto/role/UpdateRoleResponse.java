package com.tuda24.steps.dto.role;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRoleResponse {

    private UUID id;
    private String name;
}
