package com.pra.payrollmanager.exception.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pra.payrollmanager.response.dto.Response;

@Order(3)
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler{

	@Autowired
	ExceptionInterceptor exceptionInterceptor;
	
	@Override
	protected ResponseEntity<Object> handleBindException(
			BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		exceptionInterceptor.intercept(ex);
		return new ResponseEntity<>(
				Response.builder()
						.badRequest()
						.addErrorMsg("Data Bind Error : "+ex.getMessage(), ex)
						.build(),
				new HttpHeaders(),
				HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		exceptionInterceptor.intercept(ex);
		return new ResponseEntity<>(
				Response.builder()
						.badRequest()
						.addErrorMsg("Data Serialization Error : "+ex.getMessage(), ex)
						.build(),
				new HttpHeaders(),
				HttpStatus.BAD_REQUEST);
	}
	
}
