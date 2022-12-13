package com.codede.project2.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne // many user role - to - one user
    @JoinColumn(name = "user_id")
    User user;

    private String role; // admin, member


}
