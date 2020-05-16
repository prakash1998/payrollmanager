package com.pra.payrollmanager.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pra.payrollmanager.response.ResponseStatus;
import com.pra.payrollmanager.security.authorization.AuthorityService;
import com.pra.payrollmanager.user.root.permissions.endpoint.EndpointPermission;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthorizationFilter extends OncePerRequestFilter {

	public static final Set<String> endpointsWithoutAuthorization = new HashSet<>(Arrays.asList(
			"/auth/logout"));

	public static String endpointId(String uri, String httpMethod) {
		return uri + "|" + httpMethod;
	}

	public static final Map<String, EndpointPermission> universalEndpointsMap = new HashMap<>();

	@Autowired
	AuthorityService authService;

	@Autowired
	FilterResponseService filterResponse;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		log.debug("Logging Request  {} : {}", request.getMethod(), request.getRequestURI());

		String endpointId = endpointId(request.getRequestURI(), request.getMethod());

		EndpointPermission permission = universalEndpointsMap.get(endpointId);

		if (permission != null) {
			try {
				authService.validateEndpointPermission(permission);
			} catch (AuthorizationServiceException e) {
				filterResponse.writeToResponse(response, ResponseStatus.UNAUTHORIZED, e.getMessage(), e);
				return;
			}
		}

		// call next filter in the filter chain
		chain.doFilter(request, response);

		// log.info("Logging Response :{}", response);
		//
		// log.info("Logging Response Type :{}", response.getContentType());
	}

	@Override
	public void destroy() {
		log.info("########## Closing Authorization filter ##########");
	}
}