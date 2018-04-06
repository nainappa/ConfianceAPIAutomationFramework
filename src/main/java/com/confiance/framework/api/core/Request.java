package com.confiance.framework.api.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jayway.restassured.http.ContentType;

/**
 * Author: Nainappa Illi 
 * Date: 02/17/2018
 */
public class Request {
  private static final String COOKIE = "Cookie";
  private EndPoint endpoint;
  private HashMap<String, Header> headers = new HashMap<String, Header>();
  private Body body;
  private Method method;

  public Request(Method method, EndPoint endPoint) {
    setMethod(method);
    setEndpoint(endPoint);
  }

  /**
   * get endpoint of this request
   *
   * @return endpoint
   */
  public EndPoint getEndpoint() {
    return endpoint;
  }

  /**
   * set the endpoint
   *
   * @param endpoint endpoint to be set
   */
  public void setEndpoint(EndPoint endpoint) {
    this.endpoint = endpoint;
  }

  /**
   * get all the headers for the
   *
   * @return map containing all the headers of this request
   */
  public HashMap<String, Header> getHeaders() {
    return headers;
  }

  /**
   * add a header to the request
   *
   * @param header head to be added
   * @return return this request it self
   */
  public Request addHeader(Header header) {
    if (header == null || header.getName() == null || header.getValue() == null) {
      return this;
    }

    getHeaders().put(header.getName(), header);
    return this;
  }

  public Request removeHeader(String headerName) {
    if (headerName == null)
      return this;

    getHeaders().remove(headerName);
    return this;
  }

  public Body getBody() {
    return this.body;
  }

  public Request setBody(Body body) {
    this.body = body;

    if (body == null) {
      return this;
    }

    for (Header h : body.getRequiredHeaders()) {
      this.addHeader(h);
    }

    return this;
  }

  public Request setMethod(Method method) {
    this.method = method;
    return this;
  }

  public Method getMethod() {
    return this.method;
  }

  /**
   * get all the cookies of this request
   *
   * @return list of cookies of the request
   */
  public List<Cookie> getCookies() {
    CookieHeader cookies = null;
    if (getHeaders().containsKey(COOKIE) && (getHeaders().get(COOKIE) instanceof CookieHeader)) {
      cookies = (CookieHeader) getHeaders().get(COOKIE);
    }

    if (cookies == null) {
      return new ArrayList<Cookie>();
    } else {
      return cookies.getCookies();
    }
  }

  /**
   * get the cookie with given name
   *
   * @param cookieName name of the cookie
   * @return cookie matching the name
   */
  public Cookie getCookie(String cookieName) {
    CookieHeader cookies = null;
    if (getHeaders().containsKey(COOKIE) && (getHeaders().get(COOKIE) instanceof CookieHeader)) {
      cookies = (CookieHeader) getHeaders().get(COOKIE);
    }

    if (cookies == null) {
      return null;
    } else {
      return cookies.getCookie(cookieName);
    }
  }

  /**
   * add a cookie to the request
   *
   * @param cookie cookie to be added
   * @return this request itself
   */
  public Request addCookie(Cookie cookie) {
    CookieHeader cookies;
    if (getHeaders().containsKey(COOKIE) && (getHeaders().get(COOKIE) instanceof CookieHeader)) {
      cookies = (CookieHeader) getHeaders().get(COOKIE);
    } else {
      cookies = new CookieHeader();
      cookies.setName(COOKIE);
    }

    cookies.addCookie(cookie);
    this.addHeader(cookies);
    return this;
  }

  /**
   * trigger the request
   *
   * @return response of the request
   * @throws Exception
   * @throws java.io.IOException
   * @throws java.security.KeyManagementException
   * @throws java.security.NoSuchAlgorithmException
   */
  public Responses doExecute() throws Exception {
    Responses res = RESTWrapper.doRequest(this.getHeaders(), ContentType.JSON,
        this.getEndpoint().getUrl(), this.getBody().getBodyString(), this.getMethod());
    res.setRequest(this);
    return res;
  }

  /**
   * print the request
   *
   * @return log string
   */
  public String toLog() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("| method: ").append(getMethod()).append("\n");
    stringBuilder.append("| endpoint: ").append(getEndpoint().getUrl()).append("\n");

    stringBuilder.append("| headers:\n");
    for (Header h : getHeaders().values()) {
      stringBuilder.append(String.format("| -- { %s : %s }\n", h.getName(), h.getValue()));
    }

    String bodyStr = getBody() == null ? "No body for this request" : getBody().getBodyString();
    // if (bodyStr != null) {
    // bodyStr = bodyStr.replace("<", "&lt").replace(">", "&gt");
    // }

    stringBuilder.append("| body:\n| -- ").append(bodyStr).append("\n");

    return stringBuilder.toString();
  }
}

