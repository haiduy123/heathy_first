package com.example.healthy_first_ver1.service.Impl;

import com.example.healthy_first_ver1.api.form.SampleForm;
import com.example.healthy_first_ver1.entity.Role;
import com.example.healthy_first_ver1.entity.Sample;
import com.example.healthy_first_ver1.exception.NotFoundException;
import com.example.healthy_first_ver1.repository.PlanRepository;
import com.example.healthy_first_ver1.repository.ResRepository;
import com.example.healthy_first_ver1.repository.SampleRepository;
import com.example.healthy_first_ver1.service.SampleService;
import com.example.healthy_first_ver1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class SampleImpl implements SampleService {
    @Autowired
    SampleRepository sampleRepository;

    @Autowired
    UserService userService;

    @Autowired
    PlanRepository planRepository;

    @Autowired
    ResRepository resRepository;

    @Override
    public Sample addNewSample(SampleForm form) {
        Sample sample = Sample.builder()
                .name(form.getName())
                .laboratory(form.getLaboratory())
                .plan_id(form.getPlan_id())
                .endDate(form.getEndDate())
                .build();

        sample.setStatus("None");

        return save(sample);
    }

    @Override
    public Sample updateSample(Long id,String status) {
        Sample sample = sampleRepository.findById(id).get();

        if(ObjectUtils.isEmpty(sample)) {
            String mess = "sample-not-exits";
            throw new NotFoundException(mess);
        }

    sample.setStatus(status);

        return save(sample);
    }

    @Override
    public List<Sample> getSampleList() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String district = userService.getUserByName(username).getLocation();
        Collection<Role> roles = userService.getUserByName(username).getRoles();
        int temp = (int) roles.stream().filter(item -> item.getName().equals("ROLE_ADMIN")).count();

        List<Sample> samples = new ArrayList<>();

        if(temp != 0) {
            sampleRepository.findAll().forEach((sample -> {
                samples.add(sample);
            }));
        } else {
            sampleRepository.findAll().forEach((sample -> {
                if(resRepository.getById(planRepository.getById(sample.getPlan_id()).getRestaurantId()).getDistrict().equals(district)) {
                    samples.add(sample);
                }
            }));
        }

        return samples;
    }

    @Override
    public Sample save(Sample sample) {
        return sampleRepository.save(sample);
    }
}
