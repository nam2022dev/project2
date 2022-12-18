package com.codede.project2.DTO;

import lombok.Data;

import java.util.List;

@Data
public class GroupDTO {
    private Integer id;
    private String name;
    List<UserDTO> users;
}
