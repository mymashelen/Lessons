package lesson10test;

import io.github.bonigarcia.wdm.WebDriverManager;
import lesson10.HomePage;
import lesson10.PaymentPage;
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

    @BeforeEach
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
    }

    @Test
    public void testCheckBlockTitle() {
        String actualTitle = homePage.getPaymentBlockTitle();
        assertEquals("Онлайн пополнение без комиссии", actualTitle);
    }

    @Test
    public void testPaymentSystemLogos() {
        assertTrue(homePage.arePaymentLogosDisplayed());
    }

    @Test
    public void testMoreAboutServiceLink() {
        assertTrue(homePage.isServiceDetailsLinkWorking());
    }

    @Test
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
        }
    }

    @Test
    public void testOnlinePaymentForm() {
        // Выбираем услуги связи
        homePage.selectPaymentOption("Услуги связи");

        // Проверяем что форма активна
        String selectedOption = homePage.getCurrentlySelectedPaymentOption();
        assertTrue(selectedOption.contains("Услуги связи"));

        // Заполняем форму
        paymentPage.fillPaymentForm("297777777", "100");
        paymentPage.clickContinue();

        // Проверяем окно оплаты
        assertTrue(paymentPage.isPaymentIframeDisplayed(),
                "Окно оплаты должно отображаться");

        // Переключаемся в iframe и проверяем данные
        paymentPage.switchToPaymentFrame();

        // Проверяем с различными вариантами формата
        boolean amountFound = paymentPage.isAmountDisplayed("100.00 BYN");
        assertTrue(amountFound, "Сумма 100 должна отображаться в окне оплаты");

        boolean phoneFound = paymentPage.isPhoneDisplayed("375297777777");
        assertTrue(phoneFound, "Номер должен отображаться в окне оплаты");

        // Проверяем наличие полей для карты
        assertTrue(paymentPage.areCardFieldsPresent(),
                "Должны присутствовать поля для ввода данных карты");

        // Проверяем наличие иконок платежных систем
        assertTrue(paymentPage.arePaymentIconsPresent(),
                "Должны присутствовать иконки платежных систем");

        paymentPage.switchToDefaultContent();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}