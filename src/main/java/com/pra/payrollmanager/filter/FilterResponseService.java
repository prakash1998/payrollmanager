package com.pra.payrollmanager.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.response.ResponseStatus;
import com.pra.payrollmanager.response.dto.Response;
import com.pra.payrollmanager.response.dto.Response.ResponseBuilder;
import com.pra.payrollmanager.translation.JsonJacksonMapperService;

@Service
public class FilterResponseService {

	@Autowired
	JsonJacksonMapperService jsonMapperService;

	public void writeToResponse(HttpServletResponse response, ResponseStatus status, String specificMessage,
			Exception exception)
			throws IOException {

		ResponseBuilder responseBuilder = Response.builder()
				.status(status)
				.message(specificMessage);

		if (exception != null) {
			responseBuilder = responseBuilder.addErrorMsg(exception.getMessage(), exception);
		}

		response.getWriter()
				.write(jsonMapperService.objectToJson(responseBuilder.build()));

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.BAD_REQUEST.value());
	}

	public void writeToResponse(HttpServletResponse response, ResponseStatus status, String specificMessage)
			throws IOException {
		this.writeToResponse(response, status, specificMessage, null);
	}

}
