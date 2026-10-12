package com.eventmanager.backend.controller;

import com.eventmanager.backend.repository.TrangChuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/thongke")
public class DashboardController {

    @Autowired
    private TrangChuRepository repo;

    @GetMapping("/tong-quan")
    public ResponseEntity<Map<String, Integer>> getTongQuanDashboard() {
        Map<String, Integer> data = new HashMap<>();

        // Gọi DB lấy số liệu (tên bảng khớp với @Table trong entity)
        data.put("soSuKien", repo.getSoLuong("su_kien"));
        data.put("soCongViec", repo.getSoLuong("cong_viec"));
        data.put("soNguoiThamGia", repo.getSoLuong("nguoi_tham_gia"));
        data.put("soTaiLieu", repo.getSoLuong("tai_lieu_su_kien"));
        data.put("soThongBao", repo.getSoLuong("thong_bao"));
        data.put("soBaoCao", repo.getSoLuong("bao_cao"));

        // Spring Boot sẽ tự động chuyển Map này thành chuỗi JSON trả về cho Java Swing
        return ResponseEntity.ok(data);
    }
}