package com.ucms.ucmsapi.common;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField()+": "+e.getDefaultMessage()).findFirst().orElse("Validation error");
        return ResponseEntity.badRequest().body(new ApiError("VALIDATION", msg, req.getRequestURI()));
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> runtime(RuntimeException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError("ERROR", ex.getMessage(), req.getRequestURI()));
    }
}