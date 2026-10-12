package com.eventmanager.backend.repository;

import com.eventmanager.backend.entity.TaiLieuSuKien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaiLieuRepository extends JpaRepository<TaiLieuSuKien, Long> {
    // Có thể thêm phương thức tìm kiếm theo tên nếu cần
    // List<TaiLieuSuKien> findByTenTaiLieuContaining(String tenTaiLieu);
}