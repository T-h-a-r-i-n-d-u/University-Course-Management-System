
package com.ucms.ucmsapi.note;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LectureNoteRepository extends JpaRepository<LectureNote, Long> {
    List<LectureNote> findByCourse_IdOrderByUploadedAtDesc(Long courseId);
    List<LectureNote> findByCourseId(Long courseId);
    long countByCourse_Id(Long courseId);
}