package com.eventmanager.backend.repository;

import com.eventmanager.backend.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Long> {
    // Tìm tài khoản theo username
    Optional<TaiKhoan> findByTenDangNhap(String tenDangNhap);
}