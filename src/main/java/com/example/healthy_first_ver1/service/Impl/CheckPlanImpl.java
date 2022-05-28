package com.example.healthy_first_ver1.service.Impl;

import com.example.healthy_first_ver1.api.form.CheckPlanForm;
import com.example.healthy_first_ver1.entity.CheckPlan;
import com.example.healthy_first_ver1.entity.Role;
import com.example.healthy_first_ver1.exception.NotFoundException;
import com.example.healthy_first_ver1.repository.PlanRepository;
import com.example.healthy_first_ver1.repository.ResRepository;
import com.example.healthy_first_ver1.service.PlanService;
import com.example.healthy_first_ver1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class CheckPlanImpl implements PlanService {
    @Autowired
    PlanRepository planRepository;

    @Autowired
    UserService userService;

    @Autowired
    ResRepository resRepository;

    @Override
    public CheckPlan addNewPlan(CheckPlanForm form) {
        CheckPlan plan = CheckPlan.builder()
                .start_date(form.getStart_date())
                .end_date(form.getEnd_date())
                .status(form.getStatus())
                .restaurantId(form.getRestaurantId())
                .stageId(form.getStageId())
                .build();

        if(ObjectUtils.isEmpty(form.getStart_date())) {
            plan.setStart_date(LocalDate.now());
        }

        return save(plan);
    }

    @Override
    public CheckPlan getById(Long id) {
        CheckPlan plan = planRepository.findById(id).get();
        if(ObjectUtils.isEmpty(plan)) {
            String mess = "plan-not-exits";
            throw new NotFoundException(mess);
        }

        return plan;
    }

    @Override
    public List<CheckPlan> getPlanList() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String district = userService.getUserByName(username).getLocation();
        Collection<Role> roles = userService.getUserByName(username).getRoles();
        int temp = (int) roles.stream().filter(item -> item.getName().equals("ROLE_ADMIN")).count();

        List<CheckPlan> plans = new ArrayList<>();
        if(temp != 0) {
            planRepository.findAll().forEach((plan -> {
                plans.add(plan);
            }));
        } else {
            planRepository.findAll().forEach((plan -> {
                if (resRepository.getById(plan.getRestaurantId()).getDistrict().equals(district)) {
                    plans.add(plan);
                }
            }));
        }
        return plans;
    }

    @Override
    public CheckPlan save(CheckPlan checkPlan) {
        return planRepository.save(checkPlan);
    }
}
