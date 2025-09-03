
package com.ucms.ucmsapi.enrollment;

import com.ucms.ucmsapi.course.CourseUnit;
import com.ucms.ucmsapi.user.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name = "enrollments",
        uniqueConstraints = @UniqueConstraint(name = "uk_enrollment_course_student", columnNames = {"course_id","student_id"})
)
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Course
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseUnit course;

    // Student
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status = EnrollmentStatus.PENDING;

    @Column(nullable = false)
    private Instant createdAt;

    private Instant decidedAt; // approval/rejection timestamp

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CourseUnit getCourse() { return course; }
    public void setCourse(CourseUnit course) { this.course = course; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public EnrollmentStatus getStatus() { return status; }
    public void setStatus(EnrollmentStatus status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getDecidedAt() { return decidedAt; }
    public void setDecidedAt(Instant decidedAt) { this.decidedAt = decidedAt; }
}
