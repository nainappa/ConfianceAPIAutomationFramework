package com.confiance.framework.api.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ExtentReportUtil {
  public static ExtentHtmlReporter htmlReporter;
  public static ExtentReports extent;
  public static ExtentTest logger;
  
  public static void intiateReport(String reportPath, String strHost, String strEnv){
    htmlReporter = new ExtentHtmlReporter(reportPath);
    // Create an object of Extent Reports
    extent = new ExtentReports();
    extent.attachReporter(htmlReporter);
    strHost = (strHost!=null)? strHost:"Your Host Info Here";
    extent.setSystemInfo("Host Name", strHost);
    strEnv = (strEnv!=null)? strEnv:"Your Env Info Here";
    extent.setSystemInfo("Environment", strEnv);
    extent.setSystemInfo("User Name", System.getProperty("user.name"));
    htmlReporter.config().setDocumentTitle("Confiance Test Execution Report");
    // Name of the report
    htmlReporter.config().setReportName("Confiance Test Execution Report");
    // Dark Theme
    htmlReporter.config().setTheme(Theme.STANDARD);
  }
  
  public static void closeReport(){
    extent.flush();
  }
  
  public static void logFail(String strMenthodName, String strThrowable){
    logger.log(Status.FAIL,
        MarkupHelper.createLabel(strMenthodName + " - Test Case Failed", ExtentColor.RED));
    logger.log(Status.FAIL,
        MarkupHelper.createLabel(strThrowable + " - Test Case Failed", ExtentColor.RED));
  }
  
  public static void logSkip(String strMethodName){
    logger.log(Status.SKIP,
        MarkupHelper.createLabel(strMethodName + " - Test Case Skipped", ExtentColor.ORANGE));
  }
  
  public static void logSuccess(String strMethodName){
    logger.log(Status.PASS,
        MarkupHelper.createLabel(strMethodName + " Test Case PASSED", ExtentColor.GREEN));
  }
  
}
