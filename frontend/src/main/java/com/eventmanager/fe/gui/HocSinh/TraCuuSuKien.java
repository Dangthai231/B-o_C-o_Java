package com.eventmanager.fe.gui.HocSinh;

import com.eventmanager.fe.api.ApiClient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.ZoneId;
import java.util.Date;

/**
 * Use case "Tra cứu sự kiện": xem danh sách, tìm kiếm, lọc theo ngày, xem chi tiết
 * và nút "Đăng ký tham gia" ngay trên danh sách.
 */
public class TraCuuSuKien {
    private final String tenDangNhap;
    private final Runnable onDaDangKy; // báo cho Dashboard để làm mới màn "Sự kiện của tôi"

    private JPanel mainPanel;
    private JTextField txtTuKhoa;
    private JDateChooser dcTuNgay;
    private JDateChooser dcDenNgay;
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblTong;

    // Cột 0 = id (ẩn)
    private static final int COT_ID = 0;

    public TraCuuSuKien(String tenDangNhap, Runnable onDaDangKy) {
        this.tenDangNhap = tenDangNhap;
        this.onDaDangKy = onDaDangKy;
        initUI();
        taiDuLieu();
    }

    private void initUI() {
        mainPanel = HocSinhUI.taoTrang("Tra cứu sự kiện");

        // --- Thanh tìm kiếm / lọc ---
        JPanel loc = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        loc.setBackground(Color.WHITE);
        txtTuKhoa = new JTextField(18);
        txtTuKhoa.setFont(HocSinhUI.FONT);
        dcTuNgay = new JDateChooser();
        dcTuNgay.setDateFormatString("dd/MM/yyyy");
        dcTuNgay.setPreferredSize(new Dimension(130, 26));
        dcDenNgay = new JDateChooser();
        dcDenNgay.setDateFormatString("dd/MM/yyyy");
        dcDenNgay.setPreferredSize(new Dimension(130, 26));
        JButton btnTim = HocSinhUI.taoNutChinh("Tìm kiếm");
        JButton btnLamMoi = HocSinhUI.taoNut("Làm mới");

        loc.add(new JLabel("Tên sự kiện:"));
        loc.add(txtTuKhoa);
        loc.add(new JLabel("Từ ngày:"));
        loc.add(dcTuNgay);
        loc.add(new JLabel("Đến ngày:"));
        loc.add(dcDenNgay);
        loc.add(btnTim);
        loc.add(btnLamMoi);

        // --- Bảng ---
        model = HocSinhUI.taoModel("ID", "Mã", "Tên sự kiện", "Bắt đầu", "Địa điểm", "Số lượng", "Trạng thái");
        table = HocSinhUI.taoBang(model);
        HocSinhUI.anCot(table, 1);
        table.getColumnModel().getColumn(1).setPreferredWidth(260);

        JPanel giua = new JPanel(new BorderLayout(0, 10));
        giua.setBackground(Color.WHITE);
        giua.add(loc, BorderLayout.NORTH);
        giua.add(HocSinhUI.cuon(table), BorderLayout.CENTER);
        mainPanel.add(giua, BorderLayout.CENTER);

        // --- Nút dưới ---
        JPanel duoi = new JPanel(new BorderLayout());
        duoi.setBackground(Color.WHITE);
        lblTong = new JLabel(" ");
        lblTong.setFont(HocSinhUI.FONT);
        JPanel nut = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        nut.setBackground(Color.WHITE);
        JButton btnChiTiet = HocSinhUI.taoNut("Xem chi tiết");
        JButton btnDangKy = HocSinhUI.taoNutChinh("Đăng ký tham gia");
        nut.add(btnChiTiet);
        nut.add(btnDangKy);
        duoi.add(lblTong, BorderLayout.WEST);
        duoi.add(nut, BorderLayout.EAST);
        mainPanel.add(duoi, BorderLayout.SOUTH);

        // --- Sự kiện ---
        btnTim.addActionListener(e -> taiDuLieu());
        txtTuKhoa.addActionListener(e -> taiDuLieu()); // Enter để tìm
        btnLamMoi.addActionListener(e -> {
            txtTuKhoa.setText("");
            dcTuNgay.setDate(null);
            dcDenNgay.setDate(null);
            taiDuLieu();
        });
        btnChiTiet.addActionListener(e -> xemChiTiet());
        btnDangKy.addActionListener(e -> dangKy());
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) xemChiTiet();
            }
        });
    }

    /** Gọi GET /api/su-kien/danh-sach với từ khóa và khoảng ngày rồi đổ lên JTable. */
    public void taiDuLieu() {
        Date tu = dcTuNgay.getDate();
        Date den = dcDenNgay.getDate();
        if (tu != null && den != null && tu.after(den)) {
            JOptionPane.showMessageDialog(mainPanel, "\"Từ ngày\" phải trước \"Đến ngày\"");
            return;
        }

        StringBuilder url = new StringBuilder("/api/su-kien/danh-sach?tuKhoa=")
                .append(ApiClient.encode(txtTuKhoa.getText().trim()));
        if (tu != null) url.append("&tuNgay=").append(isoDate(tu));
        if (den != null) url.append("&denNgay=").append(isoDate(den));

        HocSinhUI.chayNen(mainPanel, () -> ApiClient.get(url.toString()).getAsJsonArray(), arr -> {
            model.setRowCount(0);
            for (JsonElement el : arr) {
                JsonObject o = el.getAsJsonObject();
                model.addRow(new Object[]{
                        HocSinhUI.num(o, "id"),
                        HocSinhUI.str(o, "maSuKien"),
                        HocSinhUI.str(o, "tenSuKien"),
                        HocSinhUI.thoiGian(HocSinhUI.str(o, "thoiGianBatDau")),
                        HocSinhUI.str(o, "diaDiem"),
                        HocSinhUI.str(o, "soLuongToiDa"),
                        HocSinhUI.str(o, "trangThai")
                });
            }
            lblTong.setText("Tìm thấy " + arr.size() + " sự kiện");
        });
    }

    private void xemChiTiet() {
        Object id = HocSinhUI.giaTriDongChon(table, COT_ID);
        if (id == null) {
            JOptionPane.showMessageDialog(mainPanel, "Hãy chọn một sự kiện");
            return;
        }
        HocSinhUI.hienChiTietSuKien(mainPanel, (Long) id, tenDangNhap);
    }

    /** POST /api/dang-ky/dang-ky. Backend kiểm tra trùng, hết chỗ, sự kiện đã bắt đầu... */
    private void dangKy() {
        Object id = HocSinhUI.giaTriDongChon(table, COT_ID);
        if (id == null) {
            JOptionPane.showMessageDialog(mainPanel, "Hãy chọn sự kiện muốn đăng ký");
            return;
        }
        String ten = (String) table.getModel().getValueAt(table.convertRowIndexToModel(table.getSelectedRow()), 2);
        if (JOptionPane.showConfirmDialog(mainPanel, "Đăng ký tham gia \"" + ten + "\"?", "Xác nhận",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }

        JsonObject body = new JsonObject();
        body.addProperty("suKienId", (Long) id);
        body.addProperty("tenDangNhap", tenDangNhap);
        HocSinhUI.chayNen(mainPanel, () -> ApiClient.post("/api/dang-ky/dang-ky", body), kq -> {
            JOptionPane.showMessageDialog(mainPanel, "Đăng ký thành công!");
            if (onDaDangKy != null) onDaDangKy.run();
        });
    }

    private static String isoDate(Date d) {
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString(); // yyyy-MM-dd
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
