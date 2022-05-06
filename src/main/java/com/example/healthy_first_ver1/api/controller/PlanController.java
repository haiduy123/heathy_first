package com.example.healthy_first_ver1.api.controller;


import com.example.healthy_first_ver1.api.form.CheckPlanForm;
import com.example.healthy_first_ver1.api.response.ApiResponse;
import com.example.healthy_first_ver1.dto.CheckPlanDto;
import com.example.healthy_first_ver1.entity.CheckPlan;
import com.example.healthy_first_ver1.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    PlanService planService;

    @PostMapping
    public ResponseEntity<ApiResponse> addPlan(@RequestBody CheckPlanForm _form) {
        CheckPlan plan = planService.addNewPlan(_form);
        CheckPlanDto dto = plan.toDto();
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Thêm plan thành công");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getPlanList() {
        List<CheckPlan> plans = planService.getPlanList();
        List<CheckPlanDto> dto = plans.stream().map(CheckPlan::toDto).collect(Collectors.toList());
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Danh sách các plan");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPlanById(@PathVariable("id") Long _id) {
        CheckPlan plan = planService.getById(_id);
        CheckPlanDto dto = plan.toDto();
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Plan :");
        return ResponseEntity.ok(response);
    }
}
