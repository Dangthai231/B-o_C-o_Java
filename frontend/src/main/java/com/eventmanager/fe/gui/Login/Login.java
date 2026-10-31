package com.eventmanager.fe.gui.Login;

import com.eventmanager.fe.gui.Admin.DashboardAdmin;
import com.eventmanager.fe.gui.Canbo.DashboardCanbo;
import com.eventmanager.fe.gui.HocSinh.DashboardHocSinh;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Login {
    private JPanel mainPanel;
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JCheckBox chkHienThiMatKhau;
    private JButton btnDangNhap;
    private JLabel lblThongBaoLoi;

    public Login() {
        initUI();
    }

    private void initUI() {
        // Outer panel with light background and padding
        mainPanel = new JPanel(new BorderLayout());
        Color outerBg = new Color(240, 242, 245);
        mainPanel.setBackground(outerBg);
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        // Center card container
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(225, 230, 238), 1),
                new EmptyBorder(35, 40, 30, 40)
        ));

        // 1. Title "ĐĂNG NHẬP"
        JLabel lblTitle = new JLabel("ĐĂNG NHẬP");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(16, 42, 67)); // Dark Navy
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblTitle);
        cardPanel.add(Box.createVerticalStrut(6));

        // 2. Subtitle "Hệ thống Quản lý Sự kiện"
        JLabel lblSubtitle = new JLabel("Hệ thống Quản lý Sự kiện");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitle.setForeground(new Color(98, 125, 152));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblSubtitle);
        cardPanel.add(Box.createVerticalStrut(30));

        // 3. Label "Tài khoản:"
        JLabel lblUser = new JLabel("Tài khoản:");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUser.setForeground(new Color(36, 59, 83));
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel pnlUserLbl = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlUserLbl.setBackground(Color.WHITE);
        pnlUserLbl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
        pnlUserLbl.add(lblUser);
        cardPanel.add(pnlUserLbl);
        cardPanel.add(Box.createVerticalStrut(6));

        // 4. Input "txtTenDangNhap" with active blue border
        txtTenDangNhap = new JTextField();
        txtTenDangNhap.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtTenDangNhap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        txtTenDangNhap.setPreferredSize(new Dimension(320, 36));
        txtTenDangNhap.setBorder(new CompoundBorder(
                new LineBorder(new Color(25, 118, 210), 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
        cardPanel.add(txtTenDangNhap);
        cardPanel.add(Box.createVerticalStrut(16));

        // 5. Label "Mật khẩu:"
        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPass.setForeground(new Color(36, 59, 83));
        JPanel pnlPassLbl = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlPassLbl.setBackground(Color.WHITE);
        pnlPassLbl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
        pnlPassLbl.add(lblPass);
        cardPanel.add(pnlPassLbl);
        cardPanel.add(Box.createVerticalStrut(6));

        // 6. Input "txtMatKhau"
        txtMatKhau = new JPasswordField();
        txtMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtMatKhau.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        txtMatKhau.setPreferredSize(new Dimension(320, 36));
        txtMatKhau.setBorder(new CompoundBorder(
                new LineBorder(new Color(205, 210, 220), 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
        cardPanel.add(txtMatKhau);
        cardPanel.add(Box.createVerticalStrut(10));

        // 7. Checkbox "Hiển thị mật khẩu"
        chkHienThiMatKhau = new JCheckBox("Hiển thị mật khẩu");
        chkHienThiMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkHienThiMatKhau.setForeground(new Color(72, 101, 129));
        chkHienThiMatKhau.setBackground(Color.WHITE);
        chkHienThiMatKhau.setFocusPainted(false);
        JPanel pnlChk = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlChk.setBackground(Color.WHITE);
        pnlChk.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        pnlChk.add(chkHienThiMatKhau);
        cardPanel.add(pnlChk);
        cardPanel.add(Box.createVerticalStrut(8));

        // Toggle Password Visibility
        chkHienThiMatKhau.addActionListener(e -> {
            if (chkHienThiMatKhau.isSelected()) {
                txtMatKhau.setEchoChar((char) 0);
            } else {
                txtMatKhau.setEchoChar('•');
            }
        });

        // 8. Error/Status Label
        lblThongBaoLoi = new JLabel(" ");
        lblThongBaoLoi.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblThongBaoLoi.setForeground(new Color(211, 47, 47));
        lblThongBaoLoi.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblThongBaoLoi);
        cardPanel.add(Box.createVerticalStrut(10));

        // 9. Button "ĐĂNG NHẬP"
        btnDangNhap = new JButton("ĐĂNG NHẬP");
        btnDangNhap.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDangNhap.setForeground(Color.WHITE);
        Color btnBlue = new Color(37, 99, 235);
        Color btnBlueHover = new Color(29, 78, 216);
        btnDangNhap.setBackground(btnBlue);
        // Giao diện Windows bỏ qua setBackground của JButton -> tự tô nền, nếu không chữ trắng trên nền trắng
        btnDangNhap.setContentAreaFilled(false);
        btnDangNhap.setOpaque(true);
        btnDangNhap.setFocusPainted(false);
        btnDangNhap.setBorder(new EmptyBorder(10, 0, 10, 0));
        btnDangNhap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnDangNhap.setPreferredSize(new Dimension(320, 44));
        btnDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnDangNhap.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnDangNhap.setBackground(btnBlueHover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnDangNhap.setBackground(btnBlue);
            }
        });

        cardPanel.add(btnDangNhap);

        // Spacer to push footer to bottom
        cardPanel.add(Box.createVerticalGlue());
        cardPanel.add(Box.createVerticalStrut(25));

        // 10. Footer "Hệ thống Quản lý Sự kiện • Phiên bản 1.0"
        JLabel lblFooter = new JLabel("Hệ thống Quản lý Sự kiện • Phiên bản 1.0");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFooter.setForeground(new Color(159, 179, 200));
        lblFooter.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblFooter);

        mainPanel.add(cardPanel, BorderLayout.CENTER);

        // Actions
        btnDangNhap.addActionListener(e -> xuLyDangNhap());

        KeyAdapter enterKey = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    xuLyDangNhap();
                }
            }
        };
        txtTenDangNhap.addKeyListener(enterKey);
        txtMatKhau.addKeyListener(enterKey);
    }

    private void xuLyDangNhap() {
        String username = txtTenDangNhap.getText().trim();
        String password = new String(txtMatKhau.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            lblThongBaoLoi.setText("Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        lblThongBaoLoi.setText("Đang đăng nhập...");
        lblThongBaoLoi.setForeground(new Color(37, 99, 235));

        thucHienDangNhap(username, password);
    }

    private void thucHienDangNhap(String username, String password) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    String jsonBody = String.format("{\"tenDangNhap\":\"%s\",\"matKhau\":\"%s\"}", username, password);

                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/api/auth/login"))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    SwingUtilities.invokeLater(() -> {
                        if (response.statusCode() == 200) {
                            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
                            String vaiTro = jsonResponse.has("vaiTro") ? jsonResponse.get("vaiTro").getAsString() : "CAN_BO";
                            String user = jsonResponse.has("tenDangNhap") ? jsonResponse.get("tenDangNhap").getAsString() : username;
                            moTrangChuTheoVaiTro(vaiTro, user);
                        } else {
                            lblThongBaoLoi.setForeground(new Color(211, 47, 47));
                            lblThongBaoLoi.setText("Sai tài khoản hoặc mật khẩu!");
                        }
                    });
                } catch (Exception ex) {
                    // Fallback khi backend chưa bật: hỗ trợ đăng nhập kiểm thử
                    SwingUtilities.invokeLater(() -> {
                        if (username.equalsIgnoreCase("admin") && password.equals("admin123")) {
                            moTrangChuTheoVaiTro("ADMIN", username);
                        } else if (username.toLowerCase().startsWith("canbo") && password.equals("canbo123")) {
                            moTrangChuTheoVaiTro("CAN_BO", username);
                        } else if (username.toLowerCase().startsWith("hocsinh") && password.equals("hs123")) {
                            moTrangChuTheoVaiTro("HOC_SINH", username);
                        } else {
                            lblThongBaoLoi.setForeground(new Color(211, 47, 47));
                            lblThongBaoLoi.setText("Không thể kết nối máy chủ (hoặc sai tài khoản)!");
                        }
                    });
                }
                return null;
            }
        };
        worker.execute();
    }

    private void moTrangChuTheoVaiTro(String vaiTro, String username) {
        JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        if (loginFrame != null) {
            loginFrame.dispose();
        }

        JFrame dashboardFrame = new JFrame();
        switch (vaiTro) {
            case "ADMIN":
                dashboardFrame.setTitle("Dashboard - Quản trị viên");
                dashboardFrame.setContentPane(new DashboardAdmin().getMainPanel());
                break;
            case "CAN_BO":
                dashboardFrame.setTitle("Cán Bộ - Quản lý sự kiện");
                dashboardFrame.setContentPane(new DashboardCanbo(username).getMainPanel());
                break;
            case "HOC_SINH":
                dashboardFrame.setTitle("Sinh viên - Sự kiện Liên Chi đoàn CNTT");
                dashboardFrame.setContentPane(new DashboardHocSinh(username).getMainPanel());
                break;
            default:
                JOptionPane.showMessageDialog(null, "Vai trò không hợp lệ!");
                return;
        }

        dashboardFrame.setSize(1150, 720);
        dashboardFrame.setLocationRelativeTo(null);
        dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboardFrame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void $$$setupUI$$$() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            JFrame frame = new JFrame("Đăng nhập hệ thống");
            Login login = new Login();
            frame.setContentPane(login.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(460, 560);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
