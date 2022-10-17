package com.example.healthy_first_ver1.repository;

import com.example.healthy_first_ver1.entity.Candidate;
import com.example.healthy_first_ver1.repository.result.NewsResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CandidateRepository extends CrudRepository<Candidate, Long> {
    @Query(value = "select id from ooad.candidate c\n" +
            "where c.username = :username", nativeQuery = true)
    String getCandidateId(String username);

    @Query(value = "SELECT n.end_date, n.position, n.requirement, n.title, n.salary, n.start_date FROM ooad.news n\n" +
            "join ooad.candidate_news cn\n" +
            "where cn.id_news = n.id\n" +
            "and cn.id_candidate = :id", nativeQuery = true)
    List<NewsResult> getCandidateNews(Long id);
}
