Feature: Interest calculator smoke tests

  @SMK1 @smoke
  Scenario Outline: The user attempts login with 'remember me' functionality

    Given a user logs in to the interest calculator with 'remember me' set to <remember me>
    When the user logs out
    Then the login credentials will be <remembered>

    Examples:
      | remember me | remembered |
      | true        | true       |
      | false       | false      |

  @SMK2 @smoke
  Scenario Outline: The user calculates interest with the interest calculator

    Given a user logs in to the interest calculator
    When the user enters the interest figures: <principal amount>, <interest rate>, <duration>, <consent>
    Then the displayed interest amounts will be correct to two decimal places

    Examples:
      | principal amount | interest rate | duration | consent |
      | 100              | 1             | yearly   | true    |
      | 15000            | 15            | daily    | true    |
      | 7500             | 7             | monthly  | true    |
      | 5000             | 10            | yearly   | true    |
      | 5000             | 10            | daily    | true    |
      | 5000             | 10            | monthly  | true    |