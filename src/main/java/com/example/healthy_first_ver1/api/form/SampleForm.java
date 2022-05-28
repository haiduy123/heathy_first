package com.example.healthy_first_ver1.api.form;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SampleForm {
    private String laboratory;

    private String name;

    private Long plan_id;

    private LocalDate endDate;

}
