package Day1;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;
import java.nio.file.Paths;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LocatorDrillsTest {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            // 1. Khởi tạo trình duyệt
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false) // Hiện trình duyệt để xem
                    .setSlowMo(500));   // Chậm lại 0.5s mỗi bước để kịp nhìn

            Page page = browser.newPage();

            // 2. Mở file HTML bài tập (Lưu ý: File locator-drills.html phải nằm ở thư mục gốc dự án)
            String htmlPath = Paths.get("src/main/java/Day1/locator-drills.html").toAbsolutePath().toUri().toString();
            page.navigate(htmlPath);
            System.out.println("--- BẮT ĐẦU LUYỆN TẬP LOCATOR ---");

            // --- XỬ LÝ ALERT/DIALOG TRƯỚC ---
            // Vì bài 3 và 18 có Alert, ta cần lắng nghe sự kiện này để tự động bấm OK
            page.onDialog(dialog -> {
                System.out.println("Alert xuất hiện: " + dialog.message());
                dialog.accept();
            });

            // ==========================================
            // SECTION 1: CƠ BẢN
            // ==========================================

            // Bài 1: ID
            System.out.println("Bài 1: ID");
            page.locator("#user_email_id").fill("test@email.com");

            // Bài 2: Name
            System.out.println("Bài 2: Name");
            page.locator("[name='password_field']").fill("MatKhau123");

            // Bài 3: Class (Click xong sẽ hiện Alert, Playwright đã handle ở trên)
            System.out.println("Bài 3: Class");
            page.locator(".btn-submit-action").click();

            // Bài 4: Placeholder (Dùng getByPlaceholder tiện lợi)
            System.out.println("Bài 4: Placeholder");
            page.getByPlaceholder("Tìm kiếm sản phẩm...").fill("Iphone 15");

            // ==========================================
            // SECTION 2: TEXT & ATTRIBUTES
            // ==========================================

            // Bài 5: Text Exact (Chính xác từng chữ)
            System.out.println("Bài 5: Text Exact");
            page.getByText("Mua Ngay", new Page.GetByTextOptions().setExact(true)).click();

            // Bài 6: Text Contains (Chứa 1 phần chữ)
            System.out.println("Bài 6: Text Contains");
            // Tìm cột target-zone trước, sau đó mới tìm text bên trong nó
            assertThat(page.locator(".target-zone").getByText("thông báo quan trọng")).isVisible();

            // Bài 7: Custom Attribute (data-testid)
            System.out.println("Bài 7: Data TestID");
            String bannerText = page.getByTestId("promo-banner").textContent();
            System.out.println("   -> Banner text: " + bannerText);

            // Bài 8: Partial Attribute (CSS chứa chuỗi)
            System.out.println("Bài 8: Partial ID");
            // [id*='12345'] nghĩa là ID có chứa cụm từ 12345
            page.locator("[id*='12345']").click();

            // ==========================================
            // SECTION 3: HIERARCHY (CHA CON)
            // ==========================================

            // Bài 9: Direct Child (Cha > Con)
            System.out.println("Bài 9: Direct Child");
            String childText = page.locator(".parent-a > span").textContent();
            System.out.println("   -> Text tìm được: " + childText);

            // Bài 10: Nth-Child (Lấy phần tử thứ 3 - index bắt đầu từ 0)
            System.out.println("Bài 10: Nth-Child");
            page.locator("#list-items li").nth(2).click();

            // Bài 11: Sibling (Anh em liền kề)
            System.out.println("Bài 11: Sibling");
            // Tìm label, sau đó lấy input nằm ngay sau nó (+)
            page.locator("label[for='sibling-inp'] + input").fill("Nguyen Van A");

            // ==========================================
            // SECTION 4: FORM & INTERACTION
            // ==========================================

            // Bài 12: Checkbox
            System.out.println("Bài 12: Checkbox");
            page.locator("#chk-term").check();

            // Bài 13: Radio Group
            System.out.println("Bài 13: Radio");
            // Chọn input có value='cod'
            page.locator("input[value='cod']").check();

            // Bài 14: Select Option
            System.out.println("Bài 14: Dropdown");
            page.locator("#city-select").selectOption("dn");

            // Bài 15: File Upload
            System.out.println("Bài 15: Upload File");
            // Upload chính file HTML này lên để test
            page.locator("#file-upload-input").setInputFiles(Paths.get("locator-drills.html"));

            // ==========================================
            // SECTION 5: ADVANCED / TRICKY (NÂNG CAO)
            // ==========================================

            // Bài 16: Dynamic ID (ID đổi liên tục -> Phải dùng Class)
            System.out.println("Bài 16: Dynamic ID");
            page.locator(".dynamic-inp").fill("Bắt được em rồi!");

            // Bài 17: Shadow DOM (Playwright tự xuyên qua, code như thường)
            System.out.println("Bài 17: Shadow DOM");
            page.locator("#shadow-input").fill("Hello Shadow World");

            // Bài 18: iFrame (Phải chui vào frame trước)
            System.out.println("Bài 18: iFrame");
            page.frameLocator("#iframe-target")
                    .locator("#btn-in-frame")
                    .click();

            // Bài 19: Wait / Visibility (Auto-wait)
            System.out.println("Bài 19: Auto-Wait");
            page.getByText("Hiện nút ẩn (3s)").click();
            System.out.println("   -> Đang đợi nút hiện ra...");
            // Dòng dưới sẽ tự động đợi cho đến khi nút hiện ra (không cần sleep)
            page.locator("#hidden-btn").click();

            // Bài 20: Table Cell (Lọc dòng chứa Bob -> Lấy cột Email)
            System.out.println("Bài 20: Table Logic");
            String bobEmail = page.locator("tr")
                    .filter(new Locator.FilterOptions().setHasText("Bob")) // Lọc dòng có chữ Bob
                    .locator("td").nth(1) // Lấy ô thứ 2 (cột Email)
                    .textContent();
            System.out.println("   -> Email của Bob là: " + bobEmail);

            // Bài 21: Mouse Hover
            System.out.println("Bài 21: Hover");
            page.locator("[title='Đây là tooltip của tôi']").hover();

            // Bài 22: Disabled State
            System.out.println("Bài 22: Disabled Check");
            assertThat(page.locator("button:has-text('Không thể bấm')")).isDisabled();

            System.out.println("--- HOÀN THÀNH TẤT CẢ BÀI TẬP! ---");

            // Giữ màn hình 3s để ngắm kết quả
            page.waitForTimeout(3000);
            browser.close();
        }
    }
}