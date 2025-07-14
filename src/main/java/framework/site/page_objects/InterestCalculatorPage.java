package framework.site.page_objects;

import framework.site.Site;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InterestCalculatorPage extends Site {

    private static final String PAGE_HEADER = "Interest Calculator";

    @FindBy(css = "h1") private WebElement pageHeader;
    @FindBy(css = "button.nav-link") private WebElement logoutButton;

    public void verifyPageHeader() throws Exception {
        verifyTextInElement(PAGE_HEADER, pageHeader);
    }

    public void clickLogoutButton() {
        clickOnElement(logoutButton);
    }
}
