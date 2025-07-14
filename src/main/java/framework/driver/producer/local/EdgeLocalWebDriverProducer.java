package framework.driver.producer.local;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import framework.driver.producer.BaseLocalDriver;
import framework.driver.producer.WebDriverProducer;

public class EdgeLocalWebDriverProducer extends BaseLocalDriver implements WebDriverProducer {

    @Override
    public WebDriver produce() {
        if (browserVersion != null) {
            WebDriverManager.edgedriver().forceDownload().browserVersion(browserVersion).setup();
        } else {
            WebDriverManager.edgedriver().forceDownload().setup();
        }

        return new EdgeDriver();
    }
}
