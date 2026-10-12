package Canbo;

import javax.swing.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
        $$$setupUI$$$();
        loadDuLieu(); // Gọi hàm khi vừa mở form
    }

    private void loadDuLieu() {
        // Gọi API backend để lấy số liệu thống kê
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
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
                            lblSoSuKien.setText(String.valueOf(json.get("soSuKien").getAsInt()));
                            lblSoCongViec.setText(String.valueOf(json.get("soCongViec").getAsInt()));
                            lblSoNguoiThamGia.setText(String.valueOf(json.get("soNguoiThamGia").getAsInt()));
                            lblSoTaiLieu.setText(String.valueOf(json.get("soTaiLieu").getAsInt()));
                            lblSoThongBao.setText(String.valueOf(json.get("soThongBao").getAsInt()));
                            lblSoBaoCao.setText(String.valueOf(json.get("soBaoCao").getAsInt()));
                        });
                    }
                } catch (Exception ex) {
                    // Nếu không kết nối được backend, giữ nguyên giá trị mặc định trên form
                    System.out.println("[Trangchu] Không thể kết nối backend: " + ex.getMessage());
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
        // IntelliJ GUI Designer sẽ inject code tạo giao diện từ Trangchu.form
    }
}
