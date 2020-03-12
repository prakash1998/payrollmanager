package com.pra.payrollmanager.security.authentication.jwt.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
// need default constructor for JSON Parsing
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {
	@NotNull
	@Pattern(regexp = "^[^-]*-[^-]*$", message = "User Id should have pattern 'companyId-userName'.")
	private String userId;
	@NotNull
	private String password;
}