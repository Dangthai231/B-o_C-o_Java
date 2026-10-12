package com.eventmanager.backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TrangChuRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Đếm số lượng record trong một bảng
    public int getSoLuong(String tenBang) {
        String sql = "SELECT COUNT(*) FROM " + tenBang;
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            return 0; // Trả về 0 nếu bảng không tồn tại hoặc lỗi
        }
    }
}