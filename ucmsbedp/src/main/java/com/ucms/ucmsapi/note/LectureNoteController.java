
package com.ucms.ucmsapi.note;

import com.ucms.ucmsapi.note.dto.LectureNoteDto;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders; import org.springframework.http.MediaType; import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException; import java.nio.file.Path; import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class LectureNoteController {
    private final LectureNoteService svc;
    public LectureNoteController(LectureNoteService svc){ this.svc = svc; }

    @GetMapping("/course/{courseId}") public List<LectureNoteDto> byCourse(@PathVariable Long courseId){ return svc.byCourse(courseId); }

    @PostMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public LectureNoteDto upload(@PathVariable Long courseId, @RequestParam("title") String title,
                                 @RequestParam("file") MultipartFile file) throws IOException {
        return svc.upload(courseId, title, file);
    }

    @GetMapping("/{noteId}/file")
    public ResponseEntity<FileSystemResource> download(@PathVariable Long noteId) throws IOException {
//        Path p = svc.downloadFile(noteId);
//        FileSystemResource fs = new FileSystemResource(p);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fs.getFilename() + "\"")
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .contentLength(fs.contentLength())
//                .body(fs);
        return null;
    }


    @DeleteMapping("/{noteId}")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public void delete(@PathVariable Long noteId){ svc.delete(noteId); }
}
