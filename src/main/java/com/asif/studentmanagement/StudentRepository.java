package com.asif.studentmanagement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface StudentRepository extends JpaRepository<Student, Long>,
        JpaSpecificationExecutor<Student> {

    List<Student> findByName(String name);

    List<Student> findByCourse(String course);

    List<Student> findByEmail(String email);

    Page<Student> findByNameContainingIgnoreCaseAndCourseContainingIgnoreCase(
        String name,
        String course,
        Pageable pageable
);
}
