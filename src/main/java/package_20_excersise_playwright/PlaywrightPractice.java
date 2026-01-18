package package_20_excersise_playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;
import java.nio.file.Paths;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PlaywrightPractice {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
            Page page = browser.newPage();

            // --- SETUP: MỞ FILE HTML ---
            String projectPath = System.getProperty("user.dir");
            String filePath = "file:///" + projectPath + "/src/main/java/20-levels-master.html";


            page.navigate(filePath);

            System.out.println("--- BẮT ĐẦU CHINH PHỤC 20 LEVELS ---");

            // --- LEVEL 1: ID ---
            page.locator("#input-lvl1").fill("Java Playwright");
            page.locator("button:has-text('Submit')").click();
            assertThat(page.locator("#msg-lvl1")).hasText("Level 1 Passed!");

            // --- LEVEL 2: Class ---
            page.locator(".input-class-lvl2").fill("Class Locator");

            // --- LEVEL 3: Checkbox & Radio ---
            page.locator("#chk-lvl3").check();
            page.locator("input[value='female']").check();
            // Verify
            assertThat(page.locator("#chk-lvl3")).isChecked();

            // --- LEVEL 4: Dropdown ---
            page.locator("#select-lvl4").selectOption("jp");

            // --- LEVEL 5: Text Locator ---
            page.getByText("Click me please", new Page.GetByTextOptions().setExact(true)).click();
            assertThat(page.locator("#msg-lvl5")).hasText("Pass!");

            // --- LEVEL 6: Data-TestId ---
            page.getByTestId("custom-input-lvl6").fill("Java TestID");

            // --- LEVEL 7: Input Types ---
            page.locator("#date-lvl7").fill("2025-12-25");
            page.locator("#color-lvl7").fill("#00ff00");

            // --- LEVEL 8: Multi-select ---
            // Chọn nhiều options cùng lúc
            page.locator("#multi-lvl8").selectOption(new String[]{"apple", "cherry"});

            // --- LEVEL 9: CSS Parent > Child ---
            page.locator(".parent-box > .target-child").click();

            // --- LEVEL 10: Disabled / Readonly ---
            assertThat(page.locator("#disabled-input")).isDisabled();
            assertThat(page.locator("#readonly-input")).not().isEditable();

            // --- LEVEL 11: Hover ---
            page.getByText("Rê chuột vào đây").hover();
            assertThat(page.locator("#hover-msg")).hasText("Hovered!");

            // --- LEVEL 12: Drag & Drop ---
            page.dragAndDrop("#drag-source", "#drop-target");
            assertThat(page.locator("#drag-res")).hasText("Done!");

            // --- LEVEL 13: Keyboard ---
            page.locator("#key-lvl13").fill("Java Key");
            page.locator("#key-lvl13").press("Enter");
            assertThat(page.locator("#key-res")).hasText("Enter Pressed!");

            // --- LEVEL 14: Upload File ---
            // Tạo 1 file giả để test hoặc dùng path thật
            // Ở đây mình lấy ví dụ đường dẫn file
            // page.locator("#upload-lvl14").setInputFiles(Paths.get("demo.png"));

            // --- LEVEL 15: Auto-Wait (Cực quan trọng) ---
            page.getByText("Load (3s)").click();
            // Playwright Java tự động đợi element xuất hiện (auto-wait)
            page.locator("#btn-lvl15").click();
            assertThat(page.locator("#wait-res")).hasText("Found!");

            // --- LEVEL 16: Alerts (Dialogs) ---
            // Phải đăng ký listener trước khi action xảy ra
            page.onDialog(dialog -> {
                System.out.println("Alert message: " + dialog.message());
                dialog.accept();
            });
            page.getByText("Show Alert").click();
            page.getByText("Show Confirm").click();

            // --- LEVEL 17: Dynamic ID ---
            // Không dùng ID, dùng attribute khác hoặc CSS class
            page.locator(".dynamic-id").fill("I handled dynamic ID");

            // --- LEVEL 18: Web Table Filtering (Advanced) ---
            // Tìm dòng (tr) có chứa chữ "Bob", sau đó tìm nút button bên trong dòng đó
            page.locator("tr")
                    .filter(new Locator.FilterOptions().setHasText("Bob"))
                    .locator("button")
                    .click();

            // --- LEVEL 19: Shadow DOM ---
            // Playwright Java tự xuyên qua Shadow DOM
            page.locator("#shadow-input").fill("Hello Shadow DOM");

            // --- LEVEL 20: iFrame ---
            page.frameLocator("#iframe-lvl20")
                    .locator("#btn-iframe")
                    .click();

            System.out.println("--- CHÚC MỪNG! BẠN ĐÃ HOÀN THÀNH 20 LEVEL ---");

            // Dừng 5s để ngắm thành quả trước khi tắt
            page.waitForTimeout(5000);
            browser.close();
        }
    }
}