package com.example.healthy_first_ver1.api.controller;

import com.example.healthy_first_ver1.api.form.RestaurantForm;
import com.example.healthy_first_ver1.api.response.ApiResponse;
import com.example.healthy_first_ver1.dto.RestaurantDto;
import com.example.healthy_first_ver1.dto.ResultDto;
import com.example.healthy_first_ver1.entity.Restaurant;
import com.example.healthy_first_ver1.entity.Role;
import com.example.healthy_first_ver1.repository.result.ResRecommendResult;
import com.example.healthy_first_ver1.repository.result.ResSafeResult;
import com.example.healthy_first_ver1.repository.result.ResUnsafeResult;
import com.example.healthy_first_ver1.service.Impl.FillterRestaurant;
import com.example.healthy_first_ver1.service.ResService;
import com.example.healthy_first_ver1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//@CrossOrigin
@RestController
@RequestMapping("/res")
public class ResController {
    @Autowired
    ResService resService;

    @Autowired
    UserService userService;

    @Autowired
    FillterRestaurant fillterRestaurant;

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

    /** Lay danh sach nha hang theo nguoi quan ly
     * Chuyen vien quan ly quan nao thi chi truy cap duoc thong tin quan day
     */
    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getRestaurentById() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String district = userService.getUserByName(username).getLocation();
        Collection<Role> roles = userService.getUserByName(username).getRoles();
        List<Restaurant> restaurants = new ArrayList<>();
        int temp = (int) roles.stream().filter(role -> role.getName().equals("ROLE_ADMIN")).count();

        /** tim trong list restaurant
        neu thay nha hang nao co ten quan giong district thi add
         */
        if (temp != 0) {
            resService.getRestaurantList().forEach(restaurant -> {
                restaurants.add(restaurant);
            });
        }
        else {
            resService.getRestaurantList().forEach(restaurant -> {
                if(restaurant.getDistrict().equals(district)) {
                    restaurants.add(restaurant);
                }
            });
        }

        List<RestaurantDto> dto = restaurants.stream().map(Restaurant::toDto).collect(Collectors.toList());
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Danh sách các cơ sở");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getResById(@PathVariable("id") Long _id) {
        Restaurant restaurant = resService.getById(_id);
        RestaurantDto dto = restaurant.toDto();
        ApiResponse response = ApiResponse.success(dto,HttpStatus.OK.value(), String.format("Thể loại %d",_id));
        return ResponseEntity.ok(response);
    }

    /** loc ra cac co so nha hang an toan
     */
    @GetMapping("/safe")
    public ResponseEntity<ApiResponse> getRestaurantSafe() {
        List<ResSafeResult> restaurants = resService.getRestaurantSafe();
        ApiResponse response = ApiResponse.success(restaurants, HttpStatus.OK.value(), "Danh sách các cơ sở an toàn");
        return ResponseEntity.ok(response);
    }

    /** loc ra cac co so nha hang khong an toan
     */
    @GetMapping("/unsafe")
    public ResponseEntity<ApiResponse> getRestaurantUnsafe() {
        List<ResUnsafeResult> restaurants = resService.getRestaurantUnsafe();
        ApiResponse response = ApiResponse.success(restaurants, HttpStatus.OK.value(), "Danh sách các cơ sở khoong an toàn");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/nocert")
    public ResponseEntity<ApiResponse> getResNoCert() {
        List<Restaurant> restaurants =  fillterRestaurant.getResNoCert();
        ApiResponse response = ApiResponse.success(restaurants, HttpStatus.OK.value(), "Danh sách các cơ sở chưa được cấp phép");
        return ResponseEntity.ok(response);
    }

    /** loc ra cac co so nha hang bi thu hoi cert
     */
    @GetMapping("/deletecert")
    public ResponseEntity<ApiResponse> getResDeleteCert() {
        List<Restaurant> restaurants =  fillterRestaurant.getResDeleteCert();
        ApiResponse response = ApiResponse.success(restaurants, HttpStatus.OK.value(), "Danh sách các cơ sở bị thu hồi giấy chứng nhận");
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

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRestaurant(@PathVariable("id") long _id) {
        resService.deleteById(_id);
        ApiResponse response = ApiResponse.success(null,HttpStatus.OK.value(), "Xóa thành công" + _id);
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

