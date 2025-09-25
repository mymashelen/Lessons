package lesson11test;

import io.github.bonigarcia.wdm.WebDriverManager;
import lesson11.HomePage;
import lesson11.PaymentPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.qameta.allure.*;

import static org.junit.jupiter.api.Assertions.*;

@Epic("MTS Online Payment")
@Feature("Онлайн пополнение без комиссии")
public class MtsOnlinePaymentTest {
    private WebDriver driver;
    private HomePage homePage;
    private PaymentPage paymentPage;

    @BeforeEach
    @Step("Настройка браузера и открытие сайта MTS")
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        homePage = new HomePage(driver);
        paymentPage = new PaymentPage(driver);

        driver.get("https://www.mts.by");

        // Даем время на полную загрузку
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Принимаем куки и ждем загрузки
        homePage.acceptCookies();
        homePage.waitForPageLoad();

        // Дополнительная пауза
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        takeScreenshot("start-page");
    }

    @Test
    @Story("Проверка заголовка блока")
    @Description("Тест проверяет, что заголовок блока 'Онлайн пополнение без комиссии' отображается корректно")
    @Severity(SeverityLevel.CRITICAL)
    public void testCheckBlockTitle() {
        String actualTitle = homePage.getPaymentBlockTitle();
        assertEquals("Онлайн пополнение без комиссии", actualTitle);
        takeScreenshot("block-title");
    }

    @Test
    @Story("Проверка логотипов платежных систем")
    @Description("Тест проверяет наличие логотипов платежных систем (Visa, MasterCard, Белкарт) в блоке оплаты")
    @Severity(SeverityLevel.NORMAL)
    public void testPaymentSystemLogos() {
        assertTrue(homePage.arePaymentLogosDisplayed());
        takeScreenshot("payment-logos");
    }

    @Test
    @Story("Проверка ссылки 'Подробнее о сервисе'")
    @Description("Тест проверяет работоспособность ссылки 'Подробнее о сервисе' и переход на корректную страницу")
    @Severity(SeverityLevel.NORMAL)
    public void testMoreAboutServiceLink() {
        assertTrue(homePage.isServiceDetailsLinkWorking());
        takeScreenshot("service-link");
    }

    @Test
    @Story("Проверка плейсхолдеров для всех вариантов оплаты")
    @Description("Тест проверяет корректность плейсхолдеров в полях ввода для разных услуг: Услуги связи, Домашний интернет, Рассрочка, Задолженность")
    @Severity(SeverityLevel.CRITICAL)
    public void testCheckPlaceholdersForAllPaymentOptions() {
        String[][] testData = {
                {"Услуги связи", "Номер телефона", "Сумма"},
                {"Домашний интернет", "Номер абонента", "Сумма"},
                {"Рассрочка", "Номер счета на 44", "Сумма"},
                {"Задолженность", "Номер счета на 2073", "Сумма"}
        };

        for (String[] data : testData) {
            homePage.selectPaymentOption(data[0]);

            // Проверяем выбранную опцию
            String selectedOption = homePage.getCurrentlySelectedPaymentOption();
            assertTrue(selectedOption.contains(data[0]),
                    "Должна быть выбрана опция: " + data[0] + ", но выбрана: " + selectedOption);

            // Проверяем плейсхолдеры
            String actualPhonePlaceholder = paymentPage.getPhonePlaceholder(data[0]);
            assertEquals(data[1], actualPhonePlaceholder,
                    "Для '" + data[0] + "' ожидался плейсхолдер '" + data[1] + "', но получен: " + actualPhonePlaceholder);

            String actualSumPlaceholder = paymentPage.getSumPlaceholder(data[0]);
            assertEquals(data[2], actualSumPlaceholder,
                    "Для '" + data[0] + "' ожидался плейсхолдер '" + data[2] + "', но получен: " + actualSumPlaceholder);

            takeScreenshot("placeholder-" + data[0].replace(" ", "-"));
        }
    }

    @Test
    @Story("Проверка формы оплаты услуг связи")
    @Description("Тест проверяет заполнение формы оплаты для услуг связи и открытие окна платежной системы")
    @Severity(SeverityLevel.CRITICAL)
    public void testOnlinePaymentForm() {
        homePage.selectPaymentOption("Услуги связи");
        takeScreenshot("form-selected");

        paymentPage.fillPaymentForm("297777777", "100");
        takeScreenshot("form-filled");

        paymentPage.clickContinue();

        assertTrue(paymentPage.isPaymentIframeDisplayed(),
                "Окно оплаты должно отображаться");
        takeScreenshot("payment-window");

        paymentPage.switchToPaymentFrame();

        boolean amountFound = paymentPage.isTextPresent("100");
        assertTrue(amountFound, "Сумма 100 должна отображаться");

        boolean phoneFound = paymentPage.isTextPresent("777777");
        assertTrue(phoneFound, "Номер должен отображаться");

        takeScreenshot("iframe-content");

        paymentPage.switchToDefaultContent();
    }

    @AfterEach
    @Step("Закрытие браузера")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Attachment(value = "Скриншот: {screenshotName}", type = "image/png")
    private byte[] takeScreenshot(String screenshotName) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            return new byte[0];
        }
    }
}