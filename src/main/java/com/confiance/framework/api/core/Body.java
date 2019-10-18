
package com.confiance.framework.api.core;

/**
 * Author: Nainappa Illi
 * Date: 02/17/2018
 */

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.confiance.framework.api.exception.NotSupportedMethodException;


public class Body {
  private String bodyString;

  public Body setBodyString(String bodyString) {
    this.bodyString = bodyString;
    return this;
  }

  /**
   * get the body string of body override this method when writing new Body type and body payload is
   * string
   *
   * @return string value of body
   */
  public String getBodyString() {
    return this.bodyString;
  }

  /**
   * get body as byte array override this method when writing new Body type and body payload is not
   * string
   *
   * @return byte array
   */
  public byte[] getBodyBytes() throws UnsupportedEncodingException {
    if (getBodyString() == null) {
      return "".getBytes();
    }

    return getBodyString().getBytes("UTF-8");
  }

  /**
   * use simple xpath to get the node value of xml or json
   *
   * @param jPath jpath of the node
   * @return value of the node
   * @throws Exception
   */
  public String get(String jPath) throws NoSuchFieldException {
    throw new NotSupportedMethodException(
        "get method is not supported in " + this.getClass().getSimpleName());
  }

  public <T> T getObject(String jPath, Class<T> clazz) throws NoSuchFieldException {
    throw new NotSupportedMethodException(
        "getObject(jpath, class) method is not supported in " + this.getClass().getSimpleName());
  }

  public <T> T getObject(Class<T> clazz) throws NoSuchFieldException {
    throw new NotSupportedMethodException(
        "getObject(class) method is not supported in " + this.getClass().getSimpleName());
  }

  public List<Header> getRequiredHeaders() {
    return new ArrayList<Header>();
  }

}
