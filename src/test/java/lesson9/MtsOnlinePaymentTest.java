package lesson9;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtsOnlinePaymentTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://www.mts.by";
    private static final int WAIT_TIMEOUT = 30;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT));
        driver.get(BASE_URL);
        acceptCookiesIfPresent();
    }

    @Test
    void testPaymentBlockTitle() {
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'pay')]//h2[contains(text(),'Онлайн пополнение')]")));
        assertEquals("Онлайн пополнение без комиссии", title.getText().replace("\n", " ").trim());
    }

    @Test
    void testPaymentSystemLogos() {
        List<WebElement> logos = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector(".pay__partners ul li img")));
        assertEquals(5, logos.size(), "Должно быть ровно 5 логотипов платежных систем");

        String[] expectedAlts = {"Visa", "Verified By Visa", "MasterCard", "MasterCard Secure Code", "Белкарт"};
        for (int i = 0; i < logos.size(); i++) {
            assertEquals(expectedAlts[i], logos.get(i).getAttribute("alt"),
                    "Логотип №" + (i+1) + ": неправильный атрибут 'alt'");
        }
    }

    @Test
    void testServiceDetailsLink() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Подробнее о сервисе")));
        link.click();
        wait.until(ExpectedConditions.urlContains("/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/"));

        assertEquals("https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/",
                driver.getCurrentUrl());
    }

    @Test
    public void testFormSubmission() {
        WebElement phoneNumberInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("connection-phone")));
        phoneNumberInput.sendKeys("297777777");

        WebElement sumInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("connection-sum")));
        sumInput.sendKeys("100");

        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//form[@id='pay-connection']//button[text()='Продолжить']")));
        continueButton.click();

        WebElement iframe = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".bepaid-iframe")));

        assertTrue(iframe.isDisplayed(), "Окно оплаты не открылся!");
    }

    private void acceptCookiesIfPresent() {
        try {
            List<WebElement> cookieButtons = driver.findElements(By.cssSelector(".btn.btn_black.cookie__ok"));
            if (!cookieButtons.isEmpty() && cookieButtons.get(0).isDisplayed()) {
                cookieButtons.get(0).click();
                wait.until(ExpectedConditions.invisibilityOf(cookieButtons.get(0)));
            }
        } catch (TimeoutException | NoSuchElementException ignored) {}
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}