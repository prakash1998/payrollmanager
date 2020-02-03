package com.pra.payrollmanager.exception.global;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.pra.payrollmanager.dto.response.Response;

@Order(2)
@ControllerAdvice
public class OverridenExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public final ResponseEntity<Object> handleEntityNotFound(Exception ex, WebRequest request) {
		return new ResponseEntity<>(
				Response.builder()
						.notFound()
						.message("Entity Not Found In DB")
						.addErrorMsg(ex.getMessage(), ex)
						.build(),
				new HttpHeaders(),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EntityExistsException.class)
	public final ResponseEntity<Object> handleEntityExistsException(Exception ex, WebRequest request) {
		return new ResponseEntity<>(
				Response.builder()
						.duplicateEntity()
						.message("Entity already exists In DB")
						.addErrorMsg(ex.getMessage(), ex)
						.build(),
				new HttpHeaders(),
				HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(DisabledException.class)
	public final ResponseEntity<Object> handleUserDisabled(Exception ex, WebRequest request) {
		return new ResponseEntity<>(
				Response.builder()
						.accessDenied()
						.addErrorMsg(ex.getMessage(), ex)
						.build(),
				new HttpHeaders(),
				HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(ServletException.class)
	public final ResponseEntity<Object> handleServletException(Exception ex, WebRequest request) {
		return new ResponseEntity<>(
				Response.builder()
						.exception()
						.addErrorMsg(ex.getMessage(), ex)
						.build(),
				new HttpHeaders(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(AuthorizationServiceException.class)
	public final ResponseEntity<Object> handleUnAuthorized(Exception ex, WebRequest request) {
		return new ResponseEntity<>(
				Response.builder()
						.unauthorized()
						.addErrorMsg(ex.getMessage(), ex)
						.build(),
				new HttpHeaders(),
				HttpStatus.UNAUTHORIZED);
	}
}
