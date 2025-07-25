package framework.driver.producer.local;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;

import framework.driver.producer.BaseLocalDriver;
import framework.driver.producer.WebDriverProducer;

public class FirefoxLocalWebDriverProducer extends BaseLocalDriver implements WebDriverProducer {

    @Override
    public WebDriver produce() {
        if (browserVersion != null) {
            WebDriverManager.firefoxdriver().browserVersion(browserVersion).setup();
        } else {
            WebDriverManager.firefoxdriver().setup();
        }
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

        return new FirefoxDriver(firefoxOptions);
    }
}
