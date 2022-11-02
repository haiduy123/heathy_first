package com.example.healthy_first_ver1.service;

import com.example.healthy_first_ver1.entity.Candidate;
import com.example.healthy_first_ver1.entity.Company;
import com.example.healthy_first_ver1.repository.result.NewsResult;

import java.io.InputStream;
import java.util.List;

public interface CandidateService {
    public String writeFileToServer(InputStream inputStream, String fileName, String subFolder, String folder) throws Exception;
    public List<Object> searchJob(String address, String salary, String position);
    public List<NewsResult> getCandidateNews(Long id);
    public Candidate updateCandidate(Long id, Candidate candidate);
}
