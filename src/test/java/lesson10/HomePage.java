package lesson10;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = ".btn.btn_black.cookie__ok, .cookie-warning__accept")
    private WebElement acceptCookiesButton;

    @FindBy(xpath = "//div[contains(@class,'pay')]//h2[contains(text(),'Онлайн пополнение')]")
    private WebElement blockTitle;

    @FindBy(css = ".pay__partners ul li img, .payment-systems img")
    private List<WebElement> paymentSystemLogos;

    @FindBy(linkText = "Подробнее о сервисе")
    private WebElement serviceDetailsLink;

    // Улучшенные локаторы для dropdown
    @FindBy(css = ".pay__select-trigger, .select__trigger, [class*='select-trigger']")
    private WebElement serviceDropdown;

    @FindBy(xpath = "//div[contains(@class,'pay__select-option') and contains(text(),'Услуги связи')]")
    private WebElement communicationServicesOption;

    @FindBy(xpath = "//div[contains(@class,'pay__select-option') and contains(text(),'Домашний интернет')]")
    private WebElement internetOption;

    @FindBy(xpath = "//div[contains(@class,'pay__select-option') and contains(text(),'Рассрочка')]")
    private WebElement installmentOption;

    @FindBy(xpath = "//div[contains(@class,'pay__select-option') and contains(text(),'Задолженность')]")
    private WebElement debtOption;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void acceptCookies() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton)).click();
            wait.until(ExpectedConditions.invisibilityOf(acceptCookiesButton));
        } catch (Exception e) {
            System.out.println("Cookies already accepted or not present: " + e.getMessage());
        }
    }

    public void waitForPageToLoad() {
        // Ждем загрузки основных элементов
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class,'pay')]")));
    }

    public String getBlockTitle() {
        return wait.until(ExpectedConditions.visibilityOf(blockTitle))
                .getText().replace("\n", " ").trim();
    }

    public int getPaymentSystemLogosCount() {
        wait.until(ExpectedConditions.visibilityOfAllElements(paymentSystemLogos));
        return paymentSystemLogos.size();
    }

    public String[] getPaymentSystemAltTexts() {
        wait.until(ExpectedConditions.visibilityOfAllElements(paymentSystemLogos));
        return paymentSystemLogos.stream()
                .map(logo -> logo.getAttribute("alt"))
                .toArray(String[]::new);
    }

    public void clickServiceDetailsLink() {
        wait.until(ExpectedConditions.elementToBeClickable(serviceDetailsLink)).click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void openServiceDropdown() {
        try {
            // Ждем и кликаем на dropdown
            wait.until(ExpectedConditions.elementToBeClickable(serviceDropdown)).click();
            // Ждем появления опций
            wait.until(ExpectedConditions.visibilityOf(communicationServicesOption));
        } catch (Exception e) {
            // Попробуем альтернативный локатор
            try {
                WebElement altDropdown = driver.findElement(By.cssSelector(".select__trigger, [class*='dropdown']"));
                altDropdown.click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(text(),'Услуги связи')]")));
            } catch (Exception ex) {
                System.out.println("Cannot find dropdown: " + ex.getMessage());
                throw ex;
            }
        }
    }

    public void selectCommunicationServices() {
        wait.until(ExpectedConditions.elementToBeClickable(communicationServicesOption)).click();
        waitForFormToLoad("connection-phone");
    }

    public void selectInternetServices() {
        wait.until(ExpectedConditions.elementToBeClickable(internetOption)).click();
        waitForFormToLoad("internet-phone");
    }

    public void selectInstallmentServices() {
        wait.until(ExpectedConditions.elementToBeClickable(installmentOption)).click();
        waitForFormToLoad("score-instalment");
    }

    public void selectDebtServices() {
        wait.until(ExpectedConditions.elementToBeClickable(debtOption)).click();
        waitForFormToLoad("score-arrears");
    }

    private void waitForFormToLoad(String elementId) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(elementId)));
    }
}