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

I spent roughly 2 hours on this task, and didn't manage to automate everything in the requirements - most notably testing of the mandatory fields. If I had more time, I would have ensured this was included in the test coverage

### Defects:
1. The 'Remember me' checkbox does not remember the user's login credentials after logging out
2. Calculation of interest is often incorrect
3. The 'Consent' checkbox is not mandatory

### Observations / content errors:
1. Image links are broken on the start page and login page
3. 'Credentials' is mis-spelled on the login page, and a full-stop is added to the end of the header, which isn't consistent with the styling of other pages
4. The 'RememberMe' checkbox on the login page should read 'Remember me'
5. The website is generally not user-friendly, as per the requirements - various elements are difficult to interact with, for example: the slider to select principal amount rather than a text-box and the non-standard dropdown menu for the interest rate, with difficult to click checkboxes (again, rather than a text-box)
