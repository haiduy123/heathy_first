package com.example.healthy_first_ver1.service.Impl;

import com.example.healthy_first_ver1.repository.NewsRepository;
import com.example.healthy_first_ver1.repository.result.NewsResult;
import com.example.healthy_first_ver1.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
