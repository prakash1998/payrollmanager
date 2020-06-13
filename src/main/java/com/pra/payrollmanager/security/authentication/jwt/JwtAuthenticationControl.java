package com.pra.payrollmanager.security.authentication.jwt;

import java.time.Instant;
import java.util.Date;

import javax.validation.Valid;

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

import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.checked.CredentialNotMatchedEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.exception.util.CustomExceptions;
import com.pra.payrollmanager.response.dto.Response;
import com.pra.payrollmanager.security.authentication.company.SecurityCompanyService;
import com.pra.payrollmanager.security.authentication.jwt.dto.JwtRefreshRequest;
import com.pra.payrollmanager.security.authentication.jwt.dto.JwtRequest;
import com.pra.payrollmanager.security.authentication.jwt.dto.JwtResponse;
import com.pra.payrollmanager.security.authentication.user.SecurityUser;
import com.pra.payrollmanager.security.authentication.user.SecurityUserService;
import com.pra.payrollmanager.security.authorization.AuthorityService;
import com.pra.payrollmanager.user.root.company.CompanyDetailsDAO;
import com.pra.payrollmanager.user.root.company.CompanyDetailsDTO;
import com.pra.payrollmanager.user.root.company.CompanyDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@RestController
@CrossOrigin
public class JwtAuthenticationControl {

	@Value("${jwt.refresh_cooldown}")
	private long JWT_REFRESH_COOLDOWN;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AuthorityService authService;

	@Autowired
	private JwtTokenService jwtTokenService;

	@Autowired
	private SecurityUserService userService;

	@Autowired
	private CompanyDetailsService companyDetailService;

	@Autowired
	private SecurityCompanyService securityCompanyService;

	@PostMapping(value = "/auth/token", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<JwtResponse> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest)
			throws CredentialNotMatchedEx, DuplicateDataEx, AnyThrowable {
		if (authService.inGodMode()) {
			CompanyDetailsDTO godCompany = CompanyDetailsDTO.builder()
					.id("god")
					.superUserPassword("god")
					.build();
			authenticationRequest.setUserId(godCompany.getId() + "-" + CompanyDetailsDTO.SUPER_USER_NAME);
			authenticationRequest.setPassword(godCompany.getSuperUserPassword());
			if (!securityCompanyService.existsById(godCompany.getId())) {
				Instant now = Instant.now();
				godCompany.setCreatedBy("god");
				godCompany.setCreatedDate(now);
				godCompany.setModifier("god");
				godCompany.setModifiedDate(now);
//				securityCompanyService.createFrom(godCompany);
//				CompanyDetailsDAO godDAO = companyDetailService.toDAO(godCompany);
				companyDetailService.create(godCompany);
			}
		}
		authenticate(authenticationRequest.getUserId(), authenticationRequest.getPassword());
		final SecurityUser userDetails = userService.loadUserByUsername(authenticationRequest.getUserId());
		userService.login(authenticationRequest.getUserId());
		final String token = jwtTokenService.generateToken(userDetails);
		return Response.payload(JwtResponse.builder()
				.jwt(token)
				.refreshToken(refreshToken(token, userDetails.getPassword()))
				.refreshCoolDown(JWT_REFRESH_COOLDOWN)
				.build());
	}

	@PostMapping(value = "/auth/refresh-token", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<JwtResponse> refreshAuthenticationToken(@Valid @RequestBody JwtRefreshRequest refreshRequest) {
		String expiredJwt = refreshRequest.getExpiredJwt();
		String userId;
		Date expireDate;
		try {
			try {
				Claims claims = jwtTokenService.getAllClaimsFromToken(expiredJwt);
				userId = claims.getSubject();
				expireDate = claims.getExpiration();
			} catch (JwtException e) {
				if (e.getCause() instanceof ExpiredJwtException) {
					ExpiredJwtException expiredException = (ExpiredJwtException) e.getCause();
					Claims claims = expiredException.getClaims();
					userId = claims.getSubject();
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

		final SecurityUser userDetails = userService.loadUserByUsername(userId);

		if (!refreshToken(expiredJwt, userDetails.getPassword()).equals(refreshRequest.getRefreshToken())) {
			return Response.builder()
					.wrongCredentials()
					.message("Refresh Token is not Valid")
					.payload(JwtResponse.builder().build())
					.build();
		}

		final String token = jwtTokenService.generateToken(userDetails);
		return Response.payload(JwtResponse.builder()
				.jwt(token)
				.refreshToken(refreshToken(token, userDetails.getPassword()))
				.refreshCoolDown(JWT_REFRESH_COOLDOWN)
				.build());
	}

	private void authenticate(String username, String password) throws CredentialNotMatchedEx {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			throw CustomExceptions.wrongCredentialEx(CompanyEntityNames.SECURITY_USER, username);
		}
	}

	private String refreshToken(String jwt, String password) {
		return BCrypt.hashpw(jwt, password);
	}

	private long timeDifferenceInSecondsFromNow(Date date) {
		return (long) ((new Date().getTime() - date.getTime()) / 1000);
	}
}
