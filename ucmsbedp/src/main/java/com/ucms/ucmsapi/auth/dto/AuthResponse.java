package com.ucms.ucmsapi.auth.dto;

import java.util.Set;
public record AuthResponse(String accessToken, Long id, String username, String fullName, Set<String> roles, String status) {}