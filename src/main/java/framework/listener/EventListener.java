package framework.listener;

import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class EventListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        log.debug("onTestStart!!!!" + result.getMethod().getMethodName() + ": " + Arrays.stream(result.getParameters()).map(Object::toString).collect(Collectors.joining()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.debug("onTestSuccess!!!!" + result.getMethod().getMethodName() + ": " + Arrays.stream(result.getParameters()).map(Object::toString).collect(Collectors.joining()));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.debug("onTestFailure!!!!" + result.getMethod().getMethodName() + ": " + Arrays.stream(result.getParameters()).map(Object::toString).collect(Collectors.joining()));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.debug("onTestSkipped!!!!" + result.getMethod().getMethodName() + ": " + Arrays.stream(result.getParameters()).map(Object::toString).collect(Collectors.joining()));
        log.warn("TEST SKIPPED!!!");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.debug("onTestFailedButWithinSuccessPercentage!!!!" + result.getMethod().getMethodName() + ": " + Arrays.stream(result.getParameters()).map(Object::toString).collect(Collectors.joining()));
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        log.debug("onStart!!!!" + iTestContext.getName());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        log.debug("onFinish!!!!" + iTestContext.getName());
    }
}