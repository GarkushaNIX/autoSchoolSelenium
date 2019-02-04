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
    public BasePageSteps changeSearchCategory(String category) {
        onBasePage().categoryDropdownArrow().click();
        onBasePage().categoryDropdownOption(category).click();
        return this;
    }

    @Step
    public BasePageSteps searchForRequest(String request) {
        onBasePage().inputField().sendKeys(request);
        onBasePage().searchButton().click();
        return this;
    }

    @Step
    public BasePageSteps checkPageTitle(String request) {
        assertThat("This is " + driver.getTitle() + " page, while we were searching for " + request,
                driver.getTitle().contains(request));
        return this;
    }

    @Step
    public BasePageSteps checkSearchResults(String request) {
        List<SearchResult> list = onBasePage().searchResultList();
        for (WebElement result : list) {
            assertThat("Item \n " + result.getText() + "\ndoes not contain search word",
                    result.getText().contains(request));
        }
        return this;
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
    public BasePageSteps addFirstProductToCart() {
        onBasePage().searchResultFirst().productName().click();
        onBasePage().addToCartButton().click();
        return this;
    }

    @Step
    public CartPageSteps goToCartPage() {
        onBasePage().cartButton().click();
        return new CartPageSteps(driver);
    }

    private BasePage onBasePage() {
        return new WebPageFactory().get(driver, BasePage.class);
    }

}
