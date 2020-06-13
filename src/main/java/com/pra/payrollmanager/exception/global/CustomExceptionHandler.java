package com.pra.payrollmanager.exception.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.checked.CredentialNotMatchedEx;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.exception.unchecked.StaleDataEx;
import com.pra.payrollmanager.exception.unchecked.UnAuthorizedEx;
import com.pra.payrollmanager.response.dto.Response;

@Order(1)
@ControllerAdvice
public class CustomExceptionHandler {
	
	@Autowired
	ExceptionInterceptor exceptionInterceptor;

	@ExceptionHandler(DataNotFoundEx.class)
	public final ResponseEntity<Object> handleDataNotFound(Exception ex, WebRequest request) {
		exceptionInterceptor.intercept(ex);
		return new ResponseEntity<>(
				Response.builder()
						.notFound()
						.addErrorMsg(ex.getMessage(), ex)
						.build(),
				new HttpHeaders(),
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(StaleDataEx.class)
	public final ResponseEntity<Object> handleStaleData(Exception ex, WebRequest request) {
		exceptionInterceptor.intercept(ex);
		return new ResponseEntity<>(
				Response.builder()
				.staleEntity()
				.addErrorMsg(ex.getMessage(), ex)
				.build(),
		new HttpHeaders(),
		HttpStatus.CONFLICT);
	}

	@ExceptionHandler(DuplicateDataEx.class)
	public final ResponseEntity<Object> handleDuplicateData(Exception ex, WebRequest request) {
		exceptionInterceptor.intercept(ex);
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
		exceptionInterceptor.intercept(ex);
		return new ResponseEntity<>(
				Response.builder()
						.wrongCredentials()
						.addErrorMsg(ex.getMessage(), ex)
						.build(),
				new HttpHeaders(),
				HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(UnAuthorizedEx.class)
	public final ResponseEntity<Object> handleUnAuthorized(Exception ex, WebRequest request) {
		exceptionInterceptor.intercept(ex);
		return new ResponseEntity<>(
				Response.builder()
						.unauthorized()
						.addErrorMsg(ex.getMessage(), ex)
						.build(),
				new HttpHeaders(),
				HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(AnyThrowable.class)
	public final ResponseEntity<Object> handleAnyThrowable(Exception ex, WebRequest request) {
		exceptionInterceptor.intercept(ex);
		return new ResponseEntity<>(
				Response.builder()
						.exception()
						.addErrorMsg(ex.getMessage(), ex)
						.build(),
				new HttpHeaders(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
