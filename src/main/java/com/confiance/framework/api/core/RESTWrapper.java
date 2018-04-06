
package com.confiance.framework.api.core;

import static com.jayway.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;

/**
 * Author: Nainappa Illi
 * Date: 02/17/2018
 */
public class RESTWrapper {
  private static final String SET_COOKIE = "Set-Cookie";

  /**
   * Wrapper for all restAssured calls with Header mapper, Content Type, End point, Request body as
   * String and Type of method request This is the common method for all the Restassured requests.
   *
   * @param Header header for request
   * @param ContentType Content type for the request
   * @param url End point of the request
   * @param strJsonBody Schema Registry route
   * @param requestMethodtype requestmethod type.
   */
  public static Responses doRequest(HashMap<String, Header> header, ContentType contentType,
    String strURL, String strJsonBody, Method requestMethodtype) {
    Map<String, String> headers = new HashMap<String, String>();
    Response rs = null;
    if (header != null) {
        for (Header h : header.values()) {
          headers.put(h.getName(), h.getValue());
        }
    }
    if (requestMethodtype.name().equals("GET")) {
      rs = given().relaxedHTTPSValidation().contentType(contentType).headers(headers).when()
          .get(strURL);
    } else if (requestMethodtype.name().equals("POST")) {
        rs = given().relaxedHTTPSValidation().contentType(contentType).headers(headers)
            .body(strJsonBody).when().post(strURL);
    } else if (requestMethodtype.name().equals("PUT")) {
      rs = given().relaxedHTTPSValidation().contentType(contentType).headers(headers)
          .body(strJsonBody).when().put(strURL);
    } else if (requestMethodtype.name().equals("DELETE")) {
      rs = given().relaxedHTTPSValidation().contentType(contentType).headers(headers)
          .body(strJsonBody).when().delete(strURL);
    } else if (requestMethodtype.name().equals("OPTIONS")) {
      rs = given().relaxedHTTPSValidation().contentType(contentType).headers(headers)
          .body(strJsonBody).when().options(strURL);
    } else
      throw new RuntimeException(
          "Invalid method type. Please mention the proper request method type.");
    return parseResponse(rs);
  }

  /**
   * This method is for parsing the response of the request.
   *
   * @param Response of the request
   */
  public static Responses parseResponse(Response rs) {
    HashMap<String, Header> resHeaders = new HashMap<String, Header>();
    Headers rsHeaders = rs.getHeaders();
    for (com.jayway.restassured.response.Header h : rsHeaders) {
      String headerName = h.getName();
      String headerValue = h.getValue();
      if (headerName.equalsIgnoreCase(SET_COOKIE)) {
        CookieHeader cookieHeader = (CookieHeader) resHeaders.get(SET_COOKIE);

        if (cookieHeader == null) {
          cookieHeader = new CookieHeader();
          cookieHeader.setName(headerName);
        }

        String[] cookieStrs = headerValue.split(";");
        for (String cookieStr : cookieStrs) {
          String[] cookieSepStr = cookieStr.split("=");
          String cookieName = cookieSepStr[0].trim();
          String cookieValue = null;

          if (cookieSepStr.length < 2) {
            cookieValue = "";
          } else {
            cookieValue = cookieSepStr[1].trim();
          }

          cookieHeader.addCookie(new Cookie(cookieName, cookieValue));
        }
        resHeaders.put(SET_COOKIE, cookieHeader);
        continue;
      }
      resHeaders.put(headerName, new Header(headerName, headerValue));
    }
    resHeaders.put("response-code",
        new Header("response-code", String.valueOf(rs.getStatusCode())));
    String responseBodyString = rs.prettyPrint();
    Body resBody = null;
    if (responseBodyString.startsWith("{")) {
      resBody = new JsonBody(responseBodyString);
    } else if (responseBodyString.startsWith("<")) {
      //resBody = new XmlBody(responseBodyString);
    } else {
      resBody = new Body();
      resBody.setBodyString(responseBodyString);
    }

    Responses response = new Responses(resHeaders, resBody);
    return response;
  }
}
