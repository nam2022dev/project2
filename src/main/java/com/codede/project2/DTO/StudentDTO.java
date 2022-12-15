package com.codede.project2.DTO;

import lombok.Data;

@Data
public class StudentDTO {
    private Integer id;
    private String studentCode;
    private UserDTO user;
}
