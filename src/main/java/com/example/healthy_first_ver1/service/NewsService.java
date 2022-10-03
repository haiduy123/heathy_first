package com.example.healthy_first_ver1.service;


import com.example.healthy_first_ver1.repository.result.NewsResult;

import java.util.List;

public interface NewsService {
    List<NewsResult> getNewsInfo();
}
