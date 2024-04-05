package pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class MainPageYandex {
    /**
     * @author Корнеева Дина
     * Драйвер
     */
    protected WebDriver chromeDriver;
    /**
     * @author Корнеева Дина
     * Ожидание
     */
    protected WebDriverWait wait;
    /**
     * @author Корнеева Дина
     * Локатор Меню каталога
     */
    protected String menuCatalog = "//div[@data-baobab-name='catalog']";

    /**
     * @author Корнеева Дина
     * @param chromeDriver для предачи драйвера
     * Конструктор
     */
    public MainPageYandex(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        this.wait = new WebDriverWait(chromeDriver, 10);
    }

    /**
     * @author Корнеева Дина
     * @param nameSite принимает название сайта
     * Метод сравнивает ожидаемое и актуальное название текущей страницы
     */

    @Step("Проверяем заголовок сайта {nameSite}")
    public void checkTitleYandex(String nameSite) {
        String title = chromeDriver.getTitle();
        Assertions.assertTrue(title.contains(nameSite), "Тайтл " + title + " на сайте не содержит " + nameSite);
    }

    /**
     * @author Корнеева Дина
     * Метод нажимает на кнопку Каталог
     */

    @Step("Кликаем на кнопку Каталог")
    public void clickHamburgerButton() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(menuCatalog))).click();
    }
}
