
package com.ucms.ucmsapi.report.dto;

import java.util.List;

public class AdminReportDto {
    // System totals
    private long totalUsers;
    private long totalStudents;
    private long totalLecturers;
    private long totalAdmins;

    private long totalCourses;
    private long totalAssignments;
    private long totalNotes;
    private long totalSubmissions;
    private long totalResults;

    private long pendingUserApprovals;    // users with status PENDING
    private long pendingEnrollments;      // enrollments PENDING

    // Per-course summaries
    private List<CourseSummaryDto> courses;

    // getters/setters
    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }
    public long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(long totalStudents) { this.totalStudents = totalStudents; }
    public long getTotalLecturers() { return totalLecturers; }
    public void setTotalLecturers(long totalLecturers) { this.totalLecturers = totalLecturers; }
    public long getTotalAdmins() { return totalAdmins; }
    public void setTotalAdmins(long totalAdmins) { this.totalAdmins = totalAdmins; }
    public long getTotalCourses() { return totalCourses; }
    public void setTotalCourses(long totalCourses) { this.totalCourses = totalCourses; }
    public long getTotalAssignments() { return totalAssignments; }
    public void setTotalAssignments(long totalAssignments) { this.totalAssignments = totalAssignments; }
    public long getTotalNotes() { return totalNotes; }
    public void setTotalNotes(long totalNotes) { this.totalNotes = totalNotes; }
    public long getTotalSubmissions() { return totalSubmissions; }
    public void setTotalSubmissions(long totalSubmissions) { this.totalSubmissions = totalSubmissions; }
    public long getTotalResults() { return totalResults; }
    public void setTotalResults(long totalResults) { this.totalResults = totalResults; }
    public long getPendingUserApprovals() { return pendingUserApprovals; }
    public void setPendingUserApprovals(long pendingUserApprovals) { this.pendingUserApprovals = pendingUserApprovals; }
    public long getPendingEnrollments() { return pendingEnrollments; }
    public void setPendingEnrollments(long pendingEnrollments) { this.pendingEnrollments = pendingEnrollments; }
    public List<CourseSummaryDto> getCourses() { return courses; }
    public void setCourses(List<CourseSummaryDto> courses) { this.courses = courses; }
}
