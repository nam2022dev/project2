package com.codede.project2.DTO;

import com.codede.project2.entity.Student;
import com.codede.project2.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class StudentDTO {
    //    @Id
    private Integer id;
    //    @Column(unique = true)
    private String studentCode;
    //    @OneToOne
//    @PrimaryKeyJoinColumn
    private List<StudentDTO> students;
}
