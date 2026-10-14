package com.eventmanager.fe.gui.Canbo;
import com.eventmanager.fe.gui.Canbo.Lapbaocaothongke;
import com.eventmanager.fe.gui.Canbo.Quanlytailieusukien;
import com.eventmanager.fe.gui.Canbo.Quanlycongviec;
import com.eventmanager.fe.gui.Canbo.Quanlynguoithamgia;
import com.eventmanager.fe.gui.Canbo.Quanlythongbao;
import Canbo.Trangchu;

import javax.swing.*;
import java.awt.*;

public class DashboardCanbo {
    private JPanel mainPanel;
    private JPanel sidebar;
    private JButton trangChủButton;
    private JButton lậpBáoCáoThốngButton;
    private JButton quảnLýTàiLiệuButton;
    private JButton quảnLýCôngViệcButton;
    private JButton quảnLýNgườiThamButton;
    private JButton quảnLýThôngBáoButton;
    private JButton đăngXuấtButton;
    private JPanel contentPanel;

    // Các trang con sẽ được nhúng vào contentPanel
    private Trangchu trangchu;
    private Lapbaocaothongke lapbaocaothongke;
    private Quanlytailieusukien quanlytailieusukien;
    private Quanlycongviec quanlycongviec;
    private Quanlynguoithamgia quanlynguoithamgia;
    private Quanlythongbao quanlythongbao;

    public DashboardCanbo() {
        $$$setupUI$$$();

        // Khởi tạo các trang con
        trangchu = new Trangchu();
        lapbaocaothongke = new Lapbaocaothongke();
        quanlytailieusukien = new Quanlytailieusukien();
        quanlycongviec = new Quanlycongviec();
        quanlynguoithamgia = new Quanlynguoithamgia();
        quanlythongbao = new Quanlythongbao();

        // Mặc định hiển thị Trang chủ
        showPanel(trangchu.getMainPanel());

        // Gắn sự kiện cho các nút bên sidebar
        trangChủButton.addActionListener(e -> showPanel(trangchu.getMainPanel()));

        lậpBáoCáoThốngButton.addActionListener(e -> showPanel(lapbaocaothongke.getMainPanel()));

        quảnLýTàiLiệuButton.addActionListener(e -> showPanel(quanlytailieusukien.getMainPanel()));

        quảnLýCôngViệcButton.addActionListener(e -> showPanel(quanlycongviec.getMainPanel()));

        quảnLýNgườiThamButton.addActionListener(e -> showPanel(quanlynguoithamgia.getMainPanel()));

        quảnLýThôngBáoButton.addActionListener(e -> showPanel(quanlythongbao.getMainPanel()));

        đăngXuấtButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Bạn có chắc muốn đăng xuất không?",
                    "Xác nhận đăng xuất",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    /**
     * Hiển thị một JPanel vào vùng contentPanel của Dashboard.
     */
    private void showPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Hiển thị thông báo khi chức năng chưa có form.
     */
    private void showMessage(String message) {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void $$$setupUI$$$() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Dashboard Cán Bộ");
            DashboardCanbo dashboard = new DashboardCanbo();
            frame.setContentPane(dashboard.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
