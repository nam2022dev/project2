package com.codede.project2.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    private String name;

    private String avatar; // url

    private String email;

    @Column(unique = true)
    private String username;
    private String password;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;

    @LastModifiedDate
    private Date lastUpdateAt;



    @CreatedDate // tu gen
    //    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date createdAt;

    //ko bat buoc
    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserRole> userRoles;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Group> groups;



}
