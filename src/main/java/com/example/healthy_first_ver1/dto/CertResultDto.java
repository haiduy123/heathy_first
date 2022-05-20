package com.example.healthy_first_ver1.dto;

import com.example.healthy_first_ver1.entity.Certificate;
import lombok.Data;

import java.util.List;

@Data
public class CertResultDto {
    Long certRestaurant;
    Long certProduction;
    List<Certificate> certificates;
}
