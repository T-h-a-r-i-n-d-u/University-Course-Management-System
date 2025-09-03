// src/main/java/com/ucms/ucmsapi/assignment/dto/AssignmentDto.java
package com.ucms.ucmsapi.assignment.dto;
import java.time.Instant;

public record AssignmentDto(
        Long id, Long courseId, String title, String description, Instant deadline, Instant createdAt
) {}
