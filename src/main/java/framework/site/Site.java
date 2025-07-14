package framework.site;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import framework.driver.DriverManager;
import framework.properties.CommonProperties;
import framework.properties.PropertyLoader;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

@Slf4j
public class Site {

    private static final long DEFAULT_WAIT_TIMEOUT_SECONDS = 60;

    public static WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    public static String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    public static FluentWait<WebDriver> webDriverWait(long milliseconds) {
        return new WebDriverWait(getDriver(), Duration.ofMillis(milliseconds))
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class);
    }

    public static FluentWait<WebDriver> webDriverWait() {
        String timeoutProperty = PropertyLoader.getProperty(CommonProperties.SELENIUM_DRIVER_WAIT_TIMEOUT);
        return webDriverWait(DEFAULT_WAIT_TIMEOUT_SECONDS * 1000);
    }

    public static boolean elementIsDisplayed(WebElement element) {
        try {
            return webDriverWait(2_500).until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }

    public static void waitForElementToDisappear(WebElement element) {
        webDriverWait().until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitForElementToAppear(WebElement element) {
        webDriverWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElementToAppear(WebElement element, long timeoutMillis) {
        webDriverWait(timeoutMillis).until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForMultipleElementsToAppear(List<WebElement> element) {
        for (WebElement webElement : element) {
            webDriverWait().until(ExpectedConditions.visibilityOf(webElement));
        }
    }

    public static void waitForElementToContainText(WebElement element, String expectedText) {
        waitForElementToAppear(element);

        webDriverWait().until(ExpectedConditions.textToBePresentInElement(element, expectedText));

        log.debug("WebElement [{}] contained text: [{}], expected [{}]", element, element.getText(), expectedText);
    }

    public static void goToUrl(String webAddress) {
        getDriver().get(webAddress);
    }

    public static WebElement findElement(By by) {
        return getDriver().findElement(by);
    }

    public static List<WebElement> findElements(By by) {
        return getDriver().findElements(by);
    }

    public static void closeWindow() {
        getDriver().close();
    }

    public void navigateBack() {
        getDriver().navigate().back();
    }

    public void removeCookies() {
        getDriver().manage().deleteAllCookies();
        assertThat(getDriver().manage().getCookies(), empty());
    }

    public void clickOnElement(WebElement webElement) {
        try {
            webDriverWait().until(ExpectedConditions.elementToBeClickable(webElement));
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", webElement);
            webElement.click();
        } catch (Exception ex) {
            throw new NoSuchElementException("Cannot find or click on WebElement", ex);
        }
    }

    public void enterTextIntoTheField(WebElement webElement, String value) {
        webElement.sendKeys(value);
    }

    public void replaceTextInField(WebElement webElement, String value) {
        clearTextField(webElement);
        enterTextIntoTheField(webElement, value);
    }

    public void clearTextField(WebElement webElement) {
        webElement.clear();
    }

    public void pageRefresh() {
        getDriver().navigate().refresh();
    }

    public void verifyPageHeader(String expectedPageHeader, WebElement pageHeaderElement) throws Exception {
        verifyTextInElement(expectedPageHeader, pageHeaderElement);
        log.info("Loaded page: {}", expectedPageHeader);
    }

    public void verifyTextInElement(String text, WebElement webElement) {
        waitForElementToContainText(webElement, text);
    }

    public void switchFocusToNewTab() {
        webDriverWait().until(numberOfWindowsToBe(2));
        ArrayList<String> windowHandles = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(windowHandles.get(1));
    }

    public void switchFocusToPreviousTab() {
        webDriverWait().until(numberOfWindowsToBe(1));
        ArrayList<String> windowHandles = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(windowHandles.get(0));
    }

    public void closeCurrentTab() {
        getDriver().close();
        switchFocusToPreviousTab();
    }

    public void openNewTab() {
        getDriver().switchTo().newWindow(WindowType.TAB);
    }

}