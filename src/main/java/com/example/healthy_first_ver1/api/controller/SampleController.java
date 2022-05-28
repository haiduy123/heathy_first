package com.example.healthy_first_ver1.api.controller;

import com.example.healthy_first_ver1.api.form.SampleForm;
import com.example.healthy_first_ver1.api.response.ApiResponse;
import com.example.healthy_first_ver1.dto.SampleDto;
import com.example.healthy_first_ver1.entity.Sample;
import com.example.healthy_first_ver1.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sample")
public class SampleController {
    @Autowired
    SampleService sampleService;

    @PostMapping
    public ResponseEntity<ApiResponse> addSample(@RequestBody SampleForm _form) {
        Sample sample = sampleService.addNewSample(_form);
        SampleDto dto = sample.toDto();
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Thêm mẫu thành công");
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getSampleList() {
        List<Sample> samples = sampleService.getSampleList();
        ApiResponse response = ApiResponse.success(samples, HttpStatus.OK.value(), "Danh sách các mẫu đang kiểm tra");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateSample(@PathVariable("id") Long _id,  @RequestBody String _status) {
        Sample sample = sampleService.updateSample(_id, _status);
        SampleDto dto = sample.toDto();
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Sửa mẫu thành công");
        return ResponseEntity.ok(response);
    }
}
