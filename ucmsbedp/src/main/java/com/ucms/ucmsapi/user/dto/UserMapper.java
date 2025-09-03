
package com.ucms.ucmsapi.user.dto;

import com.ucms.ucmsapi.user.Role;
import com.ucms.ucmsapi.user.User;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto toDto(User u) {
        return new UserDto(
                u.getId(),
                u.getUsername(),
                u.getFullName(),
                u.getEmail(),
                u.isApproved(),
                u.getRoles().stream().map(Role::name).collect(Collectors.toSet()),
                u.getCreatedAt()
        );
    }
}
