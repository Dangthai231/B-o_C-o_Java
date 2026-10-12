package com.eventmanager.fe.gui.Canbo;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lapbaocaothongke {
    private JComboBox cbLoaiBaoCao;

    private JPanel pnlNgayBatDau;
    private JPanel pnlNgayKetThuc;
    private JPanel mainPanel;
    private JButton xemBáoCáoButton;
    private JButton xuấtExcelButton;
    private JTextArea textArea1;

    //khai báo 2 biến Date chooser
    private JDateChooser dateChooserNgayKetThuc;
    private JDateChooser dateChooserBatDau;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    //hàm khởi tạo
    public Lapbaocaothongke() {
        // Hàm này tự động liên kết code với file .form
        $$$setupUI$$$();
        //khởi tạo và đưa vào Panel ngày bắt đầu
        dateChooserBatDau = new JDateChooser();
        dateChooserBatDau.setDateFormatString("dd/MM/yyyy");
        if (pnlNgayBatDau != null) {
            pnlNgayBatDau.setLayout(new BoxLayout(pnlNgayBatDau, BoxLayout.Y_AXIS));
            pnlNgayBatDau.add(dateChooserBatDau);
            pnlNgayBatDau.revalidate();
            pnlNgayBatDau.repaint();
        }

        dateChooserNgayKetThuc = new JDateChooser();
        dateChooserNgayKetThuc.setDateFormatString("dd/MM/yyyy");
        if (pnlNgayKetThuc != null) {
            pnlNgayKetThuc.setLayout(new BoxLayout(pnlNgayKetThuc, BoxLayout.Y_AXIS));
            pnlNgayKetThuc.add(dateChooserNgayKetThuc);
            pnlNgayKetThuc.revalidate();
            pnlNgayKetThuc.repaint();
        }

        // Cấu hình JTextArea hiển thị báo cáo
        if (textArea1 != null) {
            textArea1.setEditable(false);
            textArea1.setFont(new Font("Monospaced", Font.PLAIN, 13));
            textArea1.setLineWrap(true);
            textArea1.setWrapStyleWord(true);
            textArea1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            // Nội dung mặc định hướng dẫn
            textArea1.setText(hienThiHuongDan());
        }

        // Gắn sự kiện nút Xem báo cáo
        if (xemBáoCáoButton != null) {
            xemBáoCáoButton.addActionListener(e -> xemBaoCao());
        }

        // Gắn sự kiện nút Xuất excel
        if (xuấtExcelButton != null) {
            xuấtExcelButton.addActionListener(e -> xuatExcel());
        }
    }

    /**
     * Hiển thị hướng dẫn mặc định khi chưa xem báo cáo.
     */
    private String hienThiHuongDan() {
        StringBuilder sb = new StringBuilder();
        sb.append("HƯỚNG DẪN SỬ DỤNG CHỨC NĂNG LẬP BÁO CÁO - THỐNG KÊ\n");
        sb.append("═══════════════════════════════════════════════════════\n\n");
        sb.append("1. Chọn \"Loại báo cáo\" từ danh sách thả xuống:\n");
        sb.append("   - Báo cáo tổng quan sự kiện\n");
        sb.append("   - Báo cáo tiến độ công việc\n");
        sb.append("   - Báo cáo người tham gia\n\n");
        sb.append("2. Chọn khoảng thời gian (Từ ngày - Đến ngày) để lọc dữ liệu.\n\n");
        sb.append("3. Nhấn nút \"Xem báo cáo\" để hiển thị báo cáo dạng văn bản tại đây.\n\n");
        sb.append("4. Nhấn nút \"Xuất excel\" để xuất báo cáo ra file .xlsx.\n\n");
        sb.append("═══════════════════════════════════════════════════════\n");
        sb.append("Lưu ý: Nếu không chọn ngày, hệ thống sẽ dùng khoảng thời gian mặc định (30 ngày gần nhất).");
        return sb.toString();
    }

    /**
     * Xử lý khi nhấn nút Xem báo cáo: sinh nội dung báo cáo dạng văn bản.
     */
    private void xemBaoCao() {
        if (textArea1 == null) return;

        String loaiBaoCao = (String) cbLoaiBaoCao.getSelectedItem();
        Date tuNgay = dateChooserBatDau.getDate();
        Date denNgay = dateChooserNgayKetThuc.getDate();

        // Xử lý khoảng ngày mặc định nếu người dùng chưa chọn
        if (tuNgay == null) {
            tuNgay = new Date(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000);
        }
        if (denNgay == null) {
            denNgay = new Date();
        }

        if (tuNgay.after(denNgay)) {
            JOptionPane.showMessageDialog(mainPanel,
                    "Ngày bắt đầu không được lớn hơn ngày kết thúc!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String noiDung = taoNoiDungBaoCao(loaiBaoCao, tuNgay, denNgay);
        textArea1.setText(noiDung);
        textArea1.setCaretPosition(0); // cuộn lên đầu
    }

    /**
     * Tạo nội dung báo cáo dạng văn bản theo loại báo cáo.
     */
    private String taoNoiDungBaoCao(String loaiBaoCao, Date tuNgay, Date denNgay) {
        StringBuilder sb = new StringBuilder();

        // Tiêu đề chung
        sb.append("═══════════════════════════════════════════════════════════\n");
        sb.append("           BÁO CÁO ").append(loaiBaoCao.toUpperCase()).append("\n");
        sb.append("═══════════════════════════════════════════════════════════\n");
        sb.append("Khoảng thời gian: ").append(sdf.format(tuNgay))
                .append("  đến  ").append(sdf.format(denNgay)).append("\n");
        sb.append("Ngày lập báo cáo: ").append(sdf.format(new Date())).append("\n");
        sb.append("Người lập: Cán bộ sự kiện\n");
        sb.append("───────────────────────────────────────────────────────────\n\n");

        switch (loaiBaoCao) {
            case "Báo cáo tổng quan sự kiện":
                sb.append(baoCaoTongQuanSuKien());
                break;
            case "Báo cáo tiến độ công việc":
                sb.append(baoCaoTienDoCongViec());
                break;
            case "Báo cáo người tham gia":
                sb.append(baoCaoNguoiThamGia());
                break;
            default:
                sb.append("(Loại báo cáo không xác định)");
        }

        sb.append("\n───────────────────────────────────────────────────────────\n");
        sb.append("Kết thúc báo cáo.\n");
        sb.append("═══════════════════════════════════════════════════════════\n");
        return sb.toString();
    }

    /**
     * Nội dung báo cáo tổng quan sự kiện.
     */
    private String baoCaoTongQuanSuKien() {
        StringBuilder sb = new StringBuilder();
        sb.append("1. TỔNG QUAN CÁC SỰ KIỆN\n");
        sb.append("   ─────────────────────────────────────\n");
        sb.append(String.format("   • Tổng số sự kiện đã tổ chức: %d\n", 12));
        sb.append(String.format("   • Sự kiện đang diễn ra:        %d\n", 3));
        sb.append(String.format("   • Sự kiện sắp tới:             %d\n", 5));
        sb.append(String.format("   • Sự kiện đã hoàn thành:       %d\n", 4));
        sb.append("\n");
        sb.append("2. DANH SÁCH SỰ KIỆN NỔI BẬT\n");
        sb.append("   ─────────────────────────────────────\n");
        sb.append("   STT   Tên sự kiện              Ngày tổ chức   Trạng thái\n");
        sb.append("   1     Hội thảo CNTT 2026       15/10/2026     Đang diễn ra\n");
        sb.append("   2     Cuộc thi Code War        20/10/2026     Sắp tới\n");
        sb.append("   3     Giao lưu doanh nghiệp    25/10/2026     Sắp tới\n");
        sb.append("   4     Workshop AI cơ bản       05/10/2026     Hoàn thành\n");
        sb.append("   5     Teambuilding liên chi đoàn 01/10/2026    Hoàn thành\n");
        sb.append("\n");
        sb.append("3. KẾT LUẬN\n");
        sb.append("   ─────────────────────────────────────\n");
        sb.append("   Hoạt động sự kiện trong giai đoạn này diễn ra ổn định, \n");
        sb.append("   đa số sự kiện hoàn thành đúng kế hoạch. Cần tập trung \n");
        sb.append("   nguồn lực cho 2 sự kiện lớn sắp tới.\n");
        return sb.toString();
    }

    /**
     * Nội dung báo cáo tiến độ công việc.
     */
    private String baoCaoTienDoCongViec() {
        StringBuilder sb = new StringBuilder();
        sb.append("1. TỔNG QUAN TIẾN ĐỘ CÔNG VIỆC\n");
        sb.append("   ─────────────────────────────────────\n");
        sb.append(String.format("   • Tổng số công việc:           %d\n", 45));
        sb.append(String.format("   • Công việc hoàn thành:        %d (%.0f%%)\n", 28, 28.0 / 45 * 100));
        sb.append(String.format("   • Công việc đang thực hiện:    %d (%.0f%%)\n", 12, 12.0 / 45 * 100));
        sb.append(String.format("   • Công việc chậm tiến độ:      %d (%.0f%%)\n", 3, 3.0 / 45 * 100));
        sb.append(String.format("   • Công việc chưa bắt đầu:      %d (%.0f%%)\n", 2, 2.0 / 45 * 100));
        sb.append("\n");
        sb.append("2. DANH SÁCH CÔNG VIỆC CHỒM TIẾN ĐỘ\n");
        sb.append("   ─────────────────────────────────────\n");
        sb.append("   STT   Công việc                Tiến độ   Trạng thái\n");
        sb.append("   1     Lên kế hoạch sự kiện     100%      Hoàn thành\n");
        sb.append("   2     Thuê địa điểm            100%      Hoàn thành\n");
        sb.append("   3     In ấn thiệp mời          70%       Đang thực hiện\n");
        sb.append("   4     Liên hệ diễn giả         85%       Đang thực hiện\n");
        sb.append("   5     Chuẩn bị tài liệu        40%       Chậm tiến độ\n");
        sb.append("   6     Báo cáo kết quả          0%        Chưa bắt đầu\n");
        sb.append("\n");
        sb.append("3. KẾT LUẬN\n");
        sb.append("   ─────────────────────────────────────\n");
        sb.append("   Tỷ lệ công việc hoàn thành đạt 62%, cao hơn mục tiêu 60%.\n");
        sb.append("   Tuy nhiên còn 3 công việc chậm tiến độ cần đẩy nhanh \n");
        sb.append("   trong tuần tới để đảm bảo sự kiện diễn ra đúng lịch.\n");
        return sb.toString();
    }

    /**
     * Nội dung báo cáo người tham gia.
     */
    private String baoCaoNguoiThamGia() {
        StringBuilder sb = new StringBuilder();
        sb.append("1. TỔNG QUAN NGƯỜI THAM GIA\n");
        sb.append("   ─────────────────────────────────────\n");
        sb.append(String.format("   • Tổng số người đã đăng ký:    %d\n", 320));
        sb.append(String.format("   • Đã điểm danh tham gia:       %d (%.0f%%)\n", 285, 285.0 / 320 * 100));
        sb.append(String.format("   • Vắng mặt:                    %d (%.0f%%)\n", 35, 35.0 / 320 * 100));
        sb.append("\n");
        sb.append("2. PHÂN LOẠI NGƯỜI THAM GIA\n");
        sb.append("   ─────────────────────────────────────\n");
        sb.append("   Vai trò               Số lượng   Tỷ lệ\n");
        sb.append(String.format("   Sinh viên CNTT        %d        %.0f%%\n", 210, 210.0 / 320 * 100));
        sb.append(String.format("   Lớp trưởng            %d         %.0f%%\n", 45, 45.0 / 320 * 100));
        sb.append(String.format("   Ban tổ chức           %d         %.0f%%\n", 40, 40.0 / 320 * 100));
        sb.append(String.format("   Giảng viên cố vấn     %d         %.0f%%\n", 25, 25.0 / 320 * 100));
        sb.append("\n");
        sb.append("3. DANH SÁCH NGƯỜI THAM GIA ĐIỂM DANH (MẪU)\n");
        sb.append("   ─────────────────────────────────────\n");
        sb.append("   Mã SV    Họ tên                 Email                     Trạng thái\n");
        sb.append("   HS001    Nguyễn Văn An          an.nguyen@student.edu.vn   Đã điểm danh\n");
        sb.append("   HS002    Trần Thị Bình          binh.tran@student.edu.vn   Đã điểm danh\n");
        sb.append("   HS003    Lê Hoàng Cường         cuong.le@student.edu.vn    Đã điểm danh\n");
        sb.append("   HS004    Phạm Thị Dung          dung.pham@student.edu.vn   Chưa điểm danh\n");
        sb.append("   HS005    Võ Minh Đức            duc.vo@student.edu.vn      Đã điểm danh\n");
        sb.append("   ...    (còn 315 bản ghi nữa)\n");
        sb.append("\n");
        sb.append("4. KẾT LUẬN\n");
        sb.append("   ─────────────────────────────────────\n");
        sb.append("   Tỷ lệ tham gia đạt 89%, cao hơn dự kiến. Sinh viên CNTT \n");
        sb.append("   chiếm tỷ lệ lớn nhất. Cần nhắc nhở các bạn chưa điểm danh.\n");
        return sb.toString();
    }

    /**
     * Xử lý xuất excel (hiện tại chỉ thông báo vì chưa tích hợp thư viện).
     */
    private void xuatExcel() {
        JOptionPane.showMessageDialog(mainPanel,
                "Chức năng xuất Excel sẽ được tích hợp sau.\nHiện tại báo cáo đang được hiển thị ở vùng văn bản bên dưới.",
                "Xuất Excel", JOptionPane.INFORMATION_MESSAGE);
    }

    // hàm public để dashboard lấy ra để hiển thị
    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void $$$setupUI$$$() {
        // IntelliJ GUI Designer sẽ inject code tạo giao diện từ Lapbaocaothongke.form
    }

}
