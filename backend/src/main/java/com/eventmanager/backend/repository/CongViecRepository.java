package com.eventmanager.backend.repository;

import com.eventmanager.backend.entity.CongViec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CongViecRepository extends JpaRepository<CongViec, Long> {
}