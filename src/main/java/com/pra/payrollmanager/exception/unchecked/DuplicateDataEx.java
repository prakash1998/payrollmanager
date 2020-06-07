package com.pra.payrollmanager.exception.unchecked;

public class DuplicateDataEx extends RuntimeException{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public DuplicateDataEx(String message) {
    super(message);
  }
  
  public DuplicateDataEx(String message, Throwable cause) {
      super(message, cause);
  }

  public DuplicateDataEx() {
	  this("Data Already Exists in DB");
  }
  
}
