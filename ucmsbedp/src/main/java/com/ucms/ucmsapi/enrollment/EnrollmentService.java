
package com.ucms.ucmsapi.enrollment;

import com.ucms.ucmsapi.course.CourseRepository;
import com.ucms.ucmsapi.course.CourseUnit;
import com.ucms.ucmsapi.enrollment.dto.EnrollRequest;
import com.ucms.ucmsapi.enrollment.dto.EnrollmentDto;

import com.ucms.ucmsapi.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollments;
    private final CourseRepository courses;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollments, CourseRepository courses) {
        this.enrollments = enrollments;
        this.courses = courses;
    }

    private EnrollmentDto toDto(Enrollment e) {
        EnrollmentDto d = new EnrollmentDto();
        d.setId(e.getId());
        d.setCourseId(e.getCourse() != null ? e.getCourse().getId() : null);
        d.setStudentId(e.getStudent() != null ? e.getStudent().getId() : null);
        d.setStatus(e.getStatus());
        d.setCreatedAt(e.getCreatedAt());
        d.setDecidedAt(e.getDecidedAt());
        return d;
    }

    public EnrollmentDto requestEnroll(EnrollRequest req, User student) {
        CourseUnit course = courses.findById(req.getCourseId()).orElseThrow();

        // If already approved, block duplicate
        if (enrollments.existsByCourse_IdAndStudent_IdAndStatus(course.getId(), student.getId(), EnrollmentStatus.APPROVED)) {
            throw new RuntimeException("Already enrolled");
        }

        // If a pending exists, return it
        Enrollment existing = enrollments.findByCourse_IdAndStudent_Id(course.getId(), student.getId()).orElse(null);
        if (existing != null && existing.getStatus() == EnrollmentStatus.PENDING) {
            return toDto(existing);
        }

        Enrollment e = new Enrollment();
        e.setCourse(course);
        e.setStudent(student);
        e.setStatus(EnrollmentStatus.PENDING);
        e.setCreatedAt(Instant.now());
        e = enrollments.save(e);
        return toDto(e);
    }

    public EnrollmentDto approve(Long id, User actor) {
        Enrollment e = enrollments.findById(id).orElseThrow();
        // simple rule: lecturers/admins can approve
        if (!actor.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_LECTURER"))) {
            throw new RuntimeException("Only lecturer/admin can approve");
        }
        e.setStatus(EnrollmentStatus.APPROVED);
        e.setDecidedAt(Instant.now());
        e = enrollments.save(e);
        return toDto(e);
    }

    public EnrollmentDto reject(Long id, User actor) {
        Enrollment e = enrollments.findById(id).orElseThrow();
        if (!actor.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_LECTURER"))) {
            throw new RuntimeException("Only lecturer/admin can reject");
        }
        e.setStatus(EnrollmentStatus.REJECTED);
        e.setDecidedAt(Instant.now());
        e = enrollments.save(e);
        return toDto(e);
    }

    public void unenroll(Long id, User actor) {
        Enrollment e = enrollments.findById(id).orElseThrow();
        boolean isOwnerStudent = e.getStudent() != null && actor.getId().equals(e.getStudent().getId())
                && actor.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"));
        boolean isAdmin = actor.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!(isOwnerStudent || isAdmin)) {
            throw new RuntimeException("Not allowed to unenroll this record");
        }
        enrollments.deleteById(id);
    }

    public List<EnrollmentDto> listMy(Long studentId) {
        return enrollments.findByStudent_IdOrderByCreatedAtDesc(studentId)
                .stream().map(this::toDto).toList();
    }

    public List<EnrollmentDto> listByCourse(Long courseId) {
        return enrollments.findByCourse_IdOrderByCreatedAtDesc(courseId)
                .stream().map(this::toDto).toList();
    }

    // Helper for RBAC in other services
    public boolean isStudentEnrolled(Long studentId, Long courseId) {
        return enrollments.existsByCourse_IdAndStudent_IdAndStatus(courseId, studentId, EnrollmentStatus.APPROVED);
    }
}
