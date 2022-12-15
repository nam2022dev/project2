package com.codede.project2.DTO;

import com.codede.project2.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class UserDTO {
    private int id;

    @NotBlank //validation
    private String name;

    private String avatar;
    private String username;
    private String password;

    @DateTimeFormat(pattern = "dd/MM/yyy")
    @JsonFormat(pattern = "dd/MM/yyy")
    private Date birthdate;

    @JsonIgnore
    private MultipartFile File;

    private Date createdAt;

    @JsonManagedReference
    private List<UserRoleDTO> userRoles;

}
