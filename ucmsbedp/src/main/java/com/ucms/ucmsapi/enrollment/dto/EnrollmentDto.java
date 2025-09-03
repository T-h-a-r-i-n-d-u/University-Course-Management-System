
package com.ucms.ucmsapi.enrollment.dto;

import com.ucms.ucmsapi.enrollment.EnrollmentStatus;

import java.time.Instant;

public class EnrollmentDto {
    private Long id;
    private Long courseId;
    private Long studentId;
    private EnrollmentStatus status;
    private Instant createdAt;
    private Instant decidedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public EnrollmentStatus getStatus() { return status; }
    public void setStatus(EnrollmentStatus status) { this.status = status; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getDecidedAt() { return decidedAt; }
    public void setDecidedAt(Instant decidedAt) { this.decidedAt = decidedAt; }
}
