package com.example.healthy_first_ver1.entity;

import com.example.healthy_first_ver1.dto.CheckPlanDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "plans")
public class CheckPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDate start_date;

    @Column(name = "end_date")
    private LocalDate end_date;

    @Column
    private Long stageId;

    @Column
    private String status;

    @Column
    private Long restaurantId;

    public CheckPlanDto toDto() {
        return CheckPlanDto.builder()
                .id(id)
                .start_date(start_date)
                .end_date(end_date)
                .stageId(stageId)
                .status(status)
                .restaurantId(restaurantId)
                .build();
    }
}
