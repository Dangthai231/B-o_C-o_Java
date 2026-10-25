package com.eventmanager.fe.gui.Canbo;

import Canbo.Trangchu;
import com.eventmanager.fe.gui.Login.Login;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardCanbo {
    private JPanel mainPanel;
    private JPanel sidebar;
    private JButton trangChủButton;
    private JButton lậpBáoCáoThốngButton;
    private JButton quảnLýTàiLiệuButton;
    private JButton quảnLýNgườiThamButton;
    private JButton quảnLýThôngBáoButton;
    private JButton đăngXuấtButton;
    private JPanel contentPanel;
    private JLabel lblUser;

    private List<JButton> navButtons;
    private String currentUsername = "canbo01";

    // Các trang con
    private Trangchu trangchu;
    private Lapbaocaothongke lapbaocaothongke;
    private Quanlytailieusukien quanlytailieusukien;
    private Quanlynguoithamgia quanlynguoithamgia;
    private Quanlythongbao quanlythongbao;

    public DashboardCanbo() {
        this("canbo01");
    }

    public DashboardCanbo(String username) {
        if (username != null && !username.trim().isEmpty()) {
            this.currentUsername = username.trim();
        }
        initUI();
    }

    private void initUI() {
        mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setPreferredSize(new Dimension(1150, 700));

        // ================= SIDEBAR =================
        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        Color sidebarBg = new Color(152, 180, 209);
        sidebar.setBackground(sidebarBg);
        sidebar.setPreferredSize(new Dimension(200, 700));
        sidebar.setBorder(new EmptyBorder(25, 12, 20, 12));

        // Greeting with dynamic username from login
        lblUser = new JLabel("Xin chào, " + currentUsername);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblUser.setForeground(new Color(20, 20, 20));
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lblUser);
        sidebar.add(Box.createVerticalStrut(25));

        // Navigation Buttons
        navButtons = new ArrayList<>();

        trangChủButton = createNavButton("Trang chủ");
        lậpBáoCáoThốngButton = createNavButton("Lập báo cáo và thống kê");
        quảnLýTàiLiệuButton = createNavButton("Quản lý tài liệu sự kiện");
        quảnLýNgườiThamButton = createNavButton("Quản lý người tham gia");
        quảnLýThôngBáoButton = createNavButton("Quản lý thông báo");

        sidebar.add(trangChủButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(lậpBáoCáoThốngButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(quảnLýTàiLiệuButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(quảnLýNgườiThamButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(quảnLýThôngBáoButton);

        // Spacer to push Logout to bottom
        sidebar.add(Box.createVerticalGlue());

        // Logout Button
        đăngXuấtButton = createNavButton("Đăng xuất");
        sidebar.add(đăngXuấtButton);

        mainPanel.add(sidebar, BorderLayout.WEST);

        // ================= CONTENT PANEL =================
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Khởi tạo các trang con
        trangchu = new Trangchu();
        lapbaocaothongke = new Lapbaocaothongke();
        quanlytailieusukien = new Quanlytailieusukien();
        quanlynguoithamgia = new Quanlynguoithamgia();
        quanlythongbao = new Quanlythongbao();

        // Mặc định hiển thị Trang chủ
        showPanel(trangchu.getMainPanel(), trangChủButton);

        // Gắn sự kiện chuyển tab
        trangChủButton.addActionListener(e -> showPanel(trangchu.getMainPanel(), trangChủButton));
        lậpBáoCáoThốngButton.addActionListener(e -> showPanel(lapbaocaothongke.getMainPanel(), lậpBáoCáoThốngButton));
        quảnLýTàiLiệuButton.addActionListener(e -> showPanel(quanlytailieusukien.getMainPanel(), quảnLýTàiLiệuButton));
        quảnLýNgườiThamButton.addActionListener(e -> showPanel(quanlynguoithamgia.getMainPanel(), quảnLýNgườiThamButton));
        quảnLýThôngBáoButton.addActionListener(e -> showPanel(quanlythongbao.getMainPanel(), quảnLýThôngBáoButton));

        // Đăng xuất: đóng Dashboard và quay về màn hình Login
        đăngXuấtButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    mainPanel,
                    "Bạn có chắc muốn đăng xuất khỏi hệ thống?",
                    "Xác nhận đăng xuất",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                Window win = SwingUtilities.getWindowAncestor(mainPanel);
                if (win != null) {
                    win.dispose();
                }
                Login.main(new String[]{});
            }
        });
    }

    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(30, 30, 30));
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(185, 36));
        btn.setPreferredSize(new Dimension(185, 36));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new CompoundBorder(
                new LineBorder(new Color(90, 140, 190), 1),
                new EmptyBorder(5, 8, 5, 8)
        ));
        if (navButtons != null) {
            navButtons.add(btn);
        }
        return btn;
    }

    private void showPanel(JPanel panel, JButton activeButton) {
        if (navButtons != null) {
            for (JButton btn : navButtons) {
                if (btn == activeButton) {
                    btn.setBorder(new CompoundBorder(
                            new LineBorder(new Color(25, 118, 210), 2),
                            new EmptyBorder(5, 8, 5, 8)
                    ));
                } else {
                    btn.setBorder(new CompoundBorder(
                            new LineBorder(new Color(90, 140, 190), 1),
                            new EmptyBorder(5, 8, 5, 8)
                    ));
                }
            }
        }

        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void $$$setupUI$$$() {
    }

    public static void main(String[] args) {
        // Bắt buộc người dùng phải qua màn hình Đăng nhập trước
        Login.main(args);
    }
}
