package com.pra.payrollmanager.security.authentication.jwt;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.pra.payrollmanager.security.authentication.user.SecurityUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenService implements Serializable {
	private static final long serialVersionUID = -2550185165626007488L;

	@Value("${jwt.token_validity}")
	private long JWT_TOKEN_VALIDITY;
	@Value("${jwt.secret}")
	private String secret;

	// retrieve username from jwt token
	public String getUserIdFromToken(String token) throws JwtException {
		return getAllClaimsFromToken(token).getSubject();
	}

	public Date getExpirationDateFromToken(String token) throws JwtException {
		return getAllClaimsFromToken(token).getExpiration();
	}

	// for retrieveing any information from token we will need the secret key
	public Claims getAllClaimsFromToken(String token) throws JwtException {
		try {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (MalformedJwtException e) {
			throw new JwtException("Mulformed Jwt Exception", e);
		} catch (IllegalArgumentException e) {
			throw new JwtException("Unable to get JWT Token", e);
		} catch (ExpiredJwtException e) {
			throw new JwtException("JWT Token has been expired", e);
		}
	}

	// generate token for user
	public String generateToken(SecurityUser user) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, user.getUserId());
	}

	// while creating the token -
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact
	// Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	// compaction of the JWT to a URL-safe string
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	// validate token
	public Boolean validateToken(String token) {
		return getExpirationDateFromToken(token).after(Date.from(Instant.now()));
	}
}