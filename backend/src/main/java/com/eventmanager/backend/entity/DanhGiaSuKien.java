package com.eventmanager.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "danh_gia_su_kien",
        uniqueConstraints = @UniqueConstraint(columnNames = {"su_kien_id", "ten_dang_nhap"}))
public class DanhGiaSuKien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "su_kien_id", nullable = false)
    private Long suKienId;

    @Column(name = "ten_dang_nhap", nullable = false)
    private String tenDangNhap;

    private Integer soSao; // 1 - 5

    @Column(columnDefinition = "TEXT")
    private String noiDung;

    private LocalDateTime ngayDanhGia;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSuKienId() { return suKienId; }
    public void setSuKienId(Long suKienId) { this.suKienId = suKienId; }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public Integer getSoSao() { return soSao; }
    public void setSoSao(Integer soSao) { this.soSao = soSao; }

    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }

    public LocalDateTime getNgayDanhGia() { return ngayDanhGia; }
    public void setNgayDanhGia(LocalDateTime ngayDanhGia) { this.ngayDanhGia = ngayDanhGia; }
}
