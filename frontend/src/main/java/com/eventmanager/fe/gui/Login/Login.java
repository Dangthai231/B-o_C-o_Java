package com.eventmanager.fe.gui.Login;

import com.eventmanager.fe.gui.Canbo.DashboardCanbo;
import com.eventmanager.fe.gui.Admin.DashboardAdmin;
import com.eventmanager.fe.gui.HocSinh.DashboardHocSinh;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;

public class Login {
    private JPanel mainPanel;
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap;
    private JLabel lblThongBaoLoi;

    public Login() {
        $$$setupUI$$$();

        btnDangNhap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtTenDangNhap.getText();
                // Lấy password từ JPasswordField phải ép kiểu mảng char về String
                String password = new String(txtMatKhau.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    lblThongBaoLoi.setText("Vui lòng nhập đầy đủ thông tin!");
                    return;
                }

                // Hiển thị trạng thái đang xử lý
                lblThongBaoLoi.setText("Đang đăng nhập...");

                // Gọi API ngầm bằng SwingWorker
                thucHienDangNhap(username, password);
            }
        });
    }

    private void thucHienDangNhap(String username, String password) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    // Tạo chuỗi JSON gửi lên server
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
                            String vaiTro = jsonResponse.get("vaiTro").getAsString();

                            // Phân quyền điều hướng
                            moTrangChuTheoVaiTro(vaiTro);
                        } else {
                            lblThongBaoLoi.setText("Sai tài khoản hoặc mật khẩu!");
                        }
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> lblThongBaoLoi.setText("Không thể kết nối đến máy chủ!"));
                }
                return null;
            }
        };
        worker.execute();
    }

    private void moTrangChuTheoVaiTro(String vaiTro) {
        // Tắt cửa sổ đăng nhập hiện tại
        JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        if (loginFrame != null) {
            loginFrame.dispose();
        }

        // Mở Dashboard tương ứng
        JFrame dashboardFrame = new JFrame();
        switch (vaiTro) {
            case "ADMIN":
                dashboardFrame.setTitle("Dashboard - Quản trị viên");
                dashboardFrame.setContentPane(new DashboardAdmin().getMainPanel());
                break;
            case "CAN_BO":
                dashboardFrame.setTitle("Dashboard - Cán bộ sự kiện");
                dashboardFrame.setContentPane(new DashboardCanbo().getMainPanel());
                break;
            case "HOC_SINH":
                dashboardFrame.setTitle("Dashboard - Học sinh");
                dashboardFrame.setContentPane(new DashboardHocSinh().getMainPanel());
                break;
            default:
                JOptionPane.showMessageDialog(null, "Vai trò không hợp lệ!");
                return;
        }

        dashboardFrame.setSize(1200, 700);
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
            JFrame frame = new JFrame("Đăng nhập hệ thống");
            Login login = new Login();
            frame.setContentPane(login.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(500, 350);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
