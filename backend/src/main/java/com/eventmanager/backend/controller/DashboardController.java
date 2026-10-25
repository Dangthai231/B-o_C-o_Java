package com.eventmanager.backend.controller;

import com.eventmanager.backend.repository.TrangChuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/thongke")
public class DashboardController {

    @Autowired
    private TrangChuRepository repo;

    @GetMapping("/tong-quan")
    public ResponseEntity<Map<String, Object>> getTongQuanDashboard() {
        return ResponseEntity.ok(repo.getThongKeTongQuan());
    }

    @GetMapping("/bao-cao")
    public ResponseEntity<Map<String, Object>> getBaoCaoThongKe() {
        return ResponseEntity.ok(repo.getThongKeBaoCao());
    }
}
