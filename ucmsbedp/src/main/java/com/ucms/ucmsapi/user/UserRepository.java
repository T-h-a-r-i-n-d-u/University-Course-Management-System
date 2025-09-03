package com.ucms.ucmsapi.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    long countByStatus(UserStatus status);

    List<User> findByApprovedFalse();

    // Role counts (ElementCollection). Simpler with explicit JPQL:
    @Query("select count(u) from User u join u.roles r where r = com.ucms.ucmsapi.user.Role.STUDENT")
    long countStudents();
    @Query("select count(u) from User u join u.roles r where r = com.ucms.ucmsapi.user.Role.LECTURER")
    long countLecturers();
    @Query("select count(u) from User u join u.roles r where r = com.ucms.ucmsapi.user.Role.ADMIN")
    long countAdmins();
    @Query("select u from User u join u.roles r where r = :role")
    List<User> findByRole(@Param("role") Role role);
}