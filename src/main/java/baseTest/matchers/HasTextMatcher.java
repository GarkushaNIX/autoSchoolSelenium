package baseTest.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;

public class HasTextMatcher extends TypeSafeMatcher<WebElement> {

    private int timeout = 10;
    private Matcher<String> textMatcher;

    public HasTextMatcher(Matcher<String> textMatcher) {
        this.textMatcher = textMatcher;
    }

    public HasTextMatcher(Matcher<String> textMatcher, int timeout) {
        this.textMatcher = textMatcher;
        this.timeout = timeout;
    }

    @Override
    public boolean matchesSafely(WebElement item) {
        long waitUntil = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(timeout);
        boolean matched = false;
        while (System.currentTimeMillis() <= waitUntil && !matched) {
            try {
                matched = textMatcher.matches(item.getText());
                Thread.sleep(250);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return matched;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("collection size is").appendDescriptionOf(textMatcher);
    }

    @Override
    protected void describeMismatchSafely(WebElement item, Description mismatchDescription) {
        mismatchDescription.appendText("text of element ").appendValue(item).appendText(" was ")
                .appendValue(item.getText()).appendText(" while waiting ").appendValue(timeout).appendText(" seconds.");
    }

    @Factory
    public static Matcher<WebElement> hasText(final Matcher<String> textMatcher) {
        return new HasTextMatcher(textMatcher);
    }

    @Factory
    public static Matcher<WebElement> hasText(final String text) {
        return new HasTextMatcher(is(text));
    }

    @Factory
    public static Matcher<WebElement> hasText(final String text, int timeout) {
        return new HasTextMatcher(is(text), timeout);
    }

}
