package com.ucms.ucmsapi.auth.dto;

import jakarta.validation.constraints.*;
public record RegisterRequest(@NotBlank String username, @Email String email, @NotBlank String password, @NotBlank String fullName) {}
