package com.octo.retrospider.e2etest.pages;

import java.util.concurrent.TimeUnit;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;

public class HomePage extends PageObject {
	private int timeout = 2;
	
	@FindBy(className="usa-button")
	private WebElementFacade login;
	
	@FindBy(linkText="Logout")
	private WebElementFacade logout;
	
	@FindBy(id="username")
	private WebElementFacade usernameTextbox;
	
	@FindBy(id="password")
	private WebElementFacade passwordTextbox;
	
	@FindBy(id="kc-login")
	private WebElementFacade loginSubmitButton;
	
	@FindBy(className="kc-feedback-text")
	private WebElementFacade failedLoginAlert;
	
	@FindBy(linkText="Add Dossier")
	private WebElementFacade addDossierLink;
	
	@FindBy(linkText="Add Data Source")
	private WebElementFacade addDataSrcLink;
	
	@FindBy(linkText="Bulk Load Data")
	private WebElementFacade bulkDataLoadLink;
	
	public void login(String user, String pass) {
		login.click();
		usernameTextbox.withTimeoutOf(timeout,TimeUnit.SECONDS).waitUntilEnabled().type(user);
		passwordTextbox.type(pass);
		loginSubmitButton.click();
	}
	
	public boolean verifySuccessLogin() {
		return logout.withTimeoutOf(timeout, TimeUnit.SECONDS).waitUntilVisible().isEnabled();
	}
	
	public String getFailedAlertMessage() {
		return failedLoginAlert.waitUntilPresent().getText();
	}
	
	public boolean verifyAdminFeatures() {
		return (addDossierLink.withTimeoutOf(timeout,TimeUnit.SECONDS).waitUntilEnabled().isEnabled() && 
				addDataSrcLink.withTimeoutOf(timeout,TimeUnit.SECONDS).waitUntilEnabled().isEnabled() && 
				bulkDataLoadLink.withTimeoutOf(timeout,TimeUnit.SECONDS).waitUntilEnabled().isEnabled());
	}

}
