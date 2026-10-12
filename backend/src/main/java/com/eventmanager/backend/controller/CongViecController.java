package com.eventmanager.backend.controller;

import com.eventmanager.backend.entity.CongViec;
import com.eventmanager.backend.repository.CongViecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cong-viec")
public class CongViecController {

    @Autowired
    private CongViecRepository repo;

    // Lấy danh sách công việc đổ lên JTable
    @GetMapping("/danh-sach")
    public List<CongViec> getDanhSach() {
        return repo.findAll();
    }

    // Thêm công việc mới
    @PostMapping("/them")
    public ResponseEntity<CongViec> themCongViec(@RequestBody CongViec cv) {
        CongViec saved = repo.save(cv);
        return ResponseEntity.ok(saved);
    }

    // Cập nhật công việc
    @PutMapping("/cap-nhat/{id}")
    public ResponseEntity<CongViec> capNhat(@PathVariable Long id, @RequestBody CongViec cvDetails) {
        return repo.findById(id).map(cv -> {
            cv.setMaCv(cvDetails.getMaCv());
            cv.setTenCongViec(cvDetails.getTenCongViec());
            cv.setTrangThai(cvDetails.getTrangThai());
            cv.setTienDo(cvDetails.getTienDo());
            cv.setNhanSu(cvDetails.getNhanSu());
            return ResponseEntity.ok(repo.save(cv));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Xóa công việc
    @DeleteMapping("/xoa/{id}")
    public ResponseEntity<Void> xoa(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
