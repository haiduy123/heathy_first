package com.example.healthy_first_ver1.entity;

import com.example.healthy_first_ver1.dto.CertificateDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "certificates")
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @JsonIgnore
    @OneToOne(mappedBy = "cert")
    private Restaurant restaurant;

    public CertificateDto toDto() {
        return CertificateDto.builder()
                .id(id)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
