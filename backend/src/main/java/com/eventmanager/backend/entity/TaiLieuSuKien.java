package com.eventmanager.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tai_lieu_su_kien")
public class TaiLieuSuKien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenTaiLieu;
    private String loai;
    private String kichThuoc; // Lưu dạng chuỗi, vd: "245 KB"
    private LocalDate ngayTaiLen;
    private String nguoiTai;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTenTaiLieu() { return tenTaiLieu; }
    public void setTenTaiLieu(String tenTaiLieu) { this.tenTaiLieu = tenTaiLieu; }

    public String getLoai() { return loai; }
    public void setLoai(String loai) { this.loai = loai; }

    public String getKichThuoc() { return kichThuoc; }
    public void setKichThuoc(String kichThuoc) { this.kichThuoc = kichThuoc; }

    public LocalDate getNgayTaiLen() { return ngayTaiLen; }
    public void setNgayTaiLen(LocalDate ngayTaiLen) { this.ngayTaiLen = ngayTaiLen; }

    public String getNguoiTai() { return nguoiTai; }
    public void setNguoiTai(String nguoiTai) { this.nguoiTai = nguoiTai; }
}