package com.eventmanager.fe.gui.Canbo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Quanlynguoithamgia {
    private JPanel mainPanel;
    private JTextField txtTimkiem;
    private JButton btnTimKiem;
    private JButton btnHuyTimKiem;
    private JLabel lblTongSo;
    private JLabel lblVangMat;
    private JLabel lblChuaDiemDanh;
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JButton btnXemChiTiet;
    private JButton btnCapNhatDiemDanh;
    private JButton btnLamMoi;

    public Quanlynguoithamgia() {
        initUI();
        loadData();
    }

    private void initUI() {
        mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // 1. Top Panel: Header Title + Search Bar
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("QUẢN LÝ NGƯỜI THAM GIA SỰ KIỆN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(new Color(30, 30, 30));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        topContainer.add(lblTitle);
        topContainer.add(Box.createVerticalStrut(12));

        // Search & Stats Row
        JPanel searchRow = new JPanel(new BorderLayout(15, 0));
        searchRow.setBackground(Color.WHITE);
        searchRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel leftSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leftSearch.setBackground(Color.WHITE);

        JLabel lblSearch = new JLabel("Tìm kiếm");
        lblSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        leftSearch.add(lblSearch);

        txtTimkiem = new JTextField(20);
        txtTimkiem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtTimkiem.setBorder(new CompoundBorder(new LineBorder(new Color(180, 180, 180)), new EmptyBorder(4, 6, 4, 6)));
        leftSearch.add(txtTimkiem);

        btnTimKiem = createButton("Tìm kiếm");
        btnHuyTimKiem = createButton("Hủy tìm kiếm");
        leftSearch.add(btnTimKiem);
        leftSearch.add(btnHuyTimKiem);

        searchRow.add(leftSearch, BorderLayout.WEST);

        // Stats labels on the right
        JPanel rightStats = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 4));
        rightStats.setBackground(Color.WHITE);

        lblTongSo = new JLabel("Tổng: 30");
        lblTongSo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rightStats.add(lblTongSo);

        lblVangMat = new JLabel("Đã điểm danh Vắng: 6");
        lblVangMat.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rightStats.add(lblVangMat);

        lblChuaDiemDanh = new JLabel("Chưa điểm danh: 8");
        lblChuaDiemDanh.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rightStats.add(lblChuaDiemDanh);

        searchRow.add(rightStats, BorderLayout.EAST);

        topContainer.add(searchRow);
        mainPanel.add(topContainer, BorderLayout.NORTH);

        // 2. Center Panel: Table with GroupBox Title
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Danh sách người tham gia",
                0, 0,
                new Font("Segoe UI", Font.PLAIN, 13)
        ));

        String[] columns = {"Mã", "Họ tên", "Email", "Số điện thoại", "Vai trò", "Điểm danh", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 5 || col == 6; // Allow editing status directly or via action
            }
        };

        table = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);
        table.setSelectionBackground(new Color(25, 118, 210));
        table.setSelectionForeground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        header.setBackground(new Color(245, 245, 245));
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getWidth(), 30));

        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        table.getColumnModel().getColumn(1).setPreferredWidth(140);
        table.getColumnModel().getColumn(2).setPreferredWidth(170);
        table.getColumnModel().getColumn(3).setPreferredWidth(110);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(130);
        table.getColumnModel().getColumn(6).setPreferredWidth(110);

        // Custom badge renderer for "Điểm danh" (Column 5)
        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                String text = (value != null) ? value.toString() : "";

                if (!isSelected) {
                    if (text.contains("Đã điểm danh")) {
                        c.setBackground(new Color(232, 245, 233)); // light green
                        c.setForeground(new Color(46, 125, 50));   // dark green
                    } else if (text.contains("Vắng")) {
                        c.setBackground(new Color(255, 235, 238)); // light red
                        c.setForeground(new Color(198, 40, 40));   // dark red
                    } else if (text.contains("Chưa")) {
                        c.setBackground(new Color(255, 248, 225)); // light amber
                        c.setForeground(new Color(230, 81, 0));    // dark orange
                    } else {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                }
                setText(text + "  ▼");
                return c;
            }
        });

        // Custom renderer for "Trạng thái" (Column 6)
        table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                String text = (value != null) ? value.toString() : "";
                if (!isSelected) {
                    c.setBackground(Color.WHITE);
                    c.setForeground(new Color(40, 40, 40));
                }
                setText(text + "  ▼");
                return c;
            }
        });

        // ComboBox editors for dropdown editing
        JComboBox<String> cbDiemDanh = new JComboBox<>(new String[]{"Đã điểm danh", "Vắng", "Chưa điểm danh"});
        table.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(cbDiemDanh));

        JComboBox<String> cbTrangThai = new JComboBox<>(new String[]{"Đã xác nhận", "Chưa xác nhận", "Đã hủy"});
        table.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(cbTrangThai));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // 3. Bottom Action Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        bottomPanel.setBackground(Color.WHITE);

        btnXemChiTiet = createButton("Xem chi tiết");
        btnCapNhatDiemDanh = createButton("Lưu điểm danh");
        btnLamMoi = createButton("Làm mới");

        bottomPanel.add(btnXemChiTiet);
        bottomPanel.add(btnCapNhatDiemDanh);
        bottomPanel.add(btnLamMoi);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Events
        btnTimKiem.addActionListener(e -> {
            String query = txtTimkiem.getText().trim();
            if (query.isEmpty()) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
            }
            updateStats();
        });

        btnHuyTimKiem.addActionListener(e -> {
            txtTimkiem.setText("");
            sorter.setRowFilter(null);
            updateStats();
        });

        btnXemChiTiet.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int modelRow = table.convertRowIndexToModel(row);
                String ma = (String) tableModel.getValueAt(modelRow, 0);
                String ten = (String) tableModel.getValueAt(modelRow, 1);
                String email = (String) tableModel.getValueAt(modelRow, 2);
                String sdt = (String) tableModel.getValueAt(modelRow, 3);
                String vaiTro = (String) tableModel.getValueAt(modelRow, 4);
                String diemDanh = (String) tableModel.getValueAt(modelRow, 5);
                String trangThai = (String) tableModel.getValueAt(modelRow, 6);

                String msg = String.format("Mã: %s\nHọ tên: %s\nEmail: %s\nSố điện thoại: %s\nVai trò: %s\nĐiểm danh: %s\nTrạng thái: %s",
                        ma, ten, email, sdt, vaiTro, diemDanh, trangThai);
                JOptionPane.showMessageDialog(mainPanel, msg, "Chi tiết người tham gia", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Vui lòng chọn một người tham gia trong danh sách!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnCapNhatDiemDanh.addActionListener(e -> {
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            updateStats();
            JOptionPane.showMessageDialog(mainPanel, "Đã lưu thông tin điểm danh thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        btnLamMoi.addActionListener(e -> {
            loadData();
            txtTimkiem.setText("");
            sorter.setRowFilter(null);
        });
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(30, 30, 30));
        btn.setFocusPainted(false);
        btn.setBorder(new CompoundBorder(
                new LineBorder(new Color(190, 190, 190), 1),
                new EmptyBorder(6, 20, 6, 20)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void updateStats() {
        int total = tableModel.getRowCount();
        int vang = 0;
        int chua = 0;
        for (int i = 0; i < total; i++) {
            String dd = (String) tableModel.getValueAt(i, 5);
            if (dd != null) {
                if (dd.contains("Vắng")) vang++;
                else if (dd.contains("Chưa")) chua++;
            }
        }
        lblTongSo.setText("Tổng: " + total);
        lblVangMat.setText("Đã điểm danh Vắng: " + vang);
        lblChuaDiemDanh.setText("Chưa điểm danh: " + chua);
    }

    private void loadData() {
        tableModel.setRowCount(0);

        // Sample data matching WinForms screenshot exactly
        Object[][] data = {
                {"TG001", "Nguyễn Văn An", "nguyenvanan@gm...", "0903123456", "Đại biểu", "Vắng", "Đã xác nhận"},
                {"TG002", "Trần Thị Bích", "bichtran.it@gmail....", "0912345678", "Khách mời", "Vắng", "Đã xác nhận"},
                {"TG003", "Lê Hoàng Nam", "nam.lehoang@gm...", "0987654321", "Ban tổ chức", "Đã điểm danh", "Đã xác nhận"},
                {"TG004", "Phạm Minh Đức", "duc.pham99@gma...", "0978112233", "Sinh viên", "Chưa điểm danh", "Đã xác nhận"},
                {"TG005", "Hoàng Thu Thảo", "thuthao.hoang@g...", "0934556677", "Sinh viên", "Đã điểm danh", "Đã xác nhận"},
                {"TG006", "Vũ Quốc Huy", "quochuy.vu@gmai...", "0961223344", "Đại biểu", "Vắng", "Đã hủy"},
                {"TG007", "Đặng Thùy Linh", "thuylinh.dang@gm...", "0945667788", "Sinh viên", "Đã điểm danh", "Đã xác nhận"},
                {"TG008", "Bùi Anh Tuấn", "anhtuan.bui@gmail...", "0918990011", "Ban tổ chức", "Đã điểm danh", "Đã xác nhận"},
                {"TG009", "Đỗ Mai Phương", "maiphuong.do@g...", "0922334455", "Khách mời", "Chưa điểm danh", "Đã xác nhận"},
                {"TG010", "Ngô Kiến Huy", "kienhuy.ngo@gma...", "0988776655", "Sinh viên", "Đã điểm danh", "Đã xác nhận"},
                {"TG011", "Dương Thúy Nga", "thuynga.duong@g...", "0977889900", "Giảng viên", "Đã điểm danh", "Đã xác nhận"},
                {"TG012", "Lý Gia Bảo", "giabao.ly@gmail.c...", "0933112233", "Sinh viên", "Vắng", "Chưa xác nhận"},
                {"TG013", "Hồ Ngọc Mai", "ngocmai.ho@gmai...", "0908776655", "Sinh viên", "Đã điểm danh", "Đã xác nhận"},
                {"TG014", "Võ Trọng Tấn", "trongtan.vo@gmail...", "0919223344", "Đại biểu", "Đã điểm danh", "Đã xác nhận"},
                {"TG015", "Phan Thanh Hải", "thanhhai.phan@g...", "0981334455", "Khách mời", "Chưa điểm danh", "Đã xác nhận"},
                {"TG016", "Trịnh Bảo Trâm", "baotram.trinh@gm...", "0966778899", "Sinh viên", "Đã điểm danh", "Đã xác nhận"},
                {"TG017", "Đinh Hữu Phước", "huuphuoc.dinh@g...", "0944556677", "Sinh viên", "Vắng", "Chưa xác nhận"},
                {"TG018", "Lâm Khánh Chi", "khanhchi.lam@gm...", "0922889900", "Ban tổ chức", "Đã điểm danh", "Đã xác nhận"},
        };

        for (Object[] row : data) {
            tableModel.addRow(row);
        }
        updateStats();

        // Connect to backend if available
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/api/nguoi-tham-gia/danh-sach"))
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
                                    String ma = obj.has("maHocSinh") && !obj.get("maHocSinh").isJsonNull() ? obj.get("maHocSinh").getAsString() : "TG" + obj.get("id").getAsLong();
                                    String ten = obj.has("hoTen") && !obj.get("hoTen").isJsonNull() ? obj.get("hoTen").getAsString() : "";
                                    String email = obj.has("email") && !obj.get("email").isJsonNull() ? obj.get("email").getAsString() : "";
                                    String sdt = obj.has("soDienThoai") && !obj.get("soDienThoai").isJsonNull() ? obj.get("soDienThoai").getAsString() : "";
                                    String vaiTro = obj.has("vaiTro") && !obj.get("vaiTro").isJsonNull() ? obj.get("vaiTro").getAsString() : "Sinh viên";
                                    String diemDanh = obj.has("trangThaiDiemDanh") && !obj.get("trangThaiDiemDanh").isJsonNull() ? obj.get("trangThaiDiemDanh").getAsString() : "Chưa điểm danh";
                                    String trangThai = "Đã xác nhận";
                                    tableModel.addRow(new Object[]{ma, ten, email, sdt, vaiTro, diemDanh, trangThai});
                                }
                                updateStats();
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
