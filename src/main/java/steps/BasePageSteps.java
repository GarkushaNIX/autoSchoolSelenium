package steps;

import blocks.SearchResult;
import io.qameta.allure.Step;
import io.qameta.htmlelements.WebPageFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class BasePageSteps {
    private WebDriver driver;

    public BasePageSteps(WebDriver driver) {
        this.driver = driver;
    }

    @Step
    public void changeSearchCategory(String category) {
        onBasePage().categoryDropdownArrow().click();
        onBasePage().categoryDropdownOption(category).click();
    }

    @Step
    public void searchForRequest(String request) {
        onBasePage().inputField().sendKeys(request);
        onBasePage().searchButton().click();
    }

    @Step
    public void checkPageTitle(String request) {
        assertThat("This is " + driver.getTitle() + " page, while we were searching for " + request,
                driver.getTitle().contains(request));
    }

    @Step
    public void checkSearchResults(String request) {
        List<SearchResult> list = onBasePage().searchResultList();
        for (WebElement result : list) {
            assertThat("Item \n " + result.getText() + "\ndoes not contain search word",
                    result.getText().contains(request));
        }
    }

    @Step
    public String getFirstProductName() {
        return onBasePage().searchResultFirst().productName().getText();
    }

    @Step
    public String getFirstProductPrice() {
        return onBasePage().searchResultFirst().productPrice()
                .getText().replaceAll("\\D", "");
    }

    @Step
    public void addFirstProductToCart() {
        onBasePage().searchResultFirst().productName().click();
        onBasePage().addToCartButton().click();
    }

    @Step
    public void goToCartPage() {
        onBasePage().cartButton().click();
    }

    private BasePage onBasePage() {
        return new WebPageFactory().get(driver, BasePage.class);
    }

}
