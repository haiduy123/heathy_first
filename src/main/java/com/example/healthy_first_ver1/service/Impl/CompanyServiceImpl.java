package com.example.healthy_first_ver1.service.Impl;

import com.example.healthy_first_ver1.repository.CompanyRepository;
import com.example.healthy_first_ver1.repository.result.CandidateResult;
import com.example.healthy_first_ver1.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Override
    public List<CandidateResult> getCandidateByCompany(String companyName) {
        List<CandidateResult> rs = companyRepository.getCandidateByCompany(companyName);
        return rs;
    }
}
