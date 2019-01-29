package pages;

import blocks.SearchResult;
import io.qameta.htmlelements.WebPage;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.annotation.Param;
import io.qameta.htmlelements.element.ExtendedList;
import io.qameta.htmlelements.element.HtmlElement;

public interface BasePage extends WebPage {

    @FindBy("id('s-results-list-atf')//li[contains(@id,'result')]")
    ExtendedList<SearchResult> searchResultList();

    @FindBy("id('s-results-list-atf')//li[contains(@id,'result')]")
    SearchResult searchResultFirst();

    @FindBy("id('twotabsearchtextbox')")
    HtmlElement inputField();

    @FindBy("//input[@value='Go']")
    HtmlElement searchButton();

    @FindBy("id('nav-search')//div[@class='nav-left']")
    HtmlElement categoryDropdownArrow();

    @FindBy("id('searchDropdownBox')//option[contains(text(),'{{ category }}')]")
    HtmlElement categoryDropdownOption(@Param("category") String category);

    @FindBy("id('add-to-cart-button')")
    HtmlElement addToCartButton();

    @FindBy("id('nav-cart')")
    HtmlElement cartButton();
}
