package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
	
	static ExtentReports extentReport;
	
	public static ExtentReports getExtentReport() {
		
		String path = System.getProperty("user.dir") + "\\extent-reports\\extentreport.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Tutotials Ninja Automation results");
		reporter.config().setDocumentTitle("Test results");
		
		if(extentReport == null) {
			extentReport = new ExtentReports();
			extentReport.setSystemInfo("Operating system", "Windows 10");
			extentReport.setSystemInfo("Tested by", "SJ");
		}
		extentReport.attachReporter(reporter);
		
		return extentReport;
	}

}
