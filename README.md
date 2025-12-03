Link thiết kế 
https://www.figma.com/design/a7SYOdOgTcbPhA14H8TbKU/design_app?node-id=0-1&p=f&t=rKPWn12hWnRkqZTX-0


# Expense Management – Ứng Dụng Quản Lý Chi Tiêu Cá Nhân

<div align="center">

https://github.com/damclai/QuanLyChiTieu/blob/main/screenshots/Home_light.png
https://github.com/damclai/QuanLyChiTieu/blob/main/screenshots/Wallets.png
https://github.com/damclai/QuanLyChiTieu/blob/main/screenshots/DetailWallets_light.png
https://github.com/damclai/QuanLyChiTieu/blob/main/screenshots/HistoryTransaction_light.png
https://github.com/damclai/QuanLyChiTieu/blob/main/screenshots/AddTransaction_light.png
https://github.com/damclai/QuanLyChiTieu/blob/main/screenshots/Settings_light.png
https://github.com/damclai/QuanLyChiTieu/blob/main/screenshots/Security_light.png
https://github.com/damclai/QuanLyChiTieu/blob/main/screenshots/Theme_light.png

</div>

Ứng dụng giúp quản lý chi tiêu hàng ngày, theo dõi ví tiền, ngân sách, giao dịch – viết hoàn toàn bằng Kotlin, sử dụng Jetpack Compose cho UI hiện đại, Firebase cho backend realtime.

## Tính năng nổi bật
- **Quản lý ví tiền**: Thêm/sửa/xóa ví (thường, tiết kiệm, tín dụng) – cập nhật số dư realtime
- **Giao dịch chi tiêu**: Thêm/sửa/xóa giao dịch (chi/thu) – tự động trừ/cộng số dư ví
- **Ngân sách cá nhân**: Thiết lập hạn mức chi tiêu tháng – cảnh báo vượt ngân sách, progress bar đẹp
- **Báo cáo & biểu đồ**: Tổng thu chi, xu hướng theo tháng, biểu đồ cột thu chi
- **Chế độ tối/màu chủ đề**: Tùy chọn sáng/tối, thay đổi màu chủ đề (xanh, tím, cam, đỏ)
- **Xóa giao dịch**: Bấm vào giao dịch → mở chi tiết → nút Xóa (xác nhận) → cập nhật lại số dư + ngân sách + báo cáo realtime
- **Đăng nhập Google/Email**: An toàn, nhanh chóng

## Tech Stack
- **Frontend**: Jetpack Compose (UI hiện đại, responsive)
- **Architecture**: MVVM + Clean Architecture + Repository Pattern
- **Dependency Injection**: Hilt
- **Backend**: Firebase Firestore (realtime data) + Auth
- **Coroutines + Flow**: Xử lý async, realtime update
- **ZXing**: Tạo QR code chia sẻ app
- **Vico**: Biểu đồ báo cáo đẹp

## Cài đặt & Chạy
1. Clone repo: `git clone https://github.com/damclai/QuanLyChiTieu.git`
2. Mở bằng Android Studio (2023.1+)
3. Thêm `google-services.json` từ Firebase project của bạn vào `app/`
4. Build & Run (Debug APK để test nhanh)

## Screenshots

div align="center">

https://github.com/damclai/QuanLyChiTieu/blob/main/screenshots/Home_light.png
https://github.com/damclai/QuanLyChiTieu/blob/main/screenshots/Wallets.png
https://github.com/damclai/QuanLyChiTieu/blob/main/screenshots/DetailWallets_light.png
https://github.com/damclai/QuanLyChiTieu/blob/main/screenshots/HistoryTransaction_light.png
https://github.com/damclai/QuanLyChiTieu/blob/main/screenshots/AddTransaction_light.png
https://github.com/damclai/QuanLyChiTieu/blob/main/screenshots/Settings_light.png
https://github.com/damclai/QuanLyChiTieu/blob/main/screenshots/Security_light.png
https://github.com/damclai/QuanLyChiTieu/blob/main/screenshots/Theme_light.png

</div>

## Tác giả
Nhân Đảm – Sinh viên Lập Trình Di Động  
Email: tudatnhandam1219@gmail.com  
LinkedIn: 

Cập nhật 2025 – Chào mừng mọi người fork + star + contribute!

MIT License
