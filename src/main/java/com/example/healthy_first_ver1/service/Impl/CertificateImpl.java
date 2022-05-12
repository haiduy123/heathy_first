package com.example.healthy_first_ver1.service.Impl;

import com.example.healthy_first_ver1.api.form.CertificateForm;
import com.example.healthy_first_ver1.entity.Certificate;
import com.example.healthy_first_ver1.exception.NotFoundException;
import com.example.healthy_first_ver1.repository.CertRepository;
import com.example.healthy_first_ver1.repository.ResRepository;
import com.example.healthy_first_ver1.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class CertificateImpl implements CertificateService {
    @Autowired
    CertRepository certRepository;

    @Autowired
    ResRepository resRepository;

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
    public Certificate save(Certificate certificate) {
        return certRepository.save(certificate);
    }
}
