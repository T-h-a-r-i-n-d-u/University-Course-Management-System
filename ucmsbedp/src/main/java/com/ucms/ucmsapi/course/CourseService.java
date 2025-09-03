package com.ucms.ucmsapi.course;

import com.ucms.ucmsapi.course.dto.CourseDto;
import com.ucms.ucmsapi.course.dto.CourseUpsertRequest;
import com.ucms.ucmsapi.user.User;
import com.ucms.ucmsapi.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseService {

    private final CourseRepository repo;
    private final UserRepository users;

    public CourseService(CourseRepository repo, UserRepository users) {
        this.repo = repo;
        this.users = users;
    }

    // ---------- queries (read-only) ----------
    @Transactional(readOnly = true)
    public List<CourseDto> all() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public CourseDto getOne(Long id) {
        CourseUnit c = repo.findById(id).orElseThrow(() -> notFound(id));
        return toDto(c);
    }

    // ---------- commands ----------
    public CourseDto create(CourseUpsertRequest r) {
        String code = nullSafe(r.code());
        String title = nullSafe(r.title());
        if (code.isBlank() || title.isBlank())
            throw new IllegalArgumentException("Code and title are required");

        if (repo.existsByCodeIgnoreCase(code))
            throw new IllegalArgumentException("Course code already exists");

        CourseUnit c = new CourseUnit();
        c.setCode(code);
        c.setTitle(title);
        c.setDescription(nullSafe(r.description()));
        c.setCredits(r.credits() != null ? r.credits() : 3);
        c.setSemester(nullSafe(r.semester(), "S1"));
        return toDto(repo.save(c));
    }

    public CourseDto update(Long id, CourseUpsertRequest r) {
        CourseUnit c = repo.findById(id).orElseThrow(() -> notFound(id));

        String code = nullSafe(r.code(), c.getCode());
        String title = nullSafe(r.title(), c.getTitle());
        if (repo.existsByCodeIgnoreCaseAndIdNot(code, id))
            throw new IllegalArgumentException("Course code already exists");

        c.setCode(code);
        c.setTitle(title);
        if (r.description() != null) c.setDescription(r.description());
        if (r.credits() != null) c.setCredits(r.credits());
        if (r.semester() != null) c.setSemester(r.semester());

        return toDto(repo.save(c));
    }

    public void delete(Long id) {
        CourseUnit c = repo.findById(id).orElseThrow(() -> notFound(id));
        repo.delete(c);
    }

    public CourseDto assignLecturer(Long courseId, Long lecturerUserId) {
        CourseUnit c = repo.findById(courseId).orElseThrow(() -> notFound(courseId));
        User lec = users.findById(lecturerUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + lecturerUserId));
        c.getLecturers().add(lec);
        return toDto(repo.save(c));
    }

    // ---------- helpers ----------
    private CourseDto toDto(CourseUnit c) {
        return new CourseDto(
                c.getId(),
                c.getCode(),
                c.getTitle(),
                c.getDescription(),
                c.getCredits(),
                c.getSemester()
        );
    }

    private EntityNotFoundException notFound(Long id) {
        return new EntityNotFoundException("Course not found: " + id);
    }

    private String nullSafe(String s) { return s == null ? "" : s.trim(); }
    private String nullSafe(String s, String fallback) {
        String v = (s == null ? "" : s.trim());
        return v.isEmpty() ? fallback : v;
    }
}
