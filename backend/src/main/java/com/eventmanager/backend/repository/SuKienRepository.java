package com.eventmanager.backend.repository;

import com.eventmanager.backend.entity.SuKien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SuKienRepository extends JpaRepository<SuKien, Long> {
    // Tìm theo tên (chứa từ khóa) và thời gian bắt đầu nằm trong khoảng [tu, den]
    // Spring Data tự sinh câu SQL từ tên hàm
    List<SuKien> findByTenSuKienContainingIgnoreCaseAndThoiGianBatDauBetweenOrderByThoiGianBatDauAsc(
            String tuKhoa, LocalDateTime tu, LocalDateTime den);
}
