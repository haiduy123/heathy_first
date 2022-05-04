package com.example.healthy_first_ver1.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class CheckPlanDto {
    private Long id;

    private LocalDate start_date;

    private LocalDate end_date;

    private Long stageId;

    private String status;

    private Long restaurantId;
}
