package com.eventmanager.fe.gui.HocSinh;

import com.eventmanager.fe.api.ApiClient;
import com.google.gson.JsonObject;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Use case "Quản lý thông tin cá nhân": xem thông tin cá nhân, cập nhật thông tin.
 */
public class ThongTinCaNhan {
    private final String tenDangNhap;

    private JPanel mainPanel;
    private JTextField txtTenDangNhap, txtMaSv, txtHoTen, txtLop, txtEmail, txtSdt;
    private JDateChooser dcNgaySinh;

    public ThongTinCaNhan(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
        initUI();
        taiDuLieu();
    }

    private void initUI() {
        mainPanel = HocSinhUI.taoTrang("Thông tin cá nhân");

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        txtTenDangNhap = oNhap();
        txtTenDangNhap.setEditable(false); // không cho sửa tên đăng nhập
        txtMaSv = oNhap();
        txtHoTen = oNhap();
        dcNgaySinh = new JDateChooser();
        dcNgaySinh.setDateFormatString("dd/MM/yyyy");
        txtLop = oNhap();
        txtEmail = oNhap();
        txtSdt = oNhap();

        Object[][] dong = {
                {"Tên đăng nhập:", txtTenDangNhap},
                {"Mã sinh viên:", txtMaSv},
                {"Họ và tên (*):", txtHoTen},
                {"Ngày sinh:", dcNgaySinh},
                {"Lớp:", txtLop},
                {"Email:", txtEmail},
                {"Số điện thoại:", txtSdt},
        };
        for (int i = 0; i < dong.length; i++) {
            JLabel lbl = new JLabel((String) dong[i][0]);
            lbl.setFont(HocSinhUI.FONT);
            g.gridx = 0; g.gridy = i; g.weightx = 0;
            form.add(lbl, g);
            g.gridx = 1; g.weightx = 1;
            form.add((Component) dong[i][1], g);
        }

        JPanel nut = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        nut.setBackground(Color.WHITE);
        JButton btnHoanTac = HocSinhUI.taoNut("Hoàn tác");
        JButton btnLuu = HocSinhUI.taoNutChinh("Lưu thay đổi");
        nut.add(btnHoanTac);
        nut.add(btnLuu);
        g.gridx = 1; g.gridy = dong.length;
        form.add(nut, g);

        // Đẩy form lên trên, giới hạn chiều rộng
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(Color.WHITE);
        form.setMaximumSize(new Dimension(560, 400));
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        left.setBackground(Color.WHITE);
        form.setPreferredSize(new Dimension(560, 380));
        left.add(form);
        wrap.add(left, BorderLayout.NORTH);
        mainPanel.add(wrap, BorderLayout.CENTER);

        btnHoanTac.addActionListener(e -> taiDuLieu());
        btnLuu.addActionListener(e -> luu());
    }

    private JTextField oNhap() {
        JTextField t = new JTextField(28);
        t.setFont(HocSinhUI.FONT);
        return t;
    }

    /** GET /api/sinh-vien/thong-tin?tenDangNhap=... */
    public void taiDuLieu() {
        String url = "/api/sinh-vien/thong-tin?tenDangNhap=" + ApiClient.encode(tenDangNhap);
        HocSinhUI.chayNen(mainPanel, () -> ApiClient.get(url).getAsJsonObject(), sv -> {
            txtTenDangNhap.setText(HocSinhUI.str(sv, "tenDangNhap"));
            txtMaSv.setText(HocSinhUI.str(sv, "maSinhVien"));
            txtHoTen.setText(HocSinhUI.str(sv, "hoTen"));
            txtLop.setText(HocSinhUI.str(sv, "lop"));
            txtEmail.setText(HocSinhUI.str(sv, "email"));
            txtSdt.setText(HocSinhUI.str(sv, "soDienThoai"));
            String ns = HocSinhUI.str(sv, "ngaySinh");
            dcNgaySinh.setDate(ns.isEmpty() ? null
                    : Date.from(LocalDate.parse(ns).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        });
    }

    /** PUT /api/sinh-vien/cap-nhat?tenDangNhap=... */
    private void luu() {
        if (txtHoTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Họ tên không được để trống");
            txtHoTen.requestFocus();
            return;
        }

        JsonObject body = new JsonObject();
        body.addProperty("maSinhVien", txtMaSv.getText().trim());
        body.addProperty("hoTen", txtHoTen.getText().trim());
        body.addProperty("lop", txtLop.getText().trim());
        body.addProperty("email", txtEmail.getText().trim());
        body.addProperty("soDienThoai", txtSdt.getText().trim());
        Date ns = dcNgaySinh.getDate();
        if (ns != null) {
            body.addProperty("ngaySinh", ns.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
        }

        String url = "/api/sinh-vien/cap-nhat?tenDangNhap=" + ApiClient.encode(tenDangNhap);
        HocSinhUI.chayNen(mainPanel, () -> ApiClient.put(url, body),
                kq -> JOptionPane.showMessageDialog(mainPanel, "Cập nhật thông tin thành công!"));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
