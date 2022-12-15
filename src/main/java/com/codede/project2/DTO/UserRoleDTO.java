package com.codede.project2.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class UserRoleDTO {
    private Integer id;

    private Integer userId;

    private String userName;

    private String role;

//    @JsonIgnoreProperties("userRoles")
//    @JsonBackReference
    private UserDTO user;
}
