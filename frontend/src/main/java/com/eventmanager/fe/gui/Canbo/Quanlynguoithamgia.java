package com.eventmanager.fe.gui.Canbo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Quanlynguoithamgia {
    private JPanel mainPanel;
    private JPanel txtTimKiem;
    private JTextField txtTimkiem;
    private JButton btnTimKiem;
    private JButton btnHuyTimKiem;
    private JLabel lblTongSo;
    private JLabel lblCoMat;
    private JLabel lblVangMat;
    private JLabel lblChuaDiemDanh;
    private JScrollPane tableNguoiThamGia;
    private JButton btnXemChiTiet;
    private JButton btnCapNhatDiemDanh;
    private JButton btnLamMoi;

    // Bảng thật được tạo trong code vì scrollpane trong form chưa có JTable
    private JTable tableNguoiThamGiaTable;

    public Quanlynguoithamgia() {
        $$$setupUI$$$();
        setupTable();
    }

    private void setupTable() {
        String[] columnNames = {
                "Mã", "Họ tên", "Email", "Số điện thoại", "Vai trò", "Điểm danh", "Trạng thái"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableNguoiThamGiaTable = new JTable(tableModel);
        tableNguoiThamGiaTable.getColumnModel().getColumn(0).setPreferredWidth(70);
        tableNguoiThamGiaTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        tableNguoiThamGiaTable.getColumnModel().getColumn(2).setPreferredWidth(220);
        tableNguoiThamGiaTable.setRowHeight(30);

        if (tableNguoiThamGia != null) {
            tableNguoiThamGia.setViewportView(tableNguoiThamGiaTable);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void $$$setupUI$$$() {
        // IntelliJ GUI Designer sẽ inject code tạo giao diện từ Quanlynguoithamgia.form
    }
}
