package com.example.healthy_first_ver1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "candidate")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "file_cv")
    private String fileCv;

    @Column
    private String name;

    @Column (name = "username")
    private String username;

    @Column (name = "phone")
    private String phone;

    @Column (name = "email")
    private String email;
}
