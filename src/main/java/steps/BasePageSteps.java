package steps;

import baseTest.matchers.WebElementMatchers;
import blocks.SearchResult;
import io.qameta.allure.Step;
import io.qameta.htmlelements.WebPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.List;

import static baseTest.matchers.WebElementMatchers.isDisplayed;
import static baseTest.matchers.WebElementMatchers.hasText;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasePageSteps {
    private WebDriver driver;

    public BasePageSteps(WebDriver driver) {
        this.driver = driver;
    }

    @Step
    public BasePageSteps changeSearchCategory(String category) {
        onBasePage().categoryDropdownArrow().should(isDisplayed()).click();
        onBasePage().categoryDropdownOption(category).should(isDisplayed()).click();
        return this;
    }

    @Step
    public BasePageSteps searchForRequest(String request) {
        onBasePage().inputField().should(isDisplayed()).sendKeys(request);
        onBasePage().searchButton().should(isDisplayed()).click();
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
            assertThat(result, hasText(request));
//            assertThat("Item \n " + result.getText() + "\ndoes not contain search word",
//                    result.getText().contains(request));
        }
        return this;
    }

    @Step
    public String getFirstProductName() {
        return onBasePage().searchResultFirst().should(isDisplayed()).productName().getText();
    }

    @Step
    public String getFirstProductPrice() {
        return onBasePage().searchResultFirst().should(isDisplayed()).productPrice()
                .getText().replaceAll("\\D", "");
    }

    @Step
    public BasePageSteps addFirstProductToCart() {
        onBasePage().searchResultFirst().productName().should(isDisplayed()).click();
        onBasePage().addToCartButton().should(isDisplayed()).click();
        return this;
    }

    @Step
    public CartPageSteps goToCartPage() {
        onBasePage().cartButton().should(isDisplayed()).click();
        return new CartPageSteps(driver);
    }

    private BasePage onBasePage() {
        return new WebPageFactory().get(driver, BasePage.class);
    }

}
