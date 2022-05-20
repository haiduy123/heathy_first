package com.example.healthy_first_ver1.service.Impl;

import com.example.healthy_first_ver1.api.form.CertificateForm;
import com.example.healthy_first_ver1.dto.CertResultDto;
import com.example.healthy_first_ver1.entity.Certificate;
import com.example.healthy_first_ver1.entity.Restaurant;
import com.example.healthy_first_ver1.entity.Role;
import com.example.healthy_first_ver1.exception.NotFoundException;
import com.example.healthy_first_ver1.repository.CertRepository;
import com.example.healthy_first_ver1.repository.ResRepository;
import com.example.healthy_first_ver1.service.CertificateService;
import com.example.healthy_first_ver1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class CertificateImpl implements CertificateService {
    @Autowired
    CertRepository certRepository;

    @Autowired
    ResRepository resRepository;

    @Autowired
    UserService userService;

    @Override
    public Certificate addNewCert(CertificateForm form) {
        Certificate certificate = Certificate.builder()
                .endDate(form.getEndDate())
                .startDate(form.getStartDate())
                .build();

        return save(certificate);
    }

    @Override
    public List<Certificate> getCertList() {
        List<Certificate> certificates = (List<Certificate>) certRepository.findAll();
        if(ObjectUtils.isEmpty(certificates)) {
            return Collections.EMPTY_LIST;
        }

        return certificates;
    }

    @Override
    public Certificate updateCertificate(Long id, LocalDate endDate) {
        Certificate certificate = certRepository.findById(id).get();

        if(ObjectUtils.isEmpty(certificate)) {
            String mess = "certificate-not-exits";
            throw new NotFoundException(mess);
        }

        certificate.setEndDate(endDate);

        return save(certificate);
    }

    @Override
    public Certificate getById(Long id) {
        Certificate certificate = certRepository.findById(id).get();

        if(ObjectUtils.isEmpty(certificate)) {
            String mess = "certificate-not-exits";
            throw new NotFoundException(mess);
        }
        return certificate;
    }

    @Override
    public void deleteById(Long id) {
        Certificate certificate = certRepository.findById(id).get();

        if(ObjectUtils.isEmpty(certificate)) {
            String mess = "certificate-not-exits";
            throw new NotFoundException(mess);
        }

        certRepository.deleteById(id);
    }

    @Override
    public CertResultDto getCertLocation() {
        List<Restaurant> fullRestaurants = resRepository.findAll();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String district = userService.getUserByName(username).getLocation();
        Collection<Role> roles = userService.getUserByName(username).getRoles();
        int temp = (int) roles.stream().filter(item -> item.getName().equals("ROLE_ADMIN")).count();
        CertResultDto result = new CertResultDto();
        int countRestaurant;
        int countProduction;
        List<Certificate> certificates = new ArrayList<>();
        if(temp != 0) {
            countRestaurant = (int) fullRestaurants.stream().filter(res -> res.getType().equals("Restaurant")).count();
            countProduction = (int) fullRestaurants.stream().filter(res -> res.getType().equals("Production")).count();
            certRepository.findAll().forEach((cert) -> {
                certificates.add(cert);
            });
        } else {
            countRestaurant = (int) fullRestaurants.stream().filter(res -> res.getType().equals("Restaurant") &&
                    res.getDistrict().equals(district) && !ObjectUtils.isEmpty(res.getCert_id()) && res.getCert_id() != 0).count();
            countProduction = (int) fullRestaurants.stream().filter(res -> res.getType().equals("Production") &&
                    res.getDistrict().equals(district) && !ObjectUtils.isEmpty(res.getCert_id()) && res.getCert_id() != 0).count();
            certRepository.findAll().forEach((cert) -> {
                resRepository.findAll().forEach((res -> {
                    if (cert.getId().equals(res.getCert_id()) || cert.getId() == 0 || cert.getId() == null) {
                        if (res.getDistrict().equals(district)) {
                            certificates.add(cert);
                        }
                    }
                }));
            });
        }
        result.setCertRestaurant((long) countRestaurant);
        result.setCertProduction((long) countProduction);
        result.setCertificates(certificates);

        return result;
    }

    @Override
    public Certificate save(Certificate certificate) {
        return certRepository.save(certificate);
    }
}
