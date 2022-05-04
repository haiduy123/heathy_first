package com.example.healthy_first_ver1.dto;

import com.example.healthy_first_ver1.entity.Certificate;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RestaurantDto {
    private Long id;

    private String name;

    private String address;

    private String phone;

    private String type;

    private Long certId;
}
