package com.eventmanager.fe.gui.Canbo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Quanlythongbao {
    private JPanel mainPanel;
    private JTextField txtTimKiem;
    private JButton btnTimKiem;
    private JTable tableThongBao;
    private JTextField textField1;
    private JTextArea textArea1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton btnGuiThongBao;
    private JButton btnCapNhat;
    private JButton btnXoa;
    private JButton btnLamMoi;

    public Quanlythongbao() {
        $$$setupUI$$$();
        setupTable();

        // Thêm dữ liệu cho ComboBox "Đối tượng" (comboBox1)
        comboBox1.addItem("Tất cả người tham gia");
        comboBox1.addItem("Ban tổ chức");
        comboBox1.addItem("Đại biểu");
        comboBox1.addItem("Khách mời");

        // comboBox2 (Trạng thái) đã có sẵn item trong .form
    }

    private void setupTable() {
        String[] columns = {"Mã", "Tiêu đề", "Đối tượng", "Ngày gửi", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tableThongBao.setModel(model);

        tableThongBao.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableThongBao.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableThongBao.setRowHeight(30);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void $$$setupUI$$$() {
        // IntelliJ GUI Designer sẽ inject code tạo giao diện từ Quanlythongbao.form
    }
}
