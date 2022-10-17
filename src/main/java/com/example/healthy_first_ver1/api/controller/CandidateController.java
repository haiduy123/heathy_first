package com.example.healthy_first_ver1.api.controller;


import com.example.healthy_first_ver1.api.response.ApiResponse;
import com.example.healthy_first_ver1.entity.Candidate;
import com.example.healthy_first_ver1.entity.Candidate_News;
import com.example.healthy_first_ver1.entity.Company;
import com.example.healthy_first_ver1.repository.CandidateRepository;
import com.example.healthy_first_ver1.repository.Candidate_NewsRepository;
import com.example.healthy_first_ver1.repository.CompanyRepository;
import com.example.healthy_first_ver1.repository.result.NewsResult;
import com.example.healthy_first_ver1.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    @Value("${upload-file.subFolder}")
    private String subFolder;

    @Value("${upload-file.folder}")
    private String folder;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private Candidate_NewsRepository candidate_newsRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) {

        //Save file to server
        String fileName = file.getOriginalFilename();
        String fileInputPath = null;
        try {
            fileInputPath = candidateService.writeFileToServer(file.getInputStream(), fileName, this.subFolder, this.folder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long can_id = Long.valueOf(candidateRepository.getCandidateId(username));
        Candidate candidate = candidateRepository.findById(can_id).get();
        candidate.setFileCv(fileName);
        candidateRepository.save(candidate);
        return ResponseEntity.status(HttpStatus.OK).body(candidate);
    }

    @GetMapping ("/download")
    public ResponseEntity<Object> downloadFile(@RequestParam String fileName) throws IOException
    {
        File file = new File("D:/cv_ooad/" + fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/txt")).body(resource);

        return responseEntity;
    }

    @PostMapping ("/searchJob")
    public ResponseEntity<Object> searchJob(@RequestParam String address, @RequestParam String salary,@RequestParam String position) throws IOException
    {
        List<Object> results = candidateService.searchJob(address,salary,position);
        ApiResponse response = ApiResponse.success(results, HttpStatus.OK.value(), "Danh sách thông tin tuyển dụng");
        return ResponseEntity.ok(response);
    }

    // đăng nhập candidate ứng tuyển vào cty -> đăng nhập cty hiện ra thông tin candidate
    @PostMapping("/apply/{news_id}")
    public ResponseEntity<?>applyJob(@PathVariable("news_id") Long news_id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long can_id = Long.valueOf(candidateRepository.getCandidateId(username));
        Candidate_News candidate_news = new Candidate_News();
        candidate_news.setIdCandidate(can_id);
        candidate_news.setIdNews(news_id);
        candidate_newsRepository.save(candidate_news);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/news")
    public ResponseEntity<?>getCandidateNews() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long id = Long.valueOf(candidateRepository.getCandidateId(username));
        List<NewsResult> results = candidateService.getCandidateNews(id);
        ApiResponse response = ApiResponse.success(results, HttpStatus.OK.value(), "Danh sách thông tin tuyển dụng");
        return ResponseEntity.ok(response);
    }
}

