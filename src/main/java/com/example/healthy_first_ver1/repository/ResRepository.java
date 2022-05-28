package com.example.healthy_first_ver1.repository;

import com.example.healthy_first_ver1.entity.Restaurant;
import com.example.healthy_first_ver1.repository.result.ResRecommendResult;
import com.example.healthy_first_ver1.repository.result.ResSafeResult;
import com.example.healthy_first_ver1.repository.result.ResUnsafeResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResRepository extends JpaRepository<Restaurant, Long> {

    // query để lấy ra danh sách các nhà hàng còn thời hạn giấy chứng nhận
    @Query(value = "select r.name as tenNhaHang, c.id as maChungNhan, datediff(c.end_date, now()) as ngayHetHan, r.type as Type, r.district as District\n" +
            "from web_project.certificates c\n" +
            "inner join web_project.restaurants r\n" +
            "on r.cert_id = c.id\n" +
            "where datediff(c.end_date, now()) > 0", nativeQuery = true)
    List<ResSafeResult> getRestaurantSafe();

    // query để lấy ra danh sách các nhà hàng hết hạn giấy chứng nhận
    @Query(value = "select r.name as tenNhaHang, c.id as maChungNhan, c.end_date as ngayHetHan, r.type as Type, r.district as District\n" +
            "from web_project.certificates c\n" +
            "join web_project.restaurants r\n" +
            "on r.cert_id = c.id\n" +
            "where c.end_date < now()", nativeQuery = true)
    List<ResUnsafeResult> getRestaurantUnsafe();

    @Query(value = "select r.name as tenNhaHang, c.id as maChungNhan, r.type as Type, r.district as District, datediff(c.end_date, now()) as ngayHetHan\n" +
            "from web_project.certificates c\n" +
            "join web_project.restaurants r\n" +
            "on r.cert_id = c.id\n" +
            "where datediff(c.end_date, now()) < 10\n" +
            "and datediff(c.end_date, now()) > 0", nativeQuery = true)
    List<ResRecommendResult> getRestaurantRecommend();

    Restaurant findByName(String name);
}
