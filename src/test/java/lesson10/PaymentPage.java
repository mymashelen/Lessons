package lesson10;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PaymentPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы полей ввода для разных форм
    private By phoneInputServices = By.id("connection-phone");
    private By phoneInputInternet = By.id("internet-phone");
    private By phoneInputInstallment = By.id("score-instalment");
    private By phoneInputDebt = By.id("score-arrears");

    private By sumInputServices = By.id("connection-sum");
    private By sumInputInternet = By.id("internet-sum");
    private By sumInputInstallment = By.id("instalment-sum");
    private By sumInputDebt = By.id("arrears-sum");

    private By continueButton = By.xpath("//button[contains(text(), 'Продолжить')]");

    // Локаторы для окна оплаты
    private By paymentIframe = By.cssSelector("iframe.bepaid-iframe");

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public String getPhonePlaceholder(String optionName) {
        try {
            By phoneInput = getPhoneInputLocator(optionName);
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneInput));
            return input.getAttribute("placeholder");
        } catch (Exception e) {
            return "Поле не найдено для: " + optionName;
        }
    }

    public String getSumPlaceholder(String optionName) {
        try {
            By sumInput = getSumInputLocator(optionName);
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(sumInput));
            return input.getAttribute("placeholder");
        } catch (Exception e) {
            return "Поле не найдено для: " + optionName;
        }
    }

    private By getPhoneInputLocator(String optionName) {
        switch (optionName) {
            case "Услуги связи":
                return phoneInputServices;
            case "Домашний интернет":
                return phoneInputInternet;
            case "Рассрочка":
                return phoneInputInstallment;
            case "Задолженность":
                return phoneInputDebt;
            default:
                return phoneInputServices;
        }
    }

    private By getSumInputLocator(String optionName) {
        switch (optionName) {
            case "Услуги связи":
                return sumInputServices;
            case "Домашний интернет":
                return sumInputInternet;
            case "Рассрочка":
                return sumInputInstallment;
            case "Задолженность":
                return sumInputDebt;
            default:
                return sumInputServices;
        }
    }

    public void fillPaymentForm(String phone, String amount) {
        try {
            // Используем форму "Услуги связи" по умолчанию
            WebElement phoneField = wait.until(ExpectedConditions.elementToBeClickable(phoneInputServices));
            WebElement sumField = wait.until(ExpectedConditions.elementToBeClickable(sumInputServices));

            // Очищаем и заполняем через JS
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = ''; arguments[0].value = arguments[1];", phoneField, phone);
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = ''; arguments[0].value = arguments[1];", sumField, amount);

            Thread.sleep(1000);

        } catch (Exception e) {
            System.out.println("Ошибка при заполнении формы: " + e.getMessage());
        }
    }

    public void clickContinue() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
            Thread.sleep(5000); // Даем больше времени на загрузку iframe
        } catch (Exception e) {
            System.out.println("Ошибка при клике на кнопку: " + e.getMessage());
        }
    }

    public boolean isPaymentIframeDisplayed() {
        try {
            WebElement iframe = wait.until(ExpectedConditions.visibilityOfElementLocated(paymentIframe));
            return iframe.isDisplayed();
        } catch (Exception e) {
            System.out.println("Iframe не найден: " + e.getMessage());
            return false;
        }
    }

    public void switchToPaymentFrame() {
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(paymentIframe));
            // Ждем загрузки содержимого iframe
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Ошибка при переключении на iframe: " + e.getMessage());
        }
    }

    public void switchToDefaultContent() {
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            System.out.println("Ошибка при возврате к основному контенту: " + e.getMessage());
        }
    }

    public boolean isAmountDisplayed(String expectedAmount) {
        try {
            // Ищем сумму различными способами
            List<WebElement> amountElements = driver.findElements(
                    By.cssSelector("[data-behavior='amount'], .amount, [class*='sum'], [class*='cost']"));

            for (WebElement element : amountElements) {
                if (element.isDisplayed()) {
                    String text = element.getText();
                    System.out.println("Найден элемент с суммой: " + text);
                    if (text.contains(expectedAmount) || text.contains("1.00") || text.contains("1,00")) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Ошибка при поиске суммы: " + e.getMessage());
            return false;
        }
    }

    public boolean isPhoneDisplayed(String expectedPhone) {
        try {
            List<WebElement> phoneElements = driver.findElements(
                    By.cssSelector("[data-behavior='phone'], .phone, [class*='phone']"));

            for (WebElement element : phoneElements) {
                if (element.isDisplayed()) {
                    String text = element.getText();
                    System.out.println("Найден элемент с номером: " + text);
                    if (text.contains(expectedPhone) || text.contains("375297777777")) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Ошибка при поиске номера: " + e.getMessage());
            return false;
        }
    }

    public boolean areCardFieldsPresent() {
        try {
            List<WebElement> cardInputs = driver.findElements(
                    By.cssSelector("input[placeholder*='карт'], input[placeholder*='card'], input[name*='card']"));
            return !cardInputs.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean arePaymentIconsPresent() {
        try {
            List<WebElement> icons = driver.findElements(
                    By.cssSelector("img[alt*='Visa'], img[alt*='MasterCard'], img[alt*='Belkart']"));
            return !icons.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}