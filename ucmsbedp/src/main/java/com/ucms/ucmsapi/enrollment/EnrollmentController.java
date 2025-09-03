
package com.ucms.ucmsapi.enrollment;

import com.ucms.ucmsapi.enrollment.dto.EnrollRequest;
import com.ucms.ucmsapi.enrollment.dto.EnrollmentDto;
import com.ucms.ucmsapi.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;

    @Autowired
    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    // Student requests enrollment to a course
    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public EnrollmentDto request(@Valid @RequestBody EnrollRequest req, Principal principal) {
        User student = (User) ((org.springframework.security.core.Authentication) principal).getPrincipal();
        return service.requestEnroll(req, student);
    }

    // Student sees own enrollments
    @GetMapping("/my")
    @PreAuthorize("hasRole('STUDENT')")
    public List<EnrollmentDto> my(Principal principal) {
        User me = (User) ((org.springframework.security.core.Authentication) principal).getPrincipal();
        return service.listMy(me.getId());
    }

    // Lecturer/Admin sees roster for a course
    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public List<EnrollmentDto> byCourse(@PathVariable Long courseId) {
        return service.listByCourse(courseId);
    }

    // Approve request (Lecturer/Admin)
    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public EnrollmentDto approve(@PathVariable Long id, Principal principal) {
        User actor = (User) ((org.springframework.security.core.Authentication) principal).getPrincipal();
        return service.approve(id, actor);
    }

    // Reject request (Lecturer/Admin)
    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public EnrollmentDto reject(@PathVariable Long id, Principal principal) {
        User actor = (User) ((org.springframework.security.core.Authentication) principal).getPrincipal();
        return service.reject(id, actor);
    }

    // Unenroll (owner student or admin)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Principal principal) {
        User actor = (User) ((org.springframework.security.core.Authentication) principal).getPrincipal();
        service.unenroll(id, actor);
    }
}
