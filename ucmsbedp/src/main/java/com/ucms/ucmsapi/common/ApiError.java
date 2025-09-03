package com.ucms.ucmsapi.common;

public record ApiError(String code, String message, String path) {}