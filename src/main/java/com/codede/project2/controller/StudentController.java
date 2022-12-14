package com.codede.project2.controller;


import com.codede.project2.DTO.PageDTO;
import com.codede.project2.DTO.ResponseDTO;
import com.codede.project2.DTO.StudentDTO;
import com.codede.project2.repo.StudentRepo;
import com.codede.project2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    StudentService studentService;

    @GetMapping("/new")
    public String add() {
        return "student/add.html";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute StudentDTO studentDTO) {
        studentService.create(studentDTO);
        return "redirect:/student/search";
    }
}
