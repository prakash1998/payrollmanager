package com.pra.payrollmanager.security.authentication.jwt.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
// need default constructor for JSON Parsing
@NoArgsConstructor
@AllArgsConstructor
public class JwtRefreshRequest implements Serializable {
	private static final long serialVersionUID = 5926468583005150707L;
	@NotNull
	private String expiredJwt;
	@NotNull
	private String refreshToken;
}