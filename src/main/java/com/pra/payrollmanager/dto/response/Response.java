package com.pra.payrollmanager.dto.response;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@NoArgsConstructor
public class Response<T> {

	private ResponseStatus status;
	private int statusCode;
	private String message;
	private T payload;
	private List<ResponseError> errors;
	private PageMetadata pages;
	
	private Response(ResponseStatus status, String message,T payload, List<ResponseError> errors, PageMetadata pages) {
		super();
		this.status = status;
		this.statusCode = status.statusCode();
		this.message = message;
		this.payload = payload;
		this.errors = errors;
		this.pages = pages;
	}
	
	
	public static ResponseBuilder builder() {
		return new Response.ResponseBuilder();
	}
	
	@AllArgsConstructor
	public static class TypedResponseBuilder<T> {
		private T payload;
		private ResponseStatus status;
		private String message;
		private List<ResponseError> errors;
		private PageMetadata pages;
		
		public TypedResponseBuilder<T>  status(ResponseStatus status) {
			this.status = status;
			return this;
		}

		public TypedResponseBuilder<T>  message(String message) {
			this.message = message;
			return this;
		}
		
		public TypedResponseBuilder<T>  pageMetadata(PageMetadata pages) {
			this.pages = pages;
			return this;
		}
		
		public TypedResponseBuilder<T> payload(T payload){
			this.payload = payload;
			return this;
		}
		
		public TypedResponseBuilder<T> addErrorMsg(String errorMsg, Exception ex) {
			if(this.errors == null) {
				this.errors = new ArrayList<>();
			}
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			this.errors.add(ResponseError.builder()
//					.display(errorMsg)
					.message(errorMsg)
					.trace(sw.toString())
					.timestamp(LocalDateTime.now())
					.build());
			return this;
		}
		
		public Response<T> build() {
			if(status == null)
				status = ResponseStatus.OK;
			return new Response<>(status,message,payload,errors,pages);
		}
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ResponseBuilder {
		
		private ResponseStatus status;
		private List<ResponseError> errors;
		private String message;
		private PageMetadata pages;
		
		public ResponseBuilder status(ResponseStatus status) {
			this.status = status;
			return this;
		}

		public ResponseBuilder message(String message) {
			this.message = message;
			return this;
		}
		
		public ResponseBuilder pageMetadata(PageMetadata pages) {
			this.pages = pages;
			return this;
		}
		
		public <T> TypedResponseBuilder<T> payload(T payload){
			return new Response.TypedResponseBuilder<>(payload, status,message, errors, pages);
		}
		
		public ResponseBuilder addErrorMsg(String errorMsg, Exception ex) {
			if(this.errors == null) {
				this.errors = new ArrayList<>();
			}
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			this.errors.add(ResponseError.builder()
//					.display(errorMsg)
					.message(errorMsg)
					.trace(sw.toString())
					.timestamp(LocalDateTime.now())
					.build());
			return this;
		}

		public ResponseBuilder badRequest() {
			this.status = ResponseStatus.BAD_REQUEST;
			return this;
		}

		public ResponseBuilder ok() {
			this.status = ResponseStatus.OK;
			return this;
		}
		
		public ResponseBuilder alert() {
			this.status = ResponseStatus.ALERT;
			return this;
		}

		public ResponseBuilder unauthorized() {
			this.status = ResponseStatus.UNAUTHORIZED;
			return this;
		}

		public ResponseBuilder validationException() {
			this.status = ResponseStatus.VALIDATION_EXCEPTION;
			return this;
		}

		public ResponseBuilder wrongCredentials() {
			this.status = ResponseStatus.WRONG_CREDENTIALS;
			return this;
		}

		public ResponseBuilder accessDenied() {
			this.status = ResponseStatus.ACCESS_DENIED;
			return this;
		}

		public ResponseBuilder exception() {
			this.status = ResponseStatus.EXCEPTION;
			return this;
		}

		public ResponseBuilder notFound() {
			this.status = ResponseStatus.NOT_FOUND;
			return this;
		}

		public ResponseBuilder duplicateEntity() {
			this.status = ResponseStatus.DUPLICATE_ENTITY;
			return this;
		}
		
		public Response<Void> build() {
			if(status == null)
				status = ResponseStatus.OK;
			return new Response<>(status,message,null,errors,pages);
		}
	}

	@Getter
	@Accessors(chain = true)
	public static class PageMetadata {
		private int size;
		private long totalElements;
		private int totalPages;
		private int pageNumber;

		public PageMetadata(int size, long totalElements, int totalPages, int pageNumber) {
			this.size = size;
			this.totalElements = totalElements;
			this.totalPages = totalPages;
			this.pageNumber = pageNumber;
		}
	}
	
	
	public static Response<Void> ok(){
		return Response.builder().ok().build();
	}
	
	public static <T> Response<T> payload(T payload) {
		return Response.builder().ok().payload(payload).build();
	}

}
