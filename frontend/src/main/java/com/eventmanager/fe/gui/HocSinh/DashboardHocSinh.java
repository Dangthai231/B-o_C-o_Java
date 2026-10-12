package com.eventmanager.fe.gui.HocSinh;

import javax.swing.*;
import java.awt.*;

/**
 * Dashboard cho vai trò HOC_SINH (Học sinh).
 * Lớp này được tạo bằng code thuần Swing (không dùng file .form)
 * để tránh xung đột với thiết kế giao diện có sẵn.
 */
public class DashboardHocSinh {
    private JPanel mainPanel;

    public DashboardHocSinh() {
        initComponents();
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(39, 174, 96));
        JLabel lblTitle = new JLabel("  DASHBOARD - HỌC SINH", SwingConstants.LEFT);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        headerPanel.add(lblTitle, BorderLayout.CENTER);

        JButton btnDangXuat = new JButton("Đăng xuất");
        btnDangXuat.setFont(new Font("Arial", Font.PLAIN, 14));
        btnDangXuat.setFocusPainted(false);
        btnDangXuat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Bạn có chắc muốn đăng xuất không?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(new Color(39, 174, 96));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnPanel.add(btnDangXuat);
        headerPanel.add(btnPanel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Nội dung trung tâm
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        JLabel lblWelcome = new JLabel("Chào mừng Học sinh!");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 28));
        lblWelcome.setForeground(new Color(44, 62, 80));
        centerPanel.add(lblWelcome);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(236, 240, 241));
        JLabel lblFooter = new JLabel("  Hệ thống Quản lý Sự kiện - Liên Chi Đoàn CNTT", SwingConstants.LEFT);
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooter.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        footerPanel.add(lblFooter, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
