package com.eventmanager.backend.controller;

import com.eventmanager.backend.entity.ThongBao;
import com.eventmanager.backend.repository.ThongBaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thong-bao")
public class ThongBaoController {

    @Autowired
    private ThongBaoRepository repo;

    // Đổ dữ liệu lên JTable
    @GetMapping("/danh-sach")
    public List<ThongBao> getAll() {
        return repo.findAll();
    }

    // Nhận dữ liệu từ form khi ấn nút Gửi / Lưu Nháp
    @PostMapping("/tao-moi")
    public ResponseEntity<ThongBao> taoThongBao(@RequestBody ThongBao tb) {
        ThongBao saved = repo.save(tb);
        return ResponseEntity.ok(saved);
    }

    // Cập nhật thông báo
    @PutMapping("/cap-nhat/{id}")
    public ResponseEntity<ThongBao> capNhatThongBao(@PathVariable Long id, @RequestBody ThongBao tbDetails) {
        return repo.findById(id).map(tb -> {
            tb.setTieuDe(tbDetails.getTieuDe());
            tb.setNoiDung(tbDetails.getNoiDung());
            tb.setDoiTuong(tbDetails.getDoiTuong());
            tb.setNgayGui(tbDetails.getNgayGui());
            tb.setTrangThai(tbDetails.getTrangThai());
            return ResponseEntity.ok(repo.save(tb));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Xóa thông báo
    @DeleteMapping("/xoa/{id}")
    public ResponseEntity<Void> xoaThongBao(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}