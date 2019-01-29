package pages;

import io.qameta.htmlelements.WebPage;
import io.qameta.htmlelements.element.ExtendedWebElement;
import io.qameta.htmlelements.element.HtmlElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class Cart{

    private WebDriver driver;

    @FindBy(xpath = "id('activeCartViewForm')//span[contains(@class,'sc-product-title')]")
    private HtmlElement productName;

    @FindBy(xpath = "//span[@class='sx-price sx-price-large']")
    private HtmlElement productPrice;

    public Cart(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public HtmlElement getProductName() {
        return productName;
    }

    public HtmlElement getProductPrice() {
        return productPrice;
    }
}