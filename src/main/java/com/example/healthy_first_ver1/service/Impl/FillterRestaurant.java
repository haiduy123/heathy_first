package com.example.healthy_first_ver1.service.Impl;

import com.example.healthy_first_ver1.entity.Restaurant;
import com.example.healthy_first_ver1.repository.ResRepository;
import com.example.healthy_first_ver1.service.ResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class FillterRestaurant {
    @Autowired
    ResService resService;

    @Autowired
    ResRepository resRepository;

    /** lay restaurant chua duoc cap cert */
    public List<Restaurant> getResNoCert() {
        List<Restaurant> restaurants = new ArrayList<>();
        /** tim trong restaurant list
         neu thay certId = 0 thi add vao list ket qua
         */
        resService.getRestaurantList().forEach(restaurant -> {
            if (!ObjectUtils.isEmpty(restaurant.getCert_id()) && restaurant.getCert_id().equals(Long.valueOf(0))) {
                restaurants.add(restaurant);
            }
        });
        return restaurants;
    }

    public List<Restaurant> getResDeleteCert() {
        List<Restaurant> restaurants = new ArrayList<>();
        /** tim trong restaurant list
         neu thay certId = null thi add vao list ket qua
         */

        resService.getRestaurantList().forEach(restaurant -> {
            if (ObjectUtils.isEmpty(restaurant.getCert_id()) && restaurant.getCert_id() == null) {
                restaurants.add(restaurant);
            }
        });
        return restaurants;
    }
}
