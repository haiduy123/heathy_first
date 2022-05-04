package com.example.healthy_first_ver1.service;


import com.example.healthy_first_ver1.api.form.CheckPlanForm;
import com.example.healthy_first_ver1.entity.CheckPlan;

import java.util.List;

public interface PlanService {
    CheckPlan addNewPlan(CheckPlanForm form);

    CheckPlan getById(Long id);

    List<CheckPlan> getPlanList();

    CheckPlan save(CheckPlan checkPlan);
}
