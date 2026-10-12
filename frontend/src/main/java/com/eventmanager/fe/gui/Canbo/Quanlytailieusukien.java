package com.eventmanager.fe.gui.Canbo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Quanlytailieusukien {
    private JPanel mainPanel;
    private JTable tableTaiLieu;
    private JButton btnTaiLen;
    private JButton btnTaoThuMuc;
    private JButton btnTimKiem;

    //hàm khởi tạo
    public Quanlytailieusukien() {
        $$$setupUI$$$();
        setupTable();
    }

    // Các component có custom-create="true" trong .form phải được khởi tạo ở đây
    private void createUIComponents() {
        mainPanel = new JPanel();
        tableTaiLieu = new JTable();
        btnTaiLen = new JButton();
        btnTaoThuMuc = new JButton();
        btnTimKiem = new JButton();
    }

    private void setupTable() {
        String[] columnNames = {
                "Tên tài liệu", "Loại", "Kích thước", "Ngày tải lên", "Người tải"
        };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableTaiLieu.setModel(tableModel);

        // Dữ liệu mẫu

        tableTaiLieu.getColumnModel().getColumn(0).setPreferredWidth(250);
        tableTaiLieu.setRowHeight(30);
    }

    // Hàm Getter để lấy giao diện này nhúng vào Main Dashboard
    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void $$$setupUI$$$() {
        // IntelliJ GUI Designer sẽ inject code tạo giao diện từ Quanlytailieusukien.form
    }
}
