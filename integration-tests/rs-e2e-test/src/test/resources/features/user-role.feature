Feature: User Roles

Scenario: Admin User Role
	Given user is logged in as "admin" with password "admin"
	Then user is presented with links to Gateway, Health, Metrics and API.