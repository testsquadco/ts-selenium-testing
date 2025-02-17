Feature: TestSquad Homepage Verification

  Scenario: Verify TestSquad homepage title
    Given I am on the TestSquad homepage
    Then the page title should be "TestSquad - Quality Assurance Services" 