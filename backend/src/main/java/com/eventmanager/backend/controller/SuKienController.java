package com.eventmanager.backend.controller;

import com.eventmanager.backend.entity.SuKien;
import com.eventmanager.backend.repository.DangKySuKienRepository;
import com.eventmanager.backend.repository.SuKienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Use case "Tra cứu sự kiện": xem danh sách, tìm kiếm, lọc theo ngày, xem chi tiết.
 */
@RestController
@RequestMapping("/api/su-kien")
public class SuKienController {

    @Autowired
    private SuKienRepository repo;

    @Autowired
    private DangKySuKienRepository dangKyRepo;

    // Xem danh sách + tìm kiếm + lọc theo ngày (tham số nào bỏ trống thì không lọc theo nó)
    // VD: GET /api/su-kien/danh-sach?tuKhoa=hiến máu&tuNgay=2026-11-01&denNgay=2026-11-30
    @GetMapping("/danh-sach")
    public List<SuKien> getDanhSach(
            @RequestParam(defaultValue = "") String tuKhoa,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tuNgay,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate denNgay) {

        LocalDateTime tu = tuNgay != null ? tuNgay.atStartOfDay() : LocalDateTime.of(1900, 1, 1, 0, 0);
        // hết ngày denNgay = đầu ngày hôm sau trừ 1 giây
        LocalDateTime den = denNgay != null ? denNgay.plusDays(1).atStartOfDay().minusSeconds(1)
                : LocalDateTime.of(2999, 12, 31, 23, 59);

        return repo.findByTenSuKienContainingIgnoreCaseAndThoiGianBatDauBetweenOrderByThoiGianBatDauAsc(
                tuKhoa.trim(), tu, den);
    }

    // Xem chi tiết sự kiện, kèm số người đã đăng ký
    @GetMapping("/{id}")
    public ResponseEntity<?> getChiTiet(@PathVariable Long id) {
        SuKien sk = repo.findById(id).orElse(null);
        if (sk == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Không tìm thấy sự kiện"));
        }

        long soDaDangKy = dangKyRepo.countBySuKienIdAndTrangThaiNot(id, DangKyController.DA_HUY);

        Map<String, Object> kq = new HashMap<>();
        kq.put("suKien", sk);
        kq.put("soDaDangKy", soDaDangKy);
        return ResponseEntity.ok(kq);
    }
}
