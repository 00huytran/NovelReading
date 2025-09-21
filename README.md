# AndroidPrograming

BTL App doc truyen:

    List truyện với hình minh họa  checked
    truyện gồm phần chữ và ảnh minh họa 
    lưu lại lịch sử đọc 
    lưu lại truyện đã đọc 
    nhận xét , đánh giá truyện

    đăng nhập
    đăng ký
    thay đổi thông tin cá nhân

    tìm kiếm truyện
    

    cách chạy app:
    - cài đặt xampp trước khi sử dụng 
    - tạo file tên flatform trong file htdocs
    - tải project về
    - sau khi giải nén, copy hết các file trong file API chép vào file flatform
    - mở xamp và mở Apache và MySQL
    - mở project với Android Studio
    - Mở file RetrofitClient trong package api
    - mở Command Prompt chạy lệnh ipconfig, sau đó kéo xuống Wireless LAN Adapter Wifi copy chuỗi số sau IPv4 Address (VD: IPv4 Address. . . . . . . . . . . : (phần copy)192.168.xxx.xxx)
    - Quay trở lại file RetrofitClient tại biến BASE_URL chỉnh :  private static final String BASE_URL = "http://(chuỗi số vừa copy)/flatform/";
    - Sync Project rồi chạy
     