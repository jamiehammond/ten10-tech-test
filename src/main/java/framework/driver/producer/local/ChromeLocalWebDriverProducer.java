package framework.driver.producer.local;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import framework.driver.producer.BaseLocalDriver;
import framework.driver.producer.WebDriverProducer;

public class ChromeLocalWebDriverProducer extends BaseLocalDriver implements WebDriverProducer {

    @Override
    public WebDriver produce() {
        if (browserVersion != null) {
            WebDriverManager.chromedriver().browserVersion(browserVersion).setup();
        } else {
            WebDriverManager.chromedriver().setup();
        }
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");

        return new ChromeDriver(chromeOptions);
    }
}
