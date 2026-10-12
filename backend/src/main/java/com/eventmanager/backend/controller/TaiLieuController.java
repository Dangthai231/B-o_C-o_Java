package com.eventmanager.backend.controller;

import com.eventmanager.backend.entity.TaiLieuSuKien;
import com.eventmanager.backend.repository.TaiLieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tai-lieu")
public class TaiLieuController {

    @Autowired
    private TaiLieuRepository repo;

    // API lấy danh sách tài liệu đổ lên JTable
    @GetMapping("/danh-sach")
    public List<TaiLieuSuKien> getDanhSach() {
        return repo.findAll();
    }

    // API thêm tài liệu mới (Demo việc import)
    @PostMapping("/them")
    public ResponseEntity<TaiLieuSuKien> themTaiLieu(@RequestBody TaiLieuSuKien taiLieu) {
        TaiLieuSuKien saved = repo.save(taiLieu);
        return ResponseEntity.ok(saved);
    }
}