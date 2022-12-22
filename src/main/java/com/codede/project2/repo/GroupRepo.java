package com.codede.project2.repo;

import com.codede.project2.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepo extends JpaRepository<Group, Integer> {
    @Query("select g from Group g join g.users u where u.id = :x")
    Page<Group> searchById(@Param("x") int s, Pageable pageable);
}
