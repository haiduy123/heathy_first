package com.example.healthy_first_ver1.api.controller;

import com.example.healthy_first_ver1.api.response.ApiResponse;
import com.example.healthy_first_ver1.entity.Company;
import com.example.healthy_first_ver1.entity.News;
import com.example.healthy_first_ver1.entity.Role;
import com.example.healthy_first_ver1.repository.CompanyRepository;
import com.example.healthy_first_ver1.repository.result.CandidateResult;
import com.example.healthy_first_ver1.repository.result.NewsResult;
import com.example.healthy_first_ver1.service.CompanyService;
import com.example.healthy_first_ver1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserService userService;

    // Hiện ra list các công ty
    @GetMapping()
    public ResponseEntity<ApiResponse> getListCompany() {
        Iterable<Company> rs = companyRepository.findAll();
        ApiResponse response = ApiResponse.success(rs, HttpStatus.OK.value(), "Danh sách công ty");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCompany(@PathVariable("id") Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<Role> roles = userService.getUserByName(username).getRoles();
        int temp = (int) roles.stream().filter(item -> item.getName().equals("ROLE_ADMIN")).count();
        ApiResponse response =  ApiResponse.success(null, hashCode(), null);
        // Nếu là admin thì hiện ra tất cả các tin tuyển dụng của tất cả các công ty
        if(temp != 0) {
            companyRepository.deleteById(id);
            response.setCode(HttpStatus.OK.value());
            response.setMessage("Xóa thành công công ty");
        } else {
            response.setCode(403);
            response.setMessage("Bạn không đủ thẩm quyền");
        }
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateNews(@PathVariable("id") Long id, @RequestBody Company company) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<Role> roles = userService.getUserByName(username).getRoles();
        int temp = (int) roles.stream().filter(item -> item.getName().equals("ROLE_ADMIN")).count();
        ApiResponse response =  ApiResponse.success(null, hashCode(), null);
        // Nếu là admin thì hiện ra tất cả các tin tuyển dụng của tất cả các công ty
        if(temp != 0) {
            companyService.updateCompany(id,company);
            response.setCode(HttpStatus.OK.value());
            response.setMessage("Chỉnh sửa thành công công ty");
        } else {
            response.setCode(403);
            response.setMessage("Bạn không đủ thẩm quyền");
        }
        return ResponseEntity.ok(response);
    }

    // Hiện ra những ứng viên đã ứng tuyển vào công ty
    @GetMapping("/candidate")
    public ResponseEntity<ApiResponse> getCandidateByCompany() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String companyName = companyRepository.getCompanyName(username);
        List<CandidateResult> rs = companyService.getCandidateByCompany(companyName);
        ApiResponse response = ApiResponse.success(rs, HttpStatus.OK.value(), "Candidate Detail");
        return ResponseEntity.ok(response);
    }

    // Hiện ra công ty đã đăng những tin tuyển dụng nào
    @GetMapping("/news")
    public ResponseEntity<ApiResponse> getNewsByCompany() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<Role> roles = userService.getUserByName(username).getRoles();
        int temp = (int) roles.stream().filter(item -> item.getName().equals("ROLE_ADMIN")).count();
        List<NewsResult> rs = new ArrayList<>();
        // Nếu là admin thì hiện ra tất cả các tin tuyển dụng của tất cả các công ty
        if(temp != 0) {
            rs = companyService.getCompayNews();
        } else {
            Long idCompany = companyRepository.getCompanyId(username);   // Nếu không là admin thì hiện ra tất cả các tin tuyển dụng của công ty theo đăng nhập
            rs = companyService.getCompayNews(idCompany);
        }
        ApiResponse response = ApiResponse.success(rs, HttpStatus.OK.value(), "News Detail");
        return ResponseEntity.ok(response);
    }
}
