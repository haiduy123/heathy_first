package com.example.healthy_first_ver1.repository;

import com.example.healthy_first_ver1.entity.CheckPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<CheckPlan, Long> {
}
