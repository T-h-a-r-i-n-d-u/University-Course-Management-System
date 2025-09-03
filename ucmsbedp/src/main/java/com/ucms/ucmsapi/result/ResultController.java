
package com.ucms.ucmsapi.result;

import com.ucms.ucmsapi.result.dto.ResultDto;
import com.ucms.ucmsapi.result.dto.UpsertResultRequest;
import com.ucms.ucmsapi.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    private final ResultService service;

    @Autowired
    public ResultController(ResultService service) {
        this.service = service;
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public ResultDto upsert(@Valid @RequestBody UpsertResultRequest req) {
        return service.upsert(req);
    }

    @GetMapping("/student/me")
    @PreAuthorize("hasRole('STUDENT')")
    public List<ResultDto> myResults(Principal principal) {
        User me = (User) ((org.springframework.security.core.Authentication) principal).getPrincipal();
        return service.forStudent(me.getId())
                .stream()
                .toList();
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public List<ResultDto> forStudent(@PathVariable Long studentId) {
        return service.forStudent(studentId);
    }


    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public List<ResultDto> forCourse(@PathVariable Long courseId) {
        return service.forCourse(courseId);
    }


    @PatchMapping("/{id}/publish")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public ResultDto publish(@PathVariable Long id, @RequestParam boolean value) {
        return service.setPublish(id, value);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
