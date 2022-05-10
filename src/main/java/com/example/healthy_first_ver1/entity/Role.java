package com.example.healthy_first_ver1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

//    @ManyToMany(mappedBy = "roles")
//    private List<User> users = new ArrayList<>();

//    public Role(Long id, String name) {
//        this.id = id;
//        this.name = name;
//    }
}
