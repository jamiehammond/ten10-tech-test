package framework.site.page_objects;

import framework.site.Site;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StartPage extends Site {

    private static final String PAGE_HEADER = "Welcome to Ten10 Technical Test Website";

    @FindBy(css = "h1") private WebElement pageHeader;
    @FindBy(css = "button.btn-secondary") private WebElement logInButton;

    public void verifyPageHeader() throws Exception {
        verifyTextInElement(PAGE_HEADER, pageHeader);
    }

    public void clickLogInButton() {
        clickOnElement(logInButton);
    }
}
