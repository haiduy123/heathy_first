package com.example.healthy_first_ver1.service;

import com.example.healthy_first_ver1.api.form.RestaurantForm;
import com.example.healthy_first_ver1.entity.Restaurant;
import com.example.healthy_first_ver1.repository.result.ResRecommendResult;
import com.example.healthy_first_ver1.repository.result.ResSafeResult;
import com.example.healthy_first_ver1.repository.result.ResUnsafeResult;

import java.util.List;

public interface ResService {
    Restaurant addNewRes(RestaurantForm form);

    Restaurant getById(Long id);

    List<Restaurant> getRestaurantList();

    List<ResSafeResult> getRestaurantSafe();

    List<ResUnsafeResult> getRestaurantUnsafe();

    List<ResRecommendResult> getResRecommend();

    Long countSafeRestaurant();

    Restaurant save(Restaurant restaurant);
}
