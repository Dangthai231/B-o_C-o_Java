package com.eventmanager.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Một sinh viên đăng ký một sự kiện. Mỗi cặp (suKienId, tenDangNhap) chỉ có 1 dòng;
 * hủy rồi đăng ký lại thì cập nhật lại dòng cũ.
 */
@Entity
@Table(name = "dang_ky_su_kien",
        uniqueConstraints = @UniqueConstraint(columnNames = {"su_kien_id", "ten_dang_nhap"}))
public class DangKySuKien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "su_kien_id", nullable = false)
    private Long suKienId;

    @Column(name = "ten_dang_nhap", nullable = false)
    private String tenDangNhap; // tài khoản sinh viên, VD: hocsinh01

    private LocalDateTime ngayDangKy;
    private String trangThai; // "Đã đăng ký", "Đã hủy", "Đã tham gia", "Vắng mặt"

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSuKienId() { return suKienId; }
    public void setSuKienId(Long suKienId) { this.suKienId = suKienId; }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public LocalDateTime getNgayDangKy() { return ngayDangKy; }
    public void setNgayDangKy(LocalDateTime ngayDangKy) { this.ngayDangKy = ngayDangKy; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
