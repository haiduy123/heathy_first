package com.example.healthy_first_ver1.api.controller;

import com.example.healthy_first_ver1.api.response.ApiResponse;
import com.example.healthy_first_ver1.entity.News;
import com.example.healthy_first_ver1.repository.CompanyRepository;
import com.example.healthy_first_ver1.repository.NewsRepository;
import com.example.healthy_first_ver1.repository.result.NewsResult;
import com.example.healthy_first_ver1.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    NewsService newsService;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    NewsRepository newsRepository;

    @PostMapping("/info")
    public ResponseEntity<ApiResponse> getNewsInfo() {
        List<NewsResult> results = newsService.getNewsInfo();
        ApiResponse response = ApiResponse.success(results, HttpStatus.OK.value(), "Danh sách thông tin tuyển dụng");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addNews(@RequestBody News news) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long id = Long.valueOf(companyRepository.getCompanyId(username));
        news.setIdCompany(id);
        ApiResponse response = ApiResponse.success(news, HttpStatus.OK.value(), "Danh sách thông tin tuyển dụng");
        newsRepository.save(news);
        return ResponseEntity.ok(response);
    }
}
