package com.example.healthy_first_ver1.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class SampleDto {
    private Long id;

    private String laboratory;

    private String name;

    private Long plan_id;

    private LocalDate endDate;

    private String status;
}
