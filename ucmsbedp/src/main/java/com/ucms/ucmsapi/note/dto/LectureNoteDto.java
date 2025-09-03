
package com.ucms.ucmsapi.note.dto;
import java.time.Instant;

public record LectureNoteDto(
        Long id, Long courseId, String title, String originalFilename, Long size, String contentType,
        String uploaderName, Instant uploadedAt
) {}
