package com.pra.payrollmanager.exception.checked;

public class DataNotFoundEx extends Exception {

  /** */
  private static final long serialVersionUID = 1L;

  public DataNotFoundEx(String message) {
    super(message);
  }
  
  public DataNotFoundEx(String message, Throwable cause) {
      super(message, cause);
  }

  public DataNotFoundEx() {
    this("Data Not Found in DB");
  }
}
