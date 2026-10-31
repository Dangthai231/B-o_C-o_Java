package com.eventmanager.backend.controller;

import com.eventmanager.backend.entity.DangKySuKien;
import com.eventmanager.backend.entity.DanhGiaSuKien;
import com.eventmanager.backend.entity.SuKien;
import com.eventmanager.backend.repository.DangKySuKienRepository;
import com.eventmanager.backend.repository.DanhGiaSuKienRepository;
import com.eventmanager.backend.repository.SuKienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Use case "Đánh giá và phản hồi sự kiện".
 */
@RestController
@RequestMapping("/api/danh-gia")
public class DanhGiaController {

    @Autowired
    private DanhGiaSuKienRepository repo;

    @Autowired
    private DangKySuKienRepository dangKyRepo;

    @Autowired
    private SuKienRepository suKienRepo;

    // Gửi (hoặc sửa) đánh giá. Body: {"suKienId":1, "tenDangNhap":"hocsinh01", "soSao":5, "noiDung":"..."}
    @PostMapping("/gui")
    public ResponseEntity<?> guiDanhGia(@RequestBody Map<String, Object> body) {
        if (body.get("suKienId") == null || body.get("tenDangNhap") == null || body.get("soSao") == null) {
            return loi("Thiếu thông tin đánh giá");
        }
        Long suKienId = ((Number) body.get("suKienId")).longValue();
        String tenDangNhap = body.get("tenDangNhap").toString();
        int soSao = ((Number) body.get("soSao")).intValue();
        String noiDung = body.get("noiDung") != null ? body.get("noiDung").toString().trim() : "";

        if (soSao < 1 || soSao > 5) {
            return loi("Số sao phải từ 1 đến 5");
        }

        SuKien sk = suKienRepo.findById(suKienId).orElse(null);
        if (sk == null) {
            return loi("Sự kiện không tồn tại");
        }
        DangKySuKien dk = dangKyRepo.findBySuKienIdAndTenDangNhap(suKienId, tenDangNhap).orElse(null);
        if (dk == null || DangKyController.DA_HUY.equals(dk.getTrangThai())) {
            return loi("Bạn chưa đăng ký sự kiện này nên không thể đánh giá");
        }
        if (sk.getThoiGianBatDau() != null && sk.getThoiGianBatDau().isAfter(LocalDateTime.now())) {
            return loi("Sự kiện chưa diễn ra, chưa thể đánh giá");
        }

        // Mỗi sinh viên 1 đánh giá / sự kiện: gửi lại thì cập nhật
        DanhGiaSuKien dg = repo.findBySuKienIdAndTenDangNhap(suKienId, tenDangNhap).orElse(new DanhGiaSuKien());
        dg.setSuKienId(suKienId);
        dg.setTenDangNhap(tenDangNhap);
        dg.setSoSao(soSao);
        dg.setNoiDung(noiDung);
        dg.setNgayDanhGia(LocalDateTime.now());
        return ResponseEntity.ok(repo.save(dg));
    }

    // Các đánh giá sinh viên đã gửi. GET /api/danh-gia/cua-toi?tenDangNhap=hocsinh01
    @GetMapping("/cua-toi")
    public List<Map<String, Object>> getCuaToi(@RequestParam String tenDangNhap) {
        List<Map<String, Object>> kq = new ArrayList<>();
        for (DanhGiaSuKien dg : repo.findByTenDangNhapOrderByNgayDanhGiaDesc(tenDangNhap)) {
            Map<String, Object> row = new HashMap<>();
            row.put("id", dg.getId());
            row.put("suKienId", dg.getSuKienId());
            row.put("tenSuKien", suKienRepo.findById(dg.getSuKienId()).map(SuKien::getTenSuKien).orElse(""));
            row.put("soSao", dg.getSoSao());
            row.put("noiDung", dg.getNoiDung());
            row.put("ngayDanhGia", dg.getNgayDanhGia());
            kq.add(row);
        }
        return kq;
    }

    // Tổng hợp đánh giá của 1 sự kiện. GET /api/danh-gia/su-kien/1
    @GetMapping("/su-kien/{suKienId}")
    public Map<String, Object> getTheoSuKien(@PathVariable Long suKienId) {
        List<DanhGiaSuKien> ds = repo.findBySuKienIdOrderByNgayDanhGiaDesc(suKienId);
        double tb = ds.stream().mapToInt(DanhGiaSuKien::getSoSao).average().orElse(0);

        Map<String, Object> kq = new HashMap<>();
        kq.put("soLuot", ds.size());
        kq.put("diemTrungBinh", Math.round(tb * 10) / 10.0);
        kq.put("danhSach", ds);
        return kq;
    }

    private ResponseEntity<Map<String, String>> loi(String message) {
        return ResponseEntity.badRequest().body(Map.of("message", message));
    }
}
