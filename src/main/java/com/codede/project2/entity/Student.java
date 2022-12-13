package com.codede.project2.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String studentCode;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;
}
