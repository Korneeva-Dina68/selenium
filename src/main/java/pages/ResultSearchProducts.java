package pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ResultSearchProducts {
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
     * Поисковая строка в хедере
     */
    protected WebElement searchField;
    /**
     * @author Корнеева Дина
     * Элемент заголовка страницы
     */
    protected WebElement titleResultSearch;
    /**
     * @author Корнеева Дина
     * Кнопка установки минимальной цены
     */
    protected WebElement filterPriceMin;
    /**
     * @author Корнеева Дина
     * Кнопка установки максимальной цены
     */
    protected WebElement filterPriceMax;
    /**
     * @author Корнеева Дина
     * Кнопка Найти в хедере
     */
    protected WebElement searchButton;
    /**
     * @author Корнеева Дина
     * Элемент для ожидания загрузки
     */
    protected String loading = "//div[@data-zone-name='SearchSerp']//span[@aria-label='Загрузка...']";
    /**
     * @author Корнеева Дина
     * Элемент с названием продукта
     */
    protected String productName = "//h3[@data-auto='snippet-title']";
    /**
     * @author Корнеева Дина
     * Элемент с ценой продукта
     */
    protected String productPrice = "//span[@data-auto='snippet-price-current']";
    /**
     * @author Корнеева Дина
     * Строка для хранения url первой страницы результата поиска
     */
    protected String currentUrl;
    /**
     * @author Корнеева Дина
     * Строка для хранения названия первого продукта в списке
     */
    protected String firstProduct;
    /**
     * @author Корнеева Дина
     * Кнопка Показать ещё
     */
    protected String buttonShowMore = "//button[@data-auto='pager-more']";
    /**
     * @author Корнеева Дина
     * Элемент для поиска всех товаров на странице
     */
    protected String listOfProducts = "//div[@data-apiary-widget-name='@light/Organic']";

    /**
     * @author Корнеева Дина
     * @param chromeDriver для предачи драйвера
     * Конструктор
     */
    public ResultSearchProducts(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        wait = new WebDriverWait(chromeDriver, 10);
        this.searchField = chromeDriver.findElement(By.xpath("//input[@placeholder='Искать товары']"));
        this.titleResultSearch = chromeDriver.findElement(By.xpath("//h1[@data-auto='title']"));
        this.filterPriceMin = chromeDriver.findElement(By.xpath("//div[@data-auto='filter-range-glprice']//span[@data-auto='filter-range-min']//input"));
        this.filterPriceMax = chromeDriver.findElement(By.xpath("//div[@data-auto='filter-range-glprice']//span[@data-auto='filter-range-max']//input"));
        this.searchButton = chromeDriver.findElement(By.xpath("//button[@data-auto='search-button']"));
    }

    /**
     * @author Корнеева Дина
     * @param title для передачи названия текущей страницы
     * Метод сравнивает ожидаемое и актуальное название текущей страницы
     */
    @Step("Проверяем название текущей страницы: {title}")
    public void checkRequiredPageHasOpened(String title) {
        Assertions.assertEquals(title, titleResultSearch.getText(), "Текущая страница не " + title);
    }

    /**
     * @author Корнеева Дина
     * @param minPrice для передачи минимальной цены
     * Метод вводит минимальную цену в соответсвующую строку
     */
    @Step("Устанавливаем минимальную цену в фильтрах товара: {minPrice}")
    public void filterMinPrice(String minPrice) {
        filterPriceMin.click();
        filterPriceMin.sendKeys(minPrice);
    }

    /**
     * @author Корнеева Дина
     * @param maxPrice для передачи максимальной цены
     * Метод вводит максимальную цену в соответсвующую строку
     */
    @Step("Устанавливаем максимальную цену в фильтрах товара: {maxPrice}")
    public void filterMaxPrice(String maxPrice) {
        filterPriceMax.click();
        filterPriceMax.sendKeys(maxPrice);
    }

    /**
     * @author Корнеева Дина
     * @param filter для передачи значения для фильтра
     * Метод устанавливает любое значение фильтра, переданное в параметрах
     */
    @Step("Устанавливаем фильтр: {filter}")
    public void filterWord(String filter) {
        chromeDriver.findElement(By.xpath("//span[text()='" + filter + "']")).click();
    }

    /**
     * @author Корнеева Дина
     * Метод проверяет, что на текущей странице больше 12 позиций товара
     */
    @Step("Проверяем, что на первой странице больше 12 товаров и сохраняем ссылку на эту страницу")
    public void checkQuantityOnFirstPage() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loading)));
        List<WebElement> searchResults = chromeDriver.findElements(By.xpath(productName));
        for (WebElement result : searchResults) {
            System.out.println(result.getText());
        }
        Assertions.assertTrue(searchResults.size() > 12, "На первой странице отображается менее или ровно 12 элементов товаров");
        currentUrl = chromeDriver.getCurrentUrl();
    }

    /**
     * @author Корнеева Дина
     * Метод проверяет, что на всех страницах результата поиска товар соответствует выбранным фильтрам
     */
    @Step("Проверяем, что на всех страницах результата поиска товар соответствует установленным фильтрам")
    public void checkAllPagesForComplianceWithFilter() {

        JavascriptExecutor js = (JavascriptExecutor) chromeDriver;

        while (true) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

            List<WebElement> nextPageElements = chromeDriver.findElements(By.xpath(buttonShowMore));
            if (nextPageElements.size() == 0) {
                break;
            } else {
                wait.until(ExpectedConditions.elementToBeClickable(chromeDriver.findElement(By.xpath(buttonShowMore)))).click();
            }
        }

        List<WebElement> products = chromeDriver.findElements(By.xpath(listOfProducts));
        System.out.println(products.size());

        for (WebElement product : products) {
            String productTitle = product.findElement(By.xpath(productName)).getText().toUpperCase();
            boolean isHpOrLenovo = productTitle.contains("HP") || productTitle.contains("LENOVO") || productTitle.contains("THINKPAD");
            WebElement priceElement = product.findElement(By.xpath(productPrice));
            int price = Integer.parseInt(priceElement.getText().replaceAll("[^0-9]", ""));
            boolean isPriceInRange = price >= 10000 && price <= 30000;

            Assertions.assertTrue(isHpOrLenovo, "Товар не соответствует производителю " + productTitle);
            Assertions.assertTrue(isPriceInRange, "Товар не соответствует цене" + priceElement);

        }
    }

    /**
     * @author Корнеева Дина
     * Метод переходит на первую страницу с результатами поиска и сохраняет в переменную название первого товара
     */
    @Step("Переходим на первую страницу поиска и сохраняем название первого товара")
    public void goFirstPage() {
        chromeDriver.get(currentUrl);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loading)));
        List<WebElement> listResult = chromeDriver.findElements(By.xpath(productName));
        firstProduct = listResult.get(0).getText();

    }
    /**
     * @author Корнеева Дина
     * Метод вводит в поисковую строку название сохраненного первого товара и нажимает кнопку Поиск
     */
    @Step("Вводим в поисковую строку название первого товара")
    public void searchInTheLine() {
        searchField.click();
        searchField.sendKeys(firstProduct);
        searchButton.click();
    }

    /**
     * @author Корнеева Дина
     * Метод проверяет, что в результатах поска на странице присутствует искомый товар
     */
    @Step("Проверяем, что в результатах поиска присутствует название первого товара")
    public void checkingThatTheWordIsPresentOnThePage() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loading)));
        List<WebElement> listResult = chromeDriver.findElements(By.xpath(productName));
        Assertions.assertTrue(listResult.stream()
                .map(WebElement::getText)
                .anyMatch(x -> x.equals(firstProduct)), "На первой странице результатов поиска нет " + firstProduct);
    }
}
