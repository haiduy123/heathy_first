package com.example.healthy_first_ver1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_company")
    private Long idCompany;

    @Column
    private String requirement;

    @Column
    private Instant startDate;

    @Column
    private Instant endDate;

    @Column
    private String salary;

    @Column
    private String position;

    @Column
    private String title;
}
