package com.example.healthy_first_ver1.service;

import com.example.healthy_first_ver1.api.form.CertificateForm;
import com.example.healthy_first_ver1.entity.Certificate;

import java.time.LocalDate;
import java.util.List;

public interface CertificateService {
    Certificate addNewCert(CertificateForm form);

    List<Certificate> getCertList();

    Certificate updateCertificate(Long id, LocalDate endDate);

    void deleteById(Long id);

    Certificate getById(Long id);

    Certificate save(Certificate certificate);
}
