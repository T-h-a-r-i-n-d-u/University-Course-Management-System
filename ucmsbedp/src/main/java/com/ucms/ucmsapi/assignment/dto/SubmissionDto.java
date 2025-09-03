
package com.ucms.ucmsapi.assignment.dto;
import java.time.Instant;

public record SubmissionDto(
        Long id, Long assignmentId, Long studentId, String studentName,
        String originalFilename, Long size, String contentType,
        Instant submittedAt, Integer marks, String feedback
) {}
