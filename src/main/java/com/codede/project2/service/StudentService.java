package com.codede.project2.service;

import com.codede.project2.DTO.PageDTO;
import com.codede.project2.DTO.StudentDTO;
import com.codede.project2.entity.Student;
import com.codede.project2.repo.StudentRepo;
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

    @Transactional
    public void create(StudentDTO studentDTO) {
        Student student = new ModelMapper().map(studentDTO, Student.class);

        studentRepo.save(student);
        List<StudentDTO> students = studentDTO.getStudents();
        for (StudentDTO studentDTOS : students) {
            if (studentDTOS.getStudents() != null) {
                //save to db
                Student student1 = new Student();
                student1.setId(studentDTO.getId());
                student1.setStudentCode(studentDTO.getStudentCode());

                studentRepo.save(student1);
            }
        }
    }

    @Transactional
    public void update(StudentDTO studentDTO) {
        Student student = studentRepo.findById(studentDTO.getId())
                .orElseThrow(NoResultException::new);

        student.setId(studentDTO.getId());
        student.setStudentCode(studentDTO.getStudentCode());

        studentRepo.save(student);
    }

    public StudentDTO getById(int id) {
        Student student = studentRepo.findById(id).orElseThrow(NoResultException::new); //java8 lambda
//        UserDTO userDTO = new UserDTO();
//
//        userDTO.setId(user.getId());
//        userDTO.setName(user.getName());
//        userDTO.setUsername(user.getUsername());
//        userDTO.setBirthdate(user.getBirthdate());
////        userDTO.setPassword(user.getPassword());
//        userDTO.setCreateAt(user.getCreateAt());
//        userDTO.setAvatar(user.getAvatar());

        // co the dung nhu nay
        StudentDTO studentDTO = new ModelMapper().map(student, StudentDTO.class);

        return studentDTO;
    }

    public PageDTO<StudentDTO> searchByCode(int studentCode, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Student> pageRS = studentRepo.searchByStudentCode("%" + studentCode +"%", pageable);

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
