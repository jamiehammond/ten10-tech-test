package framework.site.page_objects;

import framework.site.Site;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogInPage extends Site {

    private static final String PAGE_HEADER = "Please enter your login credentails.";

    @FindBy(css = "h2") private WebElement pageHeader;
    @FindBy(id = "UserName") private WebElement emailInput;
    @FindBy(id = "Password") private WebElement passwordInput;
    @FindBy(id = "login-submit") private WebElement logInButton;
    @FindBy(id = "RememberMe") private WebElement rememberMeCheckBox;

    public void verifyPageHeader() throws Exception {
        verifyTextInElement(PAGE_HEADER, pageHeader);
    }

    public void enterEmail(String email) {
        enterTextIntoTheField(emailInput, email);
    }

    public void enterPassword(String password) {
        enterTextIntoTheField(passwordInput, password);
    }

    public void clickLogInButton() {
        clickOnElement(logInButton);
    }

    public void clickRememberMeCheckbox() {
        clickOnElement(rememberMeCheckBox);
    }

    public String getEmail() {
        return emailInput.getText();
    }

    public String getPassword() {
        return passwordInput.getText();
    }

    public boolean isRememberMeCheckboxSelected() {
        return rememberMeCheckBox.isSelected();
    }
}