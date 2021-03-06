package com.example.healthy_first_ver1.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class CertificateDto {
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;
}
