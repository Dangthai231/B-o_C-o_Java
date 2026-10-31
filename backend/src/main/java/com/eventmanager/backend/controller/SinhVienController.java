package com.eventmanager.backend.controller;

import com.eventmanager.backend.entity.SinhVien;
import com.eventmanager.backend.repository.SinhVienRepository;
import com.eventmanager.backend.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Use case "Quản lý thông tin cá nhân": xem và cập nhật thông tin.
 */
@RestController
@RequestMapping("/api/sinh-vien")
public class SinhVienController {

    @Autowired
    private SinhVienRepository repo;

    @Autowired
    private TaiKhoanRepository taiKhoanRepo;

    // GET /api/sinh-vien/thong-tin?tenDangNhap=hocsinh01
    @GetMapping("/thong-tin")
    public ResponseEntity<?> getThongTin(@RequestParam String tenDangNhap) {
        if (taiKhoanRepo.findByTenDangNhap(tenDangNhap).isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Tài khoản không tồn tại"));
        }
        // Tài khoản chưa có hồ sơ thì tạo hồ sơ trống
        SinhVien sv = repo.findByTenDangNhap(tenDangNhap).orElseGet(() -> {
            SinhVien moi = new SinhVien();
            moi.setTenDangNhap(tenDangNhap);
            return repo.save(moi);
        });
        return ResponseEntity.ok(sv);
    }

    // PUT /api/sinh-vien/cap-nhat?tenDangNhap=hocsinh01   Body: {"hoTen": "...", "email": "...", ...}
    @PutMapping("/cap-nhat")
    public ResponseEntity<?> capNhat(@RequestParam String tenDangNhap, @RequestBody SinhVien moi) {
        SinhVien sv = repo.findByTenDangNhap(tenDangNhap).orElse(null);
        if (sv == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Không tìm thấy hồ sơ sinh viên"));
        }
        if (moi.getHoTen() == null || moi.getHoTen().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Họ tên không được để trống"));
        }
        if (moi.getEmail() != null && !moi.getEmail().isBlank() && !moi.getEmail().matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email không hợp lệ"));
        }
        if (moi.getSoDienThoai() != null && !moi.getSoDienThoai().isBlank() && !moi.getSoDienThoai().matches("^0\\d{9}$")) {
            return ResponseEntity.badRequest().body(Map.of("message", "Số điện thoại phải gồm 10 chữ số, bắt đầu bằng 0"));
        }

        // Không cho sửa tenDangNhap
        sv.setMaSinhVien(moi.getMaSinhVien());
        sv.setHoTen(moi.getHoTen().trim());
        sv.setNgaySinh(moi.getNgaySinh());
        sv.setLop(moi.getLop());
        sv.setEmail(moi.getEmail());
        sv.setSoDienThoai(moi.getSoDienThoai());
        return ResponseEntity.ok(repo.save(sv));
    }
}
