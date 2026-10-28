package com.eventmanager.backend;

import com.eventmanager.backend.controller.DangKyController;
import com.eventmanager.backend.entity.DangKySuKien;
import com.eventmanager.backend.entity.SinhVien;
import com.eventmanager.backend.entity.SuKien;
import com.eventmanager.backend.repository.DangKySuKienRepository;
import com.eventmanager.backend.repository.SinhVienRepository;
import com.eventmanager.backend.repository.SuKienRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Tạo dữ liệu mẫu cho phần Sinh viên (sự kiện, hồ sơ sinh viên, vài lượt đăng ký)
 * khi các bảng còn trống. Thời gian tính theo ngày chạy để luôn có sự kiện cũ và sắp tới.
 */
@Component
public class SuKienSeeder implements CommandLineRunner {

    private final SuKienRepository suKienRepo;
    private final SinhVienRepository sinhVienRepo;
    private final DangKySuKienRepository dangKyRepo;

    public SuKienSeeder(SuKienRepository suKienRepo, SinhVienRepository sinhVienRepo, DangKySuKienRepository dangKyRepo) {
        this.suKienRepo = suKienRepo;
        this.sinhVienRepo = sinhVienRepo;
        this.dangKyRepo = dangKyRepo;
    }

    @Override
    public void run(String... args) {
        if (suKienRepo.count() == 0) {
            LocalDateTime now = LocalDateTime.now().withHour(8).withMinute(0).withSecond(0).withNano(0);
            SuKien sk1 = taoSuKien("SK001", "Ngày hội hiến máu tình nguyện", "Sảnh nhà A - EPU", now.minusDays(20), 200, "Đã kết thúc");
            SuKien sk2 = taoSuKien("SK002", "Hội thảo AI và ứng dụng", "Hội trường tầng 5 nhà M", now.minusDays(7), 150, "Đã kết thúc");
            taoSuKien("SK003", "Giải bóng đá Liên chi đoàn CNTT", "Sân bóng EPU", now.plusDays(5), 120, "Sắp diễn ra");
            SuKien sk4 = taoSuKien("SK004", "Workshop Lập trình Java Spring Boot", "Phòng máy B305", now.plusDays(10), 40, "Sắp diễn ra");
            taoSuKien("SK005", "Chiến dịch Mùa hè xanh - tập huấn", "Hội trường nhà E", now.plusDays(18), 100, "Sắp diễn ra");
            taoSuKien("SK006", "Cuộc thi Olympic Tin học", "Phòng máy B301-B303", now.plusDays(25), 60, "Sắp diễn ra");
            taoSuKien("SK007", "Đêm nhạc chào tân sinh viên", "Sân trường EPU", now.plusDays(3), 2, "Sắp diễn ra"); // ít chỗ để thử "đủ số lượng"

            // hocsinh01 đã tham gia 2 sự kiện cũ (có thể đánh giá) và đăng ký 1 sự kiện sắp tới (có thể hủy)
            taoDangKy(sk1.getId(), "hocsinh01", now.minusDays(25), "Đã tham gia");
            taoDangKy(sk2.getId(), "hocsinh01", now.minusDays(10), "Đã tham gia");
            taoDangKy(sk4.getId(), "hocsinh01", now.minusDays(1), DangKyController.DA_DANG_KY);
            System.out.println("[SuKienSeeder] Đã tạo 7 sự kiện mẫu và 3 lượt đăng ký cho hocsinh01.");
        }

        if (sinhVienRepo.count() == 0) {
            String[] hoTen = {"Nguyễn Văn An", "Trần Thị Bình", "Lê Hoàng Cường", "Phạm Thị Dung", "Võ Minh Đức",
                    "Bùi Thị Em", "Đặng Văn Phúc", "Hoàng Thị Giang", "Ngô Văn Hùng", "Vũ Thị Hương",
                    "Đỗ Văn Khánh", "Nguyễn Thị Lan", "Trần Văn Minh", "Lê Thị Mai"};
            for (int i = 1; i <= hoTen.length; i++) {
                SinhVien sv = new SinhVien();
                sv.setTenDangNhap("hocsinh" + String.format("%02d", i));
                sv.setMaSinhVien("2281" + String.format("%06d", 10000 + i));
                sv.setHoTen(hoTen[i - 1]);
                sv.setNgaySinh(LocalDate.of(2004, (i % 12) + 1, (i % 28) + 1));
                sv.setLop("D17CNPM" + ((i % 3) + 1));
                sv.setEmail("hocsinh" + String.format("%02d", i) + "@epu.edu.vn");
                sv.setSoDienThoai("09012345" + String.format("%02d", i));
                sinhVienRepo.save(sv);
            }
            System.out.println("[SuKienSeeder] Đã tạo hồ sơ cho 14 sinh viên.");
        }
    }

    private SuKien taoSuKien(String ma, String ten, String diaDiem, LocalDateTime batDau, int soLuong, String trangThai) {
        SuKien sk = new SuKien();
        sk.setMaSuKien(ma);
        sk.setTenSuKien(ten);
        sk.setMoTa("Sự kiện do Ban Chấp hành Liên Chi đoàn Công nghệ thông tin - Trường Đại học Điện lực tổ chức.");
        sk.setDiaDiem(diaDiem);
        sk.setThoiGianBatDau(batDau);
        sk.setThoiGianKetThuc(batDau.plusHours(4));
        sk.setSoLuongToiDa(soLuong);
        sk.setDonViToChuc("Liên Chi đoàn CNTT");
        sk.setTrangThai(trangThai);
        return suKienRepo.save(sk);
    }

    private void taoDangKy(Long suKienId, String tenDangNhap, LocalDateTime ngay, String trangThai) {
        DangKySuKien dk = new DangKySuKien();
        dk.setSuKienId(suKienId);
        dk.setTenDangNhap(tenDangNhap);
        dk.setNgayDangKy(ngay);
        dk.setTrangThai(trangThai);
        dangKyRepo.save(dk);
    }
}
