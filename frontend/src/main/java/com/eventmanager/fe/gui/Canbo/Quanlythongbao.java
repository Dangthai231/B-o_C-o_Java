package com.eventmanager.fe.gui.Canbo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Quanlythongbao {
    private JPanel mainPanel;
    private JTextField txtTimKiem;
    private JButton btnTimKiem;
    private JTable tableThongBao;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    private JTextField txtTieuDe;
    private JTextArea txtNoiDung;
    private JComboBox<String> cbDoiTuong;
    private JTextField txtNgayGui;
    private JComboBox<String> cbTrangThai;

    private JButton btnTaoThongBao;
    private JButton btnCapNhat;
    private JButton btnXoa;
    private JButton btnLamMoi;

    public Quanlythongbao() {
        initUI();
        loadData();
    }

    private void initUI() {
        mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Header
        JLabel lblTitle = new JLabel("QUẢN LÝ THÔNG BÁO SỰ KIỆN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(new Color(30, 30, 30));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Center: 2-Column Split
        JPanel contentGrid = new JPanel(new GridLayout(1, 2, 20, 0));
        contentGrid.setBackground(Color.WHITE);

        // ================= LEFT: DANH SÁCH THÔNG BÁO =================
        JPanel leftPanel = new JPanel(new BorderLayout(0, 10));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Danh sách thông báo",
                0, 0,
                new Font("Segoe UI", Font.PLAIN, 13)
        ));

        // Search row
        JPanel searchRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        searchRow.setBackground(Color.WHITE);
        searchRow.add(new JLabel("Tìm kiếm:"));

        txtTimKiem = new JTextField(20);
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtTimKiem.setBorder(new CompoundBorder(new LineBorder(new Color(180, 180, 180)), new EmptyBorder(3, 6, 3, 6)));
        searchRow.add(txtTimKiem);

        btnTimKiem = createSmallButton("Tìm");
        searchRow.add(btnTimKiem);
        leftPanel.add(searchRow, BorderLayout.NORTH);

        // Table
        String[] columns = {"Mã", "Tiêu đề", "Đối tượng", "Ngày gửi", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tableThongBao = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        tableThongBao.setRowSorter(sorter);
        tableThongBao.setRowHeight(26);
        tableThongBao.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tableThongBao.setGridColor(new Color(220, 220, 220));
        tableThongBao.setShowGrid(true);
        tableThongBao.setSelectionBackground(new Color(25, 118, 210));
        tableThongBao.setSelectionForeground(Color.WHITE);

        JTableHeader header = tableThongBao.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        header.setBackground(new Color(245, 245, 245));
        header.setForeground(Color.BLACK);

        tableThongBao.getColumnModel().getColumn(0).setPreferredWidth(60);
        tableThongBao.getColumnModel().getColumn(1).setPreferredWidth(120);
        tableThongBao.getColumnModel().getColumn(2).setPreferredWidth(90);
        tableThongBao.getColumnModel().getColumn(3).setPreferredWidth(90);
        tableThongBao.getColumnModel().getColumn(4).setPreferredWidth(80);

        JScrollPane scrollTable = new JScrollPane(tableThongBao);
        scrollTable.getViewport().setBackground(Color.WHITE);
        scrollTable.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        leftPanel.add(scrollTable, BorderLayout.CENTER);

        contentGrid.add(leftPanel);

        // ================= RIGHT: THÔNG TIN THÔNG BÁO =================
        JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Thông tin thông báo",
                0, 0,
                new Font("Segoe UI", Font.PLAIN, 13)
        ));

        JPanel formFields = new JPanel();
        formFields.setLayout(new BoxLayout(formFields, BoxLayout.Y_AXIS));
        formFields.setBackground(Color.WHITE);
        formFields.setBorder(new EmptyBorder(10, 15, 10, 15));

        // Tiêu đề
        JLabel lblTieuDe = new JLabel("Tiêu đề:");
        lblTieuDe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTieuDe.setAlignmentX(Component.LEFT_ALIGNMENT);
        formFields.add(lblTieuDe);
        formFields.add(Box.createVerticalStrut(4));

        txtTieuDe = new JTextField();
        txtTieuDe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtTieuDe.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtTieuDe.setBorder(new CompoundBorder(new LineBorder(new Color(180, 180, 180)), new EmptyBorder(3, 6, 3, 6)));
        txtTieuDe.setAlignmentX(Component.LEFT_ALIGNMENT);
        formFields.add(txtTieuDe);
        formFields.add(Box.createVerticalStrut(10));

        // Nội dung
        JLabel lblNoiDung = new JLabel("Nội dung:");
        lblNoiDung.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblNoiDung.setAlignmentX(Component.LEFT_ALIGNMENT);
        formFields.add(lblNoiDung);
        formFields.add(Box.createVerticalStrut(4));

        txtNoiDung = new JTextArea(8, 20);
        txtNoiDung.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNoiDung.setLineWrap(true);
        txtNoiDung.setWrapStyleWord(true);
        JScrollPane scrollNoiDung = new JScrollPane(txtNoiDung);
        scrollNoiDung.setBorder(new LineBorder(new Color(180, 180, 180)));
        scrollNoiDung.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollNoiDung.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        scrollNoiDung.setPreferredSize(new Dimension(300, 140));
        formFields.add(scrollNoiDung);
        formFields.add(Box.createVerticalStrut(12));

        // Đối tượng
        JPanel rowDoiTuong = new JPanel(new BorderLayout(10, 0));
        rowDoiTuong.setBackground(Color.WHITE);
        rowDoiTuong.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        rowDoiTuong.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDoiTuong = new JLabel("Đối tượng:");
        lblDoiTuong.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDoiTuong.setPreferredSize(new Dimension(80, 25));
        rowDoiTuong.add(lblDoiTuong, BorderLayout.WEST);

        cbDoiTuong = new JComboBox<>(new String[]{"Tất cả người tham gia", "Ban tổ chức", "Đại biểu", "Khách mời", "Sinh viên"});
        cbDoiTuong.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbDoiTuong.setBackground(Color.WHITE);
        rowDoiTuong.add(cbDoiTuong, BorderLayout.CENTER);
        formFields.add(rowDoiTuong);
        formFields.add(Box.createVerticalStrut(8));

        // Ngày gửi
        JPanel rowNgayGui = new JPanel(new BorderLayout(10, 0));
        rowNgayGui.setBackground(Color.WHITE);
        rowNgayGui.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        rowNgayGui.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblNgayGui = new JLabel("Ngày gửi:");
        lblNgayGui.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblNgayGui.setPreferredSize(new Dimension(80, 25));
        rowNgayGui.add(lblNgayGui, BorderLayout.WEST);

        txtNgayGui = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtNgayGui.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNgayGui.setBorder(new CompoundBorder(new LineBorder(new Color(180, 180, 180)), new EmptyBorder(3, 6, 3, 6)));
        rowNgayGui.add(txtNgayGui, BorderLayout.CENTER);
        formFields.add(rowNgayGui);
        formFields.add(Box.createVerticalStrut(8));

        // Trạng thái
        JPanel rowTrangThai = new JPanel(new BorderLayout(10, 0));
        rowTrangThai.setBackground(Color.WHITE);
        rowTrangThai.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        rowTrangThai.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTrangThai.setPreferredSize(new Dimension(80, 25));
        rowTrangThai.add(lblTrangThai, BorderLayout.WEST);

        cbTrangThai = new JComboBox<>(new String[]{"Đã gửi", "Nháp", "Đã hủy"});
        cbTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbTrangThai.setBackground(Color.WHITE);
        rowTrangThai.add(cbTrangThai, BorderLayout.CENTER);
        formFields.add(rowTrangThai);

        rightPanel.add(formFields, BorderLayout.CENTER);

        // Buttons row
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBackground(Color.WHITE);

        btnTaoThongBao = createButton("Tạo thông báo");
        btnCapNhat = createButton("Cập nhật");
        btnXoa = createButton("Xóa");
        btnLamMoi = createButton("Làm mới");

        buttonsPanel.add(btnTaoThongBao);
        buttonsPanel.add(btnCapNhat);
        buttonsPanel.add(btnXoa);
        buttonsPanel.add(btnLamMoi);

        rightPanel.add(buttonsPanel, BorderLayout.SOUTH);

        contentGrid.add(rightPanel);
        mainPanel.add(contentGrid, BorderLayout.CENTER);

        // Selection Listener
        tableThongBao.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tableThongBao.getSelectedRow();
                if (row >= 0) {
                    int modelRow = tableThongBao.convertRowIndexToModel(row);
                    txtTieuDe.setText((String) tableModel.getValueAt(modelRow, 1));
                    String dt = (String) tableModel.getValueAt(modelRow, 2);
                    for (int i = 0; i < cbDoiTuong.getItemCount(); i++) {
                        if (cbDoiTuong.getItemAt(i).equalsIgnoreCase(dt)) {
                            cbDoiTuong.setSelectedIndex(i);
                            break;
                        }
                    }
                    txtNgayGui.setText((String) tableModel.getValueAt(modelRow, 3));
                    String tt = (String) tableModel.getValueAt(modelRow, 4);
                    cbTrangThai.setSelectedItem(tt);
                }
            }
        });

        // Search Action
        btnTimKiem.addActionListener(e -> {
            String query = txtTimKiem.getText().trim();
            if (query.isEmpty()) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
            }
        });

        // Create Action
        btnTaoThongBao.addActionListener(e -> {
            String tieuDe = txtTieuDe.getText().trim();
            if (tieuDe.isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, "Vui lòng nhập tiêu đề thông báo!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int newNum = tableModel.getRowCount() + 35;
            String newMa = "TB0" + newNum;
            tableModel.insertRow(0, new Object[]{
                    newMa,
                    tieuDe,
                    cbDoiTuong.getSelectedItem(),
                    txtNgayGui.getText().trim(),
                    cbTrangThai.getSelectedItem()
            });
            JOptionPane.showMessageDialog(mainPanel, "Tạo thông báo " + newMa + " thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        // Update Action
        btnCapNhat.addActionListener(e -> {
            int row = tableThongBao.getSelectedRow();
            if (row >= 0) {
                int modelRow = tableThongBao.convertRowIndexToModel(row);
                tableModel.setValueAt(txtTieuDe.getText().trim(), modelRow, 1);
                tableModel.setValueAt(cbDoiTuong.getSelectedItem(), modelRow, 2);
                tableModel.setValueAt(txtNgayGui.getText().trim(), modelRow, 3);
                tableModel.setValueAt(cbTrangThai.getSelectedItem(), modelRow, 4);
                JOptionPane.showMessageDialog(mainPanel, "Cập nhật thông báo thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Vui lòng chọn thông báo cần cập nhật từ danh sách!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Delete Action
        btnXoa.addActionListener(e -> {
            int row = tableThongBao.getSelectedRow();
            if (row >= 0) {
                int modelRow = tableThongBao.convertRowIndexToModel(row);
                int confirm = JOptionPane.showConfirmDialog(mainPanel, "Bạn có chắc muốn xóa thông báo này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(modelRow);
                    resetForm();
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Vui lòng chọn thông báo cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Reset Action
        btnLamMoi.addActionListener(e -> {
            resetForm();
            loadData();
            txtTimKiem.setText("");
            sorter.setRowFilter(null);
        });
    }

    private void resetForm() {
        txtTieuDe.setText("");
        txtNoiDung.setText("");
        cbDoiTuong.setSelectedIndex(0);
        cbTrangThai.setSelectedIndex(0);
        txtNgayGui.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        tableThongBao.clearSelection();
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(30, 30, 30));
        btn.setFocusPainted(false);
        btn.setBorder(new CompoundBorder(
                new LineBorder(new Color(190, 190, 190), 1),
                new EmptyBorder(5, 12, 5, 12)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton createSmallButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(30, 30, 30));
        btn.setFocusPainted(false);
        btn.setBorder(new CompoundBorder(
                new LineBorder(new Color(190, 190, 190), 1),
                new EmptyBorder(3, 14, 3, 14)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadData() {
        tableModel.setRowCount(0);

        // Sample data matching WinForms screenshot exactly
        Object[][] data = {
                {"TB034", "A", "Đại biểu", "25/10/2026", "Đã gửi"},
                {"TB033", "A", "Đại biểu", "25/10/2026", "Nháp"},
                {"TB031", "Thong bao su kien", "Đại biểu", "25/10/2026", "Nháp"},
                {"TB030", "Thông báo tổng kết", "Tất cả người tham gia", "19/10/2026", "Đã gửi"},
                {"TB029", "Họp chuẩn bị đại hội", "Ban tổ chức", "15/10/2026", "Đã gửi"},
                {"TB028", "Thông báo lịch trình", "Tất cả người tham gia", "01/10/2026", "Đã gửi"},
                {"TB027", "Hủy sự kiện đột xuất", "Tất cả người tham gia", "22/09/2026", "Đã hủy"},
                {"TB026", "Hướng dẫn điểm danh", "Ban tổ chức", "12/09/2026", "Đã gửi"},
                {"TB025", "Thông báo địa điểm", "Tất cả người tham gia", "01/09/2026", "Đã gửi"},
                {"TB024", "Lịch đón tiếp khách mời", "Ban tổ chức", "15/08/2026", "Đã gửi"},
                {"TB023", "Nháp: Thư ngỏ", "Tất cả người tham gia", "01/08/2026", "Nháp"},
                {"TB022", "Cung cấp tài liệu", "Đại biểu", "10/07/2026", "Đã gửi"},
                {"TB021", "Kế hoạch tuần mới", "Tất cả người tham gia", "05/07/2026", "Đã gửi"},
                {"TB020", "Tổng kết chiến dịch", "Ban tổ chức", "30/06/2026", "Đã gửi"},
                {"TB019", "Hủy buổi giao lưu", "Khách mời", "14/06/2026", "Đã hủy"},
                {"TB018", "Tập huấn cán bộ", "Tất cả người tham gia", "08/06/2026", "Đã gửi"},
                {"TB017", "Công bố kết quả", "Tất cả người tham gia", "01/06/2026", "Đã gửi"},
                {"TB016", "Nháp: Thông báo nội bộ", "Tất cả người tham gia", "25/05/2026", "Nháp"},
                {"TB015", "Nhắc nhở nộp báo cáo", "Ban tổ chức", "18/05/2026", "Đã gửi"}
        };

        for (Object[] row : data) {
            tableModel.addRow(row);
        }

        // Try load from backend if available
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/api/thong-bao/danh-sach"))
                            .GET()
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if (response.statusCode() == 200) {
                        JsonArray arr = JsonParser.parseString(response.body()).getAsJsonArray();
                        if (arr.size() > 0) {
                            SwingUtilities.invokeLater(() -> {
                                tableModel.setRowCount(0);
                                for (JsonElement elem : arr) {
                                    JsonObject obj = elem.getAsJsonObject();
                                    String ma = obj.has("maThongBao") && !obj.get("maThongBao").isJsonNull() ? obj.get("maThongBao").getAsString() : "TB0" + obj.get("id").getAsLong();
                                    String tieuDe = obj.has("tieuDe") && !obj.get("tieuDe").isJsonNull() ? obj.get("tieuDe").getAsString() : "";
                                    String doiTuong = obj.has("doiTuong") && !obj.get("doiTuong").isJsonNull() ? obj.get("doiTuong").getAsString() : "Tất cả";
                                    String ngay = obj.has("ngayGui") && !obj.get("ngayGui").isJsonNull() ? obj.get("ngayGui").getAsString() : "25/10/2026";
                                    String tt = obj.has("trangThai") && !obj.get("trangThai").isJsonNull() ? obj.get("trangThai").getAsString() : "Đã gửi";
                                    tableModel.addRow(new Object[]{ma, tieuDe, doiTuong, ngay, tt});
                                }
                            });
                        }
                    }
                } catch (Exception ignored) {}
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
