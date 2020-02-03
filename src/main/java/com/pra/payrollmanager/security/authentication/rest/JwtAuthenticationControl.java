package com.pra.payrollmanager.security.authentication.rest;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pra.payrollmanager.dto.response.Response;
import com.pra.payrollmanager.exception.checked.CredentialNotMatchedEx;
import com.pra.payrollmanager.exception.util.CheckedException;
import com.pra.payrollmanager.exception.util.EntityType;
import com.pra.payrollmanager.security.authentication.dao.SecurityUser;
import com.pra.payrollmanager.security.authentication.jwt.JwtTokenUtil;
import com.pra.payrollmanager.security.authentication.jwt.dto.JwtRefreshRequest;
import com.pra.payrollmanager.security.authentication.jwt.dto.JwtRequest;
import com.pra.payrollmanager.security.authentication.jwt.dto.JwtResponse;
import com.pra.payrollmanager.security.authentication.service.SecurityUserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@RestController
@CrossOrigin
public class JwtAuthenticationControl {

	@Value("${jwt.token_validity}")
	private long JWT_TOKEN_VALIDITY;

	@Value("${jwt.refresh_cooldown}")
	private long JWT_REFRESH_COOLDOWN;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private SecurityUserService userService;

	@PostMapping(value = "/auth/token", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws CredentialNotMatchedEx {
		authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());
		final SecurityUser userDetails = userService.loadUserByUsername(authenticationRequest.getUserName());
		userService.login(userDetails);
		final String token = jwtTokenUtil.generateToken(userDetails);
		return Response.payload(JwtResponse.builder()
				.jwt(token)
				.validity(JWT_TOKEN_VALIDITY)
				.refreshToken(refreshToken(token, userDetails.getPassword()))
				.refreshCoolDown(JWT_REFRESH_COOLDOWN)
				.build());
	}

	@PostMapping(value = "/auth/token/refresh", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<JwtResponse> refreshAuthenticationToken(@RequestBody JwtRefreshRequest refreshRequest) {
		String expiredJwt = refreshRequest.getExpiredJwt();
		String userName;
		Date expireDate;
		try {
			try {
				Claims claims = jwtTokenUtil.getAllClaimsFromToken(expiredJwt);
				userName = claims.getSubject();
				expireDate = claims.getExpiration();
			} catch (JwtException e) {
				if (e.getCause() instanceof ExpiredJwtException) {
					ExpiredJwtException expiredException = (ExpiredJwtException) e.getCause();
					Claims claims = expiredException.getClaims();
					userName = claims.getSubject();
					expireDate = claims.getExpiration();
				} else {
					throw e;
				}
			}
		} catch (JwtException e) {
			return Response.builder()
					.badRequest()
					.message(e.getMessage())
					.addErrorMsg(e.getMessage(), e)
					.payload(JwtResponse.builder().build())
					.build();
		}

		long timeDifference = timeDifferenceInSecondsFromNow(expireDate);

		if (Math.abs(timeDifference) > JWT_REFRESH_COOLDOWN) {
			String message;
			if (timeDifference < 0) {
				message = "Please Use Existing Token before Refresh cooldown";
			} else {
				message = "Please Login again with credential, you are out of Refresh cooldown";
			}
			return Response.builder()
					.alert()
					.message(message)
					.payload(JwtResponse.builder().build())
					.build();
		}

		final SecurityUser userDetails = userService.loadUserByUsername(userName);

		if (!refreshToken(expiredJwt, userDetails.getPassword()).equals(refreshRequest.getRefreshToken())) {
			return Response.builder()
					.wrongCredentials()
					.message("Refresh Token is not Valid")
					.payload(JwtResponse.builder().build())
					.build();
		}

		final String token = jwtTokenUtil.generateToken(userDetails);
		return Response.payload(JwtResponse.builder()
				.jwt(token)
				.validity(JWT_TOKEN_VALIDITY)
				.refreshToken(refreshToken(token, userDetails.getPassword()))
				.refreshCoolDown(JWT_REFRESH_COOLDOWN)
				.build());
	}

	private void authenticate(String username, String password) throws CredentialNotMatchedEx {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			throw CheckedException.wrongCredentialEx(EntityType.SECURITY_USER, username);
		}
	}

	private String refreshToken(String jwt, String password) {
		return BCrypt.hashpw(jwt, password);
	}

	private long timeDifferenceInSecondsFromNow(Date date) {
		return (long) ((new Date().getTime() - date.getTime()) / 1000);
	}
}
