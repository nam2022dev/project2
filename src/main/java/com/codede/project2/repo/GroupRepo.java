package com.codede.project2.repo;

import com.codede.project2.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepo extends JpaRepository<Group, Integer> {
}
