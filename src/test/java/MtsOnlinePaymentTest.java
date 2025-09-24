import lesson10.HomePage;
import lesson10.PaymentPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class MtsOnlinePaymentTest {
    private WebDriver driver;
    private HomePage homePage;
    private PaymentPage paymentPage;
    private static final String BASE_URL = "https://www.mts.by";

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);

        homePage = new HomePage(driver);
        paymentPage = new PaymentPage(driver);

        homePage.acceptCookies();
        homePage.waitForPageToLoad(); // Ждем загрузки страницы
    }

    @Test
    void testPaymentBlockTitle() {
        homePage.openServiceDropdown();
        homePage.selectCommunicationServices();

        String title = homePage.getBlockTitle();
        assertEquals("Онлайн пополнение без комиссии", title);
    }

    @Test
    void testPaymentSystemLogos() {
        homePage.openServiceDropdown();
        homePage.selectCommunicationServices();

        int logosCount = homePage.getPaymentSystemLogosCount();
        assertTrue(logosCount >= 3, "Должно быть хотя бы 3 логотипа платежных систем");

        String[] actualAlts = homePage.getPaymentSystemAltTexts();
        boolean hasVisa = false;
        boolean hasMastercard = false;

        for (String alt : actualAlts) {
            if (alt.contains("Visa")) hasVisa = true;
            if (alt.contains("MasterCard")) hasMastercard = true;
        }

        assertTrue(hasVisa, "Не найден логотип Visa");
        assertTrue(hasMastercard, "Не найден логотип MasterCard");
    }

    @Test
    void testServiceDetailsLink() {
        homePage.openServiceDropdown();
        homePage.selectCommunicationServices();

        homePage.clickServiceDetailsLink();

        String currentUrl = homePage.getCurrentUrl();
        assertTrue(currentUrl.contains("/help/"), "URL должен содержать /help/");
    }

    @Test
    void testPlaceholdersForAllPaymentOptions() {
        // Проверка Услуг связи
        homePage.openServiceDropdown();
        homePage.selectCommunicationServices();

        assertTrue(paymentPage.getPhonePlaceholder().contains("Номер") ||
                paymentPage.getPhonePlaceholder().contains("телефон"));
        assertTrue(paymentPage.getSumPlaceholder().contains("Сумма"));

        // Проверка Домашнего интернета
        homePage.openServiceDropdown();
        homePage.selectInternetServices();
        assertTrue(paymentPage.getInternetPhonePlaceholder().contains("Номер") ||
                paymentPage.getInternetPhonePlaceholder().contains("абонент"));

        // Проверка Рассрочки
        homePage.openServiceDropdown();
        homePage.selectInstallmentServices();
        assertTrue(paymentPage.getInstallmentAccountPlaceholder().contains("счет"));

        // Проверка Задолженности
        homePage.openServiceDropdown();
        homePage.selectDebtServices();
        assertTrue(paymentPage.getDebtAccountPlaceholder().contains("счет"));
    }

    @Test
    void testCommunicationServicesPayment() {
        homePage.openServiceDropdown();
        homePage.selectCommunicationServices();

        // Заполняем форму
        paymentPage.fillCommunicationForm("297777777", "100", "test@example.com");
        paymentPage.clickContinueButton();

        // Проверяем открытие модального окна
        assertTrue(paymentPage.isPaymentModalDisplayed());

        // Переключаемся в iframe и проверяем содержимое
        paymentPage.switchToPaymentFrame();

        // Проверяем отображение номера телефона и суммы
        String displayedPhone = paymentPage.getDisplayedPhoneNumber();
        String displayedAmount = paymentPage.getDisplayedAmount();
        String buttonText = paymentPage.getPaymentButtonText();

        assertTrue(displayedPhone.contains("375297777777") || displayedPhone.contains("297777777"),
                "Номер телефона должен содержать 375297777777 или 297777777");
        assertTrue(displayedAmount.contains("100") || buttonText.contains("100"),
                "Сумма должна содержать 100");

        // Проверяем наличие полей для ввода реквизитов карты
        assertTrue(paymentPage.areCardFieldsPresent());

        // Проверяем наличие иконок платежных систем
        assertTrue(paymentPage.arePaymentSystemIconsDisplayed());

        paymentPage.switchToDefaultContent();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}