
package com.ucms.ucmsapi.report;

import com.ucms.ucmsapi.assignment.AssignmentRepository;
import com.ucms.ucmsapi.assignment.SubmissionRepository;
import com.ucms.ucmsapi.course.CourseRepository;
import com.ucms.ucmsapi.course.CourseUnit;
import com.ucms.ucmsapi.enrollment.EnrollmentRepository;
import com.ucms.ucmsapi.enrollment.EnrollmentStatus;
import com.ucms.ucmsapi.note.LectureNoteRepository;
import com.ucms.ucmsapi.report.dto.AdminReportDto;
import com.ucms.ucmsapi.report.dto.CourseSummaryDto;
import com.ucms.ucmsapi.result.ResultRepository;

import com.ucms.ucmsapi.user.UserRepository;
import com.ucms.ucmsapi.user.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminReportService {

    private final UserRepository users;
    private final CourseRepository courses;
    private final AssignmentRepository assignments;
    private final LectureNoteRepository notes;
    private final EnrollmentRepository enrollments;
    private final SubmissionRepository submissions;
    private final ResultRepository results;

    @Autowired
    public AdminReportService(UserRepository users,
                              CourseRepository courses,
                              AssignmentRepository assignments,
                              LectureNoteRepository notes,
                              EnrollmentRepository enrollments,
                              SubmissionRepository submissions,
                              ResultRepository results) {
        this.users = users;
        this.courses = courses;
        this.assignments = assignments;
        this.notes = notes;
        this.enrollments = enrollments;
        this.submissions = submissions;
        this.results = results;
    }

    public AdminReportDto build() {
        AdminReportDto dto = new AdminReportDto();

        // Totals
        dto.setTotalUsers(users.count());
        dto.setTotalStudents(users.countStudents());
        dto.setTotalLecturers(users.countLecturers());
        dto.setTotalAdmins(users.countAdmins());

        dto.setTotalCourses(courses.count());
        dto.setTotalAssignments(assignments.count());
        dto.setTotalNotes(notes.count());
        dto.setTotalSubmissions(submissions.count());
        dto.setTotalResults(results.count());

        dto.setPendingUserApprovals(users.countByStatus(UserStatus.PENDING));
        dto.setPendingEnrollments(enrollments.countByStatus(EnrollmentStatus.PENDING));

        // Per-course summaries
        List<CourseSummaryDto> list = new ArrayList<>();
        for (CourseUnit c : courses.findAll()) {
            CourseSummaryDto cs = new CourseSummaryDto();
            cs.setCourseId(c.getId());
            cs.setCode(c.getCode());
            cs.setTitle(c.getTitle());

            cs.setLecturers(c.getLecturers() != null ? c.getLecturers().size() : 0);
            cs.setNotes(notes.countByCourse_Id(c.getId()));
            cs.setAssignments(assignments.countByCourse_Id(c.getId()));
            cs.setApprovedEnrolls(enrollments.countByCourse_IdAndStatus(c.getId(), EnrollmentStatus.APPROVED));
            cs.setSubmissions(submissions.countByAssignment_Course_Id(c.getId()));
            cs.setResults(results.countByCourse_Id(c.getId()));
            cs.setAvgScore(results.avgScoreByCourse(c.getId())); // may be null

            list.add(cs);
        }
        dto.setCourses(list);

        return dto;
    }
}
