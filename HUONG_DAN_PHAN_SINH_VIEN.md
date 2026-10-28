# Phần Sinh viên – Hệ thống quản lý sự kiện LCĐ CNTT (EPU)

## 1. Luồng dữ liệu

```
Swing (FE)  --HTTP/JSON-->  Spring Boot Controller  -->  Repository (JPA)  -->  MySQL
TraCuuSuKien   GET /api/su-kien/danh-sach   SuKienController   SuKienRepository   bảng su_kien
```

- FE **không** kết nối thẳng DB. FE gọi REST API qua `ApiClient`, nhận JSON, đổ lên `JTable`.
- BE: `Entity` = 1 bảng, `Repository` = câu truy vấn (Spring tự sinh SQL từ tên hàm), `Controller` = API.
- Bảng tự được tạo nhờ `spring.jpa.hibernate.ddl-auto=update` khi chạy backend.

## 2. Use case → API → màn hình

| Use case | API | Màn hình FE |
|---|---|---|
| Xem danh sách / Tìm kiếm / Lọc theo ngày | `GET /api/su-kien/danh-sach?tuKhoa=&tuNgay=yyyy-MM-dd&denNgay=` | `TraCuuSuKien` |
| Xem chi tiết sự kiện | `GET /api/su-kien/{id}` | hộp thoại chi tiết (`HocSinhUI.hienChiTietSuKien`) |
| Đăng ký tham gia | `POST /api/dang-ky/dang-ky` `{suKienId, tenDangNhap}` | nút "Đăng ký tham gia" |
| Xem thông tin đăng ký | `GET /api/dang-ky/cua-toi?tenDangNhap=` | `SuKienCuaToi` |
| Hủy đăng ký | `PUT /api/dang-ky/huy/{id}?tenDangNhap=` | nút "Hủy đăng ký" |
| Xem trạng thái tham gia | `GET /api/dang-ky/trang-thai?suKienId=&tenDangNhap=` | cột Trạng thái + hộp thoại chi tiết |
| Đánh giá và phản hồi | `POST /api/danh-gia/gui`, `GET /api/danh-gia/cua-toi`, `GET /api/danh-gia/su-kien/{id}` | `DanhGiaPhanHoi` |
| Xem thông tin cá nhân | `GET /api/sinh-vien/thong-tin?tenDangNhap=` | `ThongTinCaNhan` |
| Cập nhật thông tin | `PUT /api/sinh-vien/cap-nhat?tenDangNhap=` | nút "Lưu thay đổi" |

## 3. Bảng mới

| Bảng | Cột chính |
|---|---|
| `su_kien` | ma_su_kien, ten_su_kien, mo_ta, dia_diem, thoi_gian_bat_dau, thoi_gian_ket_thuc, so_luong_toi_da, don_vi_to_chuc, trang_thai |
| `dang_ky_su_kien` | su_kien_id, ten_dang_nhap, ngay_dang_ky, trang_thai (UNIQUE su_kien_id + ten_dang_nhap) |
| `danh_gia_su_kien` | su_kien_id, ten_dang_nhap, so_sao (1-5), noi_dung, ngay_danh_gia |
| `sinh_vien` | ten_dang_nhap (UNIQUE), ma_sinh_vien, ho_ten, ngay_sinh, lop, email, so_dien_thoai |

## 4. Quy tắc nghiệp vụ (kiểm tra ở backend)

- Không đăng ký sự kiện đã hủy / đã kết thúc / đã bắt đầu; không đăng ký trùng; không vượt `so_luong_toi_da`.
- Hủy rồi đăng ký lại thì dùng lại dòng cũ.
- Chỉ hủy khi trạng thái "Đã đăng ký" và sự kiện chưa bắt đầu.
- Chỉ đánh giá sự kiện đã đăng ký (không hủy) và đã diễn ra; mỗi người 1 đánh giá / sự kiện, gửi lại = sửa.
- Thông tin cá nhân: họ tên bắt buộc, email đúng dạng, SĐT 10 số bắt đầu bằng 0.

## 5. Chạy thử

1. Bật MySQL, tạo DB `event_management_db` (nếu chưa có).
2. Chạy backend (`BackendApplication`). Lần đầu `SuKienSeeder` tạo 7 sự kiện mẫu, hồ sơ 14 sinh viên, và 3 lượt đăng ký cho `hocsinh01`.
3. Chạy frontend (`Login`), đăng nhập `hocsinh01` / `hs123`.
4. Kiểm tra trong MySQL: `SELECT * FROM dang_ky_su_kien;` sau khi bấm Đăng ký / Hủy.
