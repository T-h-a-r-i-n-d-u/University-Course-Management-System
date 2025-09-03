
package com.ucms.ucmsapi.result.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpsertResultRequest {
    @NotNull
    private Long courseId;
    @NotNull
    private Long studentId;
    @NotNull @Min(0) @Max(100)
    private Integer totalScore;

    // optional: if null, we compute grade automatically
    private String grade;

    private Boolean published;

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Integer getTotalScore() { return totalScore; }
    public void setTotalScore(Integer totalScore) { this.totalScore = totalScore; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public Boolean getPublished() { return published; }
    public void setPublished(Boolean published) { this.published = published; }
}
