package com.eventmanager.backend;

import com.eventmanager.backend.entity.TaiKhoan;
import com.eventmanager.backend.repository.TaiKhoanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Seeder tự động tạo 30 tài khoản mẫu khi ứng dụng khởi động (nếu bảng tai_khoan đang trống).
 * Bao gồm: 1 ADMIN, 15 CAN_BO, 14 HOC_SINH.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final TaiKhoanRepository taiKhoanRepository;

    public DataSeeder(TaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

    @Override
    public void run(String... args) {
        if (taiKhoanRepository.count() > 0) {
            System.out.println("[DataSeeder] Bảng tai_khoan đã có dữ liệu, bỏ qua seeding.");
            return;
        }

        List<TaiKhoan> danhSachTaiKhoan = new ArrayList<>();

        // 1. Tài khoản ADMIN
        TaiKhoan admin = new TaiKhoan();
        admin.setTenDangNhap("admin");
        admin.setMatKhau("admin123");
        admin.setVaiTro("ADMIN");
        danhSachTaiKhoan.add(admin);

        // 2. 15 tài khoản CÁN BỘ
        for (int i = 1; i <= 15; i++) {
            TaiKhoan cb = new TaiKhoan();
            cb.setTenDangNhap("canbo" + String.format("%02d", i));
            cb.setMatKhau("canbo123");
            cb.setVaiTro("CAN_BO");
            danhSachTaiKhoan.add(cb);
        }

        // 3. 14 tài khoản HỌC SINH
        for (int i = 1; i <= 14; i++) {
            TaiKhoan hs = new TaiKhoan();
            hs.setTenDangNhap("hocsinh" + String.format("%02d", i));
            hs.setMatKhau("hs123");
            hs.setVaiTro("HOC_SINH");
            danhSachTaiKhoan.add(hs);
        }

        taiKhoanRepository.saveAll(danhSachTaiKhoan);
        System.out.println("[DataSeeder] Đã tạo thành công " + danhSachTaiKhoan.size() + " tài khoản mẫu (1 ADMIN, 15 CAN_BO, 14 HOC_SINH).");
    }
}
