package com.framework.apiautomation.test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.confiance.framework.api.core.Executor;
import com.confiance.framework.api.core.Responses;
import com.confiance.framework.api.report.ExtentReportUtil;
import com.framework.pojo.examples.RegistrationSuccessResponse;

import org.json.JSONObject;

public class SampleAPITests{
  @Test
  public void getCallExampleTest() throws Exception {
    Map<String, String> headers = new HashMap<String, String>();
    
    // Execute GET Call
    Responses responses = Executor.doGetCall("http://restapi.demoqa.com/utilities/weather/city/Hyderabad", headers);
    ExtentReportUtil.logger.info("The response code: "+responses.getResponseCode());
    ExtentReportUtil.logger.info("The response: "+responses.getBody().getBodyString());
    Assert.assertEquals(responses.getResponseCode(), "200");
    Thread.sleep(2000);
    // Validate the response body using org.json jar
    JSONObject jsonResponseObject = new JSONObject(responses.getBody().getBodyString());
    Assert.assertNotNull(jsonResponseObject.get("Temperature"));
    Assert.assertEquals(jsonResponseObject.get("City"), "Hyderabad");
    Assert.assertNotNull(jsonResponseObject.get("WeatherDescription"));
    Assert.assertNotNull(jsonResponseObject.get("WindSpeed"));
  }
  
  @Test
  public void postCallExampleTest() throws Exception {
    Map<String, String> headers = new HashMap<String, String>();
    
    // Construct body
    JSONObject reqBody = new JSONObject();
    reqBody.put("FirstName", "YourFirstNameHere");
    reqBody.put("LastName", "YourLastNameHere");     
    reqBody.put("UserName", "simpleuser001"+UUID.randomUUID());
    reqBody.put("Password", "password1");
    reqBody.put("Email",  UUID.randomUUID()+"@gmail.com");
    
    // Execute POST Call
    Responses responses = Executor.doPostCall("http://restapi.demoqa.com/customer/register", reqBody.toString(), headers);
    ExtentReportUtil.logger.info("The response code: "+responses.getResponseCode());
    Assert.assertEquals(responses.getResponseCode(), "201");
    ExtentReportUtil.logger.info("The response: "+responses.getBody().getBodyString());
    // Validate the response body using org.json jar
    JSONObject jsonResponseObject = new JSONObject(responses.getBody().getBodyString());
    Assert.assertNotNull(jsonResponseObject.get("SuccessCode"));
    Assert.assertEquals(jsonResponseObject.get("SuccessCode"), "OPERATION_SUCCESS");
    Assert.assertEquals(jsonResponseObject.get("Message"), "Operation completed successfully");
  }
  
  @Test
  public void postCallWithPOJOExampleTest() throws Exception {
    Map<String, String> headers = new HashMap<String, String>();
    
    // Construct body
    JSONObject reqBody = new JSONObject();
    reqBody.put("FirstName", "YourFirstNameHere");
    reqBody.put("LastName", "YourLastNameHere");     
    reqBody.put("UserName", "simpleuser001"+UUID.randomUUID());
    reqBody.put("Password", "password1");
    reqBody.put("Email",  UUID.randomUUID()+"@gmail.com");
    
    // Execute POST Call
    RegistrationSuccessResponse registrationSuccessResponse = Executor.doPostCallWithPojo("http://restapi.demoqa.com/customer/register", reqBody.toString(), headers, RegistrationSuccessResponse.class);

    // Validate the response body using POJO class reference
    Assert.assertNotNull(registrationSuccessResponse.getSuccessCode());
    Assert.assertEquals(registrationSuccessResponse.getSuccessCode(), "OPERATION_SUCCESS");
    Assert.assertEquals(registrationSuccessResponse.getMessage(), "Operation completed successfully");
  }

}
