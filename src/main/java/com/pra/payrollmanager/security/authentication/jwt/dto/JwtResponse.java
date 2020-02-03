package com.pra.payrollmanager.security.authentication.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class JwtResponse {
	private String jwt;
	private long validity;
	private String refreshToken;
	private long refreshCoolDown;
}
