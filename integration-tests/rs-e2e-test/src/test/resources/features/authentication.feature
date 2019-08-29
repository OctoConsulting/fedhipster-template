Feature: Authentication

Scenario: Successful login
	Given user is on the login page
	When user tries to login with user ID "admin" and password "admin"
	Then user is redirected to the home page with a logout link
	
Scenario: Login attempt with bad password
	Given user is on the login page
	When user tries to login with user ID "admin" and password "foo"
	Then user sees a failure login message