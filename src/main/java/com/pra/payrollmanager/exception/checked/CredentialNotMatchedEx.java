package com.pra.payrollmanager.exception.checked;

public class CredentialNotMatchedEx extends Exception{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public CredentialNotMatchedEx(String message) {
    super(message);
  }
  
  public CredentialNotMatchedEx(String message, Throwable cause) {
      super(message, cause);
  }

  public CredentialNotMatchedEx() {
	  this("Provided Wrong Credential");
  }
  
}
