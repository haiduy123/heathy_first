package com.example.healthy_first_ver1.repository;

import com.example.healthy_first_ver1.entity.News;
import com.example.healthy_first_ver1.repository.result.NewsResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends CrudRepository<News, Long> {
    @Query(value = "SELECT n.id as id,n.salary as salary, n.position as position, c.address as address, datediff(n.end_date,now()) as dayLeft, c.name as companyName, c.introduce as introduce\n" +
            "FROM ooad_topcv.news n \n" +
            "left join ooad_topcv.company c\n" +
            "on n.id_company = c.id", nativeQuery = true)
    List<NewsResult> getNewsInfo();
}
