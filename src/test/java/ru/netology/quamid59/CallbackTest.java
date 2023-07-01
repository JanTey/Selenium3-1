package ru.netology.quamid59;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CallBackTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        //options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        //driver.get("http://localhost:9999");
        
    }

    @AfterEach
    static void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void cardForm() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Дорофеев");
        //Thread.sleep(1000);
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+70001110000");
        //Thread.sleep(1000);
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        //Thread.sleep(1000);
        driver.findElement(By.className("button")).click();
        //Thread.sleep(1000);
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals(expected, actual);

    }

    @Test
    void cardFormWithDash() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Сусанин-Дорофеев Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+70001110000");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void cardFormWithSpace() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Спартак Сидор Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+70001110000");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void cardFormJustName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Сергей");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+70001110000");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void cardFormInvalidName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Clop Viron");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+70001110000");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void cardFormInvalidPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Сидоров Николай");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7000111000");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void cardFormInvalidPhoneWith12ch() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Сидоров Николай");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+700011100001");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void cardFormWithoutName() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+71234582369");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void cardFormWithoutPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Сидоров Николай");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void cardFormWithoutCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Сидоров Николай");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+71234582369");
        driver.findElement(By.className("button")).click();

        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText().trim();

        assertEquals(expected, actual);
    }
}
