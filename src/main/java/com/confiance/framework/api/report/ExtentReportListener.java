package com.confiance.framework.api.report;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestNG;

import com.confiance.framework.api.exception.ReportConfigException;

public class ExtentReportListener implements ITestListener{
    private static String outdir;

    @SuppressWarnings("deprecation")
    @Override
    public void onStart(ITestContext context) {
      String strHostName = context.getCurrentXmlTest().getParameter("confiance.report.host");
      String strEnv = context.getCurrentXmlTest().getParameter("confiance.report.env");
      String archive_report =
          context.getCurrentXmlTest().getParameter("archive.confiance.report");
      if(archive_report==null){
        archive_report = "false";
      }
      Method f;
      outdir = "";
      if (archive_report.equalsIgnoreCase("yes") || archive_report.equalsIgnoreCase("true")) {
        String archivePath = "";
        try {
          f = TestNG.class.getMethod("setOutputDirectory", String.class);
          DateFormat dtYearFormat = new SimpleDateFormat("yyyy");
          DateFormat dtMonthFormat = new SimpleDateFormat("M");
          String strCurrYear = dtYearFormat.format(new Date());
          String strCurrMonth = dtMonthFormat.format(new Date());
          archivePath = System.getProperty("user.home") + File.separator + "TestNg_Metrics_Reports"
              + File.separator + File.separator + strCurrYear + File.separator
              + theMonth(Integer.parseInt(strCurrMonth)) + File.separator;
          archivePath +=
              new SimpleDateFormat("ddMMMyy_hhmmss").format(new Date()) + "_" + "TestExecution";
          try {
            if (!(new File(archivePath).mkdirs())) {
              throw new ReportConfigException("Failed to create the archive report directory");
            }
          } catch (Exception e) {
            throw new ReportConfigException(e.getMessage(), e);
          }
          outdir = archivePath;
          Object a[] = {outdir};
          f.invoke(TestNG.getDefault(), a);
        } catch (Exception e) {
          // e.printStackTrace();
          throw new ReportConfigException("Unable to set archive report directory", e);
        }
      } else {
        try {
          f = TestNG.class.getMethod("getOutputDirectory");
          Object a[] = {};
          outdir = (String) f.invoke(TestNG.getDefault(), a);
        } catch (Exception e) {
          // e.printStackTrace();
          throw new ReportConfigException("Unable to read report directory", e);
        }
      }
      ExtentReportUtil.intiateReport(outdir + "/STMExtentReport.html", strHostName, strEnv);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onFinish(ITestContext context) {
      Method f;
      try {
        f = TestNG.class.getMethod("getOutputDirectory");
        Object a[] = {};
        outdir = (String) f.invoke(TestNG.getDefault(), a);
        ExtentReportUtil.closeReport();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    @Override
    public void onTestStart(ITestResult result) {
      List<String> listeners = new ArrayList<String>();
      listeners = result.getTestContext().getSuite().getXmlSuite().getListeners();
      if(listeners.contains("com.confiance.framework.api.report.ExtentReportListener")){
        ExtentReportUtil.logger = ExtentReportUtil.extent.createTest(result.getMethod().getMethodName());
      }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
      ExtentReportUtil.logSuccess(result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
      ExtentReportUtil.logFail(result.getName(), result.getThrowable().toString());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
      ExtentReportUtil.logSkip(result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
      // not implemented
    }

    private static String theMonth(int month) {
      String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August",
          "September", "October", "November", "December"};
      return monthNames[month - 1];
    }

    public static String getOutdir() {
      return outdir;
    }
  }

