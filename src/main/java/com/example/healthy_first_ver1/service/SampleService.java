package com.example.healthy_first_ver1.service;

import com.example.healthy_first_ver1.api.form.SampleForm;
import com.example.healthy_first_ver1.entity.Sample;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SampleService {
    Sample addNewSample(SampleForm form);

    Sample updateSample(Long id, String status);

    List<Sample> getSampleList();

    Sample save(Sample sample);
}
