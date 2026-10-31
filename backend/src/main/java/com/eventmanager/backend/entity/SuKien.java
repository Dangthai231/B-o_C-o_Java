package com.eventmanager.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "su_kien")
public class SuKien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String maSuKien; // VD: SK001

    private String tenSuKien;

    @Column(columnDefinition = "TEXT")
    private String moTa;

    private String diaDiem;
    private LocalDateTime thoiGianBatDau;
    private LocalDateTime thoiGianKetThuc;
    private Integer soLuongToiDa;
    private String donViToChuc;
    private String trangThai; // "Sắp diễn ra", "Đang diễn ra", "Đã kết thúc", "Đã hủy"

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMaSuKien() { return maSuKien; }
    public void setMaSuKien(String maSuKien) { this.maSuKien = maSuKien; }

    public String getTenSuKien() { return tenSuKien; }
    public void setTenSuKien(String tenSuKien) { this.tenSuKien = tenSuKien; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public String getDiaDiem() { return diaDiem; }
    public void setDiaDiem(String diaDiem) { this.diaDiem = diaDiem; }

    public LocalDateTime getThoiGianBatDau() { return thoiGianBatDau; }
    public void setThoiGianBatDau(LocalDateTime thoiGianBatDau) { this.thoiGianBatDau = thoiGianBatDau; }

    public LocalDateTime getThoiGianKetThuc() { return thoiGianKetThuc; }
    public void setThoiGianKetThuc(LocalDateTime thoiGianKetThuc) { this.thoiGianKetThuc = thoiGianKetThuc; }

    public Integer getSoLuongToiDa() { return soLuongToiDa; }
    public void setSoLuongToiDa(Integer soLuongToiDa) { this.soLuongToiDa = soLuongToiDa; }

    public String getDonViToChuc() { return donViToChuc; }
    public void setDonViToChuc(String donViToChuc) { this.donViToChuc = donViToChuc; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
