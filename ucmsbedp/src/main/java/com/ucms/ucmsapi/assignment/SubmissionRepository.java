
package com.ucms.ucmsapi.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<AssignmentSubmission, Long> {
    long countByAssignment_Course_Id(Long courseId);
    List<AssignmentSubmission> findByAssignmentId(Long assignmentId);
    Optional<AssignmentSubmission> findByAssignmentIdAndStudentId(Long assignmentId, Long studentId);
    boolean existsByAssignment_IdAndStudent_Id(Long assignmentId, Long studentId);
    long countByAssignment_Id(Long assignmentId);

    @Query("""
         select count(s)
         from AssignmentSubmission s
         where s.assignment.course.id = :courseId
         """)
    long countForCourse(Long courseId);
}
