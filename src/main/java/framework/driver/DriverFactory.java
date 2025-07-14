package framework.driver;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import framework.driver.producer.WebDriverProducer;
import framework.driver.producer.local.ChromeLocalWebDriverProducer;
import framework.driver.producer.local.EdgeLocalWebDriverProducer;
import framework.driver.producer.local.FirefoxLocalWebDriverProducer;
import framework.driver.producer.local.IELocalWebDriverProducer;
import framework.driver.producer.local.SafariLocalWebDriverProducer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DriverFactory {

    private static final Map<BrowserType, WebDriverProducer> producers = new HashMap<>();

    static {
        producers.put(BrowserType.ChromeLocal, new ChromeLocalWebDriverProducer());
        producers.put(BrowserType.EdgeLocal, new EdgeLocalWebDriverProducer());
        producers.put(BrowserType.FirefoxLocal, new FirefoxLocalWebDriverProducer());
        producers.put(BrowserType.InternetExplorerLocal, new IELocalWebDriverProducer());
        producers.put(BrowserType.SafariLocal, new SafariLocalWebDriverProducer());
    }

    private final BrowserType browserType;

    DriverFactory(String browserTypeName) {
        browserType = BrowserType.valueOf(browserTypeName);
        log.info("Browser type: " + browserType + " has been set in the Driver Factory on thread " + Thread.currentThread().getName());
    }

    WebDriver createInstance() {
        return producers.get(browserType).produce();
    }
}
