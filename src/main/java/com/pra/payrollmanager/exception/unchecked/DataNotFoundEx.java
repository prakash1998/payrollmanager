package com.pra.payrollmanager.exception.unchecked;

public class DataNotFoundEx extends RuntimeException {

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
