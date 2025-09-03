package com.ucms.ucmsapi.course;

import com.ucms.ucmsapi.course.dto.CourseDto;
import com.ucms.ucmsapi.course.dto.CourseUpsertRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService service;
    public CourseController(CourseService service) { this.service = service; }

    // READ: list & view
    @PreAuthorize("hasAnyRole('ADMIN','LECTURER','STUDENT')")
    @GetMapping
    public List<CourseDto> list() { return service.all(); }

    @PreAuthorize("hasAnyRole('ADMIN','LECTURER','STUDENT')")
    @GetMapping("/{id}")
    public CourseDto getOne(@PathVariable Long id) { return service.getOne(id); }

    // CREATE
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CourseDto> create(@RequestBody CourseUpsertRequest body) {
        CourseDto created = service.create(body);
        return ResponseEntity.created(URI.create("/api/courses/" + created.id())).body(created);
    }

    // UPDATE
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CourseDto update(@PathVariable Long id, @RequestBody CourseUpsertRequest body) {
        return service.update(id, body);
    }

    // DELETE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ASSIGN LECTURER
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{cid}/lecturers/{uid}")
    public CourseDto assign(@PathVariable Long cid, @PathVariable Long uid) {
        return service.assignLecturer(cid, uid);
    }
}
