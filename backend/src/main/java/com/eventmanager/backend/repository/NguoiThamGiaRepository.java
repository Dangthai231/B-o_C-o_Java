package com.eventmanager.backend.repository;

import com.eventmanager.backend.entity.NguoiThamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NguoiThamGiaRepository extends JpaRepository<NguoiThamGia, Long> {
}