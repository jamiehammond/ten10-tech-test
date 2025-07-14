package step_definitions;

import framework.site.page_objects.InterestCalculatorPage;
import framework.site.page_objects.LogInPage;
import framework.site.page_objects.StartPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import framework.driver.DriverManager;
import framework.properties.PropertyLoader;
import framework.site.Site;
import org.openqa.selenium.support.PageFactory;

import java.text.DecimalFormat;
import java.util.InputMismatchException;

import static org.testng.Assert.*;

@Slf4j
public class InterestCalcStepDefs {

    WebDriver driver = DriverManager.getDriver();

    private final StartPage startPage = PageFactory.initElements(driver, StartPage.class);
    private final LogInPage logInPage = PageFactory.initElements(driver, LogInPage.class);
    private final InterestCalculatorPage interestCalculatorPage = PageFactory.initElements(driver, InterestCalculatorPage.class);

    int enteredPrincipalAmount;
    int enteredInterestRate;
    int enteredDurationInDays;

    @Before
    public void setUp() {
        driver.manage().deleteAllCookies();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            log.error("TEST FAILED!");
            log.error("Failed scenario name:" + scenario.getName());
        } else {
            log.info("TEST PASSED!");
        }
        Site.closeWindow();
        DriverManager.quitDriver();
    }

    private void enterLogInDetails() throws Exception {
        Site.goToUrl(PropertyLoader.getProperty("baseUrl"));
        startPage.verifyPageHeader();
        startPage.clickLogInButton();

        logInPage.verifyPageHeader();
        logInPage.enterEmail(PropertyLoader.getProperty("email"));
        logInPage.enterPassword(PropertyLoader.getProperty("password"));
    }

    @Given("a user logs in to the interest calculator")
    public void aUserLogsInToTheInterestCalculator() throws Exception {
        enterLogInDetails();
        logInPage.clickLogInButton();

        interestCalculatorPage.verifyPageHeader();
    }

    @Given("^a user logs in to the interest calculator with 'remember me' set to (.*?)$")
    public void aUserLogsInToTheInterestCalculatorWithRememberMeSetTo(boolean isRememberMeSelected) throws Exception {
        enterLogInDetails();
        if (isRememberMeSelected && !logInPage.isRememberMeCheckboxSelected() || !isRememberMeSelected && logInPage.isRememberMeCheckboxSelected()) {
            logInPage.clickRememberMeCheckbox();
        }
    }

    @Then("^the login credentials will be (.*?)$")
    public void theLoginCredentialsWillBeRemembered(boolean isRemembered) {
        if (isRemembered) {
            assertEquals(logInPage.getEmail(), PropertyLoader.getProperty("email"));
            assertEquals(logInPage.getPassword(), PropertyLoader.getProperty("password"));
            assertTrue(logInPage.isRememberMeCheckboxSelected());
        }
        else {
            assertEquals(logInPage.getEmail(), "");
            assertEquals(logInPage.getPassword(), "");
            assertFalse(logInPage.isRememberMeCheckboxSelected());
        }
    }

    @When("the user logs out")
    public void theUserLogsOut() throws Exception {
        interestCalculatorPage.clickLogoutButton();
        logInPage.verifyPageHeader();
    }

    @When("^the user enters the interest figures: (.*?), (.*?), (.*?), (.*?)$")
    public void theUserEntersTheInterestFiguresPrincipalAmountInterestRateDurationConsent(String principalAmount, String interestRate, String duration, boolean consent) throws Exception {
        enteredPrincipalAmount = Integer.parseInt(principalAmount);
        interestCalculatorPage.enterPrincipalAmount(enteredPrincipalAmount);
        enteredInterestRate = Integer.parseInt(interestRate);
        interestCalculatorPage.enterInterestRate(enteredInterestRate);
        switch(duration.toLowerCase()) {
            case "daily":
                interestCalculatorPage.clickDailyDurationButton();
                enteredDurationInDays = 1;
                break;
            case "monthly":
                interestCalculatorPage.clickMonthlyDurationButton();
                enteredDurationInDays = 28;
                break;
            case "yearly":
                interestCalculatorPage.clickYearlyDurationButton();
                enteredDurationInDays = 365;
                break;
            default:
                throw new InputMismatchException("Duration must be Daily, Monthly or Yearly");
        }
        if (consent && !interestCalculatorPage.isConsentCheckboxSelected() || !consent && interestCalculatorPage.isConsentCheckboxSelected()) {
            interestCalculatorPage.clickConsentCheckBox();
        }
        interestCalculatorPage.clickCalculateButton();
    }

    @Then("the displayed interest amounts will be correct to two decimal places")
    public void theDisplayedInterestAmountWillBeCorrectToTwoDecimalPlaces() {
        double interestRateDecimal = (double) enteredInterestRate / 100;
        double enteredDurationInYears = (double) enteredDurationInDays / 365;
        double expectedInterest = enteredPrincipalAmount * interestRateDecimal * enteredDurationInYears;
        double expectedTotalAmount = enteredPrincipalAmount + expectedInterest;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        assertEquals(interestCalculatorPage.getInterestAmount(), Double.parseDouble(decimalFormat.format(expectedInterest)));
        assertEquals(interestCalculatorPage.getTotalAmount(), Double.parseDouble(decimalFormat.format(expectedTotalAmount)));
    }
}