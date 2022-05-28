package com.example.healthy_first_ver1.repository.result;

import java.time.LocalDate;

public interface ResUnsafeResult {
    String getTenNhaHang();
    Long getMaChungNhan();
    LocalDate getNgayHetHan();
    String getType();
    String getDistrict();
}
