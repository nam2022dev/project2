package com.codede.project2.coltroller.restApi;

import com.codede.project2.DTO.PageDTO;
import com.codede.project2.DTO.StudentDTO;
import com.codede.project2.DTO.UserRoleDTO;
import com.codede.project2.repo.StudentRepo;
import com.codede.project2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentRestApi {

    @Autowired
    StudentService studentService;

    @PostMapping("/new")
//    @ResponseStatus(HttpStatus.CREATED) // muon tra ve trang thai khac thi viet cai nay
    public void add(@ModelAttribute StudentDTO studentDTO) {
        studentService.create(studentDTO);
    } // thanh cong tra ve 200

    @PostMapping("/edit")
    public void edit( @ModelAttribute StudentDTO studentDTO) {
        studentService.update(studentDTO);
    }

    @GetMapping("/edit")
    public StudentDTO edit(@RequestParam("id") int id) {
        return studentService.getById(id);
    }

    @DeleteMapping("/delete")
     //?id=1 REST API
    public void delete(@RequestParam("id") int id) {
        studentService.delete(id);
    }

    @PostMapping("/search")// search by studentCode & search by id
    public PageDTO<StudentDTO> search(
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "studentCode", required = false) String studentCode,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page) {


        size = size == null ? 10 : size;
        page = page == null ? 0 : page;


        PageDTO<StudentDTO> pageRS = null;

        if ((id != null)) {
            pageRS = studentService.searchById(id, page, size);
        } else {
            pageRS = studentService.searchByCode(studentCode, page, size);
        }


        return pageRS;
    }
}
