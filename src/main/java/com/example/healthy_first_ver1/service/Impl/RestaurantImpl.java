package com.example.healthy_first_ver1.service.Impl;

import com.example.healthy_first_ver1.api.form.RestaurantForm;
import com.example.healthy_first_ver1.entity.Restaurant;
import com.example.healthy_first_ver1.exception.NotFoundException;
import com.example.healthy_first_ver1.repository.CertRepository;
import com.example.healthy_first_ver1.repository.ResRepository;
import com.example.healthy_first_ver1.repository.result.ResRecommendResult;
import com.example.healthy_first_ver1.repository.result.ResSafeResult;
import com.example.healthy_first_ver1.repository.result.ResUnsafeResult;
import com.example.healthy_first_ver1.service.ResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

@Service
public class RestaurantImpl implements ResService {
    @Autowired
    ResRepository resRepository;

    @Autowired
    CertRepository certRepository;

    @Override
    public Restaurant addNewRes(RestaurantForm form) {
        Restaurant restaurant = Restaurant.builder()
                .name(form.getName())
                .address(form.getAddress())
                .phone(form.getPhone())
                .type(form.getType())
                .certId(form.getCertId())
                .build();

        return save(restaurant);
    }

    @Override
    public Restaurant getById(Long id) {
        Restaurant restaurant = resRepository.findById(id).get();
        if(ObjectUtils.isEmpty(restaurant)) {
            String mess = "restaurant-not-exits";
            throw new NotFoundException(mess);
        }

        return restaurant;
    }

    @Override
    public List<Restaurant> getRestaurantList() {
        List<Restaurant> restaurants = resRepository.findAll();
        if(ObjectUtils.isEmpty(restaurants)) {
            return Collections.EMPTY_LIST;
        }

        return restaurants;
    }

    @Override
    public List<ResSafeResult> getRestaurantSafe() {
        List<ResSafeResult> restaurants = resRepository.getRestaurantSafe();
        if(ObjectUtils.isEmpty(restaurants)) {
            return Collections.EMPTY_LIST;
        }
        return restaurants;
    }

    @Override
    public List<ResUnsafeResult> getRestaurantUnsafe() {
        List<ResUnsafeResult> restaurants = resRepository.getRestaurantUnsafe();
        if(ObjectUtils.isEmpty(restaurants)) {
            return Collections.EMPTY_LIST;
        }
        return restaurants;
    }

    @Override
    public List<ResRecommendResult> getResRecommend() {
        List<ResRecommendResult> restaurants = resRepository.getRestaurantRecommend();
        if(ObjectUtils.isEmpty(restaurants)) {
            return Collections.EMPTY_LIST;
        }
        return restaurants;
    }

    @Override
    public Long countSafeRestaurant() {
        List<ResSafeResult> restaurants = resRepository.getRestaurantSafe();
        Long count = Long.valueOf(restaurants.size());
        return count;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return resRepository.save(restaurant);
    }
}
