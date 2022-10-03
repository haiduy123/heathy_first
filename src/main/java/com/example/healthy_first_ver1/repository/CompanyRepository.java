package com.example.healthy_first_ver1.repository;

import com.example.healthy_first_ver1.entity.Company;
import com.example.healthy_first_ver1.repository.result.CandidateResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompanyRepository extends CrudRepository<Company,Long> {

    @Query(value = "select ca.file_cv as cv,temp.id_candidate as id, ca.name as name, temp.position as position , temp.salary as salary from ooad.candidate ca\n" +
            "join \n" +
            "(select cn.id_candidate, t.position, t.salary\n" +
            "from ooad.candidate_news cn\n" +
            "join\n" +
            "(select n.id as id_new, n.position, n.salary\n" +
            "from ooad.company c\n" +
            "join ooad.news n\n" +
            "on c.id = n.id_company\n" +
            "where c.name = :companyName) as t\n" +
            "on t.id_new = cn.id_news ) as temp\n" +
            "on ca.id = temp.id_candidate\n" +
            "order by id_candidate",nativeQuery = true)
    List<CandidateResult> getCandidateByCompany(String companyName);

    @Query (value = "select name from ooad.company c\n" +
            "where c.username = :username", nativeQuery = true)
    String getCompanyName(String username);
}
