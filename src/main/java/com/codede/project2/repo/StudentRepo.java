package com.codede.project2.repo;

import com.codede.project2.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Integer> {

    Optional<Student> findByStudentCode(String code);

    @Query("select s from Student s Join s.user u where u.name like :x")
    Page<Student> searchByName(@Param("x") String c, Pageable pageable);

    @Query("select s from Student s where s.studentCode = :x ")
    Page<Student> searchByStudentCode(@Param("x") String c, Pageable pageable);

    @Query("select s from Student s where s.id =  :x ")
    Page<Student> searchById(@Param("x") int c, Pageable pageable);

    @Query("select s from Student s Join s.user u where u.name like :code and u.name like :name")
    Page<Student> searchByNameAndCode(@Param("code") String code, @Param("name") String name,
                                      Pageable pageable);
}
