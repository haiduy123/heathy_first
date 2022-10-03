package com.example.healthy_first_ver1.api.controller;

import com.example.healthy_first_ver1.api.response.ApiResponse;
import com.example.healthy_first_ver1.entity.User;
import com.example.healthy_first_ver1.repository.CompanyRepository;
import com.example.healthy_first_ver1.repository.result.CandidateResult;
import com.example.healthy_first_ver1.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping("/candidate")
    public ResponseEntity<ApiResponse> getCandidateByCompany() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String companyName = companyRepository.getCompanyName(username);
        List<CandidateResult> rs = companyService.getCandidateByCompany(companyName);
        ApiResponse response = ApiResponse.success(rs, HttpStatus.OK.value(), "User Detail");
        return ResponseEntity.ok(response);
    }
}
