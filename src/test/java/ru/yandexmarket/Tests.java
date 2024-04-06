package ru.yandexmarket;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.CatalogPage;
import pages.MainPageYandex;
import pages.ResultSearchProducts;

import static helpers.Properties.testsProperties;

public class Tests extends BaseTest {

    @Feature("Проверка результатов поиска отфильтрованных товаров")
    @DisplayName("Зайти на сайт, задать нужные фильтры, проверить, что результат соответствует фильтрам")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#providerCheckingSearchResult")
    public void testYandex(String titleSite, String category, String subCategory, String minPrice, String maxPrice, String filter1, String filter2) {
        chromeDriver.get(testsProperties.yandexurl());
        MainPageYandex mpy = new MainPageYandex(chromeDriver);
        mpy.checkTitleYandex(titleSite);
        mpy.clickHamburgerButton();
        CatalogPage cp = new CatalogPage(chromeDriver);
        cp.productSelectionTheCatalog(category, subCategory);
        ResultSearchProducts rsp = new ResultSearchProducts(chromeDriver);
        rsp.checkRequiredPageHasOpened(subCategory);
        rsp.filterMinPrice(minPrice);
        rsp.filterMaxPrice(maxPrice);
        rsp.filterWord(filter1);
        rsp.filterWord(filter2);
        rsp.checkQuantityOnFirstPage();
        rsp.checkAllPagesForComplianceWithFilter();
        rsp.goFirstPage();
        rsp.searchInTheLine();
        rsp.checkingThatTheWordIsPresentOnThePage();
    }
}
