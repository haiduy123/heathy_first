package com.example.healthy_first_ver1.service.Impl;

import com.example.healthy_first_ver1.entity.Restaurant;
import com.example.healthy_first_ver1.entity.Role;
import com.example.healthy_first_ver1.repository.ResRepository;
import com.example.healthy_first_ver1.service.ResService;
import com.example.healthy_first_ver1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class FillterRestaurant {
    @Autowired
    ResService resService;

    @Autowired
    ResRepository resRepository;

    @Autowired
    UserService userService;


    /** lay restaurant chua duoc cap cert */
    public List<Restaurant> getResNoCert() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String district = userService.getUserByName(username).getLocation();
        Collection<Role> roles = userService.getUserByName(username).getRoles();
        int temp = (int) roles.stream().filter(item -> item.getName().equals("ROLE_ADMIN")).count();

        List<Restaurant> restaurants = new ArrayList<>();
        /** tim trong restaurant list
         neu thay certId = 0 thi add vao list ket qua
         */

        if(temp != 0) {
            resService.getRestaurantList().forEach(restaurant -> {
                if (!ObjectUtils.isEmpty(restaurant.getCert_id()) && restaurant.getCert_id().equals(Long.valueOf(0))) {
                    restaurants.add(restaurant);
                }
            });
        } else {
            resService.getRestaurantList().forEach((item -> {
                if(!ObjectUtils.isEmpty(item.getCert_id()) && item.getCert_id().equals(Long.valueOf(0)) && item.getDistrict().equals(district)) {
                    restaurants.add(item);
                }
            }));
        }

        return restaurants;
    }

    public List<Restaurant> getResDeleteCert() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String district = userService.getUserByName(username).getLocation();
        Collection<Role> roles = userService.getUserByName(username).getRoles();
        int temp = (int) roles.stream().filter(item -> item.getName().equals("ROLE_ADMIN")).count();

        List<Restaurant> restaurants = new ArrayList<>();
        /** tim trong restaurant list
         neu thay certId = 0 thi add vao list ket qua
         */

        if(temp != 0) {
            resService.getRestaurantList().forEach(restaurant -> {
                if (ObjectUtils.isEmpty(restaurant.getCert_id()) && restaurant.getCert_id() == null) {
                    restaurants.add(restaurant);
                }
            });
        } else {
            resService.getRestaurantList().forEach((item -> {
                if(ObjectUtils.isEmpty(item.getCert_id()) && item.getCert_id() == null && item.getDistrict().equals(district)) {
                    restaurants.add(item);
                }
            }));
        }

        return restaurants;
    }
}
