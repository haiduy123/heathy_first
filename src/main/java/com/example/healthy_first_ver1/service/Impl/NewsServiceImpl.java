package com.example.healthy_first_ver1.service.Impl;

import com.example.healthy_first_ver1.entity.Candidate;
import com.example.healthy_first_ver1.entity.News;
import com.example.healthy_first_ver1.repository.NewsRepository;
import com.example.healthy_first_ver1.repository.result.CandidateResult;
import com.example.healthy_first_ver1.repository.result.NewsResult;
import com.example.healthy_first_ver1.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {
    @Autowired
    NewsRepository newsRepository;

    @Override
    public List<NewsResult> getNewsInfo() {
        List<NewsResult> results = newsRepository.getNewsInfo();
        return results;
    }

    @Override
    public News updateNews(Long id, News news) {
        News news1 = newsRepository.findById(id).get();
        if(!ObjectUtils.isEmpty(news.getEndDate())) {
            news1.setEndDate(news.getEndDate());
        }
        if(!ObjectUtils.isEmpty(news.getPosition())) {
            news1.setPosition(news.getPosition());
        }
        if(!ObjectUtils.isEmpty(news.getSalary())) {
            news1.setSalary(news.getSalary());
        }
        if(!ObjectUtils.isEmpty(news.getRequirement())) {
            news1.setRequirement(news.getRequirement());
        }
        if(!ObjectUtils.isEmpty(news.getTitle())) {
            news1.setTitle(news.getTitle());
        }
        return newsRepository.save(news1);
    }

    @Override
    public List<CandidateResult> getCandidateByNews(Long idNews) {
        List<CandidateResult> rs = newsRepository.getCandidateByNews(idNews);
        return rs;
    }
}
