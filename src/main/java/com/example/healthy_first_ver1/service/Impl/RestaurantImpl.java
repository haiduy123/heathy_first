package com.example.healthy_first_ver1.service.Impl;

import com.example.healthy_first_ver1.api.form.RestaurantForm;
import com.example.healthy_first_ver1.entity.Certificate;
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

import java.util.ArrayList;
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
                .build();

        Certificate certificate = certRepository.findById(form.getCertId()).get();
        if(ObjectUtils.isEmpty(certificate)) {
            restaurant.setCert(null);
        }
        else {
            restaurant.setCert(certificate);
        }
        return save(restaurant);
    }

    // lấy cơ sở theo id
    @Override
    public Restaurant getById(Long id) {
        Restaurant restaurant = resRepository.findById(id).get();
        checkCertificate(restaurant);
        if(ObjectUtils.isEmpty(restaurant)) {
            String mess = "restaurant-not-exits";
            throw new NotFoundException(mess);
        }

        return restaurant;
    }

    // lấy danh sách tất cả cơ sở
    @Override
    public List<Restaurant> getRestaurantList() {
        List<Restaurant> restaurants = resRepository.findAll();
        if(ObjectUtils.isEmpty(restaurants)) {
            return Collections.EMPTY_LIST;
        }

        return restaurants;
    }

    @Override
    public void deleteById(Long id) {
        Restaurant restaurant = resRepository.findById(id).get();

        if(ObjectUtils.isEmpty(restaurant)) {
            String mess = "restaurant-not-exits";
            throw new NotFoundException(mess);
        }

        resRepository.deleteById(id);
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
    public List<Long> countRestaurant() {
        List<Restaurant> restaurants = new ArrayList<>();

        resRepository.findAll().forEach((item -> {
            if(item.getType().equals("Restaurant")) restaurants.add(item);
        }));

        List<ResSafeResult> safeRestaurants = resRepository.getRestaurantSafe();
        List<ResSafeResult> results = new ArrayList<>();
        safeRestaurants.forEach((item) -> {
            if(item.getType().equals("Restaurant")) results.add(item);
        });
        List<Long> count = new ArrayList<>();
        count.add(Long.valueOf(results.size()));// safe
        count.add(Long.valueOf(restaurants.size() - results.size())); // unsafe
        return count;
    }

    @Override
    public List<Long> countProduction() {
        List<Restaurant> productions = new ArrayList<>();

        resRepository.findAll().forEach((item -> {
            if(item.getType().equals("Production")) productions.add(item);
        }));

        List<ResSafeResult> safeProductions = resRepository.getRestaurantSafe();
        List<ResSafeResult> results = new ArrayList<>();
        safeProductions.forEach((item) -> {
            if(item.getType().equals("Production")) results.add(item);
        });
        List<Long> count = new ArrayList<>();
        count.add(Long.valueOf(results.size()));// safe
        count.add(Long.valueOf(productions.size() - results.size())); // unsafe
        return count;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return resRepository.save(restaurant);
    }

    public boolean checkCertificate(Restaurant restaurant) {
        if(ObjectUtils.isEmpty(restaurant.getCert())) {
            return false;
        }
        else {
            return true;
        }
    }
}
