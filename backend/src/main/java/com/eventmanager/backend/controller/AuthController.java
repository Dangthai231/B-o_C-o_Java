package com.eventmanager.backend.controller;

import com.eventmanager.backend.entity.TaiKhoan;
import com.eventmanager.backend.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private TaiKhoanRepository repo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("tenDangNhap");
        String password = request.get("matKhau");

        Optional<TaiKhoan> tkOpt = repo.findByTenDangNhap(username);

        if (tkOpt.isPresent()) {
            TaiKhoan tk = tkOpt.get();
            // So sánh mật khẩu (Trong thực tế nên dùng BCrypt mã hóa, ở đây demo so sánh chuỗi thường)
            if (tk.getMatKhau().equals(password)) {
                // Đăng nhập đúng, trả về vai trò để FE điều hướng
                return ResponseEntity.ok(Map.of(
                        "status", "success",
                        "vaiTro", tk.getVaiTro()
                ));
            }
        }

        return ResponseEntity.status(401).body(Map.of("status", "error", "message", "Sai tài khoản hoặc mật khẩu"));
    }
}