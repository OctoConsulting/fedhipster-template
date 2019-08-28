Feature: User Roles

Scenario: Admin User Role
	Given user is logged in as "admin" with password "admin"
	Then user is presented with links to add dossier, data source, and load buld data