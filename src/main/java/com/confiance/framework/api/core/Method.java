
package com.confiance.framework.api.core;

/**
 * Author: Nainappa Illi 
 * Date: 02/17/2018
 */

public enum Method {
  OPTIONS, HEAD, GET, POST, PUT, DELETE, TRACE, CONNECT;

  public String toString() {
    switch (this) {
      case OPTIONS:
        return "OPTIONS";
      case CONNECT:
        return "CONNECT";
      case DELETE:
        return "DELETE";
      case GET:
        return "GET";
      case HEAD:
        return "HEAD";
      case POST:
        return "POST";
      case PUT:
        return "PUT";
      case TRACE:
        return "TRACE";
      default:
        return "NONE";
    }
  }

  public static Method toMethod(String method) {
    if (method == null) {
      return null;
    }

    if (method.equalsIgnoreCase("options")) {
      return Method.OPTIONS;
    } else if (method.equalsIgnoreCase("connect")) {
      return Method.CONNECT;
    } else if (method.equalsIgnoreCase("delete")) {
      return Method.DELETE;
    } else if (method.equalsIgnoreCase("get")) {
      return Method.GET;
    } else if (method.equalsIgnoreCase("head")) {
      return Method.HEAD;
    } else if (method.equalsIgnoreCase("post")) {
      return Method.POST;
    } else if (method.equalsIgnoreCase("put")) {
      return Method.PUT;
    } else if (method.equalsIgnoreCase("trace")) {
      return Method.TRACE;
    } else {
      return null;
    }
  }
}
