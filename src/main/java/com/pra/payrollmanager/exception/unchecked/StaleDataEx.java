package com.pra.payrollmanager.exception.unchecked;

public class StaleDataEx extends RuntimeException {

  /** */
  private static final long serialVersionUID = 1L;

  public StaleDataEx(String message) {
    super(message);
  }
  
  public StaleDataEx(String message, Throwable cause) {
      super(message, cause);
  }

  public StaleDataEx() {
    this("Data became stale, Please refresh the Data.");
  }
}
