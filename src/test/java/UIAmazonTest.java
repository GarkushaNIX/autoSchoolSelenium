import io.qameta.allure.Feature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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

        WebElement searchField = driver.findElement(By.id("twotabsearchtextbox"));
        WebElement goButton = driver.findElement(By.xpath("//input[@value='Go']"));
        WebElement searchDropdownButton = driver.findElement(By.xpath("id('nav-search')//div[@class='nav-left']"));
        WebElement categorySearchDropdownOption =
                driver.findElement(By.xpath("id('searchDropdownBox')//option[contains(text(),'" + category + "')]"));

        searchDropdownButton.click();
        categorySearchDropdownOption.click();
        searchField.sendKeys(searchEntry);
        goButton.click();

        //Check that we are on the correct page
        assertThat("This is " + driver.getTitle() + " page, while we were searching for " + searchEntry,
                driver.getTitle().contains(searchEntry));

        List<WebElement> resultsList =
                driver.findElements(By.xpath("id('s-results-list-atf')//li[contains(@id,'result')]"));
        resultsList.removeIf(result -> result.getAttribute("class").contains("AdHolder"));

        //Check that all elements match the search word
        for (WebElement result : resultsList) {
            assertThat("Item \n " + result.getText() + "\ndoes not contain search word",
                    result.getText().contains(searchEntry));
        }

        //Save name and price of the first product
        String firstItemName = driver.findElement(By.xpath("id('s-results-list-atf')//li[contains(@id,'result')]")).
                findElement(By.xpath("//h2")).getText();
        String firstItemPrice = driver.findElement(By.xpath("id('s-results-list-atf')//li[contains(@id,'result')]"))
                .findElement(By.xpath("//span[@class='sx-price sx-price-large']"))
                .getText().replaceAll("\\D", "");

        //Open product page and add it to cart
        driver.findElement(By.xpath("id('s-results-list-atf')//li[contains(@id,'result')]")).
                findElement(By.xpath("//h2")).click();
        driver.findElement(By.id("add-to-cart-button")).click();

        //Open cart and compare displayed name and price of the product with saved
        driver.findElement(By.id("nav-cart")).click();
        String inCartName = driver.findElement(By.xpath("id('activeCartViewForm')//span[contains(@class,'sc-product-title')]")).getText();
        String inCartPrice = driver.findElement(By.xpath("id('gutterCartViewForm')//span[contains(@class,'sc-price')]"))
                .getText().replaceAll("\\D", "");

        assertThat("Different name. Expected " + firstItemName + " but was " + inCartName, inCartName.equals(firstItemName));
        assertThat("Different price. Expected " + firstItemPrice + " but was " + inCartPrice,
                inCartPrice.equals(firstItemPrice));
    }

}
