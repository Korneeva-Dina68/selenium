package ru.yandexmarket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    /**
     * @author Корнеева Дина
     * Драйвер
     */
    protected WebDriver chromeDriver;
    /**
     * @author Корнеева Дина
     * Драйвер ожидание
     */
    protected WebDriverWait wait;

    /**
     * @author Корнеева Дина
     * Здесь инициализируется драйвер и прописаны параметры, которые необходимо выполнить перед каждым тестом
     */
    @BeforeEach
    public void before() {
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));

        chromeDriver = new ChromeDriver();
        wait = new WebDriverWait(chromeDriver, 10);
        chromeDriver.manage().window().maximize();
        chromeDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        chromeDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        chromeDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
    }

    /**
     * @author Корнеева Дина
     * Здесь прописаны параметры, которые необходимо выполнить после каждого теста
     */
    @AfterEach
    public void after() {
        chromeDriver.quit();
    }
}
