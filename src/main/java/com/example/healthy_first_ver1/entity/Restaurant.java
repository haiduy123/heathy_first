package com.example.healthy_first_ver1.entity;

import com.example.healthy_first_ver1.dto.RestaurantDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private String type;

    @Column
    private String district;

    @Column
    private Long cert_id;

//    @Column
//    private String image;
//
//    @Column
//    private String time;

    public RestaurantDto toDto() {
        return RestaurantDto.builder()
                .id(id)
                .name(name)
                .address(address)
                .phone(phone)
                .type(type)
                .district(district)
                .cert_id(cert_id)
//                .image(image)
//                .time(time)
                .build();
    }
}
