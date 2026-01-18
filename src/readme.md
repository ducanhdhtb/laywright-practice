# 🎭 Playwright Java Practice - 20 Levels Challenge

Dự án này chứa mã nguồn giải quyết **20 Cấp độ thử thách Automation** sử dụng **Playwright** với ngôn ngữ **Java**.

Mục tiêu của dự án là thực hành các kỹ thuật từ cơ bản đến nâng cao trong Automation Testing, bao gồm xử lý Locators, Wait, iFrames, Shadow DOM, và các tương tác phức tạp.

## 📋 Mục lục
- [Yêu cầu hệ thống](#yêu-cầu-hệ-thống)
- [Cài đặt](#cài-đặt)
- [Cấu trúc dự án](#cấu-trúc-dự-án)
- [Nội dung thực hành](#nội-dung-thực-hành)
- [Cách chạy dự án](#cách-chạy-dự-án)

---

## 🛠 Yêu cầu hệ thống

Trước khi bắt đầu, hãy đảm bảo máy tính của bạn đã cài đặt:
1.  **Java JDK** (Phiên bản 11 trở lên).
2.  **Maven** (Công cụ quản lý thư viện).
3.  **IntelliJ IDEA** (hoặc Eclipse/VS Code).

## ⚙️ Cài đặt

1.  **Clone dự án về máy:**
    ```bash
    git clone [https://github.com/your-username/playwright-practice.git](https://github.com/your-username/playwright-practice.git)
    cd playwright-practice
    ```

2.  **Cấu hình `pom.xml`:**
    Thêm dependency Playwright vào file `pom.xml` nếu chưa có:
    ```xml
    <dependency>
        <groupId>com.microsoft.playwright</groupId>
        <artifactId>playwright</artifactId>
        <version>1.49.0</version>
    </dependency>
    ```

3.  **Tải thư viện:**
    Chạy lệnh Maven để tải các thư viện về:
    ```bash
    mvn clean install
    ```

---

## 📂 Cấu trúc dự án

```text
Playwright-Practice/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── PlaywrightPractice.java  <-- Code chính giải 20 Level
├── 20-levels-master.html                <-- File HTML bài tập
├── pom.xml                              <-- Quản lý thư viện Maven
└── README.md                            <-- Tài liệu hướng dẫn