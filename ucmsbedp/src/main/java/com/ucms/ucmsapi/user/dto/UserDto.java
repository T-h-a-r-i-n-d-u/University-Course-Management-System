
package com.ucms.ucmsapi.user.dto;

import java.time.Instant;
import java.util.Set;

public record UserDto(
        Long id,
        String username,
        String fullName,
        String email,
        boolean approved,
        Set<String> roles,
        Instant createdAt
) {}
