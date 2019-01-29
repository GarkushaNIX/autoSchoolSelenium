package pages;

import io.qameta.htmlelements.WebPage;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.HtmlElement;


public interface CartPage extends WebPage {

    @FindBy("id('activeCartViewForm')//span[contains(@class,'sc-product-title')]")
    HtmlElement productName();

    @FindBy("id('gutterCartViewForm')//span[contains(@class,'sc-price')]")
    HtmlElement productPrice();
}