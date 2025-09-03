// src/main/java/com/ucms/ucmsapi/result/ResultService.java
package com.ucms.ucmsapi.result;

import com.ucms.ucmsapi.course.CourseRepository;
import com.ucms.ucmsapi.course.CourseUnit;
import com.ucms.ucmsapi.result.dto.ResultDto;
import com.ucms.ucmsapi.result.dto.UpsertResultRequest;
import com.ucms.ucmsapi.user.User;
import com.ucms.ucmsapi.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ResultService {

    private final ResultRepository results;
    private final CourseRepository courses;
    private final UserRepository users;

    @Autowired
    public ResultService(ResultRepository results,
                         CourseRepository courses,
                         UserRepository users) {
        this.results = results;
        this.courses = courses;
        this.users = users;
    }

    private ResultDto toDto(Result r) {
        ResultDto d = new ResultDto();
        d.setId(r.getId());
        d.setCourseId(r.getCourse() != null ? r.getCourse().getId() : null);
        d.setStudentId(r.getStudent() != null ? r.getStudent().getId() : null);
        d.setTotalScore(r.getTotalScore());
        d.setGrade(r.getGrade());
        d.setPublished(r.isPublished());
        d.setUpdatedAt(r.getUpdatedAt());
        return d;
    }

    private String computeGrade(int score) {
        if (score >= 85) return "A";
        if (score >= 75) return "B+";
        if (score >= 65) return "B";
        if (score >= 55) return "C";
        if (score >= 45) return "D";
        return "F";
    }

    public ResultDto upsert(UpsertResultRequest req) {
        CourseUnit course = courses.findById(req.getCourseId()).orElseThrow();
        User student = users.findById(req.getStudentId()).orElseThrow();

        Result r = results.findByCourse_IdAndStudent_Id(req.getCourseId(), req.getStudentId())
                .orElseGet(Result::new);

        r.setCourse(course);
        r.setStudent(student);
        r.setTotalScore(req.getTotalScore());

        String grade = (req.getGrade() != null && !req.getGrade().isBlank())
                ? req.getGrade()
                : computeGrade(req.getTotalScore());
        r.setGrade(grade);

        if (req.getPublished() != null) r.setPublished(req.getPublished());
        r.setUpdatedAt(Instant.now());

        r = results.save(r);
        return toDto(r);
    }

    public List<ResultDto> forStudent(Long studentId) {
        return results.findByStudent_IdOrderByUpdatedAtDesc(studentId)
                .stream().map(this::toDto).toList();
    }

    public List<ResultDto> forCourse(Long courseId) {
        return results.findByCourse_IdOrderByUpdatedAtDesc(courseId)
                .stream().map(this::toDto).toList();
    }

    public ResultDto setPublish(Long id, boolean value) {
        Result r = results.findById(id).orElseThrow();
        r.setPublished(value);
        r.setUpdatedAt(Instant.now());
        r = results.save(r);
        return toDto(r);
    }

    public void delete(Long id) {
        results.deleteById(id);
    }
}
