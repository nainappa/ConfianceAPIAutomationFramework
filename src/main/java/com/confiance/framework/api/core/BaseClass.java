package com.confiance.framework.api.core;

import java.util.ArrayList;
import java.util.List;

import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;

import com.confiance.framework.api.report.ExtentReportUtil;

public class BaseClass {
  
  @BeforeMethod
  public void setup(ITestResult result) {
    List<String> listeners = new ArrayList<String>();
    listeners = result.getTestContext().getSuite().getXmlSuite().getListeners();
    if(listeners.contains("ExtentReportListener")){
      ExtentReportUtil.logger = ExtentReportUtil.extent.createTest(result.getMethod().getMethodName());
    }
  }
}
