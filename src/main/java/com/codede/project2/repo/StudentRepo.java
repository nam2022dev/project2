package com.codede.project2.repo;

import com.codede.project2.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepo extends JpaRepository<Student, Integer> {

    @Query("select s from Student s where s.studentCode like :x ")
    Page<Student> searchByStudentCode(@Param("x") String c, Pageable pageable);
}
