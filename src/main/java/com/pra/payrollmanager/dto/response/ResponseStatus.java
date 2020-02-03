package com.pra.payrollmanager.dto.response;

import org.springframework.http.HttpStatus;

public enum ResponseStatus {
	
	OK(HttpStatus.OK.value()), 
	BAD_REQUEST(HttpStatus.BAD_REQUEST.value()),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value()),
	VALIDATION_EXCEPTION(604),
	EXCEPTION(605),
	WRONG_CREDENTIALS(607),
	ACCESS_DENIED(608),
	NOT_FOUND(HttpStatus.NOT_FOUND.value()),
	DUPLICATE_ENTITY(609),
	JWT_EXCEPTION(555),
	ALERT(222);
	
	ResponseStatus(int code) {
		this.code = code;
	}
	
	private int code;
	
	public int statusCode() {
		return code;
	}

}
