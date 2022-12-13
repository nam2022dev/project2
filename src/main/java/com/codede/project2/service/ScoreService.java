package com.codede.project2.service;

import com.codede.project2.repo.ScoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
    @Autowired
    ScoreRepo scoreRepo;


}
