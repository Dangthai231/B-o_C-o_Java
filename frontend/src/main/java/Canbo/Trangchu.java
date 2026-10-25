package Canbo;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Trangchu {
    private JPanel mainPanel;
    private JLabel lblLoiChaoFooter;
    private JLabel lblSoSuKien;
    private JLabel lblSoCongViec;
    private JLabel lblSoNguoiThamGia;
    private JLabel lblSoTaiLieu;
    private JLabel lblSoThongBao;
    private JLabel lblSoBaoCao;

    public Trangchu() {
        initUI();
        loadDuLieu();
    }

    private void initUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // Header Title
        JLabel lblTitle = new JLabel("Tổng quan sự kiện");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(30, 30, 30));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Center: 6 Cards in 2 rows x 3 columns
        JPanel cardsGrid = new JPanel(new GridLayout(2, 3, 20, 20));
        cardsGrid.setBackground(Color.WHITE);

        // Card 1: Sự kiện đang tổ chức
        lblSoSuKien = new JLabel("3");
        cardsGrid.add(createStatCard(lblSoSuKien, "Sự kiện đang tổ chức", new Color(30, 136, 229)));

        // Card 2: Sự kiện đã hoàn thành
        lblSoCongViec = new JLabel("7");
        cardsGrid.add(createStatCard(lblSoCongViec, "Sự kiện đã hoàn thành", new Color(46, 125, 50)));

        // Card 3: Người tham gia
        lblSoNguoiThamGia = new JLabel("30");
        cardsGrid.add(createStatCard(lblSoNguoiThamGia, "Người tham gia", new Color(230, 81, 0)));

        // Card 4: Tài liệu sự kiện
        lblSoTaiLieu = new JLabel("2");
        cardsGrid.add(createStatCard(lblSoTaiLieu, "Tài liệu sự kiện", new Color(142, 36, 170)));

        // Card 5: Thông báo đã gửi
        lblSoThongBao = new JLabel("33");
        cardsGrid.add(createStatCard(lblSoThongBao, "Thông báo đã gửi", new Color(229, 57, 53)));

        // Card 6: Báo cáo hoàn thành
        lblSoBaoCao = new JLabel("2");
        cardsGrid.add(createStatCard(lblSoBaoCao, "Báo cáo hoàn thành", new Color(2, 136, 209)));

        mainPanel.add(cardsGrid, BorderLayout.CENTER);

        // Footer Card
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(230, 235, 240), 1),
                new EmptyBorder(14, 18, 14, 18)
        ));

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        lblLoiChaoFooter = new JLabel("Ngày " + today + " - Chúc bạn một ngày làm việc hiệu quả và quản lý sự kiện thành công!");
        lblLoiChaoFooter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblLoiChaoFooter.setForeground(new Color(60, 60, 60));
        footerPanel.add(lblLoiChaoFooter, BorderLayout.WEST);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createStatCard(JLabel numLabel, String title, Color numColor) {
        JPanel card = new JPanel(new BorderLayout(0, 15));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(230, 235, 240), 1),
                new EmptyBorder(25, 25, 25, 25)
        ));

        numLabel.setFont(new Font("Segoe UI", Font.BOLD, 46));
        numLabel.setForeground(numColor);
        card.add(numLabel, BorderLayout.CENTER);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTitle.setForeground(new Color(110, 110, 110));
        card.add(lblTitle, BorderLayout.SOUTH);

        return card;
    }

    private void loadDuLieu() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/api/thongke/tong-quan"))
                            .GET()
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    if (response.statusCode() == 200) {
                        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                        SwingUtilities.invokeLater(() -> {
                            if (json.has("soSuKien") && lblSoSuKien != null)
                                lblSoSuKien.setText(String.valueOf(json.get("soSuKien").getAsInt()));
                            if (json.has("soCongViec") && lblSoCongViec != null)
                                lblSoCongViec.setText(String.valueOf(json.get("soCongViec").getAsInt()));
                            if (json.has("soNguoiThamGia") && lblSoNguoiThamGia != null)
                                lblSoNguoiThamGia.setText(String.valueOf(json.get("soNguoiThamGia").getAsInt()));
                            if (json.has("soTaiLieu") && lblSoTaiLieu != null)
                                lblSoTaiLieu.setText(String.valueOf(json.get("soTaiLieu").getAsInt()));
                            if (json.has("soThongBao") && lblSoThongBao != null)
                                lblSoThongBao.setText(String.valueOf(json.get("soThongBao").getAsInt()));
                            if (json.has("soBaoCao") && lblSoBaoCao != null)
                                lblSoBaoCao.setText(String.valueOf(json.get("soBaoCao").getAsInt()));
                        });
                    }
                } catch (Exception ex) {
                    System.out.println("[Trangchu] Backend offline hoặc chưa có dữ liệu: " + ex.getMessage());
                }
                return null;
            }
        };
        worker.execute();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void $$$setupUI$$$() {
    }
}
