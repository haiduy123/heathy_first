package com.example.healthy_first_ver1.service.Impl;

import com.example.healthy_first_ver1.entity.Company;
import com.example.healthy_first_ver1.repository.CompanyRepository;
import com.example.healthy_first_ver1.repository.NewsRepository;
import com.example.healthy_first_ver1.repository.result.CandidateResult;
import com.example.healthy_first_ver1.repository.result.NewsResult;
import com.example.healthy_first_ver1.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    NewsRepository newsRepository;

    @Override
    public List<CandidateResult> getCandidateByCompany(String companyName) {
        List<CandidateResult> rs = companyRepository.getCandidateByCompany(companyName);
        return rs;
    }

    @Override
    public List<NewsResult> getCompayNews(Long idCompany) {
        List<NewsResult> rs = newsRepository.getCompanyNews(idCompany);
        return rs;
    }

    @Override
    public List<NewsResult> getCompayNews() {
        List<NewsResult> rs = newsRepository.getCompanyNews();
        return rs;
    }

    @Override
    public Company updateCompany(Long id, Company company) {
        Company com1 = companyRepository.findById(id).get();
        if(!ObjectUtils.isEmpty(company.getAddress())) {
            com1.setAddress(company.getAddress());
        }
        if(!ObjectUtils.isEmpty(company.getName())) {
            com1.setName(company.getName());
        }
        if(!ObjectUtils.isEmpty(company.getIntroduce())) {
            com1.setIntroduce(company.getIntroduce());
        }
        return companyRepository.save(com1);
    }
}
