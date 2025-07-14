# Ten10 Tech Test

This is a suite of automated tests for the Ten10 Tech Test web pages

## Pre-requisites

1. Maven is installed, and the environment variable set
2. Java 17 is installed and the environment variable set
3. A valid username & password are set in the properties file. This can be found under `/test/resources/local.properties`

## Running tests

Tests can be run with the commands:

`mvn clean test` - runs the entire test suite

`mvn clean test -Dcucumber.filter.tags=@SMK1` - runs a test with a specific tag

`mvn clean test -Dcucumber.filter.tags=@SMK1 and @SMK2` - runs multiple tests with specific tags

Different browsers can be specified by editing the `browser.type` property in `local.properties`. The supported browser types are:
`ChromeLocal`, `FirefoxLocal`, `EdgeLocal`, `IELocal` and `SafariLocal`.

## Reports

Cucumber reports are generated after executing tests, and can be found under `/target/cucumber-reports/cucumber-reports.html`

## Notes & Observations

I spent roughly 2 hours on this task, and didn't manage to automate everything in the requirements - most notably testing of the mandatory fields. If I had more time, I would have ensured this was included in the test coverage.
