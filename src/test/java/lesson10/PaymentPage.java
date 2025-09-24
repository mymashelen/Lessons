package lesson10;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PaymentPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Communication services form
    @FindBy(id = "connection-phone")
    private WebElement phoneInput;

    @FindBy(id = "connection-sum")
    private WebElement sumInput;

    @FindBy(id = "connection-email")
    private WebElement emailInput;

    @FindBy(xpath = "//form[@id='pay-connection']//button[text()='Продолжить']")
    private WebElement continueButton;

    // Internet form
    @FindBy(id = "internet-phone")
    private WebElement internetPhoneInput;

    // Installment form
    @FindBy(id = "score-instalment")
    private WebElement installmentAccountInput;

    // Debt form
    @FindBy(id = "score-arrears")
    private WebElement debtAccountInput;

    // Payment modal
    @FindBy(css = ".bepaid-iframe, iframe[src*='bepaid']")
    private WebElement paymentIframe;

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // Communication services methods
    public void fillCommunicationForm(String phone, String amount, String email) {
        wait.until(ExpectedConditions.visibilityOf(phoneInput)).clear();
        phoneInput.sendKeys(phone);

        wait.until(ExpectedConditions.visibilityOf(sumInput)).clear();
        sumInput.sendKeys(amount);

        if (email != null && !email.isEmpty()) {
            wait.until(ExpectedConditions.visibilityOf(emailInput)).clear();
            emailInput.sendKeys(email);
        }
    }

    public void clickContinueButton() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        wait.until(ExpectedConditions.visibilityOf(paymentIframe));
    }

    // Placeholder getters
    public String getPhonePlaceholder() {
        return wait.until(ExpectedConditions.visibilityOf(phoneInput))
                .getAttribute("placeholder");
    }

    public String getSumPlaceholder() {
        return wait.until(ExpectedConditions.visibilityOf(sumInput))
                .getAttribute("placeholder");
    }

    public String getEmailPlaceholder() {
        return wait.until(ExpectedConditions.visibilityOf(emailInput))
                .getAttribute("placeholder");
    }

    public String getInternetPhonePlaceholder() {
        return wait.until(ExpectedConditions.visibilityOf(internetPhoneInput))
                .getAttribute("placeholder");
    }

    public String getInstallmentAccountPlaceholder() {
        return wait.until(ExpectedConditions.visibilityOf(installmentAccountInput))
                .getAttribute("placeholder");
    }

    public String getDebtAccountPlaceholder() {
        return wait.until(ExpectedConditions.visibilityOf(debtAccountInput))
                .getAttribute("placeholder");
    }

    // Payment modal methods
    public void switchToPaymentFrame() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(paymentIframe));
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public boolean isPaymentModalDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(paymentIframe)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Methods to verify payment modal content (inside iframe)
    public String getDisplayedPhoneNumber() {
        try {
            WebElement phoneElement = wait.until(ExpectedConditions
                    .presenceOfElementLocated(By.cssSelector("[data-bepaid-value='phone'], .phone-number")));
            return phoneElement.getText();
        } catch (Exception e) {
            // Альтернативный поиск
            List<WebElement> elements = driver.findElements(By.xpath("//*[contains(text(),'375')]"));
            return elements.isEmpty() ? "Phone not found" : elements.get(0).getText();
        }
    }

    public String getDisplayedAmount() {
        try {
            WebElement amountElement = wait.until(ExpectedConditions
                    .presenceOfElementLocated(By.cssSelector(".payment-page__order-info-amount, .amount")));
            return amountElement.getText();
        } catch (Exception e) {
            List<WebElement> elements = driver.findElements(By.xpath("//*[contains(text(),'BYN')]"));
            return elements.isEmpty() ? "Amount not found" : elements.get(0).getText();
        }
    }

    public String getPaymentButtonText() {
        try {
            WebElement button = wait.until(ExpectedConditions
                    .elementToBeClickable(By.cssSelector(".payment-page__btn, .pay-button")));
            return button.getText();
        } catch (Exception e) {
            return "Button text not found";
        }
    }

    public boolean arePaymentSystemIconsDisplayed() {
        try {
            List<WebElement> icons = driver.findElements(
                    By.cssSelector(".payment-methods-list img, [class*='payment-system'] img"));
            return !icons.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areCardFieldsPresent() {
        try {
            // Ищем поля карты по различным селекторам
            List<WebElement> cardFields = driver.findElements(
                    By.cssSelector("[data-bepaid-type='card_number'], [name*='card'], input[placeholder*='карт']"));
            return !cardFields.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            Thread.sleep(500); // Небольшая пауза после скролла
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}