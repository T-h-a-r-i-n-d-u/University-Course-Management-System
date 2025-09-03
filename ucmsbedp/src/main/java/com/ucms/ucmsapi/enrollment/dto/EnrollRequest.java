
package com.ucms.ucmsapi.enrollment.dto;

import jakarta.validation.constraints.NotNull;

public class EnrollRequest {
    @NotNull
    private Long courseId;

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
}
