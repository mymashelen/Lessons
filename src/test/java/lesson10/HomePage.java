package lesson10;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By cookieBanner = By.cssSelector(".cookie.show");
    private By cookieAcceptButton = By.cssSelector(".cookie__ok, .btn_black");
    private By paymentBlockTitle = By.xpath("//h2[contains(text(), 'Онлайн пополнение')]");
    private By paymentLogosContainer = By.cssSelector(".pay__partners");
    private By serviceDetailsLink = By.partialLinkText("Подробнее о сервисе");

    private By selectDropdown = By.cssSelector(".select__header");
    private By selectOptions = By.cssSelector(".select__option");
    private By selectedOption = By.cssSelector(".select__now");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void acceptCookies() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(cookieBanner));

            List<WebElement> cookieButtons = driver.findElements(cookieAcceptButton);
            if (!cookieButtons.isEmpty()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cookieButtons.get(0));
                wait.until(ExpectedConditions.invisibilityOfElementLocated(cookieBanner));
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Куки уже приняты или баннер отсутствует: " + e.getMessage());
        }
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.presenceOfElementLocated(paymentBlockTitle));
    }

    public String getPaymentBlockTitle() {
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(paymentBlockTitle));
        return title.getText().replace("\n", " ").trim();
    }

    public boolean arePaymentLogosDisplayed() {
        try {
            WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(paymentLogosContainer));
            List<WebElement> logos = container.findElements(By.tagName("img"));
            return logos.size() >= 3;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickServiceDetailsLink() {
        try {
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(serviceDetailsLink));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
            wait.until(ExpectedConditions.urlContains("oplaty"));
        } catch (Exception e) {
            System.out.println("Ошибка при клике на ссылку: " + e.getMessage());
        }
    }

    public boolean isServiceDetailsLinkWorking() {
        try {
            String originalUrl = driver.getCurrentUrl();
            clickServiceDetailsLink();
            return !driver.getCurrentUrl().equals(originalUrl);
        } catch (Exception e) {
            return false;
        }
    }

    public void selectPaymentOption(String optionName) {
        try {
            System.out.println("Пытаемся выбрать опцию: " + optionName);

            WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(selectDropdown));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", dropdown);
            Thread.sleep(1000);

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
            Thread.sleep(1000);

            selectOptionFromList(optionName);

            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("Ошибка при выборе опции '" + optionName + "': " + e.getMessage());
        }
    }

    private void selectOptionFromList(String optionName) {
        try {
            List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(selectOptions));
            System.out.println("Найдено опций: " + options.size());

            for (WebElement option : options) {
                String optionText = option.getText().trim();
                System.out.println("Проверяем опцию: '" + optionText + "'");

                if (optionText.equals(optionName)) {
                    System.out.println("Нашли точное совпадение: " + optionName);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                    return;
                }
            }

            System.out.println("Опция '" + optionName + "' не найдена в списке");

        } catch (Exception e) {
            System.out.println("Ошибка при выборе опции из списка: " + e.getMessage());
        }
    }

    public String getCurrentlySelectedPaymentOption() {
        try {
            WebElement selected = driver.findElement(selectedOption);
            return selected.getText().trim();
        } catch (Exception e) {
            return "Не удалось определить выбранную опцию";
        }
    }
}