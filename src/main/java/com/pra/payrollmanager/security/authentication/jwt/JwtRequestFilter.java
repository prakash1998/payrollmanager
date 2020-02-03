package com.pra.payrollmanager.security.authentication.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pra.payrollmanager.dto.response.Response;
import com.pra.payrollmanager.dto.response.Response.ResponseBuilder;
import com.pra.payrollmanager.dto.response.ResponseStatus;
import com.pra.payrollmanager.security.authentication.dao.SecurityUser;
import com.pra.payrollmanager.security.authentication.service.SecurityUserService;

import io.jsonwebtoken.JwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private SecurityUserService userService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private ObjectMapper jsonMapper;

	private void writeExceptionToResponse(HttpServletResponse response, String msg, Exception e)
			throws ServletException, IOException {
		
		ResponseBuilder responseBuilder = Response.builder()
				.badRequest()
				.message(msg);
		
		if(e != null) {
			responseBuilder.addErrorMsg(e.getMessage(), e);
		}
		try {
			response.getWriter()
					.write(jsonMapper.writeValueAsString(responseBuilder.build()));
		} catch (JsonProcessingException e1) {
			throw new ServletException("Json writing exception", e1);
		}
		response.setStatus(ResponseStatus.JWT_EXCEPTION.statusCode());
	}

	private void writeExceptionToResponse(HttpServletResponse response, String msg)
			throws ServletException, IOException {
		writeExceptionToResponse(response, msg, null);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");

		String userName = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (JwtException e) {
				writeExceptionToResponse(response, e.getMessage(), e);
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
//			writeExceptionToResponse(response, "JWT Token does not begin with Bearer String");
		}
		// Once we get the token validate it.
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			SecurityUser userDetails = userService.loadUserByUsername(userName);

			if (!userDetails.isLoggedIn()) {
				writeExceptionToResponse(response, "User Session Expired, Please Login Again");
			} else
			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}
}