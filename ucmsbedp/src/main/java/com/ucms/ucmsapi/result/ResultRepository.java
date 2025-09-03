
package com.ucms.ucmsapi.result;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Result> findByCourse_IdAndStudent_Id(Long courseId, Long studentId);
    List<Result> findByStudent_IdOrderByUpdatedAtDesc(Long studentId);
    List<Result> findByCourse_IdOrderByUpdatedAtDesc(Long courseId);

    long countByCourse_Id(Long courseId);

    @Query("select avg(r.totalScore) from Result r where r.course.id = :courseId")
    Double avgScoreByCourse(Long courseId);
}