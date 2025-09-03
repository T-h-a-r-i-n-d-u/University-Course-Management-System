
package com.ucms.ucmsapi.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByCourse_IdAndStudent_Id(Long courseId, Long studentId);
    List<Enrollment> findByStudent_IdOrderByCreatedAtDesc(Long studentId);
    List<Enrollment> findByCourse_IdOrderByCreatedAtDesc(Long courseId);
    boolean existsByCourse_IdAndStudent_IdAndStatus(Long courseId, Long studentId, EnrollmentStatus status);

    long countByCourse_IdAndStatus(Long courseId, EnrollmentStatus status);
    long countByStatus(EnrollmentStatus status);
}