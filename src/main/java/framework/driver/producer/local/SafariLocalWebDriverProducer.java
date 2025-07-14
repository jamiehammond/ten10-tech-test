package framework.driver.producer.local;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

import framework.driver.producer.WebDriverProducer;

public class SafariLocalWebDriverProducer implements WebDriverProducer {

    @Override
    public WebDriver produce() {
        return new SafariDriver();
    }
}