package com.eventmanager.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cong_viec")
public class CongViec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String maCv;
    private String tenCongViec;
    private String trangThai;
    private Integer tienDo;
    private String nhanSu;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMaCv() { return maCv; }
    public void setMaCv(String maCv) { this.maCv = maCv; }

    public String getTenCongViec() { return tenCongViec; }
    public void setTenCongViec(String tenCongViec) { this.tenCongViec = tenCongViec; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public Integer getTienDo() { return tienDo; }
    public void setTienDo(Integer tienDo) { this.tienDo = tienDo; }

    public String getNhanSu() { return nhanSu; }
    public void setNhanSu(String nhanSu) { this.nhanSu = nhanSu; }
}