package com.pra.payrollmanager.exception.global;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.pra.payrollmanager.response.dto.Response;
import com.pra.payrollmanager.response.dto.Response.ResponseBuilder;

@Order(2)
@ControllerAdvice
public class OverridenExceptionHandler {

	@Autowired
	ExceptionInterceptor exceptionInterceptor;

	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> handleConstraintViolationEx(ConstraintViolationException ex,
			WebRequest request) {

		ResponseBuilder builder = Response.builder()
				.validationException()
				.message("Data Validation Error");

		Set<ConstraintViolation<?>> allErrors = ex.getConstraintViolations();

		for (ConstraintViolation<?> error : allErrors) {
			builder.addErrorMsg(String.format("%s : %s", ((PathImpl) error.getPropertyPath()).getLeafNode().getName(),
					error.getMessage()));
		}

		return new ResponseEntity<>(
				builder.build(),
				new HttpHeaders(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<Object> handleMethodArgValidationEx(MethodArgumentNotValidException ex,
			WebRequest request) {

		ResponseBuilder builder = Response.builder()
				.validationException()
				.message("Data Validation Error");
		List<FieldError> allErrors = ex.getBindingResult().getFieldErrors();
		for (FieldError error : allErrors) {
			builder.addErrorMsg(String.format("%s : %s", error.getField(), error.getDefaultMessage()));
		}

		return new ResponseEntity<>(
				builder.build(),
				new HttpHeaders(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public final ResponseEntity<Object> handleEntityNotFound(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		exceptionInterceptor.intercept(ex);

		String name = ex.getName();
		String type = ex.getRequiredType().getSimpleName();
		Object value = ex.getValue();
		String message = String.format("'%s' should be a valid '%s', but '%s' is invalid",
				name, type, value);
		return new ResponseEntity<>(
				Response.builder()
						.validationException()
						.message(message)
						.addErrorMsg(ex.getMessage(), ex)
						.build(),
				new HttpHeaders(),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public final ResponseEntity<Object> handleEntityNotFound(Exception ex, WebRequest request) {
		exceptionInterceptor.intercept(ex);
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
		exceptionInterceptor.intercept(ex);
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
		exceptionInterceptor.intercept(ex);
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
		exceptionInterceptor.intercept(ex);
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
		exceptionInterceptor.intercept(ex);
		return new ResponseEntity<>(
				Response.builder()
						.unauthorized()
						.addErrorMsg(ex.getMessage(), ex)
						.build(),
				new HttpHeaders(),
				HttpStatus.UNAUTHORIZED);
	}
}
