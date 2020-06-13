package com.pra.payrollmanager.response;

import org.springframework.http.HttpStatus;

public enum ResponseStatus {
	
	OK(HttpStatus.OK.value()), 
	BAD_REQUEST(HttpStatus.BAD_REQUEST.value()),
	ALERT(222),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value()),
	JWT_EXPIRED(554),
	JWT_EXCEPTION(555),
	VALIDATION_ERROR(604),
	EXCEPTION(605),
	WRONG_CREDENTIALS(607),
	ACCESS_DENIED(608),
	NOT_FOUND(HttpStatus.NOT_FOUND.value()),
	DUPLICATE_ENTITY(609),
	CONFLICT(HttpStatus.CONFLICT.value());
	
	ResponseStatus(int code) {
		this.code = code;
	}
	
	private int code;
	
	public int statusCode() {
		return code;
	}

}
