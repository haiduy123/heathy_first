package com.example.healthy_first_ver1.service;


import com.example.healthy_first_ver1.entity.Candidate;
import com.example.healthy_first_ver1.entity.News;
import com.example.healthy_first_ver1.repository.result.CandidateResult;
import com.example.healthy_first_ver1.repository.result.NewsResult;

import java.util.List;

public interface NewsService {
    List<NewsResult> getNewsInfo();
    News updateNews(Long id,News news);
    List<CandidateResult> getCandidateByNews(Long idNews);
}
