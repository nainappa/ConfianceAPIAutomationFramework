package com.confiance.framework.api.core;

/**
 * Author: Nainappa Illi 
 * Date: 02/17/2018
 */
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.confiance.framework.api.utils.LoggerUtil;



public class EndPoint implements Cloneable {
  private String endpoint;
  private Properties urlParams = new Properties();
  private Properties urlInlineParams = new Properties();

  public EndPoint(String endpoint) {
    setEndpoint(endpoint);
  }

  public Properties getUrlInlineParams() {
    return urlInlineParams;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  /**
   * get url parameters of this endpoint
   *
   * @return properties
   */
  public Properties getUrlParams() {
    return urlParams;
  }

  /**
   * get url parameter for this endpoint
   *
   * @param name parameter name
   * @param value parameter value
   * @return this endpoint itself
   */
  public EndPoint addParam(String name, String value) {
    if (name == null || value == null) {
      return this;
    }

    Properties properties = getUrlParams();
    if (!properties.containsKey(name)) {
      properties.put(name, new ArrayList<QueryParameter>());
    }

    ((List<QueryParameter>) properties.get(name)).add(new QueryParameter(name, value));

    return this;
  }

  /**
   * add inline parameters for this endpoint
   *
   * @param name parameter name
   * @param value parameter value
   * @return this endpoint itself
   */
  public EndPoint addInlineParam(String name, String value) {
    if (name == null || value == null) {
      return this;
    }

    getUrlInlineParams().put(name, value);
    return this;
  }

  /**
   * get the full url of this endpoint with url parameters appended to the end
   *
   * @return final url
   */
  public String getUrl() {
    String result = getEndpoint();

    if (!getUrlInlineParams().isEmpty()) {
      Properties urlInlineProperties = getUrlInlineParams();

      for (Map.Entry<Object, Object> entry : urlInlineProperties.entrySet()) {
        result = result.replace("{%" + entry.getKey() + "%}", entry.getValue().toString());
      }
    }

    if (getUrlParams().isEmpty()) {
      return result;
    }

    List<String> params = new ArrayList<String>();
    Properties urlProperties = getUrlParams();

    for (Map.Entry<Object, Object> e : urlProperties.entrySet()) {
      List<QueryParameter> queryParameters = (List<QueryParameter>) e.getValue();
      for (QueryParameter parameter : queryParameters) {
        params.add(String.format("%s=%s", encode(parameter.getName()),
            encode(String.valueOf(parameter.getValue()))));
      }
    }

    String urlString = StringUtils.join(params, "&");
    return result + "?" + urlString;
  }

  @Override
  public Object clone() {
    String endp = new String(this.endpoint);
    Properties urlPs = cloneProperties(getUrlParams());
    Properties urlInlinePs = cloneProperties(getUrlInlineParams());

    EndPoint cloneEndpoint = new EndPoint(endp);
    cloneEndpoint.urlParams = urlPs;
    cloneEndpoint.urlInlineParams = urlInlinePs;
    return cloneEndpoint;
  }

  private Properties cloneProperties(Properties src) {
    Properties dst = new Properties();

    for (Map.Entry<Object, Object> e : src.entrySet()) {
      dst.put(e.getKey(), e.getValue());
    }
    return dst;
  }


  private String encode(String src) {
    try {
      return URLEncoder.encode(src, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      LoggerUtil.log(e.getMessage());
      return "";
    }
  }

  class QueryParameter {
    private String name;
    private String value;

    QueryParameter(String name, String value) {
      this.name = name;
      this.value = value;
    }

    String getName() {
      return this.name;
    }

    String getValue() {
      return this.value;
    }
  }
}
