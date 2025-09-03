package com.ucms.ucmsapi.course;

import com.ucms.ucmsapi.user.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course_units")
public class CourseUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String title;

    private String description;
    private Integer credits;
    private String semester;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_lecturers",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "lecturer_id")
    )
    private Set<User> lecturers = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public Set<User> getLecturers() { return lecturers; }
    public void setLecturers(Set<User> lecturers) { this.lecturers = lecturers; }
}