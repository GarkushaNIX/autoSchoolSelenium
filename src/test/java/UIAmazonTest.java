import blocks.SearchResult;
import io.qameta.allure.Feature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import pages.BasePage;
import pages.Cart;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@Feature("Amazon test")
@RunWith(Parameterized.class)
public class UIAmazonTest {

    private WebDriver driver;
    private String searchEntry;

    public UIAmazonTest(String searchEntry) {
        this.searchEntry = searchEntry;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<String> testData() {
        return Arrays.asList("Pratchett", "GURPS", "Lovecraft");
    }

    @Before
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void finish() {
        driver.quit();
    }

    @Test
    public void testAmazon() {
        //test data
        String category = "Books";

        driver.get("https://www.amazon.com");

        BasePage page = PageFactory.initElements(driver, BasePage.class);

        page.changeSearchCategory();
        page.searchForRequest(searchEntry);

        //Check that we are on the correct page
        assertThat("This is " + driver.getTitle() + " page, while we were searching for " + searchEntry,
                driver.getTitle().contains(searchEntry));

        //Save list of results and delete sponsored content
        List<SearchResult> resultsList = page.getListOfResults();
        resultsList.removeIf(result -> result.getAttribute("class").contains("AdHolder"));

        //Check that all elements match the search word
        for (WebElement result : resultsList) {
            assertThat("Item \n " + result.getText() + "\ndoes not contain search word",
                    result.getText().contains(searchEntry));
        }

        //Save name and price of the first product
        String firstResultName = page.getFirstResult().productName().getText();
        String firstResultPrice = page.getFirstResult().productPrice()
                .getText().replaceAll("\\D", "");

        //Open product page and add it to cart
        page.getFirstResult().productName().click();
        page.addToCartClick();

        //Open cart and compare displayed name and price of the product with saved
        page.goToCart();

        Cart cart = PageFactory.initElements(driver, Cart.class);

        String inCartName = cart.getProductName().getText();
        String inCartPrice = cart.getProductPrice().getText().replaceAll("\\D", "");

        assertThat("Different name. Expected " + firstResultName + " but was " + inCartName, inCartName.equals(firstResultName));
        assertThat("Different price. Expected " + firstResultPrice + " but was " + inCartPrice,
                inCartPrice.equals(firstResultPrice));
    }

}
