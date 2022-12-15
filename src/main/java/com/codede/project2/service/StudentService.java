package com.codede.project2.service;

import com.codede.project2.DTO.PageDTO;
import com.codede.project2.DTO.StudentDTO;
import com.codede.project2.entity.Student;
import com.codede.project2.entity.User;
import com.codede.project2.repo.StudentRepo;
import com.codede.project2.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepo studentRepo;
    @Autowired
    UserRepo userRepo;

    @Transactional
    public void create(StudentDTO studentDTO) {
        Student student = new Student();
        student.setStudentCode(studentDTO.getStudentCode());
        student.setId(studentDTO.getId());

        studentRepo.save(student);
    }

    @Transactional
    public void update(StudentDTO studentDTO) {
        Student student = new Student();

        student.setStudentCode(studentDTO.getStudentCode());
        User user = userRepo.findById(studentDTO.getId()).orElseThrow(NoResultException::new);
        student.setUser(user);

        studentRepo.save(student);
    }

    public StudentDTO getById(int id) {
        Student student = studentRepo.findById(id).orElseThrow(NoResultException::new); //java8 lambda
        StudentDTO studentDTO = new ModelMapper().map(student, StudentDTO.class);

        return studentDTO;
    }

    public PageDTO<StudentDTO> searchByCode(String studentCode, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Student> pageRS = studentRepo.searchByStudentCode("%" + studentCode + "%", pageable);

        PageDTO<StudentDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPage(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<StudentDTO> studentDTOS = new ArrayList<>();

        for (Student student : pageRS.getContent()) {
            studentDTOS.add(new ModelMapper().map(student, StudentDTO.class));
        }

        pageDTO.setContents(studentDTOS); // set vao pagedto
        return pageDTO;
    }

    public PageDTO<StudentDTO> searchById(int id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Student> pageRS = studentRepo.searchById(id, pageable);

        PageDTO<StudentDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPage(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<StudentDTO> studentDTOS = new ArrayList<>();

        for (Student student : pageRS.getContent()) {
            studentDTOS.add(new ModelMapper().map(student, StudentDTO.class));
        }

        pageDTO.setContents(studentDTOS); // set vao pagedto
        return pageDTO;
    }

    @Transactional
    public void delete(int id) {
        studentRepo.deleteById(id);
    }

    @Transactional
    public void deleteAll(List<Integer> ids) {
        studentRepo.deleteAllById(ids);
    }


}
