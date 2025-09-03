
package com.ucms.ucmsapi.note;

import com.ucms.ucmsapi.common.SecurityUtils;
import com.ucms.ucmsapi.course.CourseRepository;
import com.ucms.ucmsapi.note.dto.LectureNoteDto;
import com.ucms.ucmsapi.note.dto.NoteMapper;
import com.ucms.ucmsapi.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException; import java.nio.file.*; import java.util.List; import java.util.UUID;

@Service
@Transactional
public class LectureNoteService {
    private final LectureNoteRepository repo;
    private final CourseRepository courses;
    private final SecurityUtils sec;
//    private final Path root;

    public LectureNoteService(
            LectureNoteRepository repo, CourseRepository courses, SecurityUtils sec
//            @Value("${storage.notes-root:uploads/notes}") String rootDir
    ) throws IOException {
        this.repo = repo; this.courses = courses; this.sec = sec;
//        this.root = Path.of(rootDir); Files.createDirectories(this.root);
    }

    private boolean canManageCourse(User u, Long courseId){
        if (sec.hasRole(u, "ADMIN")) return true;
        var c = courses.findById(courseId).orElseThrow();
        return c.getLecturers().stream().anyMatch(l -> l.getId().equals(u.getId()));
    }

    public LectureNoteDto upload(Long courseId, String title, MultipartFile file) throws IOException {
        var u = sec.currentUser();
        if (!(sec.hasRole(u,"LECTURER") || sec.hasRole(u,"ADMIN"))) throw new RuntimeException("Not allowed");
        if (!canManageCourse(u, courseId)) throw new RuntimeException("Not allowed");

        var c = courses.findById(courseId).orElseThrow();
        var stored = UUID.randomUUID() + "_" + file.getOriginalFilename();
//        Files.copy(file.getInputStream(), this.root.resolve(stored), StandardCopyOption.REPLACE_EXISTING);

        var n = new LectureNote();
        n.setCourse(c); n.setUploader(u); n.setTitle(title);
        n.setStoredFilename(stored);
        n.setOriginalFilename(file.getOriginalFilename());
        n.setContentType(file.getContentType()==null?"application/octet-stream":file.getContentType());
        n.setSize(file.getSize());

        return NoteMapper.toDto(repo.save(n));
    }

    @Transactional(readOnly = true)
    public List<LectureNoteDto> byCourse(Long courseId){
        return repo.findByCourseId(courseId).stream().map(NoteMapper::toDto).toList();
    }

//    @Transactional(readOnly = true)
//    public Path downloadFile(Long noteId){
//        var n = repo.findById(noteId).orElseThrow();
//        // anyone enrolled/signed-in can download; if you want to enforce enrollment, add check here
//        return this.root.resolve(n.getStoredFilename());
//    }

    public void delete(Long noteId){
        var n = repo.findById(noteId).orElseThrow();
        var u = sec.currentUser();
        if (!(sec.hasRole(u,"ADMIN") || canManageCourse(u, n.getCourse().getId())))
            throw new RuntimeException("Not allowed");
//        try { Files.deleteIfExists(this.root.resolve(n.getStoredFilename())); } catch(Exception ignored){}
        repo.delete(n);
    }
}
