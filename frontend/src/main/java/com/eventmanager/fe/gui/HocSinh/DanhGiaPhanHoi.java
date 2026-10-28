package com.eventmanager.fe.gui.HocSinh;

import com.eventmanager.fe.api.ApiClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Use case "Đánh giá và phản hồi sự kiện".
 * Chỉ các sự kiện đã đăng ký (không hủy) và đã diễn ra mới được đánh giá.
 */
public class DanhGiaPhanHoi {
    private final String tenDangNhap;

    private JPanel mainPanel;
    private JComboBox<SuKienItem> cboSuKien;
    private JComboBox<String> cboSoSao;
    private JTextArea txtNoiDung;
    private DefaultTableModel model;
    private JTable table;

    // Mục trong combobox: hiện tên, giữ id
    private record SuKienItem(long id, String ten) {
        @Override
        public String toString() {
            return ten;
        }
    }

    private static final String[] SAO = {"★★★★★  (5 - Rất hài lòng)", "★★★★  (4 - Hài lòng)",
            "★★★  (3 - Bình thường)", "★★  (2 - Chưa hài lòng)", "★  (1 - Rất tệ)"};

    public DanhGiaPhanHoi(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
        initUI();
        taiDuLieu();
    }

    private void initUI() {
        mainPanel = HocSinhUI.taoTrang("Đánh giá và phản hồi sự kiện");

        // --- Form gửi đánh giá ---
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Gửi đánh giá", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 13)));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        cboSuKien = new JComboBox<>();
        cboSuKien.setFont(HocSinhUI.FONT);
        cboSoSao = new JComboBox<>(SAO);
        cboSoSao.setFont(HocSinhUI.FONT);
        txtNoiDung = new JTextArea(4, 40);
        txtNoiDung.setFont(HocSinhUI.FONT);
        txtNoiDung.setLineWrap(true);
        txtNoiDung.setWrapStyleWord(true);
        JButton btnGui = HocSinhUI.taoNutChinh("Gửi đánh giá");

        g.gridx = 0; g.gridy = 0; g.weightx = 0;
        form.add(new JLabel("Sự kiện:"), g);
        g.gridx = 1; g.weightx = 1;
        form.add(cboSuKien, g);
        g.gridx = 0; g.gridy = 1; g.weightx = 0;
        form.add(new JLabel("Mức độ hài lòng:"), g);
        g.gridx = 1; g.weightx = 1;
        form.add(cboSoSao, g);
        g.gridx = 0; g.gridy = 2; g.weightx = 0;
        form.add(new JLabel("Nhận xét / góp ý:"), g);
        g.gridx = 1; g.weightx = 1;
        form.add(new JScrollPane(txtNoiDung), g);
        g.gridx = 1; g.gridy = 3; g.fill = GridBagConstraints.NONE; g.anchor = GridBagConstraints.EAST;
        form.add(btnGui, g);

        // --- Bảng đánh giá đã gửi ---
        model = HocSinhUI.taoModel("ID sự kiện", "Sự kiện", "Số sao", "Nhận xét", "Ngày gửi");
        table = HocSinhUI.taoBang(model);
        HocSinhUI.anCot(table, 1);
        table.getColumnModel().getColumn(0).setPreferredWidth(220);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
        JPanel bang = new JPanel(new BorderLayout(0, 6));
        bang.setBackground(Color.WHITE);
        JLabel lbl = new JLabel("Đánh giá bạn đã gửi (chọn một dòng để sửa):");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        bang.add(lbl, BorderLayout.NORTH);
        bang.add(HocSinhUI.cuon(table), BorderLayout.CENTER);

        JPanel giua = new JPanel(new BorderLayout(0, 15));
        giua.setBackground(Color.WHITE);
        giua.add(form, BorderLayout.NORTH);
        giua.add(bang, BorderLayout.CENTER);
        mainPanel.add(giua, BorderLayout.CENTER);

        btnGui.addActionListener(e -> guiDanhGia());
        // Chọn đánh giá cũ -> đổ lên form để sửa
        table.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting() || table.getSelectedRow() < 0) return;
            int row = table.convertRowIndexToModel(table.getSelectedRow());
            long suKienId = (Long) model.getValueAt(row, 0);
            for (int i = 0; i < cboSuKien.getItemCount(); i++) {
                if (cboSuKien.getItemAt(i).id() == suKienId) cboSuKien.setSelectedIndex(i);
            }
            int soSao = (Integer) model.getValueAt(row, 2);
            cboSoSao.setSelectedIndex(5 - soSao);
            txtNoiDung.setText((String) model.getValueAt(row, 3));
        });
    }

    /** Lấy danh sách sự kiện được phép đánh giá + các đánh giá đã gửi. */
    public void taiDuLieu() {
        String ten = ApiClient.encode(tenDangNhap);
        HocSinhUI.chayNen(mainPanel, () -> {
            JsonObject kq = new JsonObject();
            kq.add("dangKy", ApiClient.get("/api/dang-ky/cua-toi?tenDangNhap=" + ten));
            kq.add("danhGia", ApiClient.get("/api/danh-gia/cua-toi?tenDangNhap=" + ten));
            return kq;
        }, kq -> {
            cboSuKien.removeAllItems();
            for (JsonElement el : kq.getAsJsonArray("dangKy")) {
                JsonObject o = el.getAsJsonObject();
                boolean khongHuy = !"Đã hủy".equals(HocSinhUI.str(o, "trangThai"));
                if (khongHuy && HocSinhUI.daBatDau(HocSinhUI.str(o, "thoiGianBatDau"))) {
                    cboSuKien.addItem(new SuKienItem(HocSinhUI.num(o, "suKienId"), HocSinhUI.str(o, "tenSuKien")));
                }
            }

            model.setRowCount(0);
            JsonArray ds = kq.getAsJsonArray("danhGia");
            for (JsonElement el : ds) {
                JsonObject o = el.getAsJsonObject();
                model.addRow(new Object[]{
                        HocSinhUI.num(o, "suKienId"),
                        HocSinhUI.str(o, "tenSuKien"),
                        (int) HocSinhUI.num(o, "soSao"),
                        HocSinhUI.str(o, "noiDung"),
                        HocSinhUI.thoiGian(HocSinhUI.str(o, "ngayDanhGia"))
                });
            }
        });
    }

    /** POST /api/danh-gia/gui */
    private void guiDanhGia() {
        SuKienItem sk = (SuKienItem) cboSuKien.getSelectedItem();
        if (sk == null) {
            JOptionPane.showMessageDialog(mainPanel, "Bạn chưa có sự kiện nào đã tham gia để đánh giá");
            return;
        }
        String noiDung = txtNoiDung.getText().trim();
        if (noiDung.length() > 1000) {
            JOptionPane.showMessageDialog(mainPanel, "Nhận xét tối đa 1000 ký tự");
            return;
        }

        JsonObject body = new JsonObject();
        body.addProperty("suKienId", sk.id());
        body.addProperty("tenDangNhap", tenDangNhap);
        body.addProperty("soSao", 5 - cboSoSao.getSelectedIndex());
        body.addProperty("noiDung", noiDung);
        HocSinhUI.chayNen(mainPanel, () -> ApiClient.post("/api/danh-gia/gui", body), kq -> {
            JOptionPane.showMessageDialog(mainPanel, "Cảm ơn bạn đã gửi đánh giá!");
            txtNoiDung.setText("");
            taiDuLieu();
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
