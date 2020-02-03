package com.pra.payrollmanager.exception.checked;

public class DuplicateDataEx extends Exception{

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
