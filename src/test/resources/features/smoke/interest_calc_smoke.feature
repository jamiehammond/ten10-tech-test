Feature: User journey smoke tests

  @SMK1 @smoke
  Scenario Outline: The user tests the 'remember me' functionality

    Given a user logs in to the interest calculator with 'remember me' set to <remember me>
    When the user logs out
    Then the login credentials will be <remembered>

    Examples:
      | remember me | remembered |
      | true        | true       |
      | false       | false      |

