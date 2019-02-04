package steps;

import io.qameta.allure.Step;
import io.qameta.htmlelements.WebPageFactory;
import org.openqa.selenium.WebDriver;
import pages.CartPage;

import static org.hamcrest.MatcherAssert.assertThat;

public class CartPageSteps {
    private WebDriver driver;

    CartPageSteps(WebDriver driver) {
        this.driver = driver;
    }

    @Step
    public CartPageSteps checkProductName(String name) {
        String inCartName = onCartPage().productName().getText();
        assertThat("Different name. Expected " + name + " but was " + inCartName, inCartName.equals(name));
        return this;
    }

    @Step
    public CartPageSteps checkProductPrice(String price) {
        String inCartPrice = onCartPage().productPrice().getText().replaceAll("\\D", "");
        assertThat("Different price. Expected " + price + " but was " + inCartPrice, inCartPrice.equals(price));
        return this;
    }


    private CartPage onCartPage() {
        return new WebPageFactory().get(driver, CartPage.class);
    }
}
