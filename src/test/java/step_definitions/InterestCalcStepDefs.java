package step_definitions;

import framework.site.page_objects.StartPage;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import framework.driver.DriverManager;
import framework.properties.PropertyLoader;
import framework.site.Site;
import framework.site.page_objects.*;
import org.openqa.selenium.support.PageFactory;

public class InterestCalcStepDefs {

    WebDriver driver = DriverManager.getDriver();

    private final StartPage startPage = PageFactory.initElements(driver, StartPage.class);

    @Given("a user logs in to the interest calculator")
    public void aUserLogsInToTheInterestCalculator() throws Exception {
        Site.goToUrl(PropertyLoader.getProperty("baseUrl"));
        startPage.verifyPageHeader();
    }
}