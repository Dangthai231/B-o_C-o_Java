-- Tạo database nếu chưa có
CREATE DATABASE IF NOT EXISTS event_management_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE event_management_db;

-- Tạo bảng nguoi_tham_gia (khớp với entity NguoiThamGia)
CREATE TABLE IF NOT EXISTS nguoi_tham_gia (
    id BIGINT NOT NULL AUTO_INCREMENT,
    ma_hoc_sinh VARCHAR(50),
    ho_ten VARCHAR(255),
    email VARCHAR(255),
    so_dien_thoai VARCHAR(50),
    vai_tro VARCHAR(100),
    trang_thai_diem_danh VARCHAR(50),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Xóa dữ liệu cũ (nếu có) để import sạch
TRUNCATE TABLE nguoi_tham_gia;

-- Import 30 học sinh
INSERT INTO nguoi_tham_gia (ma_hoc_sinh, ho_ten, email, so_dien_thoai, vai_tro, trang_thai_diem_danh) VALUES
('HS001', 'Nguyễn Văn An', 'an.nguyen@student.edu.vn', '0901234501', 'Sinh viên', 'Đã điểm danh'),
('HS002', 'Trần Thị Bình', 'binh.tran@student.edu.vn', '0901234502', 'Sinh viên', 'Đã điểm danh'),
('HS003', 'Lê Hoàng Cường', 'cuong.le@student.edu.vn', '0901234503', 'Lớp trưởng', 'Đã điểm danh'),
('HS004', 'Phạm Thị Dung', 'dung.pham@student.edu.vn', '0901234504', 'Sinh viên', 'Chưa điểm danh'),
('HS005', 'Võ Minh Đức', 'duc.vo@student.edu.vn', '0901234505', 'Sinh viên', 'Đã điểm danh'),
('HS006', 'Bùi Thị Em', 'em.bui@student.edu.vn', '0901234506', 'Sinh viên', 'Đã điểm danh'),
('HS007', 'Đặng Văn Phúc', 'phuc.dang@student.edu.vn', '0901234507', 'Sinh viên', 'Vắng mặt'),
('HS008', 'Hoàng Thị Giang', 'giang.hoang@student.edu.vn', '0901234508', 'Sinh viên', 'Đã điểm danh'),
('HS009', 'Ngô Văn Hùng', 'hung.ngo@student.edu.vn', '0901234509', 'Sinh viên', 'Đã điểm danh'),
('HS010', 'Vũ Thị Hương', 'huong.vu@student.edu.vn', '0901234510', 'Phó lớp', 'Đã điểm danh'),
('HS011', 'Đỗ Văn Khánh', 'khanh.do@student.edu.vn', '0901234511', 'Sinh viên', 'Chưa điểm danh'),
('HS012', 'Nguyễn Thị Lan', 'lan.nguyen@student.edu.vn', '0901234512', 'Sinh viên', 'Đã điểm danh'),
('HS013', 'Trần Văn Minh', 'minh.tran@student.edu.vn', '0901234513', 'Sinh viên', 'Đã điểm danh'),
('HS014', 'Lê Thị Mai', 'mai.le@student.edu.vn', '0901234514', 'Sinh viên', 'Đã điểm danh'),
('HS015', 'Phạm Văn Nam', 'nam.pham@student.edu.vn', '0901234515', 'Sinh viên', 'Vắng mặt'),
('HS016', 'Võ Thị Nga', 'nga.vo@student.edu.vn', '0901234516', 'Sinh viên', 'Đã điểm danh'),
('HS017', 'Bùi Văn Oanh', 'oanh.bui@student.edu.vn', '0901234517', 'Sinh viên', 'Đã điểm danh'),
('HS018', 'Đặng Thị Phượng', 'phuong.dang@student.edu.vn', '0901234518', 'Sinh viên', 'Chưa điểm danh'),
('HS019', 'Hoàng Văn Quân', 'quan.hoang@student.edu.vn', '0901234519', 'Sinh viên', 'Đã điểm danh'),
('HS020', 'Ngô Thị Rạng', 'rang.ngo@student.edu.vn', '0901234520', 'Sinh viên', 'Đã điểm danh'),
('HS021', 'Vũ Văn Sơn', 'son.vu@student.edu.vn', '0901234521', 'Lớp trưởng', 'Đã điểm danh'),
('HS022', 'Đỗ Thị Tâm', 'tam.do@student.edu.vn', '0901234522', 'Sinh viên', 'Đã điểm danh'),
('HS023', 'Nguyễn Văn Toàn', 'toan.nguyen@student.edu.vn', '0901234523', 'Sinh viên', 'Vắng mặt'),
('HS024', 'Trần Thị Úc', 'uc.tran@student.edu.vn', '0901234524', 'Sinh viên', 'Đã điểm danh'),
('HS025', 'Lê Văn Vũ', 'vu.le@student.edu.vn', '0901234525', 'Sinh viên', 'Đã điểm danh'),
('HS026', 'Phạm Thị Xuân', 'xuan.pham@student.edu.vn', '0901234526', 'Sinh viên', 'Đã điểm danh'),
('HS027', 'Võ Văn Yên', 'yen.vo@student.edu.vn', '0901234527', 'Sinh viên', 'Chưa điểm danh'),
('HS028', 'Bùi Thị Anh', 'anh.bui@student.edu.vn', '0901234528', 'Sinh viên', 'Đã điểm danh'),
('HS029', 'Đặng Văn Bảo', 'bao.dang@student.edu.vn', '0901234529', 'Sinh viên', 'Đã điểm danh'),
('HS030', 'Hoàng Thị Chi', 'chi.hoang@student.edu.vn', '0901234530', 'Phó lớp', 'Đã điểm danh');

-- Kiểm tra số lượng đã import
SELECT COUNT(*) AS so_luong_hoc_sinh FROM nguoi_tham_gia;
