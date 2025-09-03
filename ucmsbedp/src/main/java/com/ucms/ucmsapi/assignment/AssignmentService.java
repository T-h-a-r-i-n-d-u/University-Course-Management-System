// src/main/java/com/ucms/ucmsapi/assignment/AssignmentService.java
package com.ucms.ucmsapi.assignment;

import com.ucms.ucmsapi.assignment.dto.AssignmentDto;
import com.ucms.ucmsapi.assignment.dto.AssignmentMapper;
import com.ucms.ucmsapi.common.SecurityUtils;
import com.ucms.ucmsapi.course.CourseRepository;
import com.ucms.ucmsapi.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class AssignmentService {
    private final AssignmentRepository repo;
    private final CourseRepository courses;
    private final SecurityUtils sec;

    public AssignmentService(AssignmentRepository repo, CourseRepository courses, SecurityUtils sec){
        this.repo = repo; this.courses = courses; this.sec = sec;
    }

    private boolean canManageCourse(User u, Long courseId){
        if (sec.hasRole(u, "ADMIN")) return true;
        var c = courses.findById(courseId).orElseThrow();
        return c.getLecturers().stream().anyMatch(l -> l.getId().equals(u.getId()));
    }

    public AssignmentDto create(Long courseId, String title, String desc, Instant deadline){
        var u = sec.currentUser();
        if (!canManageCourse(u, courseId)) throw new RuntimeException("Not allowed");
        var c = courses.findById(courseId).orElseThrow();
        var a = new Assignment();
        a.setCourse(c); a.setTitle(title); a.setDescription(desc); a.setDeadline(deadline);
        return AssignmentMapper.toDto(repo.save(a));
    }

    public AssignmentDto update(Long id, String title, String desc, Instant deadline){
        var a = repo.findById(id).orElseThrow();
        var u = sec.currentUser();
        if (!canManageCourse(u, a.getCourse().getId())) throw new RuntimeException("Not allowed");
        a.setTitle(title); a.setDescription(desc); a.setDeadline(deadline);
        return AssignmentMapper.toDto(repo.save(a));
    }

    public void delete(Long id){
        var a = repo.findById(id).orElseThrow();
        var u = sec.currentUser();
        if (!canManageCourse(u, a.getCourse().getId())) throw new RuntimeException("Not allowed");
        repo.delete(a);
    }

    @Transactional(readOnly = true)
    public AssignmentDto one(Long id){ return repo.findById(id).map(AssignmentMapper::toDto).orElseThrow(); }

    @Transactional(readOnly = true)
    public List<AssignmentDto> byCourse(Long courseId){
        return repo.findByCourseId(courseId).stream().map(AssignmentMapper::toDto).toList();
    }
}
