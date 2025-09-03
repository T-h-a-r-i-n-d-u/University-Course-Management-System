
package com.ucms.ucmsapi.assignment.dto;

import com.ucms.ucmsapi.assignment.Assignment;
import com.ucms.ucmsapi.assignment.AssignmentSubmission;

public class AssignmentMapper {

    public static AssignmentDto toDto(Assignment a) {
        Long courseId = (a.getCourse() != null ? a.getCourse().getId() : null);
        return new AssignmentDto(
                a.getId(),
                courseId,
                a.getTitle(),
                a.getDescription(),
                a.getDeadline(),
                a.getCreatedAt()
        );
    }

    public static SubmissionDto toDto(AssignmentSubmission s) {
        Long assignmentId = (s.getAssignment() != null ? s.getAssignment().getId() : null);
        Long studentId = (s.getStudent() != null ? s.getStudent().getId() : null);
        String studentName = (s.getStudent() != null ? s.getStudent().getFullName() : null);

        return new SubmissionDto(
                s.getId(),
                assignmentId,
                studentId,
                studentName,
                s.getOriginalFilename(),
                s.getSize(),
                s.getContentType(),
                s.getSubmittedAt(),
                s.getMarks(),
                s.getFeedback()
        );
    }
}
