package com.eventmanager.fe.gui.Canbo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Quanlycongviec {
    private JPanel panel1;
    private JTable tableDanhSachCongViec;
    private DefaultTableModel modelCongViec;

    public Quanlycongviec() {
        initUI();
    }

    private void initUI() {
        panel1 = new JPanel(new BorderLayout(0, 15));
        panel1.setBackground(Color.WHITE);
        panel1.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel lblTitle = new JLabel("Quản lý công việc");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(30, 30, 30));
        panel1.add(lblTitle, BorderLayout.NORTH);

        String[] columnNames = {"Mã CV", "Tên công việc", "Trạng thái", "Tiến độ", "Nhân sự"};
        modelCongViec = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableDanhSachCongViec = new JTable(modelCongViec);
        tableDanhSachCongViec.setRowHeight(28);
        tableDanhSachCongViec.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableDanhSachCongViec.getColumnModel().getColumn(1).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(tableDanhSachCongViec);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(new LineBorder(new Color(200, 200, 200)));
        panel1.add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel getMainPanel() {
        return panel1;
    }

    private void $$$setupUI$$$() {
    }
}
