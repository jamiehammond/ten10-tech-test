package step_definitions;

import framework.site.page_objects.InterestCalculatorPage;
import framework.site.page_objects.LogInPage;
import framework.site.page_objects.StartPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import framework.driver.DriverManager;
import framework.properties.PropertyLoader;
import framework.site.Site;
import org.openqa.selenium.support.PageFactory;

import static org.testng.Assert.*;

public class InterestCalcStepDefs {

    WebDriver driver = DriverManager.getDriver();

    private final StartPage startPage = PageFactory.initElements(driver, StartPage.class);
    private final LogInPage logInPage = PageFactory.initElements(driver, LogInPage.class);
    private final InterestCalculatorPage interestCalculatorPage = PageFactory.initElements(driver, InterestCalculatorPage.class);

    @Given("^a user logs in to the interest calculator with 'remember me' set to (.*?)$")
    public void aUserLogsInToTheInterestCalculator(boolean isRememberMeSelected) throws Exception {
        Site.goToUrl(PropertyLoader.getProperty("baseUrl"));
        startPage.verifyPageHeader();
        startPage.clickLogInButton();

        logInPage.verifyPageHeader();
        logInPage.enterEmail(PropertyLoader.getProperty("email"));
        logInPage.enterPassword(PropertyLoader.getProperty("password"));
        if (isRememberMeSelected && !logInPage.isRememberMeCheckboxSelected() || !isRememberMeSelected && logInPage.isRememberMeCheckboxSelected()) {
            logInPage.clickRememberMeCheckbox();
        }
        logInPage.clickLogInButton();

        interestCalculatorPage.verifyPageHeader();
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
}