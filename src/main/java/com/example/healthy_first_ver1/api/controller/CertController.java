package com.example.healthy_first_ver1.api.controller;

import com.example.healthy_first_ver1.api.form.CertificateForm;
import com.example.healthy_first_ver1.api.response.ApiResponse;
import com.example.healthy_first_ver1.dto.CertificateDto;
import com.example.healthy_first_ver1.entity.Certificate;
import com.example.healthy_first_ver1.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/cert")
public class CertController {
    @Autowired
    CertificateService certificateService;

    @PostMapping
    public ResponseEntity<ApiResponse> addCert(@RequestBody CertificateForm _form) {
        Certificate cert = certificateService.addNewCert(_form);
        CertificateDto dto = cert.toDto();
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Thêm cert thành công");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getCertList() {
        List<Certificate> certificates = certificateService.getCertList();
        List<CertificateDto> dto = certificates.stream().map(Certificate::toDto).collect(Collectors.toList());
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Danh sách các cert");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCertificate(@PathVariable("id") long _id) {
        certificateService.deleteById(_id);
        ApiResponse response = ApiResponse.success(null,HttpStatus.OK.value(), "Xóa thành công" + _id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCertById(@PathVariable("id") Long _id) {
        Certificate cert = certificateService.getById(_id);
        CertificateDto dto = cert.toDto();
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Certificate :");
        return ResponseEntity.ok(response);
    }
}
