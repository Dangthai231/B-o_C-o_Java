package com.eventmanager.fe.gui.HocSinh;

import com.eventmanager.fe.api.ApiClient;
import com.google.gson.JsonObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

/**
 * Các hàm dùng chung cho màn hình Sinh viên: style, đọc JSON, chạy API ở luồng nền.
 */
public final class HocSinhUI {

    public static final Font FONT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Color PRIMARY = new Color(25, 118, 210);

    private static final DateTimeFormatter FMT_THOI_GIAN = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter FMT_NGAY = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private HocSinhUI() {
    }

    // ---------- Gọi API không làm treo giao diện ----------

    public interface ApiCall<T> {
        T call() throws ApiClient.ApiException;
    }

    /**
     * Chạy call() ở luồng nền (SwingWorker). Xong thì gọi onSuccess trên luồng giao diện,
     * lỗi thì hiện hộp thoại báo lỗi.
     */
    public static <T> void chayNen(Component parent, ApiCall<T> call, Consumer<T> onSuccess) {
        new SwingWorker<T, Void>() {
            @Override
            protected T doInBackground() throws Exception {
                return call.call();
            }

            @Override
            protected void done() {
                try {
                    onSuccess.accept(get());
                } catch (ExecutionException e) {
                    JOptionPane.showMessageDialog(parent, e.getCause().getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }.execute();
    }

    // ---------- Đọc JSON ----------

    public static String str(JsonObject o, String key) {
        return o.has(key) && !o.get(key).isJsonNull() ? o.get(key).getAsString() : "";
    }

    public static long num(JsonObject o, String key) {
        return o.has(key) && !o.get(key).isJsonNull() ? o.get(key).getAsLong() : 0;
    }

    // "2026-11-05T08:00:00" -> "05/11/2026 08:00"
    public static String thoiGian(String iso) {
        try {
            return iso.isEmpty() ? "" : LocalDateTime.parse(iso).format(FMT_THOI_GIAN);
        } catch (RuntimeException e) {
            return iso;
        }
    }

    // "2004-03-12" -> "12/03/2004"
    public static String ngay(String iso) {
        try {
            return iso.isEmpty() ? "" : LocalDate.parse(iso).format(FMT_NGAY);
        } catch (RuntimeException e) {
            return iso;
        }
    }

    public static boolean daBatDau(String isoBatDau) {
        try {
            return LocalDateTime.parse(isoBatDau).isBefore(LocalDateTime.now());
        } catch (RuntimeException e) {
            return false;
        }
    }

    // ---------- Thành phần giao diện ----------

    public static JPanel taoTrang(String tieuDe) {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel lbl = new JLabel(tieuDe);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lbl.setForeground(new Color(30, 30, 30));
        panel.add(lbl, BorderLayout.NORTH);
        return panel;
    }

    public static JButton taoNut(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static JButton taoNutChinh(String text) {
        JButton btn = taoNut(text);
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.WHITE);
        return btn;
    }

    public static DefaultTableModel taoModel(String... cot) {
        return new DefaultTableModel(cot, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public static JTable taoBang(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(FONT);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        return table;
    }

    public static JScrollPane cuon(JComponent c) {
        JScrollPane sp = new JScrollPane(c);
        sp.getViewport().setBackground(Color.WHITE);
        sp.setBorder(new LineBorder(new Color(200, 200, 200)));
        return sp;
    }

    // Ẩn cột (thường là cột id) nhưng vẫn giữ dữ liệu trong model
    public static void anCot(JTable table, int soCotDauCanAn) {
        for (int i = 0; i < soCotDauCanAn; i++) {
            table.removeColumn(table.getColumnModel().getColumn(0));
        }
    }

    // Lấy giá trị ở cột của model tại dòng đang chọn (đã tính đến sắp xếp); -1 nếu chưa chọn
    public static Object giaTriDongChon(JTable table, int cotModel) {
        int row = table.getSelectedRow();
        if (row < 0) return null;
        return table.getModel().getValueAt(table.convertRowIndexToModel(row), cotModel);
    }

    /**
     * Hộp thoại "Xem chi tiết sự kiện": lấy chi tiết, trạng thái đăng ký của sinh viên
     * và điểm đánh giá trung bình từ backend.
     */
    public static void hienChiTietSuKien(Component parent, long suKienId, String tenDangNhap) {
        chayNen(parent, () -> {
            JsonObject ct = ApiClient.get("/api/su-kien/" + suKienId).getAsJsonObject();
            JsonObject tt = ApiClient.get("/api/dang-ky/trang-thai?suKienId=" + suKienId
                    + "&tenDangNhap=" + ApiClient.encode(tenDangNhap)).getAsJsonObject();
            JsonObject dg = ApiClient.get("/api/danh-gia/su-kien/" + suKienId).getAsJsonObject();
            ct.add("trangThaiCuaToi", tt.get("trangThai"));
            ct.add("danhGia", dg);
            return ct;
        }, ct -> {
            JsonObject sk = ct.getAsJsonObject("suKien");
            JsonObject dg = ct.getAsJsonObject("danhGia");
            String soLuong = num(ct, "soDaDangKy") + " / " + (str(sk, "soLuongToiDa").isEmpty() ? "không giới hạn" : str(sk, "soLuongToiDa"));
            String danhGia = num(dg, "soLuot") == 0 ? "Chưa có"
                    : str(dg, "diemTrungBinh") + " / 5 sao (" + num(dg, "soLuot") + " lượt)";

            String html = "<html><body style='width:380px;font-family:Segoe UI'>"
                    + "<h2>" + str(sk, "tenSuKien") + "</h2>"
                    + "<b>Mã sự kiện:</b> " + str(sk, "maSuKien") + "<br>"
                    + "<b>Thời gian:</b> " + thoiGian(str(sk, "thoiGianBatDau")) + " - " + thoiGian(str(sk, "thoiGianKetThuc")) + "<br>"
                    + "<b>Địa điểm:</b> " + str(sk, "diaDiem") + "<br>"
                    + "<b>Đơn vị tổ chức:</b> " + str(sk, "donViToChuc") + "<br>"
                    + "<b>Đã đăng ký:</b> " + soLuong + "<br>"
                    + "<b>Trạng thái sự kiện:</b> " + str(sk, "trangThai") + "<br>"
                    + "<b>Đánh giá:</b> " + danhGia + "<br><br>"
                    + "<b>Trạng thái của bạn:</b> <span style='color:#1976d2'>" + str(ct, "trangThaiCuaToi") + "</span><br><br>"
                    + "<b>Mô tả:</b><br>" + str(sk, "moTa")
                    + "</body></html>";
            JOptionPane.showMessageDialog(parent, html, "Chi tiết sự kiện", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
