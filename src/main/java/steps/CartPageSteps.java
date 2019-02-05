package steps;

import io.qameta.allure.Step;
import io.qameta.htmlelements.WebPageFactory;
import org.openqa.selenium.WebDriver;
import pages.CartPage;

import static baseTest.matchers.WebElementMatchers.isDisplayed;
import static baseTest.matchers.WebElementMatchers.hasText;
import static org.hamcrest.MatcherAssert.assertThat;

public class CartPageSteps {
    private WebDriver driver;

    CartPageSteps(WebDriver driver) {
        this.driver = driver;
    }

    @Step
    public CartPageSteps checkProductName(String name) {
        assertThat(onCartPage().productName().should(isDisplayed()), hasText(name));
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
