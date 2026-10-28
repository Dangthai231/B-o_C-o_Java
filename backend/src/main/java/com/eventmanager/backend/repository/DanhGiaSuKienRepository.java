package com.eventmanager.backend.repository;

import com.eventmanager.backend.entity.DanhGiaSuKien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DanhGiaSuKienRepository extends JpaRepository<DanhGiaSuKien, Long> {
    List<DanhGiaSuKien> findBySuKienIdOrderByNgayDanhGiaDesc(Long suKienId);

    List<DanhGiaSuKien> findByTenDangNhapOrderByNgayDanhGiaDesc(String tenDangNhap);

    Optional<DanhGiaSuKien> findBySuKienIdAndTenDangNhap(Long suKienId, String tenDangNhap);
}
