package framework.driver;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import framework.properties.CommonProperties;
import framework.properties.PropertyLoader;

@Slf4j
public class DriverManager {

    private static final ThreadLocal<WebDriver> WEB_DRIVER = new ThreadLocal<>();
    private static final BrowserType DEFAULT_BROWSER = BrowserType.ChromeLocal;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (WEB_DRIVER.get() != null) {
                WEB_DRIVER.get().quit();
            }
        }));
    }

    public static WebDriver getDriver() {
        WebDriver driver = WEB_DRIVER.get();
        if (driver == null) {
            log.info("Driver needs to be created, creating one");
            driver = createDriver();
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = WEB_DRIVER.get();
        if (driver != null) {
            try {
                WEB_DRIVER.get().quit();
            } catch (Exception e) {
                log.warn("Couldn't quit one of the created drivers because: " + e);
            }
            WEB_DRIVER.remove();
        }
    }

    private static WebDriver createDriver() {
        String currentBrowser;
        currentBrowser = PropertyLoader.getProperty(CommonProperties.BROWSER_TYPE);
        if (currentBrowser == null) {
            String defaultBrowser = DEFAULT_BROWSER.toString();
            log.warn("No browser set, falling back to default of " + defaultBrowser);
            currentBrowser = defaultBrowser;
        }
        WebDriver driver = new DriverFactory(currentBrowser).createInstance();
        driver.manage().window().setPosition((new Point(0, 0)));
        driver.manage().window().maximize();
        WEB_DRIVER.set(driver);

        return driver;
    }
}
