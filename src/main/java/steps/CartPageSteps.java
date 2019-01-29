package steps;

import io.qameta.allure.Step;
import io.qameta.htmlelements.WebPageFactory;
import org.openqa.selenium.WebDriver;
import pages.CartPage;

import static org.hamcrest.MatcherAssert.assertThat;

public class CartPageSteps {
    private WebDriver driver;

    public CartPageSteps(WebDriver driver) {
        this.driver = driver;
    }

    @Step
    public void checkProductName(String name) {
        String inCartName = onCartPage().productName().getText();
        assertThat("Different name. Expected " + name + " but was " + inCartName, inCartName.equals(name));
    }

    @Step
    public void checkProductPrice(String price) {
        String inCartPrice = onCartPage().productPrice().getText().replaceAll("\\D", "");
        assertThat("Different price. Expected " + price + " but was " + inCartPrice, inCartPrice.equals(price));
    }


    private CartPage onCartPage() {
        return new WebPageFactory().get(driver, CartPage.class);
    }
}
