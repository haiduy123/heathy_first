package com.example.healthy_first_ver1.service.Impl;


import com.example.healthy_first_ver1.entity.Candidate;
import com.example.healthy_first_ver1.entity.Company;
import com.example.healthy_first_ver1.repository.CandidateRepository;
import com.example.healthy_first_ver1.repository.result.NewsResult;
import com.example.healthy_first_ver1.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {
    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    public String writeFileToServer(InputStream inputStream, String fileName, String subFolder, String folder)
            throws Exception {

        String safeFileName = safeToString(fileName) ;//+ File.pathSeparator + UString.extractFileExt(fileName);

        String uploadPath = folder + File.separator + getSafeFileName(subFolder) + File.separator;
        File udir = new File(uploadPath);
        if (!udir.exists()) {
            udir.mkdirs();
        }
        try (OutputStream out = new FileOutputStream(udir.getAbsolutePath() + File.separator + safeFileName)) {
            int bytesRead = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((bytesRead = inputStream.read(buffer, 0, 1024 * 8)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        return uploadPath + File.separator + safeFileName;

    }

    @Override
    public List<Object> searchJob(String address, String salary, String position) {
        String salaryChoice1 = "Dưới 5 triệu";
        String salaryChoice2 = "5 triệu - 10 triệu";
        String salaryChoice3 = "10 triệu - 20 triệu";
        String salaryChoice4 = "20 triệu - 30 triệu";
        String salaryChoice5 = "Trên 30 triệu";

        List<Object[]> lstObj = new ArrayList<>();
        List<Object> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT n.id,n.salary, n.position, c.address, datediff(n.end_date,now()) as dayleft, c.name, c.introduce\n" +
                "FROM ooad.news n \n" +
                "left join ooad.company c\n" +
                "on n.id_company = c.id\n" +
                "where position like concat ('%"+
                position + "%') ");
        if (!address.isEmpty()) {
            sql.append(" AND address = '");
            sql.append(address);
            sql.append("'");
        }
        if (salary.equals(salaryChoice1)) {
            sql.append(" and salary < 5000000");
        }
        if (salary.equals(salaryChoice2)) {
            sql.append(" and salary > 5000000 and salary < 10000000");
        }
        if (salary.equals(salaryChoice3)) {
            sql.append(" and salary > 10000000 and salary < 20000000");
        }
        if (salary.equals(salaryChoice4)) {
            sql.append(" and salary > 20000000 and salary < 30000000");
        }
        if (salary.equals(salaryChoice5)) {
            sql.append(" and salary > 30000000");
        }

        Query query = entityManager.createNativeQuery(sql.toString());
        lstObj = query.getResultList();
        if(lstObj != null && !lstObj.isEmpty()){
            for(Object[] obj : lstObj){
                Map<String, Object> res = new HashMap<>();
                res.put("ID",obj[0]);
                res.put("salary",obj[1]);
                res.put("position",obj[2]);
                res.put("address",obj[3]);
                res.put("dayleft",obj[4]);
                res.put("nameCompany",obj[5]);
                res.put("introduce",obj[6]);

                results.add(res);
            }
        }

        return results;
    }

    @Override
    public List<NewsResult> getCandidateNews(Long id) {
        List<NewsResult> rs = candidateRepository.getCandidateNews(id);
        return rs;
    }

    @Override
    public Candidate updateCandidate(Long id, Candidate candidate) {
        Candidate currentCandidate = candidateRepository.findById(id).get();
        if(!ObjectUtils.isEmpty(candidate.getFileCv())) {
            currentCandidate.setFileCv(candidate.getFileCv());
        }
        if(!ObjectUtils.isEmpty(candidate.getName())) {
            currentCandidate.setName(candidate.getName());
        }
        if(!ObjectUtils.isEmpty(candidate.getPhone())) {
            currentCandidate.setPhone(candidate.getPhone());
        }
        if(!ObjectUtils.isEmpty(candidate.getEmail())) {
            currentCandidate.setEmail(candidate.getEmail());
        }
        return candidateRepository.save(currentCandidate);
    }

    public static String getSafeFileName(String input) {
        StringBuilder sb = new StringBuilder();
        if (input != null) {
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c != '/' && c != '\\' && c != 0 ) {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    public static String safeToString(Object obj1, String defaultValue) {
        if (obj1 == null || obj1.toString().isEmpty()) {
            return defaultValue;
        }

        return obj1.toString();
    }

    public static String safeToString(Object obj1) {
        return safeToString(obj1, "");
    }
}

