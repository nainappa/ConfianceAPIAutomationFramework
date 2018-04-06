package com.confiance.framework.api.core;

/**
 * Author: Nainappa Illi 
 * Date: 02/17/2018
 */
public class Header {
  private String name;
  private String value;

  public Header() {
    this(null, null);
  }

  public Header(String name, String value) {
    setName(name);
    setValue(value);
  }

  /**
   * method to get name of this header override this method if you create your own header type
   *
   * @return name value
   */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * method to get value of this header override this method if you create your own header type
   *
   * @return value
   */
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
