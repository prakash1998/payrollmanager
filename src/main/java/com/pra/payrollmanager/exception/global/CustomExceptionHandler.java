package com.pra.payrollmanager.exception.global;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.pra.payrollmanager.exception.checked.CredentialNotMatchedEx;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.response.dto.Response;

@Order(1)
@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(DataNotFoundEx.class)
	public final ResponseEntity<Object> handleDataNotFound(Exception ex, WebRequest request) {
		return new ResponseEntity<>(
				Response.builder()
						.notFound()
						.addErrorMsg(ex.getMessage(), ex)
						.build(),
				new HttpHeaders(),
				HttpStatus.NOT_FOUND);
	}

	
	@ExceptionHandler(DuplicateDataEx.class)
	public final ResponseEntity<Object> handleDuplicateData(Exception ex, WebRequest request) {
		return new ResponseEntity<>(
				Response.builder()
				.duplicateEntity()
				.addErrorMsg(ex.getMessage(), ex)
				.build(),
		new HttpHeaders(),
		HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(CredentialNotMatchedEx.class)
	public final ResponseEntity<Object> handleWrongCredential(Exception ex, WebRequest request) {
		return new ResponseEntity<>(
				Response.builder()
						.wrongCredentials()
						.addErrorMsg(ex.getMessage(), ex)
						.build(),
				new HttpHeaders(),
				HttpStatus.UNAUTHORIZED);
	}
	
}
