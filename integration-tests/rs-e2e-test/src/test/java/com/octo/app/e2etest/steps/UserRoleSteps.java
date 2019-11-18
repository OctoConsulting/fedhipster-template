package com.octo.app.e2etest.steps;

import com.octo.app.e2etest.pages.HomePage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class UserRoleSteps {
	private HomePage homePage;
	
	@Given("^user is logged in as \"([^\"]*)\" with password \"([^\"]*)\"$")
	public void user_is_logged_in_as_with_password(String user, String pass) {
		homePage.open();
		homePage.login(user, pass);
	}


	@Then("^user is presented with links to Gateway, Health, Metrics and API.$")
	public void user_is_presented_with_links_to_add_dossier_data_source_and_load_buld_data() {
		assertThat(homePage.verifyAdminFeatures(),is(true));
	}

}
