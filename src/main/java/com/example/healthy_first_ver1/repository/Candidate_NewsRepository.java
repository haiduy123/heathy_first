package com.example.healthy_first_ver1.repository;

import com.example.healthy_first_ver1.entity.Candidate_News;
import com.example.healthy_first_ver1.repository.result.CheckApplyResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface Candidate_NewsRepository extends CrudRepository<Candidate_News, Long> {
    @Query(value = "SELECT cn.id_candidate as CandidateId, cn.id_news as NewsId FROM ooad.candidate_news cn\n" +
            "where cn.id_candidate = :can_id\n" +
            "and cn.id_news = :news_id", nativeQuery = true)
    CheckApplyResult checkCandidateApply(Long can_id, Long news_id);
}
