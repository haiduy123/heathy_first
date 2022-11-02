package com.example.healthy_first_ver1.repository;

import com.example.healthy_first_ver1.entity.Candidate;
import com.example.healthy_first_ver1.entity.News;
import com.example.healthy_first_ver1.repository.result.CandidateResult;
import com.example.healthy_first_ver1.repository.result.NewsResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends CrudRepository<News, Long> {
    @Query(value = "SELECT n.id as id,n.salary as salary, n.position as position, n.title, c.address as address, datediff(now(),n.end_date) as dayLeft, c.name as companyName, c.introduce as introduce\n" +
            "FROM ooad.news n \n" +
            "left join ooad.company c\n" +
            "on n.id_company = c.id", nativeQuery = true)
    List<NewsResult> getNewsInfo();

    @Query(value = "SELECT datediff(n.end_date,now()) as dayLeft, n.position, n.requirement, n.title, n.salary, n.start_date FROM ooad.news n\n" +
            "where id_company = :idCompany", nativeQuery = true)
    List<NewsResult> getCompanyNews(Long idCompany);

    @Query(value = "SELECT datediff(n.end_date,now()) as dayLeft, n.position, n.requirement, n.title, n.salary, n.start_date FROM ooad.news n" , nativeQuery = true)
    List<NewsResult> getCompanyNews();

    @Query(value = "SELECT c.id, c.file_cv as cv, c.name FROM ooad.candidate c\n" +
            "join ooad.candidate_news cn\n" +
            "where cn.id_candidate = c.id\n" +
            "and cn.id_news = :idNews", nativeQuery = true)
    List<CandidateResult> getCandidateByNews(Long idNews);
}
