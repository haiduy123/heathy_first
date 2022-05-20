package com.example.healthy_first_ver1.api.controller;

import com.example.healthy_first_ver1.api.form.CertificateForm;
import com.example.healthy_first_ver1.api.response.ApiResponse;
import com.example.healthy_first_ver1.dto.CertResultDto;
import com.example.healthy_first_ver1.dto.CertificateDto;
import com.example.healthy_first_ver1.entity.Certificate;
import com.example.healthy_first_ver1.entity.Restaurant;
import com.example.healthy_first_ver1.repository.ResRepository;
import com.example.healthy_first_ver1.service.CertificateService;
import com.example.healthy_first_ver1.service.ResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/cert")
public class CertController {
    @Autowired
    CertificateService certificateService;

    @Autowired
    ResService resService;

    @Autowired
    ResRepository resRepository;

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
    public ResponseEntity<ApiResponse> deleteCertificate(@PathVariable("id") Long _id) {
        List<Restaurant> restaurants = resRepository.findAll();

        /** tim trong list restaurant
         * neu xoa cert id thi cert id trong restaurant se = null
         */

        restaurants.forEach(restaurant -> {
            if (!ObjectUtils.isEmpty(restaurant.getCert_id()) && restaurant.getCert_id().equals(_id)) {
                restaurant.setCert_id(null);
            }
        });

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

    @GetMapping("/location")
    public ResponseEntity<ApiResponse> getCertLocation() {
        CertResultDto cert = certificateService.getCertLocation();
        ApiResponse response = ApiResponse.success(cert, HttpStatus.OK.value(), "Certificate :");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCertificate(@PathVariable("id") Long _id, @RequestBody LocalDate _endDate) {
        Certificate certificate = certificateService.updateCertificate(_id, _endDate);
        CertificateDto dto = certificate.toDto();
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Cập nhật thành công");
        return ResponseEntity.ok(response);
    }
}
