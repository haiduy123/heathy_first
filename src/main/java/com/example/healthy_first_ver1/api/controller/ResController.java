package com.example.healthy_first_ver1.api.controller;

import com.example.healthy_first_ver1.api.form.RestaurantForm;
import com.example.healthy_first_ver1.api.response.ApiResponse;
import com.example.healthy_first_ver1.dto.RestaurantDto;
import com.example.healthy_first_ver1.dto.ResultDto;
import com.example.healthy_first_ver1.entity.Restaurant;
import com.example.healthy_first_ver1.repository.result.ResRecommendResult;
import com.example.healthy_first_ver1.repository.result.ResSafeResult;
import com.example.healthy_first_ver1.repository.result.ResUnsafeResult;
import com.example.healthy_first_ver1.service.ResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//@CrossOrigin
@RestController
@RequestMapping("/res")
public class ResController {
    @Autowired
    ResService resService;

    @PostMapping
    public ResponseEntity<ApiResponse> addRes(@RequestBody RestaurantForm _form) {
        Restaurant restaurant = resService.addNewRes(_form);
        RestaurantDto dto = restaurant.toDto();
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Thêm cơ sở thành công");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getResList() {
        List<Restaurant> restaurants = resService.getRestaurantList();
        List<RestaurantDto> dto = restaurants.stream().map(Restaurant::toDto).collect(Collectors.toList());
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Danh sách các cơ sở");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRestaurant(@PathVariable("id") long _id) {
        resService.deleteById(_id);
        ApiResponse response = ApiResponse.success(null,HttpStatus.OK.value(), "Xóa thành công" + _id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getResById(@PathVariable("id") Long _id) {
        Restaurant restaurant = resService.getById(_id);
        RestaurantDto dto = restaurant.toDto();
        ApiResponse response = ApiResponse.success(dto,HttpStatus.OK.value(), String.format("Thể loại %d",_id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/safe")
    public ResponseEntity<ApiResponse> getRestaurantSafe() {
        List<ResSafeResult> restaurants = resService.getRestaurantSafe();
        ApiResponse response = ApiResponse.success(restaurants, HttpStatus.OK.value(), "Danh sách các cơ sở an toàn");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/unsafe")
    public ResponseEntity<ApiResponse> getRestaurantUnsafe() {
        List<ResUnsafeResult> restaurants = resService.getRestaurantUnsafe();
        ApiResponse response = ApiResponse.success(restaurants, HttpStatus.OK.value(), "Danh sách các cơ sở khoong an toàn");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/restaurant/count")
    public ResponseEntity<ApiResponse> countSafeRestaurant() {
        List<Long> safeRes = resService.countRestaurant();
        ResultDto dto = new ResultDto();
        dto.setSafe(safeRes.get(0));
        dto.setUnsafe(safeRes.get(1));
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Số lượng nhà hàng an toàn");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/production/count")
    public ResponseEntity<ApiResponse> countSafeProduction() {
        List<Long> safeProduction = resService.countProduction();
        ResultDto dto = new ResultDto();
        dto.setSafe(safeProduction.get(0));
        dto.setUnsafe(safeProduction.get(1));
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Số lượng cơ sở sản xuất an toàn");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recommend")
    public ResponseEntity<ApiResponse> getRestaurantRecommend() {
        List<ResRecommendResult> restaurants = resService.getResRecommend();
        ApiResponse response = ApiResponse.success(restaurants, HttpStatus.OK.value(), "Danh sách các cơ sở caafn kieerm tra");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateRestaurant(@PathVariable("id") Long _id, @RequestBody RestaurantForm _form) {
        Restaurant restaurant = resService.updateRestaurant(_id, _form.getName(), _form.getAddress(), _form.getPhone());
        RestaurantDto dto = restaurant.toDto();
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Cập nhật thành công");
        return ResponseEntity.ok(response);
    }
}
