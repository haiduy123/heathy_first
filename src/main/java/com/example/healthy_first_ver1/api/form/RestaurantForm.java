package com.example.healthy_first_ver1.api.form;

import lombok.Data;

@Data
public class RestaurantForm {
    private String name;

    private String address;

    private String phone;

    private String type;

    private String district;

    private Long certId;
}
