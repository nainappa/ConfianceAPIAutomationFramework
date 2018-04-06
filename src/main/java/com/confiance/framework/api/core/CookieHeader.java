package com.confiance.framework.api.core;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Author: Nainappa Illi 
 * Date: 02/17/2018
 */

public class CookieHeader extends Header {
  private List<Cookie> cookies = new ArrayList<Cookie>();

  /**
   * add a cookie to this header
   *
   * @param cookie cookie to be added
   * @return this header itself
   */
  public CookieHeader addCookie(Cookie cookie) {
    cookies.add(cookie);
    return this;
  }

  /**
   * get cookie with given name
   *
   * @param cookieName name of the cookie
   * @return cookie
   */
  public Cookie getCookie(String cookieName) {
    for (Cookie c : cookies) {
      if (c.getName().equals(cookieName)) {
        return c;
      }
    }
    return null;
  }

  public List<Cookie> getCookies() {
    return cookies;
  }

  /**
   * remove the cookie with given name
   *
   * @param cookieName name of cookie to be removed
   * @return this header itself
   */
  public CookieHeader removeCookie(String cookieName) {
    for (Cookie c : cookies) {
      if (c.getName().equals(cookieName)) {
        cookies.remove(c);
      }
    }
    return this;
  }

  @Override
  public String getValue() {
    return StringUtils.join(cookies, ";");
  }

}
