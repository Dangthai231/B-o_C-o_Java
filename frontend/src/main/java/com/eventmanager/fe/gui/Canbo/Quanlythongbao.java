package com.eventmanager.fe.gui.Canbo;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));

        final JLabel label1 = new JLabel();
        label1.setFont(new Font("Arial", Font.BOLD, 24));
        label1.setText("QUẢN LÝ THÔNG BÁO SỰ KIỆN");
        mainPanel.add(label1, BorderLayout.NORTH);

        final JSplitPane splitPane1 = new JSplitPane();
        mainPanel.add(splitPane1, BorderLayout.CENTER);

        // Left: danh sách thông báo
        final JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout(0, 0));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Danh sách thông báo"));
        splitPane1.setLeftComponent(leftPanel);

        final JPanel leftTop = new JPanel();
        leftTop.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        leftPanel.add(leftTop, BorderLayout.NORTH);
        leftTop.add(new JLabel("Tìm kiếm"));
        txtTimKiem = new JTextField();
        txtTimKiem.setText("");
        leftTop.add(txtTimKiem);
        btnTimKiem = new JButton();
        btnTimKiem.setText("Tìm kiếm");
        leftTop.add(btnTimKiem);

        final JScrollPane scrollPane1 = new JScrollPane();
        leftPanel.add(scrollPane1, BorderLayout.CENTER);
        tableThongBao = new JTable();
        scrollPane1.setViewportView(tableThongBao);

        // Right: thông tin chi tiết
        final JPanel rightOuter = new JPanel();
        rightOuter.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane1.setRightComponent(rightOuter);

        final JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
        rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Thông tin thông báo"));
        rightOuter.add(rightPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        rightPanel.add(new JLabel("Tiêu đề"), new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        textField1.setText("");
        rightPanel.add(textField1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));

        rightPanel.add(new JLabel("Nội dung:"), new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textArea1 = new JTextArea();
        rightPanel.add(textArea1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 0, false));

        rightPanel.add(new JLabel("Đối tượng:"), new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        rightPanel.add(comboBox1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        rightPanel.add(new JLabel("Ngày gửi:"), new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panelNgayGui = new JPanel();
        panelNgayGui.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rightPanel.add(panelNgayGui, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        rightPanel.add(new JLabel("Trạng thái:"), new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox2 = new JComboBox();
        comboBox2.setModel(new DefaultComboBoxModel<>(new String[]{"Nháp", "Đã gửi", "Đã hủy"}));
        rightPanel.add(comboBox2, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        final JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        rightPanel.add(btnPanel, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        btnGuiThongBao = new JButton();
        btnGuiThongBao.setText("Tạo thông báo");
        btnPanel.add(btnGuiThongBao, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        btnCapNhat = new JButton();
        btnCapNhat.setText("Cập nhật");
        btnPanel.add(btnCapNhat, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        btnXoa = new JButton();
        btnXoa.setText("Xóa");
        btnPanel.add(btnXoa, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        btnLamMoi = new JButton();
        btnLamMoi.setText("Làm mới");
        btnPanel.add(btnLamMoi, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }
}
