package com.eventmanager.fe.gui.Canbo;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));

        final JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.add(northPanel, BorderLayout.NORTH);

        // West: tìm kiếm
        txtTimKiem = new JPanel();
        txtTimKiem.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(txtTimKiem, BorderLayout.WEST);

        txtTimKiem.add(new JLabel("Tìm kiếm"), new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        final Spacer spacerW = new Spacer();
        txtTimKiem.add(spacerW, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        txtTimkiem = new JTextField();
        txtTimKiem.add(txtTimkiem, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));

        btnTimKiem = new JButton();
        btnTimKiem.setText("Tìm kiếm");
        txtTimKiem.add(btnTimKiem, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        btnHuyTimKiem = new JButton();
        btnHuyTimKiem.setText("Hủy tìm kiếm");
        txtTimKiem.add(btnHuyTimKiem, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        // East: thống kê
        final JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(eastPanel, BorderLayout.EAST);

        eastPanel.add(new JLabel("Tổng"), new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        final Spacer spacerE = new Spacer();
        eastPanel.add(spacerE, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        eastPanel.add(new JLabel("| Có mặt"), new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        eastPanel.add(new JLabel("| Vắng mặt"), new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        eastPanel.add(new JLabel("| Chưa điểm dan"), new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        // Center: bảng danh sách
        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(0, 0));
        centerPanel.setBorder(BorderFactory.createTitledBorder("Danh sách người tham gia"));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        tableNguoiThamGia = new JScrollPane();
        centerPanel.add(tableNguoiThamGia, BorderLayout.CENTER);

        // South: nút thao tác
        final JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        btnXemChiTiet = new JButton();
        btnXemChiTiet.setText("Xem chi tiết");
        southPanel.add(btnXemChiTiet);

        btnCapNhatDiemDanh = new JButton();
        btnCapNhatDiemDanh.setText("Cập nhật điểm danh");
        southPanel.add(btnCapNhatDiemDanh);

        btnLamMoi = new JButton();
        btnLamMoi.setText("Làm mới");
        southPanel.add(btnLamMoi);
    }
}
