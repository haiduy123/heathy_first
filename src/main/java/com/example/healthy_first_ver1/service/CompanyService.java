package com.example.healthy_first_ver1.service;

import com.example.healthy_first_ver1.repository.result.CandidateResult;

import java.util.List;

public interface CompanyService {
    List<CandidateResult> getCandidateByCompany(String companyName);
}
