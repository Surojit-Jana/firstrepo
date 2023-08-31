package tests;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.AccountPage;
import pageobjects.LandingPage;
import pageobjects.LoginPage;
import resources.BaseClass;

public class LoginTest extends BaseClass {

	public WebDriver driver;
	Logger logger;

	@BeforeMethod
	public void setup(ITestContext context) throws IOException {
		logger = LogManager.getLogger(LoginTest.class);
		driver = initialiseDriver();
		//context.setAttribute("driver", driver);
		logger.debug("Browser got launched LoginTest");
		driver.get(properties.getProperty("url"));
		logger.debug("Navigated to landing page");
	}

	@Test(dataProvider = "getLoginData")
	public void login(String email, String password, String expectedResult) throws IOException {

		LandingPage landingPage = new LandingPage(driver);
		landingPage.myAccountDropdown().click();
		logger.debug("Clicked on My Account Dropdown");
		
		landingPage.loginOption().click();
		logger.debug("Clicked on login option");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.emailField().sendKeys(email);
		logger.debug("Entered email");
		loginPage.passwordField().sendKeys(password);
		logger.debug("Entered password");
		loginPage.loginButton().click();
		logger.debug("Clicked on Login button");

		AccountPage accountPage = new AccountPage(driver);
		String actualResult;
		try {
			accountPage.editAccountInformationOption().isDisplayed();
			logger.debug("User got logged in");
			actualResult = "Success";
		} catch (Exception e) {
			actualResult = "Failure";
			logger.error("User didn't login");
		}

		// in case of wrong data, failure will be expected and failure should be actual too.
		Assert.assertEquals(actualResult, expectedResult);
		logger.info("Login test got passed");
		
	}

	@DataProvider
	public Object[][] getLoginData() {

		Object[][] data = { { "arun.selenium@gmail.com", "Second@123", "Success" },
				//{ "dummytest@gmail.com", "1234", "Failure" } 
				};

		return data;
	}

	@AfterMethod
	public void closure() {
		driver.close();
		logger.debug("Driver got closed");
	}

}
