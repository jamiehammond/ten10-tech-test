package framework.site.page_objects;

import framework.site.Site;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.InputMismatchException;

public class InterestCalculatorPage extends Site {

    private static final String PAGE_HEADER = "Interest Calculator";

    @FindBy(css = "h1") private WebElement pageHeader;
    @FindBy(css = "button.nav-link") private WebElement logoutButton;
    @FindBy(id = "customRange1") private WebElement principalAmountInput;
    @FindBy(id = "dropdownMenuButton") private WebElement interestRateDropdownButton;
    @FindBy(id = "customRange1") private WebElement principalAmount;
    @FindBy(css = "a[data-value='Daily']") private WebElement dailyDurationButton;
    @FindBy(css = "a[data-value='Monthly']") private WebElement monthlyDurationButton;
    @FindBy(css = "a[data-value='Yearly']") private WebElement yearlyDurationButton;
    @FindBy(css = "label.form-check-label") private WebElement consentCheckBox;
    @FindBy(css = "button.btn-primary") private WebElement calculateButton;
    @FindBy(id = "interestAmount") private WebElement interestAmountText;
    @FindBy(id = "totalAmount") private WebElement totalAmountText;
    @FindBy(css = "div.dropdown-item") private WebElement dropdownListItem;


    public void verifyPageHeader() throws Exception {
        verifyTextInElement(PAGE_HEADER, pageHeader);
    }

    public void clickLogoutButton() {
        clickOnElement(logoutButton);
    }

    public void enterPrincipalAmount(int principalAmount) {
        if (principalAmount < 7500) {
            int leftSteps = (7500 - principalAmount) / 100;
            for (int i = 0; i < leftSteps; i++) {
                principalAmountInput.sendKeys(Keys.LEFT);
            }
        } else {
            int leftSteps = (15000 - principalAmount) / 100;
            for (int i = 0; i < leftSteps; i++) {
                principalAmountInput.sendKeys(Keys.RIGHT);
            }
        }
    }

    public void enterInterestRate(int interestRate) throws Exception {
        if (interestRate < 0 || interestRate > 16) {
            throw new InputMismatchException("Interest rate must be between 1 and 15 inclusive");
        }
        clickOnElement(interestRateDropdownButton);
        WebElement interestRateCheckBox = findElement(By.id("rate-" + interestRate + "%"));
        clickOnElement(interestRateCheckBox);
        clickOnElement(dropdownListItem);
    }

    public void clickDailyDurationButton() {
        waitForElementToAppear(dailyDurationButton);
        clickOnElement(dailyDurationButton);
    }

    public void clickMonthlyDurationButton() {
        clickOnElement(monthlyDurationButton);
    }

    public void clickYearlyDurationButton() {
        clickOnElement(yearlyDurationButton);
    }

    public void clickConsentCheckBox() {
        clickOnElement(consentCheckBox);
    }

    public boolean isConsentCheckboxSelected() {
        return consentCheckBox.isSelected();
    }

    public void clickCalculateButton() {
        clickOnElement(calculateButton);
    }

    public double getInterestAmount() {
        return Double.parseDouble(interestAmountText.getText().replaceAll("[^0-9.]", ""));
    }

    public double getTotalAmount() {
        return Double.parseDouble(totalAmountText.getText().replaceAll("[^0-9.]", ""));
    }
}