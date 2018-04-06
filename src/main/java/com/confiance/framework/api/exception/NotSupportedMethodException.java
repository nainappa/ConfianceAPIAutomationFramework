
package com.confiance.framework.api.exception;

/**
 * Author: Nainappa Illi Date: 02/17/2018
 */

public class NotSupportedMethodException extends RuntimeException {

  private static final long serialVersionUID = -3097015790839087481L;

  public NotSupportedMethodException(String message, Throwable e) {
    super(message, e);
  }

  public NotSupportedMethodException(String message) {
    super(message);
  }
}
