
package com.confiance.framework.api.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Nainappa Illi 
 * Date: 02/17/2018
 */
public class Responses {
  private HashMap<String, Header> headers = new HashMap<String, Header>();
  private Body body;
  private long responseTime;
  private Request request;
  private static final String SET_COOKIE = "Set-Cookie";

  public Responses(HashMap<String, Header> headers, Body body) {
    if (headers != null) {
      this.headers = headers;
    }

    this.body = body;
  }

  public long getResponseTime() {
    return responseTime;
  }

  public void setResponseTime(long responseTime) {
    this.responseTime = responseTime;
  }


  /**
   * get cookie for given name
   *
   * @param cookieName name of the cookie
   * @return cookie object
   */
  public Cookie getCookie(String cookieName) {
    CookieHeader cookies = null;
    if (getHeaders().containsKey(SET_COOKIE)
        && (getHeaders().get(SET_COOKIE) instanceof CookieHeader)) {
      cookies = (CookieHeader) getHeaders().get(SET_COOKIE);
    }

    if (cookies == null) {
      return null;
    } else {
      return cookies.getCookie(cookieName);
    }
  }

  public Request getRequest() {
    return request;
  }


  public void setRequest(Request request) {
    this.request = request;
  }

  /**
   * get all the cookies
   *
   * @return list containing all cookies of this response
   */
  public List<Cookie> getCookies() {
    CookieHeader cookies = null;
    if (getHeaders().containsKey(SET_COOKIE)
        && (getHeaders().get(SET_COOKIE) instanceof CookieHeader)) {
      cookies = (CookieHeader) getHeaders().get(SET_COOKIE);
    }

    if (cookies == null) {
      return new ArrayList<Cookie>();
    } else {
      return cookies.getCookies();
    }
  }

  /**
   * get all the headers for this response
   *
   * @return map containing all the headers
   */
  public HashMap<String, Header> getHeaders() {
    return headers;
  }

  /**
   * get response body
   *
   * @return body for this response
   */
  public Body getBody() {
    return this.body;
  }

  /**
   * get head for the given name
   *
   * @param name name of the header
   * @return the expected header
   */
  public Header getHeader(String name) {
    return getHeaders().get(name);
  }

  /**
   * log the response
   *
   * @return reponse log string
   */
  public String toLog() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("| response time: ").append(getResponseTime()).append("ms\n");
    stringBuilder.append("| headers:\n");
    for (Header h : getHeaders().values()) {
      stringBuilder.append(String.format("| -- { %s : %s }\n", h.getName(), h.getValue()));
    }
    String bodyStr = getBody() == null ? "No body for this response" : getBody().getBodyString();
    stringBuilder.append("| body:\n| -- ").append(bodyStr).append("\n");

    return stringBuilder.toString();
  }

  public String getResponseCode() {
    for (Header h : getHeaders().values()) {
      if ("response-code".equalsIgnoreCase(h.getName())) {
        return h.getValue();
      }
    }
    return null;
  }
}
