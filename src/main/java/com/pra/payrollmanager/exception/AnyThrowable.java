package com.pra.payrollmanager.exception;

public class AnyThrowable extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2393858373904484112L;

	public AnyThrowable(Throwable cause) {
		super(cause);
	}

	public AnyThrowable(String message) {
		super(message);
	}

	public AnyThrowable(String message, Throwable cause) {
		super(message, cause);
	}

	public AnyThrowable() {
		this("thrown any other exception");
	}
}
