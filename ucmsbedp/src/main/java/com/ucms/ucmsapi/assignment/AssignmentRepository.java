
package com.ucms.ucmsapi.assignment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByCourse_IdOrderByDeadlineAsc(Long courseId);

    long countByCourse_Id(Long courseId);
    List<Assignment> findByCourseId(Long courseId);
}