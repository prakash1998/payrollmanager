package com.pra.payrollmanager.exception.unchecked;

public class UnAuthorizedEx extends RuntimeException {

  /** */
  private static final long serialVersionUID = 1L;

  public UnAuthorizedEx(String message) {
    super(message);
  }
  
  public UnAuthorizedEx(String message, Throwable cause) {
      super(message, cause);
  }

  public UnAuthorizedEx() {
    this("Not Authorized");
  }
}
