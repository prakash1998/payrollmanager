package com.pra.payrollmanager.security.authentication.jwt.dto;

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
	private String userName;
	private String password;
}