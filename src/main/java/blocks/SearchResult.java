package blocks;

import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;
import io.qameta.htmlelements.element.HtmlElement;


public interface SearchResult extends ExtendedWebElement<SearchResult> {

    @FindBy(".//h2")
    HtmlElement productName();

    @FindBy(".//span[@class='sx-price sx-price-large']")
    HtmlElement productPrice();

}