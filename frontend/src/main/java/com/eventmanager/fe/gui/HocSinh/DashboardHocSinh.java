package com.eventmanager.fe.gui.HocSinh;

import com.eventmanager.fe.gui.Login.Login;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dashboard cho vai trò HOC_SINH (Sinh viên). Bố cục giống DashboardCanbo:
 * sidebar bên trái, nội dung bên phải. Mỗi nút mở một màn hình theo use case.
 */
public class DashboardHocSinh {
    private JPanel mainPanel;
    private JPanel contentPanel;
    private final List<JButton> navButtons = new ArrayList<>();
    private String currentUsername = "hocsinh01";

    // Các trang con
    private TraCuuSuKien traCuuSuKien;
    private SuKienCuaToi suKienCuaToi;
    private DanhGiaPhanHoi danhGiaPhanHoi;
    private ThongTinCaNhan thongTinCaNhan;

    public DashboardHocSinh() {
        this("hocsinh01");
    }

    public DashboardHocSinh(String username) {
        if (username != null && !username.trim().isEmpty()) {
            this.currentUsername = username.trim();
        }
        initUI();
    }

    private void initUI() {
        mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setPreferredSize(new Dimension(1150, 700));

        // ================= SIDEBAR =================
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(152, 180, 209));
        sidebar.setPreferredSize(new Dimension(200, 700));
        sidebar.setBorder(new EmptyBorder(25, 12, 20, 12));

        JLabel lblUser = new JLabel("Xin chào, " + currentUsername);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblUser.setForeground(new Color(20, 20, 20));
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lblUser);
        sidebar.add(Box.createVerticalStrut(25));

        JButton btnTraCuu = createNavButton("Tra cứu sự kiện");
        JButton btnCuaToi = createNavButton("Sự kiện đã đăng ký");
        JButton btnDanhGia = createNavButton("Đánh giá và phản hồi");
        JButton btnThongTin = createNavButton("Thông tin cá nhân");
        for (JButton b : List.of(btnTraCuu, btnCuaToi, btnDanhGia, btnThongTin)) {
            sidebar.add(b);
            sidebar.add(Box.createVerticalStrut(10));
        }
        sidebar.add(Box.createVerticalGlue());
        JButton btnDangXuat = createNavButton("Đăng xuất");
        sidebar.add(btnDangXuat);
        mainPanel.add(sidebar, BorderLayout.WEST);

        // ================= CONTENT PANEL =================
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Khởi tạo các trang con (mỗi trang tự gọi API lấy dữ liệu)
        suKienCuaToi = new SuKienCuaToi(currentUsername);
        traCuuSuKien = new TraCuuSuKien(currentUsername, suKienCuaToi::taiDuLieu);
        danhGiaPhanHoi = new DanhGiaPhanHoi(currentUsername);
        thongTinCaNhan = new ThongTinCaNhan(currentUsername);

        showPanel(traCuuSuKien.getMainPanel(), btnTraCuu);

        btnTraCuu.addActionListener(e -> showPanel(traCuuSuKien.getMainPanel(), btnTraCuu));
        // Mở lại trang nào thì tải lại dữ liệu mới nhất từ DB
        btnCuaToi.addActionListener(e -> {
            suKienCuaToi.taiDuLieu();
            showPanel(suKienCuaToi.getMainPanel(), btnCuaToi);
        });
        btnDanhGia.addActionListener(e -> {
            danhGiaPhanHoi.taiDuLieu();
            showPanel(danhGiaPhanHoi.getMainPanel(), btnDanhGia);
        });
        btnThongTin.addActionListener(e -> {
            thongTinCaNhan.taiDuLieu();
            showPanel(thongTinCaNhan.getMainPanel(), btnThongTin);
        });

        btnDangXuat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(mainPanel, "Bạn có chắc muốn đăng xuất khỏi hệ thống?",
                    "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);
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
        btn.setBorder(new CompoundBorder(new LineBorder(new Color(90, 140, 190), 1), new EmptyBorder(5, 8, 5, 8)));
        navButtons.add(btn);
        return btn;
    }

    private void showPanel(JPanel panel, JButton activeButton) {
        for (JButton btn : navButtons) {
            int doDay = btn == activeButton ? 2 : 1;
            Color mau = btn == activeButton ? new Color(25, 118, 210) : new Color(90, 140, 190);
            btn.setBorder(new CompoundBorder(new LineBorder(mau, doDay), new EmptyBorder(5, 8, 5, 8)));
        }
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
