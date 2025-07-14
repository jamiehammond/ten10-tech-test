package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.Pickle;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import framework.driver.DriverManager;
import framework.listener.EventListener;

import java.util.Arrays;
import java.util.function.Predicate;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "step_definitions",
        tags = "@smoke",
        plugin = {"pretty", "html:target/cucumber-reports/cucumber-reports.html"})

@Listeners(EventListener.class)
public class RunSmokeTest extends AbstractTestNGCucumberTests {

    private static final Predicate<Pickle> isSerial = pickle -> pickle.getTags().contains("@Serial") || pickle.getTags().contains("@serial");

    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber parallel", description = "Runs parallel enabled scenarios", dataProvider = "parallelScenarios")
    public void runParallelScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        DriverManager.getDriver().manage().deleteAllCookies();
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
        DriverManager.quitDriver();
    }

    @Test(groups = "cucumber serial", description = "Runs serial scenarios", dataProvider = "serialScenarios")
    public void runSerialScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        DriverManager.getDriver().manage().deleteAllCookies();
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
        DriverManager.quitDriver();
    }

    @DataProvider(parallel = true)
    public Object[][] parallelScenarios() {
        if (testNGCucumberRunner == null) {
            return new Object[0][0];
        }
        return filter(testNGCucumberRunner.provideScenarios(), isSerial.negate());
    }

    @DataProvider
    public Object[][] serialScenarios() {
        if (testNGCucumberRunner == null) {
            return new Object[0][0];
        }
        return filter(testNGCucumberRunner.provideScenarios(), isSerial);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (testNGCucumberRunner == null) {
            return;
        }
        testNGCucumberRunner.finish();
    }

    private Object[][] filter(Object[][] scenarios, Predicate<Pickle> accept) {
        return Arrays.stream(scenarios).filter(objects -> {
            PickleWrapper candidate = (PickleWrapper) objects[0];
            return accept.test(candidate.getPickle());
        }).toArray(Object[][]::new);
    }
}