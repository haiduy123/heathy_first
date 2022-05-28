package com.example.healthy_first_ver1.service.Impl;

import com.example.healthy_first_ver1.api.form.RestaurantForm;
import com.example.healthy_first_ver1.entity.Restaurant;
import com.example.healthy_first_ver1.entity.Role;
import com.example.healthy_first_ver1.exception.NotFoundException;
import com.example.healthy_first_ver1.repository.CertRepository;
import com.example.healthy_first_ver1.repository.ResRepository;
import com.example.healthy_first_ver1.repository.result.ResRecommendResult;
import com.example.healthy_first_ver1.repository.result.ResSafeResult;
import com.example.healthy_first_ver1.repository.result.ResUnsafeResult;
import com.example.healthy_first_ver1.service.ResService;
import com.example.healthy_first_ver1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class RestaurantImpl implements ResService {
    @Autowired
    ResRepository resRepository;

    @Autowired
    CertRepository certRepository;

    @Autowired
    UserService userService;

    @Override
    public Restaurant addNewRes(RestaurantForm form) {
        Restaurant restaurant = Restaurant.builder()
                .name(form.getName())
                .address(form.getAddress())
                .phone(form.getPhone())
                .type(form.getType())
                .district(form.getDistrict())
                .build();

        /** neu certId de trong thi nghia la chua duoc cap cert
         * set certId = 0
         */
        if (ObjectUtils.isEmpty(form.getCertId())) {
            restaurant.setCert_id(Long.valueOf(0));
        } else {
            restaurant.setCert_id(form.getCertId());
        }

        return save(restaurant);
    }

    // lấy cơ sở theo id
    @Override
    public Restaurant getById(Long id) {
        Restaurant restaurant = resRepository.findById(id).get();
//        checkCertificate(restaurant);
        if(ObjectUtils.isEmpty(restaurant)) {
            String mess = "restaurant-not-exits";
            throw new NotFoundException(mess);
        }

        return restaurant;
    }

    @Override
    public Restaurant getByName(String name) {
        Restaurant restaurant = resRepository.findByName(name);
        return restaurant;
    }

    @Override
    public Restaurant updateRestaurant(Long id, String name, String address, String phone) {
        Restaurant restaurant = resRepository.findById(id).get();

        if(ObjectUtils.isEmpty(restaurant)) {
            String mess = "restaurant-not-exits";
            throw new NotFoundException(mess);
        }

        restaurant.setAddress(address);
        restaurant.setName(name);
        restaurant.setPhone(phone);

        return save(restaurant);
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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String district = userService.getUserByName(username).getLocation();
        Collection<Role> roles = userService.getUserByName(username).getRoles();
        int temp = (int) roles.stream().filter(item -> item.getName().equals("ROLE_ADMIN")).count();

        List<ResSafeResult> safeRes = new ArrayList<>();

        if(temp != 0) {
            resRepository.getRestaurantSafe().forEach((res -> {
                safeRes.add(res);
            }));
        } else {
            resRepository.getRestaurantSafe().forEach((item -> {
                if(item.getDistrict().equals(district)) {
                    safeRes.add(item);
                }
            }));
        }

        return safeRes;
    }

    @Override
    public List<ResUnsafeResult> getRestaurantUnsafe() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String district = userService.getUserByName(username).getLocation();
        Collection<Role> roles = userService.getUserByName(username).getRoles();
        int temp = (int) roles.stream().filter(item -> item.getName().equals("ROLE_ADMIN")).count();

        List<ResUnsafeResult> unsafeRes = new ArrayList<>();


        if(temp != 0) {
            resRepository.getRestaurantUnsafe().forEach((res -> {
                unsafeRes.add(res);
            }));
        } else {
            resRepository.getRestaurantUnsafe().forEach((item -> {
                if(item.getDistrict().equals(district)) {
                    unsafeRes.add(item);
                }
            }));
        }
        return unsafeRes;
    }

    @Override
    public List<ResRecommendResult> getResRecommend() {
        List<ResRecommendResult> restaurants = new ArrayList<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String district = userService.getUserByName(username).getLocation();
        Collection<Role> roles = userService.getUserByName(username).getRoles();
        int temp = (int) roles.stream().filter(item -> item.getName().equals("ROLE_ADMIN")).count();

        if(temp != 0) {
            resRepository.getRestaurantRecommend().forEach((res -> {
                restaurants.add(res);
            }));
        } else {
            resRepository.getRestaurantRecommend().forEach((item -> {
                if(item.getDistrict().equals(district)) {
                    restaurants.add(item);
                }
            }));
        }

        return restaurants;
    }

    @Override
    public List<Long> countRestaurant() {
        List<Restaurant> restaurants = new ArrayList<>();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String district = userService.getUserByName(username).getLocation();
        Collection<Role> roles = userService.getUserByName(username).getRoles();
        int temp = (int) roles.stream().filter(item -> item.getName().equals("ROLE_ADMIN")).count();

        List<ResSafeResult> safeRes = new ArrayList<>();

        if(temp != 0) {
            resRepository.findAll().forEach((res -> {
                if(res.getType().equals("Restaurant")) {
                    restaurants.add(res);
                }
            }));

            resRepository.getRestaurantSafe().forEach((item -> {
                if(item.getType().equals("Restaurant")) {
                    safeRes.add(item);
                }
            }));
        } else {
            resRepository.findAll().forEach((item -> {
                if(item.getType().equals("Restaurant") && item.getDistrict().equals(district)) {
                    restaurants.add(item);
                }
            }));

            resRepository.getRestaurantSafe().forEach((item -> {
                if(item.getType().equals("Restaurant") && item.getDistrict().equals(district)) {
                    safeRes.add(item);
                }
            }));
        }

        List<Long> count = new ArrayList<>();
        count.add(Long.valueOf(safeRes.size()));// safe
        count.add(Long.valueOf(restaurants.size() - safeRes.size())); // unsafe
        return count;
    }

    @Override
    public List<Long> countProduction() {
        List<Restaurant> productions = new ArrayList<>();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String district = userService.getUserByName(username).getLocation();
        Collection<Role> roles = userService.getUserByName(username).getRoles();
        int temp = (int) roles.stream().filter(item -> item.getName().equals("ROLE_ADMIN")).count();

        List<ResSafeResult> safeProduction = new ArrayList<>();

        if(temp != 0) {
            resRepository.findAll().forEach((res -> {
                if(res.getType().equals("Production")) {
                    productions.add(res);
                }
            }));

            resRepository.getRestaurantSafe().forEach((item -> {
                if(item.getType().equals("Production")) {
                    safeProduction.add(item);
                }
            }));
        } else {
            resRepository.findAll().forEach((item -> {
                if(item.getType().equals("Production") && item.getDistrict().equals(district)) {
                    productions.add(item);
                }
            }));

            resRepository.getRestaurantSafe().forEach((item -> {
                if(item.getType().equals("Production") && item.getDistrict().equals(district)) {
                    safeProduction.add(item);
                }
            }));
        }

        List<Long> count = new ArrayList<>();
        count.add(Long.valueOf(safeProduction.size()));// safe
        count.add(Long.valueOf(productions.size() - safeProduction.size())); // unsafe
        return count;
    }

    @Override
    public Map<String, Integer> count() {
        List<Restaurant> productions = new ArrayList<>();
        List<Restaurant> restaurants = new ArrayList<>();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String district = userService.getUserByName(username).getLocation();
        Collection<Role> roles = userService.getUserByName(username).getRoles();
        int temp = (int) roles.stream().filter(item -> item.getName().equals("ROLE_ADMIN")).count();

        List<ResSafeResult> safeProduction = new ArrayList<>();

        if(temp != 0) {
            resRepository.findAll().forEach((res -> {
                if(res.getType().equals("Production")) {
                    productions.add(res);
                }
            }));

            resRepository.findAll().forEach((res -> {
                if(res.getType().equals("Restaurant")) {
                    restaurants.add(res);
                }
            }));
        } else {
            resRepository.findAll().forEach((item -> {
                if(item.getType().equals("Production") && item.getDistrict().equals(district)) {
                    productions.add(item);
                }
            }));

            resRepository.findAll().forEach((item -> {
                if(item.getType().equals("Restaurant") && item.getDistrict().equals(district)) {
                    restaurants.add(item);
                }
            }));
        }

        Map<String,Integer> count = new HashMap<>();
        count.put("Restautant",restaurants.size()); // restaurant
        count.put("Production",productions.size()); // productions
        return count;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return resRepository.save(restaurant);
    }

}
