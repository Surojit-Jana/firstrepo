package listeners;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import resources.BaseClass;
import utilities.ExtentManager;

public class Listener extends BaseClass implements ITestListener {

	ExtentReports extentReport;
	ExtentTest extentTest;
	ThreadLocal<ExtentTest> extentTestThread = new ThreadLocal<>(); // make extent report thread-safe.
	// an instance/variable of ExtentTest will be returned that will be local to the
	// currently executing thread only.
	// using this variable, perform the operations of ExtentTest.

	@Override
	public void onTestStart(ITestResult result) {
		extentReport = ExtentManager.getExtentReport();
		extentTest = extentReport.createTest(result.getName());
		extentTestThread.set(extentTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// extentTest.log(Status.PASS, result.getName());
		extentTestThread.get().log(Status.PASS, result.getName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// extentTest.fail(result.getThrowable());
		String testName = result.getName();
		extentTestThread.get().fail(result.getThrowable());

		WebDriver driver = null;
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver")
					.get(result.getInstance());
			// driver = (WebDriver)result.getTestContext().getAttribute("driver");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String screenshotPath = takeScreenshot(testName, driver);
			extentTestThread.get().addScreenCaptureFromPath(screenshotPath, testName); 
			//attach screenshot to extent report from folder.
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@AfterSuite
	public void onFinish(ITestContext context) {
		extentReport.flush();
	}

}
