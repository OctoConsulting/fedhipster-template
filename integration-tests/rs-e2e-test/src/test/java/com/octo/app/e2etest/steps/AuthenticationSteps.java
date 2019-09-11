package com.octo.retrospider.e2etest.steps;

import com.octo.retrospider.e2etest.pages.HomePage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class AuthenticationSteps {
	private HomePage loginPage;
	
	@Given("^user is on the login page$")
	public void user_is_on_the_login_page() {
		loginPage.open();
	}

	@When("^user tries to login with user ID \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void user_tries_to_login_with_user_ID_and_password(String user, String pass) {
		loginPage.login(user, pass);
	}

	@Then("^user is redirected to the home page with a logout link$")
	public void user_is_redirected_to_the_home_page_with_a_logout_link() {
		assertThat(loginPage.verifySuccessLogin(),is(true));
	}

	@Then("^user sees a failure login message$")
	public void user_sees_a_failure_login_message() {
		assertThat(loginPage.getFailedAlertMessage(), containsString("Invalid username or password."));
	}
	
}
