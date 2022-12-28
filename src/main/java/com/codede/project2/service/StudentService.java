package com.codede.project2.service;

import com.codede.project2.DTO.PageDTO;
import com.codede.project2.DTO.StudentDTO;
import com.codede.project2.entity.Student;
import com.codede.project2.entity.User;
import com.codede.project2.entity.UserRole;
import com.codede.project2.repo.StudentRepo;
import com.codede.project2.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
        User user = userRepo.findById(studentDTO.getId()).orElseThrow(NoClassDefFoundError::new);

        for (UserRole userRole : user.getUserRoles()) {
            if (userRole.getRole().equals("ROLE_STUDENT")) {
                Student student = new Student();
                student.setStudentCode(studentDTO.getStudentCode());
                student.setId(studentDTO.getId());

                studentRepo.save(student);
                return; // ket thuc
            }
        }
    }

    @Transactional
    @Caching(evict = {@CacheEvict(cacheNames = "students", allEntries = true),
            @CacheEvict(cacheNames = "users", allEntries = true)
    })
    public void update(StudentDTO studentDTO) {
        Student student = studentRepo.findById(studentDTO.getId()).orElseThrow(NoResultException::new);

        student.setStudentCode(studentDTO.getStudentCode());
        studentRepo.save(student);
    }

    public StudentDTO getById(int id) {
        Student student = studentRepo.findById(id).orElseThrow(NoResultException::new); //java8 lambda
        StudentDTO studentDTO = new ModelMapper().map(student, StudentDTO.class);

        return studentDTO;
    }

    @Cacheable(cacheNames = "students") // key - value
    public PageDTO<StudentDTO> search(String name, String studentCode, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Student> pageRS = null;

        if (StringUtils.hasText(name)) {
            pageRS = studentRepo.searchByName(name, pageable);
        } else if (StringUtils.hasText(studentCode)) {
            pageRS = studentRepo.searchByStudentCode(studentCode, pageable);
        } else if (StringUtils.hasText(studentCode) && StringUtils.hasText(name)) {
            pageRS = studentRepo.searchByNameAndCode(studentCode, name, pageable);
        } else {
            pageRS = studentRepo.findAll(pageable);
        }

        PageDTO<StudentDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPage(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<StudentDTO> studentDTOs = new ArrayList<>();
        for (Student student : pageRS.getContent()) {
            studentDTOs.add(new ModelMapper().map(student, StudentDTO.class));
        }

        pageDTO.setContents(studentDTOs); // set vao pagedto
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
