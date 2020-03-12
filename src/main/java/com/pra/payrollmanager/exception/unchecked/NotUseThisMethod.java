package com.pra.payrollmanager.exception.unchecked;

public class NotUseThisMethod extends UnsupportedOperationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotUseThisMethod(String message) {
		super(message);
	}

	public NotUseThisMethod(String message, Throwable cause) {
		super(message, cause);
	}

	public NotUseThisMethod() {
		this("Caution: do not use this method. there may be alternate for doing Task.");
	}

}
