import baseTest.BaseTest;
import io.qameta.allure.Feature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.BasePageSteps;
import steps.CartPageSteps;

import java.util.Arrays;

@Feature("Amazon test")
@RunWith(Parameterized.class)
public class UIAmazonTest extends BaseTest {

    private String searchEntry;
    private String testCategory = "Books";

    public UIAmazonTest(String searchEntry) {
        this.searchEntry = searchEntry;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<String> testData() {
        return Arrays.asList("Pratchett", "GURPS", "Lovecraft");
    }

    @Test
    public void testAmazon() {

        super.driver.get("https://www.amazon.com");

        BasePageSteps basePageSteps = new BasePageSteps(driver);
        basePageSteps.changeSearchCategory(testCategory);
        basePageSteps.searchForRequest(searchEntry);
        basePageSteps.checkPageTitle(searchEntry);
        basePageSteps.checkSearchResults(searchEntry);

        //Save name and price of the first product
        String firstResultName = basePageSteps.getFirstProductName();
        String firstResultPrice = basePageSteps.getFirstProductPrice();

        //Open product page and add it to cart
        basePageSteps.addFirstProductToCart();

        //Open cart and compare displayed name and price of the product with saved
        basePageSteps.goToCartPage();

        CartPageSteps cartPageSteps = new CartPageSteps(driver);
        cartPageSteps.checkProductName(firstResultName);
        cartPageSteps.checkProductPrice(firstResultPrice);
    }

}
