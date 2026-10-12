package com.eventmanager.fe.gui.Canbo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Quanlycongviec {
    private JPanel panel1;
    private JTabbedPane tabbedPaneCongViec;
    private JTable tableDanhSachCongViec;
    private JTable table1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextArea textArea1;
    private JProgressBar progressBar1;
    private JTable table2;
    private JScrollPane tableDanhSachNhanSu;
    private JButton btnLuu;
    private JButton btnAddCV;
    private JButton btnXacNhan;
    private JButton btnXemCt;
    private JButton btnReturn;
    private JButton btnClose;
    private JButton btnUpdate;

    public Quanlycongviec() {
        $$$setupUI$$$();
        setupTable();
    }

    private void setupTable() {
        // 1. Tạo mảng chứa tên các cột theo thiết kế
        String[] columnNames = {"Mã CV", "Tên công việc", "Trạng thái", "Tiến độ", "Nhân sự"};

        // 2. Tạo một TableModel mới với các cột vừa định nghĩa
        DefaultTableModel modelCongViec = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Khóa không cho sửa trực tiếp
            }
        };

        // 3. Gắn model vào JTable
        tableDanhSachCongViec.setModel(modelCongViec);

        // 4. Chỉnh độ rộng cột "Tên công việc"
        tableDanhSachCongViec.getColumnModel().getColumn(1).setPreferredWidth(200);
    }

    public JPanel getMainPanel() {
        return panel1;
    }

    private void $$$setupUI$$$() {
        // IntelliJ GUI Designer sẽ inject code tạo giao diện từ Quanlycongviec.form
    }
}
