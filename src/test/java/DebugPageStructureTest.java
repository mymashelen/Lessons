import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DebugPageStructureTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get("https://www.mts.by");

        // Принимаем куки если есть
        try {
            List<WebElement> cookieButtons = driver.findElements(By.cssSelector(".cookie__ok, [class*='cookie'] button, .btn_black"));
            if (!cookieButtons.isEmpty() && cookieButtons.get(0).isDisplayed()) {
                cookieButtons.get(0).click();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Куки уже приняты или баннер отсутствует");
        }
    }

    @Test
    public void debugPageStructure() {
        System.out.println("=== ДЕБАГ СТРУКТУРЫ СТРАНИЦЫ ===");

        // Ждем загрузки
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        // Ищем заголовок блока оплаты
        List<WebElement> titles = driver.findElements(By.xpath("//*[contains(text(), 'Онлайн пополнение')]"));
        System.out.println("Найдено заголовков с 'Онлайн пополнение': " + titles.size());
        for (WebElement title : titles) {
            System.out.println("Заголовок: " + title.getText() + " | Tag: " + title.getTagName() + " | Class: " + title.getAttribute("class"));
        }

        // Ищем табы/кнопки
        System.out.println("\n=== ПОИСК ТАБОВ ===");
        String[] tabNames = {"Услуги связи", "Домашний интернет", "Рассрочка", "Задолженность"};

        for (String tabName : tabNames) {
            List<WebElement> tabs = driver.findElements(By.xpath("//*[contains(text(), '" + tabName + "')]"));
            System.out.println("Найдено элементов с текстом '" + tabName + "': " + tabs.size());

            for (WebElement tab : tabs) {
                System.out.println("Элемент: " + tab.getText() + " | Tag: " + tab.getTagName() +
                        " | Class: " + tab.getAttribute("class") +
                        " | Clickable: " + tab.isEnabled() +
                        " | Visible: " + tab.isDisplayed());
            }
        }

        // Ищем формы оплаты
        System.out.println("\n=== ПОИСК ФОРМ ===");
        List<WebElement> forms = driver.findElements(By.cssSelector("form, [class*='form']"));
        System.out.println("Найдено форм: " + forms.size());
        for (WebElement form : forms) {
            System.out.println("Form ID: " + form.getAttribute("id") + " | Class: " + form.getAttribute("class"));
        }

        // Ищем поля ввода
        System.out.println("\n=== ПОИСК ПОЛЕЙ ВВОДА ===");
        List<WebElement> inputs = driver.findElements(By.cssSelector("input"));
        System.out.println("Найдено input полей: " + inputs.size());
        for (WebElement input : inputs) {
            System.out.println("Input: " + input.getAttribute("placeholder") +
                    " | Type: " + input.getAttribute("type") +
                    " | ID: " + input.getAttribute("id") +
                    " | Name: " + input.getAttribute("name"));
        }

        // Делаем скриншот для визуальной проверки
        try {
            Thread.sleep(5000); // Даем время посмотреть
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}