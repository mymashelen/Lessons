package lesson11;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaymentPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By phoneInputServices = By.id("connection-phone");
    private By phoneInputInternet = By.id("internet-phone");
    private By phoneInputInstallment = By.id("score-instalment");
    private By phoneInputDebt = By.id("score-arrears");

    private By sumInputServices = By.id("connection-sum");
    private By sumInputInternet = By.id("internet-sum");
    private By sumInputInstallment = By.id("instalment-sum");
    private By sumInputDebt = By.id("arrears-sum");

    private By continueButton = By.xpath("//button[contains(text(), 'Продолжить')]");

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
            WebElement phoneField = wait.until(ExpectedConditions.elementToBeClickable(phoneInputServices));
            WebElement sumField = wait.until(ExpectedConditions.elementToBeClickable(sumInputServices));

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

    public boolean isTextPresent(String text) {
        try {
            return driver.getPageSource().contains(text);
        } catch (Exception e) {
            return false;
        }
    }
}