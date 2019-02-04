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
    public void testCartNameAndPrice() {

        driver.get("https://www.amazon.com");

        BasePageSteps basePageSteps = new BasePageSteps(driver);

        basePageSteps
                .changeSearchCategory(testCategory)
                .searchForRequest(searchEntry)
                .checkPageTitle(searchEntry)
                .checkSearchResults(searchEntry);

        //Save name and price of the first product
        String firstResultName = basePageSteps.getFirstProductName();
        String firstResultPrice = basePageSteps.getFirstProductPrice();

        basePageSteps
                .addFirstProductToCart()
                .goToCartPage()
                .checkProductName(firstResultName)
                .checkProductPrice(firstResultPrice);
    }

}
