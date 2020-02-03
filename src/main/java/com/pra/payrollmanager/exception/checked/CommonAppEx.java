package com.pra.payrollmanager.exception.checked;

public class CommonAppEx extends Exception {

  /** */
  private static final long serialVersionUID = 1L;

  public CommonAppEx(String message) {
    super(message);
  }
  
  public CommonAppEx(String message, Throwable cause) {
      super(message, cause);
  }

  public CommonAppEx() {
    this("Common App Exception");
  }
}
