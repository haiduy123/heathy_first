package com.example.healthy_first_ver1.api.form;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CertificateForm {
    private LocalDate startDate;

    private LocalDate endDate;

}
