package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CatalogPage {
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
     * @param chromeDriver для предачи драйвера
     * Конструктор
     */
    public CatalogPage(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        this.wait = new WebDriverWait(chromeDriver, 10);
    }

    /**
     * @author Корнеева Дина
     * @param categoryLink принимает название категории
     * @param subItems принимает название подкатегории
     * Метод в каталоге наводит курсор на категорию и кликает на подкатегорию
     */

    @Step("В каталоге выбираем категорию: {categoryLink} и подкатегорию: {subItems} ")
    public void productSelectionTheCatalog(String categoryLink, String subItems) {

        Actions actions = new Actions(chromeDriver);
        actions.moveToElement(chromeDriver.findElement(By.xpath("//span[text()='" + categoryLink + "']")))
                .build()
                .perform();
        chromeDriver.findElement(By.xpath("//a[text()='" + subItems + "']")).click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@data-cms-page-id='search-page']")));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@data-apiary-widget-name='@marketfront/SearchSerpPicker']")));
    }
}
