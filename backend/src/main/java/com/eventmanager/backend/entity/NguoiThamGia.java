package com.eventmanager.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "nguoi_tham_gia")
public class NguoiThamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String maHocSinh;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String vaiTro; // VD: Sinh viên, Lớp trưởng, Giảng viên
    private String trangThaiDiemDanh;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMaHocSinh() { return maHocSinh; }
    public void setMaHocSinh(String maHocSinh) { this.maHocSinh = maHocSinh; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }

    public String getTrangThaiDiemDanh() { return trangThaiDiemDanh; }
    public void setTrangThaiDiemDanh(String trangThaiDiemDanh) { this.trangThaiDiemDanh = trangThaiDiemDanh; }
}