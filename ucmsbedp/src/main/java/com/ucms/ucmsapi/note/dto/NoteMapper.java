
package com.ucms.ucmsapi.note.dto;
import com.ucms.ucmsapi.note.LectureNote;

public class NoteMapper {
    public static LectureNoteDto toDto(LectureNote n){
        return new LectureNoteDto(
                n.getId(), n.getCourse().getId(), n.getTitle(), n.getOriginalFilename(), n.getSize(),
                n.getContentType(), n.getUploader().getFullName(), n.getUploadedAt()
        );
    }
}
