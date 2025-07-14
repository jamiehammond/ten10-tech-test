package framework.site.page_objects;

import framework.site.Site;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StartPage extends Site {

    private static final String PAGE_HEADER = "Sign in";

    @FindBy(css = "h1") private WebElement pageHeader;

    public void verifyPageHeader() throws Exception {
        verifyTextInElement(PAGE_HEADER, pageHeader);
    }
}
