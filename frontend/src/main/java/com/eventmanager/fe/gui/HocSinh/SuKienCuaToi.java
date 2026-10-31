package com.eventmanager.fe.gui.HocSinh;

import com.eventmanager.fe.api.ApiClient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Use case "Đăng ký tham gia sự kiện": xem thông tin đăng ký, hủy đăng ký, xem trạng thái tham gia.
 */
public class SuKienCuaToi {
    private final String tenDangNhap;

    private JPanel mainPanel;
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblThongKe;

    // Cột 0 = id đăng ký (ẩn), cột 1 = id sự kiện (ẩn)
    private static final int COT_ID_DANG_KY = 0;
    private static final int COT_ID_SU_KIEN = 1;
    private static final int COT_BAT_DAU_ISO = 2; // (ẩn) dùng để kiểm tra đã bắt đầu chưa
    private static final int COT_TRANG_THAI = 8;

    public SuKienCuaToi(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
        initUI();
        taiDuLieu();
    }

    private void initUI() {
        mainPanel = HocSinhUI.taoTrang("Sự kiện đã đăng ký");

        model = HocSinhUI.taoModel("ID", "ID sự kiện", "Bắt đầu ISO",
                "Mã", "Tên sự kiện", "Bắt đầu", "Địa điểm", "Ngày đăng ký", "Trạng thái");
        table = HocSinhUI.taoBang(model);
        HocSinhUI.anCot(table, 3);
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        mainPanel.add(HocSinhUI.cuon(table), BorderLayout.CENTER);

        JPanel duoi = new JPanel(new BorderLayout());
        duoi.setBackground(Color.WHITE);
        lblThongKe = new JLabel(" ");
        lblThongKe.setFont(HocSinhUI.FONT);
        JPanel nut = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        nut.setBackground(Color.WHITE);
        JButton btnLamMoi = HocSinhUI.taoNut("Làm mới");
        JButton btnChiTiet = HocSinhUI.taoNut("Xem chi tiết");
        JButton btnHuy = HocSinhUI.taoNutChinh("Hủy đăng ký");
        btnHuy.setBackground(new Color(211, 47, 47));
        nut.add(btnLamMoi);
        nut.add(btnChiTiet);
        nut.add(btnHuy);
        duoi.add(lblThongKe, BorderLayout.WEST);
        duoi.add(nut, BorderLayout.EAST);
        mainPanel.add(duoi, BorderLayout.SOUTH);

        btnLamMoi.addActionListener(e -> taiDuLieu());
        btnChiTiet.addActionListener(e -> {
            Object id = HocSinhUI.giaTriDongChon(table, COT_ID_SU_KIEN);
            if (id == null) {
                JOptionPane.showMessageDialog(mainPanel, "Hãy chọn một dòng");
                return;
            }
            HocSinhUI.hienChiTietSuKien(mainPanel, (Long) id, tenDangNhap);
        });
        btnHuy.addActionListener(e -> huyDangKy());
    }

    /** GET /api/dang-ky/cua-toi?tenDangNhap=... */
    public void taiDuLieu() {
        String url = "/api/dang-ky/cua-toi?tenDangNhap=" + ApiClient.encode(tenDangNhap);
        HocSinhUI.chayNen(mainPanel, () -> ApiClient.get(url).getAsJsonArray(), arr -> {
            model.setRowCount(0);
            int dangKy = 0, thamGia = 0, huy = 0;
            for (JsonElement el : arr) {
                JsonObject o = el.getAsJsonObject();
                String tt = HocSinhUI.str(o, "trangThai");
                switch (tt) {
                    case "Đã đăng ký" -> dangKy++;
                    case "Đã tham gia" -> thamGia++;
                    case "Đã hủy" -> huy++;
                    default -> { }
                }
                model.addRow(new Object[]{
                        HocSinhUI.num(o, "id"),
                        HocSinhUI.num(o, "suKienId"),
                        HocSinhUI.str(o, "thoiGianBatDau"),
                        HocSinhUI.str(o, "maSuKien"),
                        HocSinhUI.str(o, "tenSuKien"),
                        HocSinhUI.thoiGian(HocSinhUI.str(o, "thoiGianBatDau")),
                        HocSinhUI.str(o, "diaDiem"),
                        HocSinhUI.thoiGian(HocSinhUI.str(o, "ngayDangKy")),
                        tt
                });
            }
            lblThongKe.setText("Đã đăng ký: " + dangKy + "   |   Đã tham gia: " + thamGia + "   |   Đã hủy: " + huy);
        });
    }

    /** PUT /api/dang-ky/huy/{id}?tenDangNhap=... */
    private void huyDangKy() {
        Object id = HocSinhUI.giaTriDongChon(table, COT_ID_DANG_KY);
        if (id == null) {
            JOptionPane.showMessageDialog(mainPanel, "Hãy chọn đăng ký muốn hủy");
            return;
        }
        // Kiểm tra sơ bộ ở FE cho thân thiện; backend vẫn kiểm tra lại
        if (!"Đã đăng ký".equals(HocSinhUI.giaTriDongChon(table, COT_TRANG_THAI))) {
            JOptionPane.showMessageDialog(mainPanel, "Chỉ hủy được đăng ký đang ở trạng thái \"Đã đăng ký\"");
            return;
        }
        if (HocSinhUI.daBatDau((String) HocSinhUI.giaTriDongChon(table, COT_BAT_DAU_ISO))) {
            JOptionPane.showMessageDialog(mainPanel, "Sự kiện đã bắt đầu, không thể hủy");
            return;
        }
        if (JOptionPane.showConfirmDialog(mainPanel, "Bạn chắc chắn muốn hủy đăng ký?", "Xác nhận",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }

        String url = "/api/dang-ky/huy/" + id + "?tenDangNhap=" + ApiClient.encode(tenDangNhap);
        HocSinhUI.chayNen(mainPanel, () -> ApiClient.put(url, null), kq -> {
            JOptionPane.showMessageDialog(mainPanel, "Đã hủy đăng ký");
            taiDuLieu();
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
