
package com.ucms.ucmsapi.result;

import com.ucms.ucmsapi.course.CourseUnit;
import com.ucms.ucmsapi.user.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name = "results",
        uniqueConstraints = @UniqueConstraint(name = "uk_result_course_student", columnNames = {"course_id","student_id"})
)
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A result is for one course…
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseUnit course;

    // …and one student
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student;

    @Column(nullable = false)
    private Integer totalScore;      // e.g., 0..100

    @Column(length = 8)
    private String grade;            // e.g., A, B+, …

    @Column(nullable = false)
    private boolean published = false;

    @Column(nullable = false)
    private Instant updatedAt;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CourseUnit getCourse() { return course; }
    public void setCourse(CourseUnit course) { this.course = course; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public Integer getTotalScore() { return totalScore; }
    public void setTotalScore(Integer totalScore) { this.totalScore = totalScore; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public boolean isPublished() { return published; }
    public void setPublished(boolean published) { this.published = published; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
