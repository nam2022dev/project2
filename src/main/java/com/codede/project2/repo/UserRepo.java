package com.codede.project2.repo;

import com.codede.project2.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.name like :x ")
    Page<User> searchByName(@Param("x") String s, Pageable pageable);

    @Query("SELECT u FROM User u WHERE month(u.birthdate) = :m AND day(u.birthdate) = :d ")
    List<User> findByBirthdate(@Param("d") int d, @Param("m") int m);
}
