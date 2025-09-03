
package com.ucms.ucmsapi.assignment;

import com.ucms.ucmsapi.assignment.dto.SubmissionDto;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException; import java.nio.file.Path; import java.util.List; import java.util.Map;



@RestController
@RequestMapping("/api/assignments")
public class AssignmentSubmissionController {
    private final AssignmentSubmissionService svc;
    public AssignmentSubmissionController(AssignmentSubmissionService svc){ this.svc = svc; }

    // student submit
    @PostMapping("/{assignmentId}")
    @PreAuthorize("hasRole('STUDENT')")
    public SubmissionDto submit(@PathVariable Long assignmentId, @RequestParam("file") MultipartFile file) throws IOException {
        return svc.submit(assignmentId, file);
    }

    // lecturer/admin list
    @GetMapping("/assignment/{assignmentId}")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public List<SubmissionDto> forAssignment(@PathVariable Long assignmentId){ return svc.listForAssignment(assignmentId); }

    // student view own
    @GetMapping("/mine/{assignmentId}")
    @PreAuthorize("hasRole('STUDENT')")
    public SubmissionDto mine(@PathVariable Long assignmentId){ return svc.mySubmission(assignmentId); }

    // grade
    @PostMapping("/{submissionId}/grade")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public SubmissionDto grade(@PathVariable Long submissionId, @RequestBody Map<String,Object> b){
        Integer marks = b.get("marks")==null? null : ((Number)b.get("marks")).intValue();
        String feedback = (String)b.getOrDefault("feedback","");
        return svc.grade(submissionId, marks, feedback);
    }

    @GetMapping("/{submissionId}/file")
    public ResponseEntity<FileSystemResource> download(@PathVariable Long submissionId) throws IOException {
//        Path p = svc.downloadFile(submissionId);
//        FileSystemResource fs = new FileSystemResource(p);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fs.getFilename() + "\"")
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .contentLength(fs.contentLength())
//                .body(fs);
        return null;
    }
}
