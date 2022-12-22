package com.codede.project2.restApi;

import com.codede.project2.DTO.PageDTO;
import com.codede.project2.DTO.ResponseDTO;
import com.codede.project2.DTO.StudentDTO;
import com.codede.project2.repo.StudentRepo;
import com.codede.project2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentRestApi {

    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepo studentRepo;

    @PostMapping("/new")
//    @ResponseStatus(HttpStatus.CREATED) // muon tra ve trang thai khac thi viet cai nay
    public ResponseDTO<StudentDTO> add(@ModelAttribute StudentDTO studentDTO) {
        studentService.create(studentDTO);
        return ResponseDTO.<StudentDTO>builder().status(200).data(studentDTO).build();
    } // thanh cong tra ve 200

    @PutMapping("/edit")
    public void update(@ModelAttribute StudentDTO studentDTO) {
        studentService.update(studentDTO);
    }

    @GetMapping("/get{id}")
    public StudentDTO edit(@PathVariable("id") int id) {
        return studentService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") int id) {
        studentService.delete(id);
    }

    @PostMapping("/search-all")// search by studentCode & search by id
    public ResponseDTO<PageDTO<StudentDTO>> search(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "studentCode", required = false) String studentCode,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page) {


        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        return ResponseDTO.<PageDTO<StudentDTO>>builder()
                .status(200)
                .data(studentService.search(name, studentCode, page, size))
                .build();
    }
}
