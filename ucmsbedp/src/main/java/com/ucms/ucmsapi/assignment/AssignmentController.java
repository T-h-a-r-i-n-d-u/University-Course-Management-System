// src/main/java/com/ucms/ucmsapi/assignment/AssignmentController.java
package com.ucms.ucmsapi.assignment;

import com.ucms.ucmsapi.assignment.dto.AssignmentDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {
    private final AssignmentService svc;
    public AssignmentController(AssignmentService svc){ this.svc = svc; }

    @GetMapping("/{id}") public AssignmentDto one(@PathVariable Long id){ return svc.one(id); }
    @GetMapping("/course/{courseId}") public List<AssignmentDto> byCourse(@PathVariable Long courseId){ return svc.byCourse(courseId); }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    public AssignmentDto create(@RequestBody Map<String,Object> b){
        return svc.create(
                ((Number)b.get("courseId")).longValue(),
                (String)b.get("title"),
                (String)b.getOrDefault("description",""),
                Instant.parse((String)b.get("deadline"))
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    public AssignmentDto update(@PathVariable Long id, @RequestBody Map<String,Object> b){
        return svc.update(
                id,
                (String)b.get("title"),
                (String)b.getOrDefault("description",""),
                Instant.parse((String)b.get("deadline"))
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    public void delete(@PathVariable Long id){ svc.delete(id); }
}
