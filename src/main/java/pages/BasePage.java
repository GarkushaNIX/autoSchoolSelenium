package pages;

import blocks.SearchResult;

import io.qameta.htmlelements.WebPage;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.annotation.Param;
import io.qameta.htmlelements.element.ExtendedList;
import io.qameta.htmlelements.element.HtmlElement;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public interface BasePage  extends WebPage {

    @FindBy("id('s-results-list-atf')//li[contains(@id,'result')]")
    ExtendedList<SearchResult> searchResultList();

    @FindBy(xpath = "id('s-results-list-atf')//li[contains(@id,'result')]")
    SearchResult searchResult;

    @FindBy(id = "twotabsearchtextbox")
    HtmlElement inputField;

    @FindBy(xpath = "//input[@value='Go']")
    HtmlElement searchButton;

    @FindBy(xpath = "id('nav-search')//div[@class='nav-left']")
    HtmlElement categoryDropdownArrow;

    @FindBy(xpath = "id('searchDropdownBox')//option[contains(text(),'Books')]")
    HtmlElement categoryDropdownOptionBook;

    @FindBy(id = "add-to-cart-button")
    HtmlElement addToCartButton;

    @FindBy(id = "nav-cart")
    HtmlElement cartButton;

//    public BasePage(WebDriver driver) {
//        PageFactory.initElements(driver, this);
//        this.driver = driver;
//    }
//
//    public void searchForRequest (String request) {
//        inputField.sendKeys(request);
//        searchButton.click();
//    }
//
//    public void changeSearchCategory () {
//        categoryDropdownArrow.click();
//        categoryDropdownOptionBook.click();
//    }
//
//    public List<SearchResult> getListOfResults () {
//        return searchResultList;
//    }
//
//    public SearchResult getFirstResult () {
//        return searchResult;
//    }
//
//    public void addToCartClick (){
//        addToCartButton.click();
//    }
//
//    public void goToCart () {
//        cartButton.click();
//    }
}
