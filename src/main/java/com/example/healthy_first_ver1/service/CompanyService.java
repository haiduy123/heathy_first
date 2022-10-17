package com.example.healthy_first_ver1.service;

import com.example.healthy_first_ver1.entity.Company;
import com.example.healthy_first_ver1.repository.result.CandidateResult;
import com.example.healthy_first_ver1.repository.result.NewsResult;

import java.util.List;

public interface CompanyService {
    List<CandidateResult> getCandidateByCompany(String companyName);
    List<NewsResult> getCompayNews(Long idCompany);
    List<NewsResult> getCompayNews();
    Company updateCompany(Long id, Company company);
}
