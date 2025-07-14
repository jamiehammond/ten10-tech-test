package framework.driver.producer.local;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import framework.driver.producer.BaseLocalDriver;
import framework.driver.producer.WebDriverProducer;

public class IELocalWebDriverProducer extends BaseLocalDriver implements WebDriverProducer {

    @Override
    public WebDriver produce() {
        if (browserVersion != null) {
            WebDriverManager.iedriver().browserVersion(browserVersion).setup();
        } else {
            WebDriverManager.iedriver().setup();
        }
        InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
        internetExplorerOptions.ignoreZoomSettings();
        internetExplorerOptions.requireWindowFocus();
        internetExplorerOptions.destructivelyEnsureCleanSession();

        return new InternetExplorerDriver(internetExplorerOptions);
    }
}
