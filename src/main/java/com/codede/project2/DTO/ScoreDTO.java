package com.codede.project2.DTO;

import lombok.Data;

@Data
public class ScoreDTO {
    private Integer id;

    private double score; // diem thi mon hoc/ nguoi

    private StudentDTO student;

    private CourseDTO course;
}
