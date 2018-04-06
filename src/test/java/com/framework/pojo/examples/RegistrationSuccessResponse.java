package com.framework.pojo.examples;


import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationSuccessResponse {

      private String SuccessCode;

      private String Message;
      
      @JsonIgnore
      public RegistrationSuccessResponse(String SuccessCode, String Message) {
        this.SuccessCode = SuccessCode;
        this.Message = Message;
      }
      
      public RegistrationSuccessResponse() {
      }

      public String getSuccessCode() {
        return SuccessCode;
      }

      public void setSuccessCode(String SuccessCode) {
        this.SuccessCode = SuccessCode;
      }

      public String getMessage() {
        return Message;
      }

      public void setMessage(String Message) {
        this.Message = Message;
      }
}
