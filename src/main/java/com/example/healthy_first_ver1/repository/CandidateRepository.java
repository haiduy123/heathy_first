package com.example.healthy_first_ver1.repository;

import com.example.healthy_first_ver1.entity.Candidate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CandidateRepository extends CrudRepository<Candidate, Long> {
    @Query(value = "select id from ooad.candidate c\n" +
            "where c.username = :username", nativeQuery = true)
    String getCandidateId(String username);
}
