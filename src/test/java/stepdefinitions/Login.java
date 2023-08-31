package stepdefinitions;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageobjects.AccountPage;
import pageobjects.LandingPage;
import pageobjects.LoginPage;
import resources.BaseClass;

public class Login extends BaseClass {

	public WebDriver driver;
	LoginPage loginPage;
	LandingPage landingPage;
	
	@Given("Open any browser")
	public void Open_any_browser() throws IOException {
		driver = initialiseDriver();
	}
	
	@And("Navigate to login page")
	public void Navigate_to_login_page() {
		driver.get(properties.getProperty("url"));
		landingPage = new LandingPage(driver);
		landingPage.myAccountDropdown().click();
		landingPage.loginOption().click();

	}
	
	@When("User enters username as {string} and password as {string} into the fields")
	public void user_enters_username_and_password_into_the_fields(String username, String password) {
		loginPage = new LoginPage(driver);
		loginPage.emailField().sendKeys(username);
		loginPage.passwordField().sendKeys(password);
	}
	
	@And("User clicks on the login button")
	public void User_clicks_on_the_login_button() {
		loginPage.loginButton().click();
	}
	
	@Then("Verify user login, it should depend on {string}")
	public void Verify_user_login_it_should_depend_on_status(String expectedResult) {
		String actualResult;
		AccountPage accountPage = new AccountPage(driver);
		
		try {
			accountPage.editAccountInformationOption().isDisplayed();
			actualResult = "success";
		} catch(Exception e) {
			actualResult = "failure";
		}
		
		Assert.assertEquals(actualResult, expectedResult);
		
	}
	
	@After
	public void closure() {
		driver.close();
	}

}
