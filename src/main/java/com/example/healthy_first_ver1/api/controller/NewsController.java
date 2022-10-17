package com.example.healthy_first_ver1.api.controller;

import com.example.healthy_first_ver1.api.response.ApiResponse;
import com.example.healthy_first_ver1.entity.Candidate;
import com.example.healthy_first_ver1.entity.News;
import com.example.healthy_first_ver1.repository.CompanyRepository;
import com.example.healthy_first_ver1.repository.NewsRepository;
import com.example.healthy_first_ver1.repository.result.CandidateResult;
import com.example.healthy_first_ver1.repository.result.NewsResult;
import com.example.healthy_first_ver1.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteNews(@PathVariable("id") Long id) {
        newsRepository.deleteById(id);
        ApiResponse response = ApiResponse.success(null, HttpStatus.OK.value(), "Xóa thành công thông tin tuyển dụng");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateNews(@PathVariable("id") Long id, @RequestBody News news) {
        newsService.updateNews(id, news);
        ApiResponse response = ApiResponse.success(news, HttpStatus.OK.value(), "Thay đổi thành công thông tin tuyển dụng");
        return ResponseEntity.ok(response);
    }
    // thông tin các tin tuyển dụng
    @PostMapping("/info")
    public ResponseEntity<ApiResponse> getNewsInfo() {
        List<NewsResult> results = newsService.getNewsInfo();
        ApiResponse response = ApiResponse.success(results, HttpStatus.OK.value(), "Danh sách thông tin tuyển dụng");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/candidate/{id_news}")
    public ResponseEntity<ApiResponse> getCandidateByNews(@PathVariable("id_news") Long id_news) {
        List<CandidateResult> results = newsService.getCandidateByNews(id_news);
        ApiResponse response = ApiResponse.success(results, HttpStatus.OK.value(), "Danh sách ứng viên ứng tuyển");
        return ResponseEntity.ok(response);
    }

    // đăng nhập công ty -> thêm tin tuyển dụng -> đánh id vào các bảng liên quan
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
