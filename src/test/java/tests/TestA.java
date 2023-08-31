package tests;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import resources.BaseClass;

public class TestA extends BaseClass{
	
	public WebDriver driver;
	
	@Test
	public void testA() throws IOException, InterruptedException {
		driver = initialiseDriver();
		driver.get("https://www.google.com/");
		Logger logger = LogManager.getLogger(TestA.class);
		logger.debug("Browser got launched TestA");
		Assert.fail();
		//Thread.sleep(1000);
		driver.close();
	}

}
