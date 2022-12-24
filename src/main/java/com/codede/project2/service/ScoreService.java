package com.codede.project2.service;

import com.codede.project2.DTO.ScoreDTO;
import com.codede.project2.entity.Course;
import com.codede.project2.entity.Score;
import com.codede.project2.entity.Student;
import com.codede.project2.repo.CourseRepo;
import com.codede.project2.repo.ScoreRepo;
import com.codede.project2.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScoreService {
    @Autowired
    ScoreRepo scoreRepo;

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    CourseRepo courseRepo;

    @Transactional
    public void create(ScoreDTO scoreDTO) {
        Score score = new Score();
        Student student = studentRepo.findById(scoreDTO.getStudent().getId()).orElseThrow(RuntimeException::new);

        Course course = courseRepo.findById(scoreDTO.getCourse().getId()).orElseThrow(RuntimeException::new);


        score.setScore(scoreDTO.getScore());
        score.setStudent(student);
        score.setCourse(course);

        scoreRepo.save(score);
    }
}
