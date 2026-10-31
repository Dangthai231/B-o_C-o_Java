package com.eventmanager.backend.repository;

import com.eventmanager.backend.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, Long> {
    Optional<SinhVien> findByTenDangNhap(String tenDangNhap);
}
