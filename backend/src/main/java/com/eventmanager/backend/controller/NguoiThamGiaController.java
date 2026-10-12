package com.eventmanager.backend.controller;

import com.eventmanager.backend.entity.NguoiThamGia;
import com.eventmanager.backend.repository.NguoiThamGiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nguoi-tham-gia")
public class NguoiThamGiaController {

    @Autowired
    private NguoiThamGiaRepository repo;

    // Lấy danh sách người tham gia đổ lên JTable
    @GetMapping("/danh-sach")
    public List<NguoiThamGia> getDanhSach() {
        return repo.findAll();
    }

    // Thêm người tham gia mới
    @PostMapping("/them")
    public ResponseEntity<NguoiThamGia> themNguoiThamGia(@RequestBody NguoiThamGia ntg) {
        NguoiThamGia saved = repo.save(ntg);
        return ResponseEntity.ok(saved);
    }

    // Cập nhật người tham gia
    @PutMapping("/cap-nhat/{id}")
    public ResponseEntity<NguoiThamGia> capNhat(@PathVariable Long id, @RequestBody NguoiThamGia ntgDetails) {
        return repo.findById(id).map(ntg -> {
            ntg.setMaHocSinh(ntgDetails.getMaHocSinh());
            ntg.setHoTen(ntgDetails.getHoTen());
            ntg.setEmail(ntgDetails.getEmail());
            ntg.setSoDienThoai(ntgDetails.getSoDienThoai());
            ntg.setVaiTro(ntgDetails.getVaiTro());
            ntg.setTrangThaiDiemDanh(ntgDetails.getTrangThaiDiemDanh());
            return ResponseEntity.ok(repo.save(ntg));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Xóa người tham gia
    @DeleteMapping("/xoa/{id}")
    public ResponseEntity<Void> xoa(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
