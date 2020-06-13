package com.pra.payrollmanager.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pra.payrollmanager.response.ResponseStatus;
import com.pra.payrollmanager.security.authentication.jwt.JwtTokenService;
import com.pra.payrollmanager.security.authentication.user.SecurityUser;
import com.pra.payrollmanager.security.authentication.user.SecurityUserService;
import com.pra.payrollmanager.security.authorization.AuthorityService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@Service
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private SecurityUserService userService;
	@Autowired
	private JwtTokenService jwtTokenUtil;
	@Autowired
	private AuthorityService authService;

	@Autowired
	private FilterResponseService filterResponse;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

//		if (!WebSecurityConfig.openEndpoints.contains(request.getRequestURI())) {

			final String requestTokenHeader = request.getHeader("Authorization");

			String userId = null;
			String jwtToken = null;

			if (requestTokenHeader == null) {
				filterResponse.writeToResponse(response, ResponseStatus.JWT_EXCEPTION, "No JWT Token provided");
				return;
			} else if (requestTokenHeader.startsWith("Bearer ")) {
				jwtToken = requestTokenHeader.substring(7);
				try {
					userId = jwtTokenUtil.getUserIdFromToken(jwtToken);
				} catch (ExpiredJwtException e) {
					filterResponse.writeToResponse(response, ResponseStatus.JWT_EXPIRED, e.getMessage(), e);
					return;
				} catch (JwtException e) {
					filterResponse.writeToResponse(response, ResponseStatus.JWT_EXCEPTION, e.getMessage(), e);
					return;
				}
			} else {
				filterResponse.writeToResponse(response, ResponseStatus.JWT_EXCEPTION,
						"JWT Token does not begin with Bearer String");
				return;
			}
			// Once we get the token validate it.
			if (userId != null && !authService.hasAuthentication()) {
				SecurityUser userDetails = userService.loadUserByUsername(userId);

				if (!userDetails.getLoggedIn()) {
					filterResponse.writeToResponse(response, ResponseStatus.ACCESS_DENIED,
							"User Session Expired, Please Login Again");
					return;
				} else {
					// if token is valid configure Spring Security to manually set
					// authentication
					if (jwtTokenUtil.validateToken(jwtToken)) {

						// UserCrendentials userCrendentials = UserCrendentials.builder()
						// .userName(userDetails.getUsername())
						// .companyName("tst")

						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, null);
						usernamePasswordAuthenticationToken
								.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						// After setting the Authentication in the context, we specify
						// that the current user is authenticated. So it passes the
						// Spring Security Configurations successfully.
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					}
				}
			}
//		}
		chain.doFilter(request, response);
	}
}