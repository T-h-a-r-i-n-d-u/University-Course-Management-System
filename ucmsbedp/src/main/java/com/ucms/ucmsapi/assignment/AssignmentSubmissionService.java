
package com.ucms.ucmsapi.assignment;

import com.ucms.ucmsapi.assignment.dto.AssignmentMapper;
import com.ucms.ucmsapi.assignment.dto.SubmissionDto;
import com.ucms.ucmsapi.common.SecurityUtils;
import com.ucms.ucmsapi.course.CourseRepository;
import com.ucms.ucmsapi.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException; import java.nio.file.*; import java.util.List; import java.util.UUID;

@Service
@Transactional
public class AssignmentSubmissionService {

    private final SubmissionRepository repo;
    private final AssignmentRepository assignments;
    private final CourseRepository courses;
    private final SecurityUtils sec;
//    private final Path uploadDir;


    public AssignmentSubmissionService(
            SubmissionRepository repo, AssignmentRepository assignments, CourseRepository courses, SecurityUtils sec
//            @Value("${app.upload.dir}") String uploadDirStr
    ) throws IOException {
        this.repo = repo; this.assignments = assignments; this.courses = courses; this.sec = sec;
//        this.uploadDir = Path.of(uploadDirStr).toAbsolutePath().normalize();
//        try {
//            Files.createDirectories(this.uploadDir);
//        } catch (IOException e) {
//            throw new IllegalStateException(
//                    "Cannot create upload directory: " + this.uploadDir + ". " +
//                            "Check container volume/permissions.", e);
//        }
    }

    private boolean canManageAssignment(User u, Assignment a){
        if (sec.hasRole(u, "ADMIN")) return true;
        var c = a.getCourse();
        return c.getLecturers().stream().anyMatch(l -> l.getId().equals(u.getId()));
    }

    public SubmissionDto submit(Long assignmentId, MultipartFile file) throws IOException {
        var a = assignments.findById(assignmentId).orElseThrow();
        var now = java.time.Instant.now();
        if (now.isAfter(a.getDeadline())) throw new RuntimeException("Deadline passed");

        var u = sec.currentUser();
        if (!sec.hasRole(u, "STUDENT")) throw new RuntimeException("Only students can submit");

        // overwrite if exists
        var prev = repo.findByAssignmentIdAndStudentId(assignmentId, u.getId()).orElse(null);
        if (prev != null) {
//            try { Files.deleteIfExists(this.uploadDir.resolve(prev.getStoredFilename())); } catch (Exception ignored){}
            repo.delete(prev);
        }

        var stored = UUID.randomUUID() + "_" + file.getOriginalFilename();
//        Files.copy(file.getInputStream(), this.uploadDir.resolve(stored), StandardCopyOption.REPLACE_EXISTING);

        var s = new AssignmentSubmission();
        s.setAssignment(a); s.setStudent(u);
        s.setStoredFilename(stored);
        s.setOriginalFilename(file.getOriginalFilename());
        s.setContentType(file.getContentType() == null ? "application/octet-stream" : file.getContentType());
        s.setSize(file.getSize());

        return AssignmentMapper.toDto(repo.save(s));
    }

    @Transactional(readOnly = true)
    public List<SubmissionDto> listForAssignment(Long assignmentId){
        var a = assignments.findById(assignmentId).orElseThrow();
        var u = sec.currentUser();
        if (!canManageAssignment(u, a)) throw new RuntimeException("Not allowed");
        return repo.findByAssignmentId(assignmentId).stream().map(AssignmentMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public SubmissionDto mySubmission(Long assignmentId){
        var u = sec.currentUser();
        return repo.findByAssignmentIdAndStudentId(assignmentId, u.getId()).map(AssignmentMapper::toDto)
                .orElseThrow(() -> new RuntimeException("No submission"));
    }

    public SubmissionDto grade(Long submissionId, Integer marks, String feedback){
        var s = repo.findById(submissionId).orElseThrow();
        var u = sec.currentUser();
        if (!canManageAssignment(u, s.getAssignment())) throw new RuntimeException("Not allowed");
        s.setMarks(marks); s.setFeedback(feedback);
        return AssignmentMapper.toDto(repo.save(s));
    }

//    @Transactional(readOnly = true)
//    public Path downloadFile(Long submissionId){
//        var s = repo.findById(submissionId).orElseThrow();
//        var u = sec.currentUser();
//        // lecturer/admin OR the student owner can download
//        if (!(canManageAssignment(u, s.getAssignment()) || s.getStudent().getId().equals(u.getId())))
//            throw new RuntimeException("Not allowed");
//        return this.uploadDir.resolve(s.getStoredFilename());
//    }
}
