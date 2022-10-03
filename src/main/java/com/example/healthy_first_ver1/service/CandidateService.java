package com.example.healthy_first_ver1.service;

import java.io.InputStream;
import java.util.List;

public interface CandidateService {
    public String writeFileToServer(InputStream inputStream, String fileName, String subFolder, String folder) throws Exception;
    public List<Object> searchJob(String address, String salary, String position);
}
