
package com.ucms.ucmsapi.report.dto;

public class CourseSummaryDto {
    private Long courseId;
    private String code;
    private String title;

    private long lecturers;        // assigned lecturers
    private long notes;            // lecture notes count
    private long assignments;      // assignment count
    private long approvedEnrolls;  // approved enrollments
    private long submissions;      // submissions across all assignments
    private long results;          // results rows
    private Double avgScore;       // average totalScore (nullable)

    // getters/setters
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public long getLecturers() { return lecturers; }
    public void setLecturers(long lecturers) { this.lecturers = lecturers; }
    public long getNotes() { return notes; }
    public void setNotes(long notes) { this.notes = notes; }
    public long getAssignments() { return assignments; }
    public void setAssignments(long assignments) { this.assignments = assignments; }
    public long getApprovedEnrolls() { return approvedEnrolls; }
    public void setApprovedEnrolls(long approvedEnrolls) { this.approvedEnrolls = approvedEnrolls; }
    public long getSubmissions() { return submissions; }
    public void setSubmissions(long submissions) { this.submissions = submissions; }
    public long getResults() { return results; }
    public void setResults(long results) { this.results = results; }
    public Double getAvgScore() { return avgScore; }
    public void setAvgScore(Double avgScore) { this.avgScore = avgScore; }
}
