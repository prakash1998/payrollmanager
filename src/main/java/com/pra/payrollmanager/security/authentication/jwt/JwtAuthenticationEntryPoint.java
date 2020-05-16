//package com.pra.payrollmanager.security.authentication.jwt;
//
//import java.io.IOException;
//import java.io.Serializable;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import com.pra.payrollmanager.response.ResponseStatus;
//
//@Component
//public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//	@Override
//	public void commence(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException authException) throws IOException {
//		if (response.getStatus() == ResponseStatus.JWT_EXCEPTION.statusCode()) {
//			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//			response.setStatus(HttpStatus.BAD_REQUEST.value());
//		} else {
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Required");
//		}
//	}
//}